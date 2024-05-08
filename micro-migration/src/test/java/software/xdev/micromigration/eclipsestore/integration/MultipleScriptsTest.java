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
package software.xdev.micromigration.eclipsestore.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import software.xdev.micromigration.eclipsestore.MigrationEmbeddedStorage;
import software.xdev.micromigration.eclipsestore.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.migrater.ExplicitMigrater;
import software.xdev.micromigration.migrater.VersionAlreadyRegisteredException;
import software.xdev.micromigration.scripts.SimpleTypedMigrationScript;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


class MultipleScriptsTest
{
	@Test
	void checkMigrationWithTwoScriptsAtOnceMigrationEmbeddedStorageManager(@TempDir final Path storageFolder)
	{
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> firstScript =
			new SimpleTypedMigrationScript<>(
				new MigrationVersion(1),
				(context) -> context.getStorageManager().setRoot(1)
			);
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> secondScript =
			new SimpleTypedMigrationScript<>(
				new MigrationVersion(2),
				(context) -> context.getStorageManager().setRoot(2)
			);
		final ExplicitMigrater migrater = new ExplicitMigrater(firstScript, secondScript);
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(
			storageFolder,
			migrater))
		{
			assertEquals(2, migrationStorageManager.root());
			assertEquals(new MigrationVersion(2), migrationStorageManager.getCurrentVersion());
		}
	}
	
	@Test
	void checkMigrationWithTwoScriptsWithSameVersion()
	{
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> firstScript =
			new SimpleTypedMigrationScript<>(
				new MigrationVersion(1),
				(context) -> context.getStorageManager().setRoot(1)
			);
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> secondScript =
			new SimpleTypedMigrationScript<>(
				new MigrationVersion(1),
				(context) -> context.getStorageManager().setRoot(2)
			);
		Assertions.assertThrows(VersionAlreadyRegisteredException.class, () ->
			new ExplicitMigrater(firstScript, secondScript)
		);
	}
	
	@Test
	void checkMigrationWithThreeScriptsWithSameVersion()
	{
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> firstScript =
			new SimpleTypedMigrationScript<>(
				new MigrationVersion(1),
				(context) -> context.getStorageManager().setRoot(1)
			);
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> secondScript =
			new SimpleTypedMigrationScript<>(
				new MigrationVersion(2),
				(context) -> context.getStorageManager().setRoot(2)
			);
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> thirdScript =
			new SimpleTypedMigrationScript<>(
				new MigrationVersion(1),
				(context) -> context.getStorageManager().setRoot(3)
			);
		Assertions.assertThrows(VersionAlreadyRegisteredException.class, () ->
			new ExplicitMigrater(firstScript, secondScript, thirdScript)
		);
	}
}
