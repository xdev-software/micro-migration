package de.johannes_rabauer.micromigration.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import de.johannes_rabauer.micromigration.MigrationEmbeddedStorage;
import de.johannes_rabauer.micromigration.MigrationEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.migrater.ExplicitMigrater;
import de.johannes_rabauer.micromigration.scripts.MicroMigrationScript;
import de.johannes_rabauer.micromigration.scripts.SimpleMigrationScript;
import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;

class MigrationScriptAfterScript 
{
	@Test
	public void testMigrationWithThreeDifferenMigrater(@TempDir Path storageFolder) throws IOException 
	{
		//First run without any migration script
		final ExplicitMigrater firstMigrater = new ExplicitMigrater();
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(storageFolder, firstMigrater))
		{
			migrationStorageManager.setRoot(0);
			assertEquals(0, migrationStorageManager.root());
			assertEquals(new MicroMigrationVersion(0,0,0), migrationStorageManager.getCurrentVersion());
		}
		
		//Run with one migration script		
		final MicroMigrationScript firstScript = new SimpleMigrationScript(
				new MicroMigrationVersion(1), 
				(root, storage) -> storage.setRoot(1)
		);
		final ExplicitMigrater secondMigrater = new ExplicitMigrater(firstScript);
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(storageFolder, secondMigrater))
		{
			assertEquals(1, migrationStorageManager.root());
			assertEquals(new MicroMigrationVersion(1,0,0), migrationStorageManager.getCurrentVersion());
		}

		//Run with two migration scripts	
		final MicroMigrationScript secondScript = new SimpleMigrationScript(
				new MicroMigrationVersion(2), 
				(root, storage) -> storage.setRoot(2)
		);
		final ExplicitMigrater thirdMigrater = new ExplicitMigrater(firstScript, secondScript);
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(storageFolder, thirdMigrater))
		{
			assertEquals(2, migrationStorageManager.root());
			assertEquals(new MicroMigrationVersion(2,0,0), migrationStorageManager.getCurrentVersion());
		}
	}

}
