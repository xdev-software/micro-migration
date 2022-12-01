package de.johannes_rabauer.micromigration.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import de.johannes_rabauer.micromigration.MigrationEmbeddedStorage;
import de.johannes_rabauer.micromigration.MigrationEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.migrater.ExplicitMigrater;
import de.johannes_rabauer.micromigration.testUtil.MicroMigrationScriptDummy;
import de.johannes_rabauer.micromigration.version.MigrationVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


class IntroduceMigrationOnExistingDatastoreTest
{
	final static String ROOT = "OriginalRoot";

	@Test
	void testIntroducingMigrationOnExistingDatastore_MigrationEmbeddedStorageManager(@TempDir Path storageFolder) throws IOException 
	{
		try(final EmbeddedStorageManager storageManager = EmbeddedStorage.start(storageFolder))
		{
			storageManager.setRoot(ROOT);
			storageManager.storeRoot();
		}
		
		final ExplicitMigrater migrater = new ExplicitMigrater(
				new MicroMigrationScriptDummy(new MigrationVersion(1))
		);
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(storageFolder, migrater))
		{
			assertEquals(ROOT, migrationStorageManager.root());
			Assertions.assertEquals(1, migrationStorageManager.getCurrentVersion().getVersions()[0]);
		}
	}
}
