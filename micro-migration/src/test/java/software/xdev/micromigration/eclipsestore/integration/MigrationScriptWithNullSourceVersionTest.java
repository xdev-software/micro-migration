/*
 * Copyright © 2021 XDEV Software (https://xdev.software)
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
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.file.Path;

import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import software.xdev.micromigration.eclipsestore.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.eclipsestore.MigrationManager;
import software.xdev.micromigration.migrater.ExplicitMigrater;
import software.xdev.micromigration.scripts.SimpleTypedMigrationScript;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.version.Versioned;
import software.xdev.micromigration.version.VersionedObject;


class MigrationScriptWithNullSourceVersionTest
{
	public static class EmptyVersionedRoot implements Versioned
	{
		private MigrationVersion version;
		
		@Override
		public void setVersion(final MigrationVersion version)
		{
			this.version = version;
		}
		
		@Override
		public MigrationVersion getVersion()
		{
			return this.version;
		}
	}
	
	@Test
	void updateFromEmptyVersion(@TempDir final Path storageFolder)
	{
		// First run without any migration script
		try(final EmbeddedStorageManager storageManager = this.startEmbeddedStorageManagerWithPath(storageFolder))
		{
			final EmptyVersionedRoot firstRoot = new EmptyVersionedRoot();
			storageManager.setRoot(firstRoot);
			storageManager.storeRoot();
			assertNull(firstRoot.getVersion());
		}
		
		// Run with one migration script
		final VersionAgnosticMigrationScript<VersionedObject<Integer>, MigrationEmbeddedStorageManager> firstScript =
			new SimpleTypedMigrationScript<>(
				new MigrationVersion(1),
				(context) -> {
				}
			);
		
		try(final EmbeddedStorageManager storageManager = this.startEmbeddedStorageManagerWithPath(storageFolder))
		{
			new MigrationManager(
				(Versioned)storageManager.root(),
				new ExplicitMigrater(firstScript),
				storageManager
			)
				.migrate(storageManager.root());
			@SuppressWarnings("unchecked")
			final EmptyVersionedRoot currentRoot = (EmptyVersionedRoot)storageManager.root();
			assertEquals(new MigrationVersion(1), currentRoot.getVersion());
		}
	}
	
	@Test
	void updateWithNoScripts(@TempDir final Path storageFolder)
	{
		// First run without any migration script
		try(final EmbeddedStorageManager storageManager = this.startEmbeddedStorageManagerWithPath(storageFolder))
		{
			final EmptyVersionedRoot firstRoot = new EmptyVersionedRoot();
			storageManager.setRoot(firstRoot);
			storageManager.storeRoot();
			assertNull(firstRoot.getVersion());
		}
		
		try(final EmbeddedStorageManager storageManager = this.startEmbeddedStorageManagerWithPath(storageFolder))
		{
			new MigrationManager(
				(Versioned)storageManager.root(),
				new ExplicitMigrater(),
				storageManager
			)
				.migrate(storageManager.root());
			@SuppressWarnings("unchecked")
			final EmptyVersionedRoot currentRoot = (EmptyVersionedRoot)storageManager.root();
			assertNull(currentRoot.getVersion());
		}
	}
	
	private EmbeddedStorageManager startEmbeddedStorageManagerWithPath(final Path storageFolder)
	{
		return EmbeddedStorage.start(storageFolder);
	}
}
