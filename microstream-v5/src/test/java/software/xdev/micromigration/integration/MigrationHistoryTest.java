package software.xdev.micromigration.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorage;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.migrater.ExplicitMigrater;
import software.xdev.micromigration.scripts.SimpleTypedMigrationScript;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MigrationHistoryTest
{
	@Test
	void testMigrationHistoryWithTwoScripts(@TempDir Path storageFolder)
	{
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> firstScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1),
				(context) -> context.getStorageManager().setRoot(1)
		);
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> secondScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(2), 
				(context) -> context.getStorageManager().setRoot(2)
		);
		final ExplicitMigrater migrater = new ExplicitMigrater(firstScript, secondScript);
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(storageFolder, migrater))
		{
			assertEquals(2, migrationStorageManager.getMigrationHistory().size());
			assertEquals("SimpleTypedMigrationScript", migrationStorageManager.getMigrationHistory().get(0).getExecutedScriptName());
		}
	}

}
