package de.johannes_rabauer.micromigration;

import java.nio.file.Path;
import java.util.Objects;

import de.johannes_rabauer.micromigration.migrater.MicroMigrater;
import one.microstream.storage.configuration.Configuration;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;

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
	 * Uses the MicroStream {@link Configuration#Default()} configuration for the actual
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
			Configuration.Default()
			    .createEmbeddedStorageFoundation()
			    .createEmbeddedStorageManager(),
			migrater
		).start();
	}
	
	/**
	 * Creates a {@link MigrationEmbeddedStorageManager} with the given {@link MicroMigrater}.
	 * Uses the MicroStream {@link Configuration#Default()} configuration for the actual
	 * {@link EmbeddedStorageManager}.
	 * <p>Warning "resource" is suppressed because it is used and closed in the {@link MigrationEmbeddedStorageManager}.
	 * 
	 * @param storageDirectory is used as the base directory for the datastore {@link Configuration#setBaseDirectory(String)}
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
			Configuration.Default()
				.setBaseDirectory(storageDirectory.toAbsolutePath().toString())
			    .createEmbeddedStorageFoundation()
			    .createEmbeddedStorageManager(),
			migrater
		).start();
	}
}
