package de.johannes_rabauer.micromigration.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import de.johannes_rabauer.micromigration.MigrationEmbeddedStorage;
import de.johannes_rabauer.micromigration.MigrationEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.migrater.ExplicitMigrater;
import de.johannes_rabauer.micromigration.testUtil.MicroMigrationScriptDummy;
import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;

class IntroduceMigrationOnExistingDatastore 
{
	final static String ROOT = "OriginalRoot";
	
	@Test
	public void testMigration(@TempDir Path storageFolder) throws IOException 
	{
		try(final EmbeddedStorageManager storageManager = EmbeddedStorage.start(storageFolder))
		{
			storageManager.setRoot(ROOT);
			storageManager.storeRoot();
		}
		
		final ExplicitMigrater migrater = new ExplicitMigrater(
				new MicroMigrationScriptDummy(new MicroMigrationVersion(1))
		);
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(storageFolder, migrater))
		{
			assertEquals(ROOT, migrationStorageManager.root());
			assertEquals(1, migrationStorageManager.getCurrentVersion().getMajorVersion());
		}
	}

}
