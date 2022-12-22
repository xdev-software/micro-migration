package software.xdev.micromigration.microstream;

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
import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticMigrationEmbeddedStorageManager;
import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticMigrationManager;
import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.version.Versioned;

import java.util.function.Predicate;


/**
 * Wrapper class for the MicroStream {@link software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticEmbeddedStorageManager} interface.
 * <p>
 * Basically it intercepts storing the root object and places a {@link Versioned}
 * in front of it. This means the root object of the datastore is then versioned.<br>
 * Internally uses the {@link VersionAgnosticMigrationManager} to do the actual migration.
 *
 * @author Johannes Rabauer
 *
 */
public class MigrationEmbeddedStorageManager
	extends VersionAgnosticMigrationEmbeddedStorageManager<MigrationEmbeddedStorageManager, EmbeddedStorageManager>
	implements EmbeddedStorageManager
{
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
		super(nativeManager, migrater);
	}

	////////////////////////////////////////////////////////////////
	// Simply forward all the methods
	////////////////////////////////////////////////////////////////

	@Override
	public EmbeddedStorageManager startNative()
	{
		this.getTunnelingManager().start();
		return this;
	}

	@Override
	public Object root() {
		return this.getTunnelingManager().root();
	}

	@Override
	public Object setRoot(Object newRoot) {
		return this.getTunnelingManager().setRoot(newRoot);
	}

	@Override
	public long storeRoot() {
		return this.getTunnelingManager().storeRoot();
	}

	@Override
	public StorageConfiguration configuration()
	{
		return this.getTunnelingManager().configuration();
	}

	@Override
	public StorageTypeDictionary typeDictionary()
	{
		return this.getTunnelingManager().typeDictionary();
	}

	@Override
	public boolean shutdown()
	{
		return this.getTunnelingManager().shutdown();
	}

	@Override
	public void close() throws StorageException
	{
		this.getTunnelingManager().close();
	}

	@Override
	public StorageConnection createConnection()
	{
		return this.getTunnelingManager().createConnection();
	}


	@Override
	public PersistenceRootsView viewRoots()
	{
		return this.getTunnelingManager().viewRoots();
	}

	@Override
	@Deprecated
	public Reference<Object> defaultRoot()
	{
		return this.getTunnelingManager().defaultRoot();
	}

	@Override
	public Database database()
	{
		return this.getTunnelingManager().database();
	}

	@Override
	public boolean isAcceptingTasks()
	{
		return this.getTunnelingManager().isAcceptingTasks();
	}

	@Override
	public boolean isRunning()
	{
		return this.getTunnelingManager().isRunning();
	}

	@Override
	public boolean isStartingUp()
	{
		return this.getTunnelingManager().isStartingUp();
	}

	@Override
	public boolean isShuttingDown()
	{
		return this.getTunnelingManager().isShuttingDown();
	}

	@Override
	public void checkAcceptingTasks()
	{
		this.getTunnelingManager().checkAcceptingTasks();
	}

	@Override
	public long initializationTime()
	{
		return this.getTunnelingManager().initializationTime();
	}

	@Override
	public long operationModeTime()
	{
		return this.getTunnelingManager().operationModeTime();
	}

	@Override
	public boolean isActive()
	{
		return this.getTunnelingManager().isActive();
	}

	@Override
	public boolean issueGarbageCollection(long nanoTimeBudget)
	{
		return this.getTunnelingManager().issueGarbageCollection(nanoTimeBudget);
	}

	@Override
	public boolean issueFileCheck(long nanoTimeBudget)
	{
		return this.getTunnelingManager().issueFileCheck(nanoTimeBudget);
	}

	@Override
	public boolean issueCacheCheck(long nanoTimeBudget, StorageEntityCacheEvaluator entityEvaluator)
	{
		return this.getTunnelingManager().issueCacheCheck(nanoTimeBudget, entityEvaluator);
	}

	@Override
	public StorageRawFileStatistics createStorageStatistics()
	{
		return this.getTunnelingManager().createStorageStatistics();
	}

	@Override
	public void exportChannels(StorageLiveFileProvider fileProvider, boolean performGarbageCollection)
	{
		this.getTunnelingManager().exportChannels(fileProvider, performGarbageCollection);
	}

	@Override
	public StorageEntityTypeExportStatistics exportTypes(
		StorageEntityTypeExportFileProvider exportFileProvider,
		Predicate<? super StorageEntityTypeHandler> isExportType)
	{
		return this.getTunnelingManager().exportTypes(exportFileProvider, isExportType);
	}

	@Override
	public void importFiles(XGettingEnum<AFile> importFiles)
	{
		this.getTunnelingManager().importFiles(importFiles);
	}

	@Override
	public PersistenceManager<Binary> persistenceManager()
	{
		return this.getTunnelingManager().persistenceManager();
	}

	@Override
	public long store(Object instance)
	{
		return EmbeddedStorageManager.super.store(instance);
	}

	@Override public EmbeddedStorageManager getNativeStorageManager()
	{
		return this.getTunnelingManager();
	}

	@Override
	public void issueFullBackup(StorageLiveFileProvider targetFileProvider,
		PersistenceTypeDictionaryExporter typeDictionaryExporter) {
		this.getTunnelingManager().issueFullBackup(targetFileProvider, typeDictionaryExporter);
	}

}
