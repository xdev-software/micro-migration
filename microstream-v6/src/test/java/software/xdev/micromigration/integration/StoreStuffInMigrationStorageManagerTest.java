package software.xdev.micromigration.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Path;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorage;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.migrater.ExplicitMigrater;
import software.xdev.micromigration.scripts.MigrationScript;
import software.xdev.micromigration.scripts.SimpleTypedMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


class StoreStuffInMigrationStorageManagerTest
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
	void testStoringSomethingAfterUpdating(@TempDir Path storageFolder) throws IOException 
	{
		final MigrationScript<Object, EmbeddedStorageManager> script = new SimpleTypedMigrationScript<>(
			new MigrationVersion(1),
			(context) -> {}
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