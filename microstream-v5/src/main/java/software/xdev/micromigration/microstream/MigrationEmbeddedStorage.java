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
package software.xdev.micromigration.microstream;

import java.nio.file.Path;
import java.util.Objects;

import one.microstream.afs.nio.types.NioFileSystem;
import one.microstream.storage.embedded.types.EmbeddedStorageFoundation;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import one.microstream.storage.types.Storage;
import one.microstream.storage.types.StorageConfiguration;
import software.xdev.micromigration.migrater.MicroMigrater;


/**
 * Provides static utility calls to create the {@link software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager} for
 * updateable datastores. Basically a wrapper for the utility class {@link one.microstream.storage.embedded.types.EmbeddedStorage}.
 * 
 * @author Johannes Rabauer
 * 
 */
public class MigrationEmbeddedStorage
{
	/**
	 * Creates a {@link MigrationEmbeddedStorageManager} with the given {@link MicroMigrater}.
	 * Uses the MicroStream {@link EmbeddedStorageFoundation#New()} configuration for the actual
	 * {@link EmbeddedStorageManager}.
	 * <p>Warning "resource" is suppressed because it is used and closed in the {@link MigrationEmbeddedStorageManager}.
	 * 
	 * @param migrater which is used as source for the migration scripts
	 * @return the created storage manager with the given migrater
	 */
	public static final MigrationEmbeddedStorageManager start(final MicroMigrater migrater)
	{
		Objects.requireNonNull(migrater);
		return new MigrationEmbeddedStorageManager(
			createStorageManager(),
			migrater
		).start();
	}
	
	/**
	 * Creates a {@link MigrationEmbeddedStorageManager} with the given {@link MicroMigrater}.
	 * Uses the MicroStream {@link EmbeddedStorageFoundation#New()} configuration for the actual
	 * {@link EmbeddedStorageManager}.
	 * <p>Warning "resource" is suppressed because it is used and closed in the {@link MigrationEmbeddedStorageManager}.
	 * 
	 * @param storageDirectory is used as the base directory for the datastore
	 * @param migrater which is used as source for the migration scripts
	 * @return the created storage manager with the given migrater
	 */
	@SuppressWarnings("resource")
	public static final MigrationEmbeddedStorageManager start(
		final Path          storageDirectory,
		final MicroMigrater migrater
	)
	{
		Objects.requireNonNull(migrater);
		Objects.requireNonNull(storageDirectory);
		
		return new MigrationEmbeddedStorageManager(
			createStorageManager(storageDirectory),
			migrater
		).start();
	}
	
	private static EmbeddedStorageManager createStorageManager(final Path storageDirectory)
	{
		final NioFileSystem fileSystem = NioFileSystem.New();
		return EmbeddedStorageFoundation.New()
			.setConfiguration(
				StorageConfiguration.Builder()
					.setStorageFileProvider(
						Storage.FileProviderBuilder(fileSystem)
							.setDirectory(fileSystem.ensureDirectoryPath(storageDirectory.toAbsolutePath().toString()))
							.createFileProvider()
					)
					.createConfiguration()
			)
			.createEmbeddedStorageManager();	
	}
	
	private static EmbeddedStorageManager createStorageManager()
	{
		return EmbeddedStorageFoundation.New()
			.setConfiguration(
				StorageConfiguration.Builder().createConfiguration()
			)
			.createEmbeddedStorageManager();	
	}
}
