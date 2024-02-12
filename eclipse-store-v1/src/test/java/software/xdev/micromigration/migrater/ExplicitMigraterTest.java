/*
 * Copyright © 2021 XDEV Software GmbH (https://xdev.software)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package software.xdev.micromigration.migrater;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import software.xdev.micromigration.eclipse.store.MigrationEmbeddedStorage;
import software.xdev.micromigration.eclipse.store.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.scripts.SimpleTypedMigrationScript;
import software.xdev.micromigration.testUtil.MicroMigrationScriptDummy;
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
	void testWrongTypedVersionedScript(@TempDir final Path storageFolder)
	{		
		try(final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(storageFolder, new ExplicitMigrater()))
		{
			storageManager.setRoot("SomeString");
			storageManager.storeRoot();
		}
		final ExplicitMigrater migrater = new ExplicitMigrater(
			new SimpleTypedMigrationScript<Double, MigrationEmbeddedStorageManager>(
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
