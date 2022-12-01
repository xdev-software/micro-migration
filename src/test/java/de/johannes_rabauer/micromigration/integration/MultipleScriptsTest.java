package de.johannes_rabauer.micromigration.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import de.johannes_rabauer.micromigration.MigrationEmbeddedStorage;
import de.johannes_rabauer.micromigration.MigrationEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.migrater.ExplicitMigrater;
import de.johannes_rabauer.micromigration.migrater.VersionAlreadyRegisteredException;
import de.johannes_rabauer.micromigration.scripts.MigrationScript;
import de.johannes_rabauer.micromigration.scripts.SimpleTypedMigrationScript;
import de.johannes_rabauer.micromigration.version.MigrationVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


class MultipleScriptsTest
{
	@Test
	void testMigrationWithTwoScriptsAtOnce_MigrationEmbeddedStorageManager(@TempDir Path storageFolder)
	{
		final MigrationScript<Integer> firstScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1),
				(context) -> context.getStorageManager().setRoot(1)
		);
		final MigrationScript<Integer> secondScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(2), 
				(context) -> context.getStorageManager().setRoot(2)
		);
		final ExplicitMigrater migrater = new ExplicitMigrater(firstScript, secondScript);
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(storageFolder, migrater))
		{
			assertEquals(2, migrationStorageManager.root());
			Assertions.assertEquals(new MigrationVersion(2), migrationStorageManager.getCurrentVersion());
		}
	}
	
	@Test
	void testMigrationWithTwoScriptsWithSameVersion(@TempDir Path storageFolder)
	{
		final MigrationScript<Integer> firstScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1), 
				(context) -> context.getStorageManager().setRoot(1)
		);
		final MigrationScript<Integer> secondScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1), 
				(context) -> context.getStorageManager().setRoot(2)
		);
		Assertions.assertThrows(VersionAlreadyRegisteredException.class, () ->
			new ExplicitMigrater(firstScript, secondScript)
		);
	}
	
	@Test
	void testMigrationWithThreeScriptsWithSameVersion(@TempDir Path storageFolder)
	{
		final MigrationScript<Integer> firstScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1), 
				(context) -> context.getStorageManager().setRoot(1)
		);
		final MigrationScript<Integer> secondScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(2), 
				(context) -> context.getStorageManager().setRoot(2)
		);
		final MigrationScript<Integer> thirdScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1), 
				(context) -> context.getStorageManager().setRoot(3)
		);
		Assertions.assertThrows(VersionAlreadyRegisteredException.class, () -> 
			new ExplicitMigrater(firstScript, secondScript, thirdScript)
		);
	}
}
