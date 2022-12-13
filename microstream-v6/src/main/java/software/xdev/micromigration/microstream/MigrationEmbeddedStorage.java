package software.xdev.micromigration.microstream;

import java.nio.file.Path;
import java.util.Objects;

import software.xdev.micromigration.migrater.MicroMigrater;
import one.microstream.afs.nio.types.NioFileSystem;
import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageFoundation;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import one.microstream.storage.types.Storage;
import one.microstream.storage.types.StorageConfiguration;


/**
 * Provides static utility calls to create the {@link MigrationEmbeddedStorageManager} for
 * updateable datastores. Basically a wrapper for the utility class {@link EmbeddedStorage}.
 * 
 * @author Johannes Rabauer
 * 
 */
public class MigrationEmbeddedStorage
{
	/**
	 * Creates a {@link MigrationEmbeddedStorageManager} with the given {@link MicroMigrater}.
	 * Uses the MicroStream {@link EmbeddedStorageFoundation#New()} configuration for the actual
	 * {@link EmbeddedStorageManager}.
	 * <p>Warning "resource" is suppressed because it is used and closed in the {@link MigrationEmbeddedStorageManager}.
	 * 
	 * @param migrater which is used as source for the migration scripts
	 * @return the created storage manager with the given migrater
	 */
	@SuppressWarnings("resource")
	public static final MigrationEmbeddedStorageManager start(MicroMigrater migrater)
	{
		Objects.requireNonNull(migrater);
		return new MigrationEmbeddedStorageManager(
			createStorageManager(),
			migrater
		).start();
	}
	
	/**
	 * Creates a {@link MigrationEmbeddedStorageManager} with the given {@link MicroMigrater}.
	 * Uses the MicroStream {@link EmbeddedStorageFoundation#New()} configuration for the actual
	 * {@link EmbeddedStorageManager}.
	 * <p>Warning "resource" is suppressed because it is used and closed in the {@link MigrationEmbeddedStorageManager}.
	 * 
	 * @param storageDirectory is used as the base directory for the datastore
	 * @param migrater which is used as source for the migration scripts
	 * @return the created storage manager with the given migrater
	 */
	@SuppressWarnings("resource")
	public static final MigrationEmbeddedStorageManager start(
		Path          storageDirectory,
		MicroMigrater migrater
	)
	{
		Objects.requireNonNull(migrater);
		Objects.requireNonNull(storageDirectory);
		
		return new MigrationEmbeddedStorageManager(
			createStorageManager(storageDirectory),
			migrater
		).start();
	}
	
	private static EmbeddedStorageManager createStorageManager(Path storageDirectory)
	{
		NioFileSystem fileSystem = NioFileSystem.New();
		return EmbeddedStorageFoundation.New()
			.setConfiguration(
				StorageConfiguration.Builder()
					.setStorageFileProvider(
						Storage.FileProviderBuilder(fileSystem)
							.setDirectory(fileSystem.ensureDirectoryPath(storageDirectory.toAbsolutePath().toString()))
							.createFileProvider()
					)
					.createConfiguration()
			)
			.createEmbeddedStorageManager();	
	}
	
	private static EmbeddedStorageManager createStorageManager()
	{
		return EmbeddedStorageFoundation.New()
			.setConfiguration(
				StorageConfiguration.Builder().createConfiguration()
			)
			.createEmbeddedStorageManager();	
	}
}
