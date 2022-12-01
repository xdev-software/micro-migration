package de.johannes_rabauer.micromigration.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import de.johannes_rabauer.micromigration.microstream.v5.MigrationEmbeddedStorage;
import de.johannes_rabauer.micromigration.microstream.v5.MigrationEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.migrater.ExplicitMigrater;
import de.johannes_rabauer.micromigration.migrater.VersionAlreadyRegisteredException;
import de.johannes_rabauer.micromigration.scripts.MigrationScript;
import de.johannes_rabauer.micromigration.scripts.SimpleTypedMigrationScript;
import de.johannes_rabauer.micromigration.version.MigrationVersion;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


class MultipleScriptsTest
{
	@Test
	void testMigrationWithTwoScriptsAtOnce_MigrationEmbeddedStorageManager(@TempDir Path storageFolder)
	{
		final MigrationScript<Integer, EmbeddedStorageManager> firstScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1),
				(context) -> context.getStorageManager().setRoot(1)
		);
		final MigrationScript<Integer, EmbeddedStorageManager> secondScript = new SimpleTypedMigrationScript<>(
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
		final MigrationScript<Integer, EmbeddedStorageManager> firstScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1), 
				(context) -> context.getStorageManager().setRoot(1)
		);
		final MigrationScript<Integer, EmbeddedStorageManager> secondScript = new SimpleTypedMigrationScript<>(
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
		final MigrationScript<Integer, EmbeddedStorageManager> firstScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1), 
				(context) -> context.getStorageManager().setRoot(1)
		);
		final MigrationScript<Integer, EmbeddedStorageManager> secondScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(2), 
				(context) -> context.getStorageManager().setRoot(2)
		);
		final MigrationScript<Integer, EmbeddedStorageManager> thirdScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1), 
				(context) -> context.getStorageManager().setRoot(3)
		);
		Assertions.assertThrows(VersionAlreadyRegisteredException.class, () -> 
			new ExplicitMigrater(firstScript, secondScript, thirdScript)
		);
	}
}
