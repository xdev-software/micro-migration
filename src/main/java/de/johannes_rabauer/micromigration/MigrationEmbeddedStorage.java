package de.johannes_rabauer.micromigration;

import one.microstream.storage.configuration.Configuration;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;

public class MigrationEmbeddedStorage 
{
	public static final MigrationEmbeddedStorageManager start()
	{
		return new MigrationEmbeddedStorageManager(
			Configuration.Default()
			    .createEmbeddedStorageFoundation()
			    .createEmbeddedStorageManager()
		).start();
	}
}
