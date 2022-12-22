package software.xdev.micromigration.microstream.versionagnostic;

import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.version.Versioned;
import software.xdev.micromigration.version.VersionedRoot;

import java.util.Objects;


/**
 * Wrapper class for the MicroStream {@link VersionAgnosticEmbeddedStorageManager} interface.
 * <p>
 * Basically it intercepts storing the root object and places a {@link Versioned}
 * in front of it. This means the root object of the datastore is then versioned.<br>
 * Internally uses the {@link software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticMigrationManager} to do the actual migration.
 *
 * @author Johannes Rabauer
 *
 */
public class VersionAgnosticMigrationEmbeddedStorageManager<SELF, TUNNELINGMANAGER>
	implements AutoCloseable
{
	private final MicroMigrater migrater     ;
	private VersionedRoot versionRoot  ;

	private VersionAgnosticTunnelingEmbeddedStorageManager<TUNNELINGMANAGER> tunnelingManager;

	/**
	 * @param tunnelingManager which will be used as the underlying storage manager.
	 * Almost all methods are only rerouted to this native manager.
	 * Only {@link #start()}, {@link #root()} and {@link #setRoot(Object)} are intercepted
	 * and a {@link Versioned} is placed between the requests.
	 * @param migrater which is used as source for the migration scripts
	 */
	public VersionAgnosticMigrationEmbeddedStorageManager(
		VersionAgnosticTunnelingEmbeddedStorageManager<TUNNELINGMANAGER> tunnelingManager,
		MicroMigrater                                                 migrater
	)
	{
		this.tunnelingManager = Objects.requireNonNull(tunnelingManager);
		this.migrater = Objects.requireNonNull(migrater);
	}

	protected VersionAgnosticTunnelingEmbeddedStorageManager<TUNNELINGMANAGER> getTunnelingManager()
	{
		return this.tunnelingManager;
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
	public SELF start()
	{
		this.tunnelingManager.start();
		if(this.tunnelingManager.root() instanceof VersionedRoot)
		{
			this.versionRoot = (VersionedRoot)this.tunnelingManager.root();
		}
		else
		{
			//Build VersionedRoot around actual root, set by user.
			this.versionRoot = new VersionedRoot(this.tunnelingManager.root());
			this.tunnelingManager.setRoot(versionRoot);
			this.tunnelingManager.storeRoot();
		}
		new VersionAgnosticMigrationManager(
			this.versionRoot,
			migrater,
			this.tunnelingManager
		)
		.migrate(this.versionRoot.getRoot());
		return (SELF)this;
	}

	/**
	 * @return current version that's managed
	 */
	public MigrationVersion getCurrentVersion()
	{
		return this.versionRoot.getVersion();
	}

	public Object root() {
		return this.versionRoot.getRoot();
	}

	public Object setRoot(Object newRoot) {
		this.versionRoot.setRoot(newRoot);
		return newRoot;
	}

	public long storeRoot() {
		this.tunnelingManager.store(this.versionRoot);
		return this.tunnelingManager.store(this.versionRoot.getRoot());
	}

	public long store(Object objectToStore) {
		return this.tunnelingManager.store(objectToStore);
	}

	@Override public void close()
	{
		this.tunnelingManager.close();
	}
}
