package software.xdev.micromigration.microstream;

import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticEmbeddedStorageManager;
import software.xdev.micromigration.version.Versioned;
import one.microstream.afs.types.AFile;
import one.microstream.collections.types.XGettingEnum;
import one.microstream.persistence.binary.types.Binary;
import one.microstream.persistence.types.PersistenceManager;
import one.microstream.persistence.types.PersistenceRootsView;
import one.microstream.persistence.types.PersistenceTypeDictionaryExporter;
import one.microstream.reference.Reference;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import one.microstream.storage.exceptions.StorageException;
import one.microstream.storage.types.Database;
import one.microstream.storage.types.StorageConfiguration;
import one.microstream.storage.types.StorageConnection;
import one.microstream.storage.types.StorageEntityCacheEvaluator;
import one.microstream.storage.types.StorageEntityTypeExportFileProvider;
import one.microstream.storage.types.StorageEntityTypeExportStatistics;
import one.microstream.storage.types.StorageEntityTypeHandler;
import one.microstream.storage.types.StorageLiveFileProvider;
import one.microstream.storage.types.StorageRawFileStatistics;
import one.microstream.storage.types.StorageTypeDictionary;

import java.util.Objects;
import java.util.function.Predicate;


/**
 * Wrapper class for the MicroStream {@link VersionAgnosticEmbeddedStorageManager} interface.
 * <p>
 * Basically it intercepts storing the root object and places a {@link Versioned}
 * in front of it. This means the root object of the datastore is then versioned.<br>
 * Internally uses the {@link MigrationManager} to do the actual migration.
 * 
 * @author Johannes Rabauer
 * 
 */
public class TunnelingEmbeddedStorageManager implements EmbeddedStorageManager, VersionAgnosticEmbeddedStorageManager<EmbeddedStorageManager>
{
	/**
	 * The underlying, actual MicroStream EmbeddedStorageManager
	 */
	protected final EmbeddedStorageManager nativeManager;

	/**
	 * @param nativeManager which will be used as the underlying storage manager.
	 * All methods are only rerouted to this native manager.
	 * Only {@link #start()}, {@link #root()} and {@link #setRoot(Object)} are intercepted
	 * and a {@link Versioned} is placed between the requests.
	 */
	public TunnelingEmbeddedStorageManager(
		EmbeddedStorageManager nativeManager
	)
	{
		Objects.requireNonNull(nativeManager);
		this.nativeManager = nativeManager;
	}

	////////////////////////////////////////////////////////////////
	// Simply forward all the methods
	////////////////////////////////////////////////////////////////

	@Override
	public TunnelingEmbeddedStorageManager start()
	{
		this.nativeManager.start();
		return this;
	}

	@Override
	public Object root() {
		return this.nativeManager.root();
	}

	@Override
	public Object setRoot(Object newRoot) {
		return this.nativeManager.setRoot(newRoot);
	}

	@Override
	public long storeRoot() {
		return this.nativeManager.storeRoot();
	}

	@Override
	public StorageConfiguration configuration()
	{
		return this.nativeManager.configuration();
	}

	@Override
	public StorageTypeDictionary typeDictionary()
	{
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
	public StorageConnection createConnection()
	{
		return this.nativeManager.createConnection();
	}


	@Override
	public PersistenceRootsView viewRoots()
	{
		return this.nativeManager.viewRoots();
	}

	@Override
	@Deprecated
	public Reference<Object> defaultRoot()
	{
		return this.nativeManager.defaultRoot();
	}

	@Override
	public Database database()
	{
		return this.nativeManager.database();
	}

	@Override
	public boolean isAcceptingTasks()
	{
		return this.nativeManager.isAcceptingTasks();
	}

	@Override
	public boolean isRunning()
	{
		return this.nativeManager.isRunning();
	}

	@Override
	public boolean isStartingUp()
	{
		return this.nativeManager.isStartingUp();
	}

	@Override
	public boolean isShuttingDown()
	{
		return this.nativeManager.isShuttingDown();
	}

	@Override
	public void checkAcceptingTasks()
	{
		this.nativeManager.checkAcceptingTasks();
	}

	@Override
	public long initializationTime()
	{
		return this.nativeManager.initializationTime();
	}

	@Override
	public long operationModeTime()
	{
		return this.nativeManager.operationModeTime();
	}

	@Override
	public boolean isActive()
	{
		return this.nativeManager.isActive();
	}

	@Override
	public boolean issueGarbageCollection(long nanoTimeBudget)
	{
		return this.nativeManager.issueGarbageCollection(nanoTimeBudget);
	}

	@Override
	public boolean issueFileCheck(long nanoTimeBudget)
	{
		return this.nativeManager.issueFileCheck(nanoTimeBudget);
	}

	@Override
	public boolean issueCacheCheck(long nanoTimeBudget, StorageEntityCacheEvaluator entityEvaluator)
	{
		return this.nativeManager.issueCacheCheck(nanoTimeBudget, entityEvaluator);
	}

	@Override
	public StorageRawFileStatistics createStorageStatistics()
	{
		return this.nativeManager.createStorageStatistics();
	}

	@Override
	public void exportChannels(StorageLiveFileProvider fileProvider, boolean performGarbageCollection)
	{
		this.nativeManager.exportChannels(fileProvider, performGarbageCollection);
	}

	@Override
	public StorageEntityTypeExportStatistics exportTypes(
		StorageEntityTypeExportFileProvider exportFileProvider,
		Predicate<? super StorageEntityTypeHandler> isExportType)
	{
		return this.nativeManager.exportTypes(exportFileProvider, isExportType);
	}

	@Override
	public void importFiles(XGettingEnum<AFile> importFiles)
	{
		this.nativeManager.importFiles(importFiles);
	}

	@Override
	public PersistenceManager<Binary> persistenceManager()
	{
		return this.nativeManager.persistenceManager();
	}

	@Override
	public long store(Object instance)
	{
		return EmbeddedStorageManager.super.store(instance);
	}

	@Override public EmbeddedStorageManager getNativeStorageManager()
	{
		return this.nativeManager;
	}

	@Override
	public void issueFullBackup(StorageLiveFileProvider targetFileProvider,
		PersistenceTypeDictionaryExporter typeDictionaryExporter) {
		this.nativeManager.issueFullBackup(targetFileProvider, typeDictionaryExporter);
	}
	
	}
