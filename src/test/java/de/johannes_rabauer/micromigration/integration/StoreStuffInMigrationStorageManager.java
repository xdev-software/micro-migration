package de.johannes_rabauer.micromigration.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import de.johannes_rabauer.micromigration.MigrationEmbeddedStorage;
import de.johannes_rabauer.micromigration.MigrationEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.migrater.ExplicitMigrater;
import de.johannes_rabauer.micromigration.scripts.MigrationScript;
import de.johannes_rabauer.micromigration.scripts.SimpleMigrationScript;
import de.johannes_rabauer.micromigration.version.MigrationVersion;

public class StoreStuffInMigrationStorageManager 
{	
	private static class RootClass
	{
		private ChildClass child = new ChildClass();
	}
	
	private static class ChildClass
	{
		private int i = 0;
	}
	
	@Test
	public void testStoringSomethingAfterUpdating(@TempDir Path storageFolder) throws IOException 
	{
		final MigrationScript script = new SimpleMigrationScript(
				new MigrationVersion(1), 
				(root, storage) -> {}
		);
		final ExplicitMigrater migrater = new ExplicitMigrater(script);
		//Create new store and change stored object
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(storageFolder, migrater))
		{
			migrationStorageManager.setRoot(new RootClass());
			migrationStorageManager.storeRoot();
			final RootClass storedRoot = ((RootClass)migrationStorageManager.root());
			assertEquals(0, storedRoot.child.i);
			((RootClass)migrationStorageManager.root()).child.i = 1;
			migrationStorageManager.store(storedRoot.child);
			assertEquals(1, storedRoot.child.i);
		}
		//Check if stored object is correct
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(storageFolder, migrater))
		{
			final RootClass storedRoot = ((RootClass)migrationStorageManager.root());
			assertNotNull(storedRoot);
			assertEquals(1, storedRoot.child.i);
		}
	}

}
