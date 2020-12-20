package de.johannes_rabauer.micromigration;

import java.util.Objects;
import java.util.function.Predicate;

import de.johannes_rabauer.micromigration.migrater.MicroMigrater;
import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;
import de.johannes_rabauer.micromigration.version.Versioned;
import de.johannes_rabauer.micromigration.version.VersionedRoot;
import one.microstream.afs.AFile;
import one.microstream.collections.types.XGettingEnum;
import one.microstream.persistence.binary.types.Binary;
import one.microstream.persistence.types.PersistenceManager;
import one.microstream.persistence.types.PersistenceRootsView;
import one.microstream.reference.Reference;
import one.microstream.storage.exceptions.StorageException;
import one.microstream.storage.types.Database;
import one.microstream.storage.types.EmbeddedStorageManager;
import one.microstream.storage.types.StorageConfiguration;
import one.microstream.storage.types.StorageConnection;
import one.microstream.storage.types.StorageEntityCacheEvaluator;
import one.microstream.storage.types.StorageEntityTypeExportFileProvider;
import one.microstream.storage.types.StorageEntityTypeExportStatistics;
import one.microstream.storage.types.StorageEntityTypeHandler;
import one.microstream.storage.types.StorageLiveFileProvider;
import one.microstream.storage.types.StorageRawFileStatistics;
import one.microstream.storage.types.StorageTypeDictionary;

/**
 * Wrapper class for the MicroStream {@link EmbeddedStorageManager} interface.
 * <p>
 * Basically it intercepts storing the root object and places a {@link Versioned}
 * in front of it. This means the datastore is then versioned.
 * 
 * @author Johannes Rabauer
 * 
 */
public class MigrationEmbeddedStorageManager implements EmbeddedStorageManager  
{
	private final EmbeddedStorageManager nativeManager;
	private final MicroMigrater          migrater     ;
	private       VersionedRoot          versionRoot  ;
	
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
		Objects.requireNonNull(nativeManager);
		Objects.requireNonNull(migrater);
		this.nativeManager = nativeManager;
		this.migrater      = migrater     ;
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
	
	public MicroMigrationVersion getCurrentVersion()
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
	
	////////////////////////////////////////////////////////////////
	// Simply forward all the other methods
	////////////////////////////////////////////////////////////////
	
	@Override
	public StorageConfiguration configuration() {
		return this.nativeManager.configuration();
	}

	@Override
	public StorageTypeDictionary typeDictionary() {
		return this.nativeManager.typeDictionary();
	}

	@Override
	public boolean shutdown() 
	{
		return this.nativeManager.shutdown();
	}

	@Override 
	public void close() throws StorageException 
	{
		this.nativeManager.close();
	}

	@Override
	public StorageConnection createConnection() {
		return this.nativeManager.createConnection();
	}
	

	@Override
	public PersistenceRootsView viewRoots() {
		return this.nativeManager.viewRoots();
	}

	@Override
	@Deprecated
	public Reference<Object> defaultRoot() {
		return this.nativeManager.defaultRoot();
	}

	@Override
	public Database database() {
		return this.nativeManager.database();
	}

	@Override
	public boolean isAcceptingTasks() {
		return this.nativeManager.isAcceptingTasks();
	}

	@Override
	public boolean isRunning() {
		return this.nativeManager.isRunning();
	}

	@Override
	public boolean isStartingUp() {
		return this.nativeManager.isStartingUp();
	}

	@Override
	public boolean isShuttingDown() {
		return this.nativeManager.isShuttingDown();
	}

	@Override
	public void checkAcceptingTasks() {
		this.nativeManager.checkAcceptingTasks();
	}

	@Override
	public long initializationTime() {
		return this.nativeManager.initializationTime();
	}

	@Override
	public long operationModeTime() {
		return this.nativeManager.operationModeTime();
	}

	@Override
	public boolean isActive() {
		return this.nativeManager.isActive();
	}

	@Override
	public boolean issueGarbageCollection(long nanoTimeBudget) {
		return this.nativeManager.issueGarbageCollection(nanoTimeBudget);
	}

	@Override
	public boolean issueFileCheck(long nanoTimeBudget) {
		return this.nativeManager.issueFileCheck(nanoTimeBudget);
	}

	@Override
	public boolean issueCacheCheck(long nanoTimeBudget, StorageEntityCacheEvaluator entityEvaluator) {
		return this.nativeManager.issueCacheCheck(nanoTimeBudget, entityEvaluator);
	}

	@Override
	public StorageRawFileStatistics createStorageStatistics() {
		return this.nativeManager.createStorageStatistics();
	}

	@Override
	public void exportChannels(StorageLiveFileProvider fileProvider, boolean performGarbageCollection) {
		this.nativeManager.exportChannels(fileProvider, performGarbageCollection);
	}

	@Override
	public StorageEntityTypeExportStatistics exportTypes(StorageEntityTypeExportFileProvider exportFileProvider,
			Predicate<? super StorageEntityTypeHandler> isExportType) {
		return this.nativeManager.exportTypes(exportFileProvider, isExportType);
	}

	@Override
	public void importFiles(XGettingEnum<AFile> importFiles) {
		this.nativeManager.importFiles(importFiles);
	}

	@Override
	public PersistenceManager<Binary> persistenceManager() {
		return this.nativeManager.persistenceManager();
	}

}
