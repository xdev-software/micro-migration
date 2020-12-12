package de.johannes_rabauer.micromigration;

import java.util.function.Predicate;

import de.johannes_rabauer.micromigration.version.MicroStreamVersionedRoot;
import one.microstream.afs.AFile;
import one.microstream.collections.types.XGettingEnum;
import one.microstream.persistence.binary.types.Binary;
import one.microstream.persistence.types.PersistenceManager;
import one.microstream.persistence.types.PersistenceRootsView;
import one.microstream.reference.Reference;
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

public class MigrationEmbeddedStorageManager implements EmbeddedStorageManager  
{
	private final EmbeddedStorageManager nativeManager; 
	
	public MigrationEmbeddedStorageManager(EmbeddedStorageManager nativeManager)
	{
		this.nativeManager = nativeManager;
	}

	public EmbeddedStorageManager start() 
	{
		this.nativeManager.start();
		if(!(this.nativeManager.root() instanceof MicroStreamVersionedRoot))
		{
			//Build VersionedRoot around actual root, set by user.
			MicroStreamVersionedRoot versionRoot = new MicroStreamVersionedRoot(this.nativeManager.root());
			nativeManager.setRoot(versionRoot);
		}
		return this;
	}

	public Object root() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object setRoot(Object newRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	// Simply forward all the other methods
	
	public StorageConfiguration configuration() {
		// TODO Auto-generated method stub
		return null;
	}

	public StorageTypeDictionary typeDictionary() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean shutdown() {
		// TODO Auto-generated method stub
		return false;
	}

	public long storeRoot() {
		// TODO Auto-generated method stub
		return 0;
	}

	public StorageConnection createConnection() {
		// TODO Auto-generated method stub
		return null;
	}
	

	public PersistenceRootsView viewRoots() {
		// TODO Auto-generated method stub
		return null;
	}

	public Reference<Object> defaultRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	public Database database() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAcceptingTasks() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isStartingUp() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isShuttingDown() {
		// TODO Auto-generated method stub
		return false;
	}

	public void checkAcceptingTasks() {
		// TODO Auto-generated method stub
		
	}

	public long initializationTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long operationModeTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean issueGarbageCollection(long nanoTimeBudget) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean issueFileCheck(long nanoTimeBudget) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean issueCacheCheck(long nanoTimeBudget, StorageEntityCacheEvaluator entityEvaluator) {
		// TODO Auto-generated method stub
		return false;
	}

	public StorageRawFileStatistics createStorageStatistics() {
		// TODO Auto-generated method stub
		return null;
	}

	public void exportChannels(StorageLiveFileProvider fileProvider, boolean performGarbageCollection) {
		// TODO Auto-generated method stub
		
	}

	public StorageEntityTypeExportStatistics exportTypes(StorageEntityTypeExportFileProvider exportFileProvider,
			Predicate<? super StorageEntityTypeHandler> isExportType) {
		// TODO Auto-generated method stub
		return null;
	}

	public void importFiles(XGettingEnum<AFile> importFiles) {
		// TODO Auto-generated method stub
		
	}

	public PersistenceManager<Binary> persistenceManager() {
		// TODO Auto-generated method stub
		return null;
	}

}
