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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import one.microstream.persistence.types.Storer;
import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorage;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.microstream.MigrationManager;
import software.xdev.micromigration.migrater.ExplicitMigrater;
import software.xdev.micromigration.scripts.SimpleTypedMigrationScript;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.version.Versioned;
import software.xdev.micromigration.version.VersionedObject;

class MigrationScriptAfterScriptTest 
{
	@Test
	void testMigrationWithThreeDifferenMigrater_MigrationEmbeddedStorageManager(@TempDir final Path storageFolder) throws IOException
	{
		// First run without any migration script
		final ExplicitMigrater firstMigrater = new ExplicitMigrater();
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(
			storageFolder,
			firstMigrater))
		{
			migrationStorageManager.setRoot(0);
			migrationStorageManager.storeRoot();
			assertEquals(0, migrationStorageManager.root());
			Assertions.assertEquals(new MigrationVersion(0), migrationStorageManager.getCurrentVersion());
		}
		
		// Run with one migration script
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> firstScript =
			new SimpleTypedMigrationScript<>(
				new MigrationVersion(1),
				(context) -> context.getStorageManager().setRoot(1)
			);
		final ExplicitMigrater secondMigrater = new ExplicitMigrater(firstScript);
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(
			storageFolder,
			secondMigrater))
		{
			assertEquals(1, migrationStorageManager.root());
			Assertions.assertEquals(new MigrationVersion(1), migrationStorageManager.getCurrentVersion());
		}
		
		// Run with two migration scripts
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> secondScript =
			new SimpleTypedMigrationScript<>(
				new MigrationVersion(2),
				(context) -> context.getStorageManager().setRoot(2)
			);
		final ExplicitMigrater thirdMigrater = new ExplicitMigrater(firstScript, secondScript);
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(
			storageFolder,
			thirdMigrater))
		{
			assertEquals(2, migrationStorageManager.root());
			Assertions.assertEquals(new MigrationVersion(2), migrationStorageManager.getCurrentVersion());
		}
	}
	
	@Test
	void testMigrationAndUseStorer(@TempDir final Path storageFolder) throws IOException
	{
		final List<String> firstList = new ArrayList<>();
		// Run with one migration script
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> firstScript =
			new SimpleTypedMigrationScript<>(
				new MigrationVersion(1),
				(context) ->
				{
					context.getStorageManager().setRoot(firstList);
					firstList.add("1");
					final Storer storer = context.getStorageManager().getNativeStorageManager().createStorer();
					storer.store(firstList);
					storer.commit();
				}
			);
		
		final ExplicitMigrater migrater = new ExplicitMigrater(firstScript);
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(
			storageFolder,
			migrater))
		{
			assertEquals(1, ((List<String>)migrationStorageManager.root()).size());
		}
	}
	
	@Test
	void testMigrationWithScriptExecutionNotification(@TempDir final Path storageFolder) throws IOException
	{
		final VersionAgnosticMigrationScript<Integer, MigrationEmbeddedStorageManager> firstScript =
			new SimpleTypedMigrationScript<>(
				new MigrationVersion(1),
				(context) -> context.getStorageManager().setRoot(1)
			);
		final ExplicitMigrater migrater = new ExplicitMigrater(firstScript);
		final AtomicBoolean notificationReceived = new AtomicBoolean(false);
		migrater.registerNotificationConsumer(
			notification ->
			{
				Assertions.assertEquals(firstScript, notification.getExecutedScript());
				Assertions.assertEquals(new MigrationVersion(0), notification.getSourceVersion());
				Assertions.assertEquals(new MigrationVersion(1), notification.getTargetVersion());
				notificationReceived.set(true);
			}
		);
		try(final MigrationEmbeddedStorageManager migrationStorageManager = MigrationEmbeddedStorage.start(storageFolder, migrater))
		{
			assertTrue(notificationReceived.get());
		}
	}
	
	@Test
	void testMigrationWithThreeDifferenMigrater_StandaloneMicroMigrationManager(@TempDir final Path storageFolder) throws IOException
	{
		//First run without any migration script
		try(final EmbeddedStorageManager storageManager = this.startEmbeddedStorageManagerWithPath(storageFolder))
		{
			final VersionedObject<Integer> firstRoot = new VersionedObject<>(0);
			storageManager.setRoot(firstRoot);
			storageManager.storeRoot();
			assertEquals(Integer.valueOf(0), firstRoot.getObject());
			assertEquals(new MigrationVersion(0), firstRoot.getVersion());
		}
		
		
		//Run with one migration script		
		final VersionAgnosticMigrationScript<VersionedObject<Integer>, MigrationEmbeddedStorageManager> firstScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(1), 
				(context) -> context.getMigratingObject().setObject(1)
		);
		try(final EmbeddedStorageManager storageManager = this.startEmbeddedStorageManagerWithPath(storageFolder))
		{
			new MigrationManager(
				(Versioned) storageManager.root(),
				new ExplicitMigrater(firstScript),
				storageManager
			)
			.migrate(storageManager.root());
			@SuppressWarnings("unchecked")
			final VersionedObject<Integer> currentRoot = (VersionedObject<Integer>)storageManager.root();
			assertEquals(Integer.valueOf(1), currentRoot.getObject());
			assertEquals(new MigrationVersion(1), currentRoot.getVersion());
		}

		
		//Run with two migration scripts	
		final VersionAgnosticMigrationScript<VersionedObject<Integer>, MigrationEmbeddedStorageManager> secondScript = new SimpleTypedMigrationScript<>(
				new MigrationVersion(2), 
				(context) -> context.getMigratingObject().setObject(2)
		);
		try(final EmbeddedStorageManager storageManager = this.startEmbeddedStorageManagerWithPath(storageFolder))
		{
			new MigrationManager(
				(Versioned) storageManager.root(), 
				new ExplicitMigrater(firstScript, secondScript),
				storageManager
			)
			.migrate(storageManager.root());
			@SuppressWarnings("unchecked")
			final VersionedObject<Integer> currentRoot = (VersionedObject<Integer>)storageManager.root();
			assertEquals(Integer.valueOf(2), currentRoot.getObject());
			assertEquals(new MigrationVersion(2), currentRoot.getVersion());
		}
	}
	
	private EmbeddedStorageManager startEmbeddedStorageManagerWithPath(final Path storageFolder)
	{
		return EmbeddedStorage.start(storageFolder);
	}

}
