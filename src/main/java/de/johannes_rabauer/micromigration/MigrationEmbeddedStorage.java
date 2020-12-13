package de.johannes_rabauer.micromigration;

import de.johannes_rabauer.micromigration.migrater.MicroMigrater;
import one.microstream.storage.configuration.Configuration;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;

public class MigrationEmbeddedStorage
{
	/**
	 * Warning "resource" is suppressed because it is used and closed in the @link {@link MigrationEmbeddedStorageManager}.
	 * @param migrater
	 * @return
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
}
