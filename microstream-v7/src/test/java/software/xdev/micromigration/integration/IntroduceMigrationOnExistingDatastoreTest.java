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
package software.xdev.micromigration.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorage;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.migrater.ExplicitMigrater;
import software.xdev.micromigration.testUtil.MicroMigrationScriptDummy;
import software.xdev.micromigration.version.MigrationVersion;


class IntroduceMigrationOnExistingDatastoreTest
{
	final static String ROOT = "OriginalRoot";

	@Test
	void testIntroducingMigrationOnExistingDatastore_MigrationEmbeddedStorageManager(@TempDir Path storageFolder) throws IOException 
	{
		try(final EmbeddedStorageManager storageManager = EmbeddedStorage.start(storageFolder))
		{
			storageManager.setRoot(ROOT);
			storageManager.storeRoot();
		}
		
		final ExplicitMigrater migrater = new ExplicitMigrater(
				new MicroMigrationScriptDummy(new MigrationVersion(1))
		);
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(storageFolder, migrater))
		{
			assertEquals(ROOT, migrationStorageManager.root());
			assertEquals(1, migrationStorageManager.getCurrentVersion().getVersions()[0]);
		}
	}
}
