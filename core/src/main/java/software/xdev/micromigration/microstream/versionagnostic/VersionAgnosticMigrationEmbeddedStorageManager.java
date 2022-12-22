package software.xdev.micromigration.microstream.versionagnostic;

import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.notification.ScriptExecutionNotificationWithoutScriptReference;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.version.Versioned;
import software.xdev.micromigration.version.VersionedRoot;
import software.xdev.micromigration.version.VersionedRootWithHistory;

import java.util.List;
import java.util.Objects;


/**
 * Wrapper class for the MicroStream {@code one.microstream.storage.embedded.types.EmbeddedStorageManager} interface.
 * <p>
 * Basically it intercepts storing the root object and places a {@link Versioned}
 * in front of it. This means the root object of the datastore is then versioned.<br>
 * Internally uses the {@link VersionAgnosticMigrationManager} to do the actual migration.
 *  <p>
 *  {@code VersionAgnostic} because it should be independent from the actual MicroStream implementation used.
 *
 * @param <T> class of itself to be able to return the actual class and not just a generic class
 *
 * @param <E> The actually used MicroStream EmbeddedStorageManager
 *
 * @author Johannes Rabauer
 *
 */
public abstract class VersionAgnosticMigrationEmbeddedStorageManager<T, E>
	implements AutoCloseable
{
	private final MicroMigrater migrater     ;
	private VersionedRootWithHistory versionRoot  ;

	private VersionAgnosticTunnelingEmbeddedStorageManager<E> tunnelingManager;

	/**
	 * @param tunnelingManager which will be used as the underlying storage manager.
	 * Almost all methods are only rerouted to this native manager.
	 * Only {@link #start()}, {@link #root()} and {@link #setRoot(Object)} are intercepted
	 * and a {@link Versioned} is placed between the requests.
	 * @param migrater which is used as source for the migration scripts
	 */
	public VersionAgnosticMigrationEmbeddedStorageManager(
		VersionAgnosticTunnelingEmbeddedStorageManager<E> tunnelingManager,
		MicroMigrater                                                    migrater
	)
	{
		this.tunnelingManager = Objects.requireNonNull(tunnelingManager);
		this.migrater = Objects.requireNonNull(migrater);
	}

	/**
	 * @return the used {@link VersionAgnosticTunnelingEmbeddedStorageManager}
	 */
	protected VersionAgnosticTunnelingEmbeddedStorageManager<E> getTunnelingManager()
	{
		return this.tunnelingManager;
	}

	/**
	 * Checks if the root object is of the instance of {@link Versioned}.
	 * If it is not, the root will be replaced with the versioned root and the actual root object
	 * will be put inside the versioned root.
	 * <p>
	 * After starting the storage manager, all the available update scripts are executed in order
	 * until the newest version of the datastore is reached.
	 * @return itself
	 */
	public T start()
	{
		this.tunnelingManager.start();
		if(this.tunnelingManager.root() instanceof VersionedRootWithHistory)
		{
			this.versionRoot = (VersionedRootWithHistory)this.tunnelingManager.root();
		}
		else
		{
			//Build VersionedRootWithHistory around actual root, set by user.
			this.versionRoot = new VersionedRootWithHistory(this.tunnelingManager.root());
			this.tunnelingManager.setRoot(versionRoot);
			this.tunnelingManager.storeRoot();
		}
		new VersionAgnosticMigrationManager(
			this.versionRoot,
			migrater,
			this
		)
		.migrate(this.versionRoot.getRoot());
		return (T)this;
	}

	/**
	 * @return current version that's managed
	 */
	public MigrationVersion getCurrentVersion()
	{
		return this.versionRoot.getVersion();
	}

	/**
	 * @return the actual root object
	 */
	public Object root() {
		return this.versionRoot.getRoot();
	}

	/**
	 * @return the actual root object
	 */
	public List<ScriptExecutionNotificationWithoutScriptReference> getMigrationHistory() {
		return this.versionRoot.getMigrationHistory();
	}

	/**
	 * Sets the actual root element (not the versioned root)
	 * @param newRoot to set
	 * @return the set object
	 */
	public Object setRoot(Object newRoot) {
		this.versionRoot.setRoot(newRoot);
		return newRoot;
	}

	/**
	 * Stores the {@link VersionedRoot} and the actual root object.
	 * @return what EmbeddedStorageManager#storeRoot returns
	 */
	public long storeRoot() {
		this.tunnelingManager.store(this.versionRoot);
		return this.tunnelingManager.store(this.versionRoot.getRoot());
	}

	/**
	 * Stores the objectToStore
	 * @param objectToStore which is stored
	 * @return what EmbeddedStorageManager#store returns
	 */
	public long store(Object objectToStore) {
		return this.tunnelingManager.store(objectToStore);
	}

	/**
	 * Shuts down the datastore.
	 * @return what EmbeddedStorageManager#storeRoot shutdown
	 */
	public boolean shutdown(){
		return this.tunnelingManager.shutdown();
	}

	/**
	 * Closes the datastore.
	 */
	@Override public void close()
	{
		this.tunnelingManager.close();
	}
}
