/*
 * Copyright © 2021 XDEV Software GmbH (https://xdev.software)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package software.xdev.micromigration.eclipse.store;

import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.function.Predicate;

import org.eclipse.serializer.afs.types.AFile;
import org.eclipse.serializer.collections.types.XGettingEnum;
import org.eclipse.serializer.persistence.binary.types.Binary;
import org.eclipse.serializer.persistence.types.PersistenceManager;
import org.eclipse.serializer.persistence.types.PersistenceRootsView;
import org.eclipse.serializer.persistence.types.PersistenceTypeDictionaryExporter;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.eclipse.store.storage.exceptions.StorageException;
import org.eclipse.store.storage.types.Database;
import org.eclipse.store.storage.types.StorageConfiguration;
import org.eclipse.store.storage.types.StorageConnection;
import org.eclipse.store.storage.types.StorageEntityCacheEvaluator;
import org.eclipse.store.storage.types.StorageEntityTypeExportFileProvider;
import org.eclipse.store.storage.types.StorageEntityTypeExportStatistics;
import org.eclipse.store.storage.types.StorageEntityTypeHandler;
import org.eclipse.store.storage.types.StorageLiveFileProvider;
import org.eclipse.store.storage.types.StorageRawFileStatistics;
import org.eclipse.store.storage.types.StorageTypeDictionary;

import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticTunnelingEmbeddedStorageManager;
import software.xdev.micromigration.version.Versioned;

/**
 * Wrapper class for the MicroStream {@code one.microstream.storage.embedded.types.EmbeddedStorageManager} interface.
 * <p>
 * It simply relays all the commands to the contained {@link EmbeddedStorageManager}.
 *
 * @see VersionAgnosticTunnelingEmbeddedStorageManager
 *
 * @author Johannes Rabauer
 * 
 */
public class TunnelingEmbeddedStorageManager
	implements
	EmbeddedStorageManager,
		VersionAgnosticTunnelingEmbeddedStorageManager<EmbeddedStorageManager>
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
		final EmbeddedStorageManager nativeManager
	)
	{
		Objects.requireNonNull(nativeManager);
		this.nativeManager = nativeManager;
	}

	////////////////////////////////////////////////////////////////
	// Simply forward all the methods
	////////////////////////////////////////////////////////////////

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#start}
	 */
	@Override
	public TunnelingEmbeddedStorageManager start()
	{
		this.nativeManager.start();
		return this;
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#root}
	 */
	@Override
	public Object root() {
		return this.nativeManager.root();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#setRoot(Object)}
	 */
	@Override
	public Object setRoot(final Object newRoot) {
		return this.nativeManager.setRoot(newRoot);
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#storeRoot()}
	 */
	@Override
	public long storeRoot() {
		return this.nativeManager.storeRoot();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#configuration()}
	 */
	@Override
	public StorageConfiguration configuration()
	{
		return this.nativeManager.configuration();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#typeDictionary()}
	 */
	@Override
	public StorageTypeDictionary typeDictionary()
	{
		return this.nativeManager.typeDictionary();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#shutdown()}
	 */
	@Override
	public boolean shutdown()
	{
		return this.nativeManager.shutdown();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#close()}
	 */
	@Override
	public void close() throws StorageException
	{
		this.nativeManager.close();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#createConnection()}
	 */
	@Override
	public StorageConnection createConnection()
	{
		return this.nativeManager.createConnection();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#viewRoots()}
	 */
	@Override
	public PersistenceRootsView viewRoots()
	{
		return this.nativeManager.viewRoots();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#database()}
	 */
	@Override
	public Database database()
	{
		return this.nativeManager.database();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#isAcceptingTasks()}
	 */
	@Override
	public boolean isAcceptingTasks()
	{
		return this.nativeManager.isAcceptingTasks();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#isRunning()}
	 */
	@Override
	public boolean isRunning()
	{
		return this.nativeManager.isRunning();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#isStartingUp()}
	 */
	@Override
	public boolean isStartingUp()
	{
		return this.nativeManager.isStartingUp();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#isShuttingDown()}
	 */
	@Override
	public boolean isShuttingDown()
	{
		return this.nativeManager.isShuttingDown();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#checkAcceptingTasks()}
	 */
	@Override
	public void checkAcceptingTasks()
	{
		this.nativeManager.checkAcceptingTasks();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#initializationTime()}
	 */
	@Override
	public long initializationTime()
	{
		return this.nativeManager.initializationTime();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#operationModeTime()}
	 */
	@Override
	public long operationModeTime()
	{
		return this.nativeManager.operationModeTime();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#isActive()}
	 */
	@Override
	public boolean isActive()
	{
		return this.nativeManager.isActive();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#issueGarbageCollection(long)}
	 */
	@Override
	public boolean issueGarbageCollection(final long nanoTimeBudget)
	{
		return this.nativeManager.issueGarbageCollection(nanoTimeBudget);
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#issueFileCheck(long)}
	 */
	@Override
	public boolean issueFileCheck(final long nanoTimeBudget)
	{
		return this.nativeManager.issueFileCheck(nanoTimeBudget);
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#issueCacheCheck(long, StorageEntityCacheEvaluator)}
	 */
	@Override
	public boolean issueCacheCheck(final long nanoTimeBudget, final StorageEntityCacheEvaluator entityEvaluator)
	{
		return this.nativeManager.issueCacheCheck(nanoTimeBudget, entityEvaluator);
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#createStorageStatistics}
	 */
	@Override
	public StorageRawFileStatistics createStorageStatistics()
	{
		return this.nativeManager.createStorageStatistics();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#exportChannels(StorageLiveFileProvider)}
	 */
	@Override
	public void exportChannels(final StorageLiveFileProvider fileProvider, final boolean performGarbageCollection)
	{
		this.nativeManager.exportChannels(fileProvider, performGarbageCollection);
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#exportTypes(StorageEntityTypeExportFileProvider)}
	 */
	@Override
	public StorageEntityTypeExportStatistics exportTypes(
		final StorageEntityTypeExportFileProvider exportFileProvider,
		final Predicate<? super StorageEntityTypeHandler> isExportType)
	{
		return this.nativeManager.exportTypes(exportFileProvider, isExportType);
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#importFiles(XGettingEnum)}
	 */
	@Override
	public void importFiles(final XGettingEnum<AFile> importFiles)
	{
		this.nativeManager.importFiles(importFiles);
	}
	
	@Override
	public void importData(final XGettingEnum<ByteBuffer> xGettingEnum)
	{
		this.nativeManager.importData(xGettingEnum);
	}
	
	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#persistenceManager()}
	 */
	@Override
	public PersistenceManager<Binary> persistenceManager()
	{
		return this.nativeManager.persistenceManager();
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#store(Object)}
	 */
	@Override
	public long store(final Object instance)
	{
		return EmbeddedStorageManager.super.store(instance);
	}

	@Override public EmbeddedStorageManager getNativeStorageManager()
	{
		return this.nativeManager;
	}

	/**
	 * Simply tunneling the call through to the {@link EmbeddedStorageManager} given through the constructor.
	 * Please see {@link EmbeddedStorageManager#issueFullBackup(StorageLiveFileProvider, PersistenceTypeDictionaryExporter)}
	 */
	@Override
	public void issueFullBackup(
		final StorageLiveFileProvider targetFileProvider,
		final PersistenceTypeDictionaryExporter typeDictionaryExporter) {
		this.nativeManager.issueFullBackup(targetFileProvider, typeDictionaryExporter);
	}
	
	@Override
	public void issueTransactionsLogCleanup()
	{
		this.nativeManager.issueTransactionsLogCleanup();
	}
}
