package de.johannes_rabauer.micromigration;

import java.util.function.Predicate;

import de.johannes_rabauer.micromigration.migrater.MicroMigrater;
import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;
import de.johannes_rabauer.micromigration.version.MicroStreamVersionedRoot;
import one.microstream.afs.AFile;
import one.microstream.collections.types.XGettingEnum;
import one.microstream.persistence.binary.types.Binary;
import one.microstream.persistence.types.PersistenceManager;
import one.microstream.persistence.types.PersistenceRootsView;
import one.microstream.reference.Reference;
import one.microstream.storage.types.Database;
import one.microstream.storage.types.EmbeddedStorage;
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
 * Basically it intercepts storing the root object and places a {@link MicroStreamVersionedRoot}
 * in front of it. This means the datastore is then versioned.
 * 
 * @author Johannes Rabauer
 * 
 */
public class MigrationEmbeddedStorageManager implements EmbeddedStorageManager  
{
	private final EmbeddedStorageManager   nativeManager;
	private final MicroMigrater            migrater     ;
	private       MicroStreamVersionedRoot versionRoot  ;
	
	public MigrationEmbeddedStorageManager(
		EmbeddedStorageManager nativeManager,
		MicroMigrater          migrater
	)
	{
		this.nativeManager = nativeManager;
		this.migrater      = migrater     ;
	}

	public MigrationEmbeddedStorageManager start() 
	{
		this.nativeManager.start();
		if(this.nativeManager.root() instanceof MicroStreamVersionedRoot)
		{
			this.versionRoot = (MicroStreamVersionedRoot)this.nativeManager.root();
		}
		else
		{
			//Build VersionedRoot around actual root, set by user.
			this.versionRoot = new MicroStreamVersionedRoot(this.nativeManager.root());
			nativeManager.setRoot(versionRoot);
		}
		// Execute Updates
		final MicroMigrationVersion versionAfterUpdate = migrater.migrateToNewest(
			this.versionRoot.getVersion(),
			this                         ,
			this.versionRoot.getRoot()
		);
		//Update stored version, if needed
		if(versionAfterUpdate != this.versionRoot.getVersion())
		{
			this.versionRoot.setVersion(versionAfterUpdate);
			nativeManager.storeRoot();
		}
		return this;
	}

	public Object root() {
		return this.versionRoot.getRoot();
	}

	public Object setRoot(Object newRoot) {
		this.versionRoot.setRoot(newRoot);
		return newRoot;
	}
	
	////////////////////////////////////////////////////////////////
	// Simply forward all the other methods
	////////////////////////////////////////////////////////////////
	
	public StorageConfiguration configuration() {
		return this.nativeManager.configuration();
	}

	public StorageTypeDictionary typeDictionary() {
		return this.nativeManager.typeDictionary();
	}

	public boolean shutdown() 
	{
		return this.nativeManager.shutdown();
	}

	public long storeRoot() {
		return this.nativeManager.storeRoot();
	}

	public StorageConnection createConnection() {
		return this.nativeManager.createConnection();
	}
	

	public PersistenceRootsView viewRoots() {
		return this.nativeManager.viewRoots();
	}

	@Deprecated
	public Reference<Object> defaultRoot() {
		return this.nativeManager.defaultRoot();
	}

	public Database database() {
		return this.nativeManager.database();
	}

	public boolean isAcceptingTasks() {
		return this.nativeManager.isAcceptingTasks();
	}

	public boolean isRunning() {
		return this.nativeManager.isRunning();
	}

	public boolean isStartingUp() {
		return this.nativeManager.isStartingUp();
	}

	public boolean isShuttingDown() {
		return this.nativeManager.isShuttingDown();
	}

	public void checkAcceptingTasks() {
		this.nativeManager.checkAcceptingTasks();
	}

	public long initializationTime() {
		return this.nativeManager.initializationTime();
	}

	public long operationModeTime() {
		return this.nativeManager.operationModeTime();
	}

	public boolean isActive() {
		return this.nativeManager.isActive();
	}

	public boolean issueGarbageCollection(long nanoTimeBudget) {
		return this.nativeManager.issueGarbageCollection(nanoTimeBudget);
	}

	public boolean issueFileCheck(long nanoTimeBudget) {
		return this.nativeManager.issueFileCheck(nanoTimeBudget);
	}

	public boolean issueCacheCheck(long nanoTimeBudget, StorageEntityCacheEvaluator entityEvaluator) {
		return this.nativeManager.issueCacheCheck(nanoTimeBudget, entityEvaluator);
	}

	public StorageRawFileStatistics createStorageStatistics() {
		return this.nativeManager.createStorageStatistics();
	}

	public void exportChannels(StorageLiveFileProvider fileProvider, boolean performGarbageCollection) {
		this.nativeManager.exportChannels(fileProvider, performGarbageCollection);
	}

	public StorageEntityTypeExportStatistics exportTypes(StorageEntityTypeExportFileProvider exportFileProvider,
			Predicate<? super StorageEntityTypeHandler> isExportType) {
		return this.nativeManager.exportTypes(exportFileProvider, isExportType);
	}

	public void importFiles(XGettingEnum<AFile> importFiles) {
		this.nativeManager.importFiles(importFiles);
	}

	public PersistenceManager<Binary> persistenceManager() {
		return this.nativeManager.persistenceManager();
	}

}
