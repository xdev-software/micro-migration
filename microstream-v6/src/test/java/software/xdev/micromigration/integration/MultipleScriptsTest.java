package software.xdev.micromigration.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorage;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.migrater.ExplicitMigrater;
import software.xdev.micromigration.migrater.VersionAlreadyRegisteredException;
import software.xdev.micromigration.scripts.SimpleTypedMigrationScript;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


class MultipleScriptsTest
{
	@Test
	void testMigrationWithTwoScriptsAtOnce_MigrationEmbeddedStorageManager(@TempDir Path storageFolder)
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
			assertEquals(2, migrationStorageManager.root());
			assertEquals(new MigrationVersion(2), migrationStorageManager.getCurrentVersion());
		}
	}
	
	@Test
	void testMigrationWithTwoScriptsWithSameVersion(@TempDir Path storageFolder)
	{
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> firstScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1), 
				(context) -> context.getStorageManager().setRoot(1)
		);
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> secondScript = new SimpleTypedMigrationScript<>(
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
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> firstScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1), 
				(context) -> context.getStorageManager().setRoot(1)
		);
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> secondScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(2), 
				(context) -> context.getStorageManager().setRoot(2)
		);
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> thirdScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1), 
				(context) -> context.getStorageManager().setRoot(3)
		);
		Assertions.assertThrows(VersionAlreadyRegisteredException.class, () -> 
			new ExplicitMigrater(firstScript, secondScript, thirdScript)
		);
	}
}
