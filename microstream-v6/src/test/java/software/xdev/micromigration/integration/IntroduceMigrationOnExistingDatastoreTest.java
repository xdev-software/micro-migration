package software.xdev.micromigration.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorage;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.migrater.ExplicitMigrater;
import software.xdev.micromigration.testUtil.MicroMigrationScriptDummy;
import software.xdev.micromigration.version.MigrationVersion;


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
			assertEquals(1, migrationStorageManager.getCurrentVersion().getVersions()[0]);
		}
	}
}
