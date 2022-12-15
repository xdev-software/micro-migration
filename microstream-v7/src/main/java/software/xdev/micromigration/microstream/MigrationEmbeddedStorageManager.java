package software.xdev.micromigration.microstream;

import java.util.Objects;

import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticEmbeddedStorageManager;
import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.version.Versioned;
import software.xdev.micromigration.version.VersionedRoot;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


/**
 * Wrapper class for the MicroStream {@link software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticEmbeddedStorageManager} interface.
 * <p>
 * Basically it intercepts storing the root object and places a {@link software.xdev.micromigration.version.Versioned}
 * in front of it. This means the root object of the datastore is then versioned.<br>
 * Internally uses the {@link software.xdev.micromigration.microstream.MigrationManager} to do the actual migration.
 * 
 * @author Johannes Rabauer
 * 
 */
public class MigrationEmbeddedStorageManager extends TunnelingEmbeddedStorageManager
{
	private final MicroMigrater migrater     ;
	private VersionedRoot versionRoot  ;
	
	/**
	 * @param nativeManager which will be used as the underlying storage manager. 
	 * Almost all methods are only rerouted to this native manager. 
	 * Only {@link #start()}, {@link #root()} and {@link #setRoot(Object)} are intercepted 
	 * and a {@link Versioned} is placed between the requests.
	 * @param migrater which is used as source for the migration scripts
	 */
	public MigrationEmbeddedStorageManager(
		EmbeddedStorageManager nativeManager,
		MicroMigrater          migrater
	)
	{
		super(nativeManager);
		Objects.requireNonNull(migrater);
		this.migrater = migrater;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Checks if the root object is of the instance of {@link Versioned}.
	 * If it is not, the root will be replaced with the versioned root and the actual root object
	 * will be put inside the versioned root.
	 * <p>
	 * After starting the storage manager, all the available update scripts are executed in order
	 * until the newest version of the datastore is reached.
	 */
	@Override
	public MigrationEmbeddedStorageManager start() 
	{
		this.nativeManager.start();
		if(this.nativeManager.root() instanceof VersionedRoot)
		{
			this.versionRoot = (VersionedRoot)this.nativeManager.root();
		}
		else
		{
			//Build VersionedRoot around actual root, set by user.
			this.versionRoot = new VersionedRoot(this.nativeManager.root());
			nativeManager.setRoot(versionRoot);
			nativeManager.storeRoot();
		}
		new MigrationManager(
			this.versionRoot, 
			migrater, 
			this
		)
		.migrate(this.versionRoot.getRoot());
		return this;
	}

	/**
	 * @return current version that's managed
	 */
	public MigrationVersion getCurrentVersion()
	{
		return this.versionRoot.getVersion();
	}

	@Override
	public Object root() {
		return this.versionRoot.getRoot();
	}

	@Override
	public Object setRoot(Object newRoot) {
		this.versionRoot.setRoot(newRoot);
		return newRoot;
	}

	@Override
	public long storeRoot() {
		return this.nativeManager.store(this.versionRoot);
	}
}
