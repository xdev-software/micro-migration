package de.johannes_rabauer.micromigration.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import de.johannes_rabauer.micromigration.MigrationEmbeddedStorage;
import de.johannes_rabauer.micromigration.MigrationEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.MigrationManager;
import de.johannes_rabauer.micromigration.migrater.ExplicitMigrater;
import de.johannes_rabauer.micromigration.scripts.MigrationScript;
import de.johannes_rabauer.micromigration.scripts.SimpleTypedMigrationScript;
import de.johannes_rabauer.micromigration.version.MigrationVersion;
import de.johannes_rabauer.micromigration.version.Versioned;
import de.johannes_rabauer.micromigration.version.VersionedObject;
import one.microstream.storage.configuration.Configuration;
import one.microstream.storage.types.EmbeddedStorageManager;

class MigrationScriptAfterScript 
{
	@Test
	public void testMigrationWithThreeDifferenMigrater_MigrationEmbeddedStorageManager(@TempDir Path storageFolder) throws IOException 
	{
		//First run without any migration script
		final ExplicitMigrater firstMigrater = new ExplicitMigrater();
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(storageFolder, firstMigrater))
		{
			migrationStorageManager.setRoot(0);
			migrationStorageManager.storeRoot();
			assertEquals(0, migrationStorageManager.root());
			assertEquals(new MigrationVersion(0), migrationStorageManager.getCurrentVersion());
		}
		
		
		//Run with one migration script		
		final MigrationScript<Integer> firstScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1), 
				(context) -> context.getStorageManager().setRoot(1)
		);
		final ExplicitMigrater secondMigrater = new ExplicitMigrater(firstScript);
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(storageFolder, secondMigrater))
		{
			assertEquals(1, migrationStorageManager.root());
			assertEquals(new MigrationVersion(1), migrationStorageManager.getCurrentVersion());
		}

		
		//Run with two migration scripts	
		final MigrationScript<Integer> secondScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(2), 
				(context) -> context.getStorageManager().setRoot(2)
		);
		final ExplicitMigrater thirdMigrater = new ExplicitMigrater(firstScript, secondScript);
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(storageFolder, thirdMigrater))
		{
			assertEquals(2, migrationStorageManager.root());
			assertEquals(new MigrationVersion(2), migrationStorageManager.getCurrentVersion());
		}
	}
	
	@Test
	public void testMigrationWithThreeDifferenMigrater_StandaloneMicroMigrationManager(@TempDir Path storageFolder) throws IOException 
	{
		//First run without any migration script
		try(final EmbeddedStorageManager storageManager = startEmbeddedStorageManagerWithPath(storageFolder))
		{
			VersionedObject<Integer> firstRoot = new VersionedObject<>(0);
			storageManager.setRoot(firstRoot);
			storageManager.storeRoot();
			assertEquals(Integer.valueOf(0), firstRoot.getObject());
			assertEquals(new MigrationVersion(0), firstRoot.getVersion());
		}
		
		
		//Run with one migration script		
		final MigrationScript<VersionedObject<Integer>> firstScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1), 
				(context) -> context.getMigratingObject().setObject(1)
		);
		try(final EmbeddedStorageManager storageManager = startEmbeddedStorageManagerWithPath(storageFolder))
		{
			new MigrationManager(
				(Versioned) storageManager.root(), 
				new ExplicitMigrater(firstScript), 
				storageManager
			)
			.migrate(storageManager.root());
			VersionedObject<Integer> currentRoot = (VersionedObject<Integer>)storageManager.root();
			assertEquals(Integer.valueOf(1), currentRoot.getObject());
			assertEquals(new MigrationVersion(1), currentRoot.getVersion());
		}

		
		//Run with two migration scripts	
		final MigrationScript<VersionedObject<Integer>> secondScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(2), 
				(context) -> context.getMigratingObject().setObject(2)
		);
		try(final EmbeddedStorageManager storageManager = startEmbeddedStorageManagerWithPath(storageFolder))
		{
			new MigrationManager(
				(Versioned) storageManager.root(), 
				new ExplicitMigrater(firstScript, secondScript), 
				storageManager
			)
			.migrate(storageManager.root());
			VersionedObject<Integer> currentRoot = (VersionedObject<Integer>)storageManager.root();
			assertEquals(Integer.valueOf(2), currentRoot.getObject());
			assertEquals(new MigrationVersion(2), currentRoot.getVersion());
		}
	}
	
	private EmbeddedStorageManager startEmbeddedStorageManagerWithPath(Path storageFolder)
	{
		return Configuration.Default()
					.setBaseDirectory(storageFolder.toAbsolutePath().toString())
				    .createEmbeddedStorageFoundation()
				    .createEmbeddedStorageManager()
				    .start();
	}

}
