package de.johannes_rabauer.micromigration;

import java.nio.file.Path;

import de.johannes_rabauer.micromigration.migrater.MicroMigrater;
import one.microstream.storage.configuration.Configuration;
import one.microstream.storage.types.EmbeddedStorage;

/**
 * Wrapper class for the MicroStream {@link EmbeddedStorage} utility class.
 * Produces {@link MigrationEmbeddedStorageManager} for updateable datastores.
 * 
 * @author Johannes Rabauer
 * 
 */
public class MigrationEmbeddedStorage
{
	/**
	 * Warning "resource" is suppressed because it is used and closed in the @link {@link MigrationEmbeddedStorageManager}.
	 */
	@SuppressWarnings("resource")
	public static final MigrationEmbeddedStorageManager start(MicroMigrater migrater)
	{
		return new MigrationEmbeddedStorageManager(
			Configuration.Default()
			    .createEmbeddedStorageFoundation()
			    .createEmbeddedStorageManager(),
			migrater
		).start();
	}
	
	/**
	 * Warning "resource" is suppressed because it is used and closed in the @link {@link MigrationEmbeddedStorageManager}.
	 */
	@SuppressWarnings("resource")
	public static final MigrationEmbeddedStorageManager start(
		Path          storageDirectory, 
		MicroMigrater migrater
	)
	{
		return new MigrationEmbeddedStorageManager(
			Configuration.Default()
				.setBaseDirectory(storageDirectory.toAbsolutePath().toString())
			    .createEmbeddedStorageFoundation()
			    .createEmbeddedStorageManager(),
			migrater
		).start();
	}
}
