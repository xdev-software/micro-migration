package software.xdev.micromigration.migrater;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import software.xdev.micromigration.testUtil.MicroMigrationScriptDummy;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorage;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.scripts.SimpleTypedMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


class ExplicitMigraterTest
{
	
	@Test
	void testGetSortedScripts_empty() 
	{
		final ExplicitMigrater migrater = new ExplicitMigrater();
		assertEquals(0,migrater.getSortedScripts().size());
	}	
	
	@Test
	void testGetSortedScripts_sorted() 
	{
		final ExplicitMigrater migrater = new ExplicitMigrater(
			new MicroMigrationScriptDummy(new MigrationVersion(1)),
			new MicroMigrationScriptDummy(new MigrationVersion(2))
		);
		assertEquals(1, migrater.getSortedScripts().first().getTargetVersion().getVersions()[0]);
		assertEquals(2, migrater.getSortedScripts().last().getTargetVersion().getVersions()[0]);
	}	
	
	@Test
	void testGetSortedScripts_unsorted() 
	{
		final ExplicitMigrater migrater = new ExplicitMigrater(
			new MicroMigrationScriptDummy(new MigrationVersion(2)),
			new MicroMigrationScriptDummy(new MigrationVersion(1))
		);
		assertEquals(1, migrater.getSortedScripts().first().getTargetVersion().getVersions()[0]);
		assertEquals(2, migrater.getSortedScripts().last().getTargetVersion().getVersions()[0]);
	}	
	
	@Test 
	void testWrongTypedVersionedScript(@TempDir Path storageFolder) 
	{		
		try(final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(storageFolder, new ExplicitMigrater()))
		{
			storageManager.setRoot("SomeString");
			storageManager.storeRoot();
		}
		final ExplicitMigrater migrater = new ExplicitMigrater(
			new SimpleTypedMigrationScript<Double, EmbeddedStorageManager>(
				new MigrationVersion(1),
				(context) -> {
					context.getStorageManager().setRoot(context.getMigratingObject() + 1);
				}
			)
		);
		Assertions.assertThrows(ClassCastException.class, () -> 
			{
				MigrationEmbeddedStorage.start(storageFolder, migrater);
			}
		);

	}

}
