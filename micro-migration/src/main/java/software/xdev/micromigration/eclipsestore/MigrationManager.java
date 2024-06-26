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
package software.xdev.micromigration.eclipsestore;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;

import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.version.Versioned;
import software.xdev.micromigration.versionagnostic.VersionAgnosticMigrationManager;


/**
 * Specific implementation of the {@link VersionAgnosticMigrationManager}.
 *
 * @see VersionAgnosticMigrationManager
 */
public class MigrationManager extends VersionAgnosticMigrationManager<EmbeddedStorageManager>
{
	/**
	 * Much more complicated constructor than
	 * {@link MigrationManager#MigrationManager(Versioned, MicroMigrater, EmbeddedStorageManager)}
	 *
	 * @param currentVersionGetter which supplies the current version of the object to update.
	 * @param currentVersionSetter which sets the new version of the object in some membervariable. This Consumer is
	 *                               not
	 *                             supposed to store the version, but only save it in some membervariable to be stored
	 *                             after.
	 * @param currentVersionStorer which is supposed to store the new version of the object somewhere in the datastore.
	 * @param migrater             does the actual migration with the given {@link VersionAgnosticMigrationScript}
	 * @param storageManager       for the {@link VersionAgnosticMigrationScript}s to use. Is not used for the storing
	 *                             of the new version.
	 */
	public MigrationManager(
		final Supplier<MigrationVersion> currentVersionGetter,
		final Consumer<MigrationVersion> currentVersionSetter,
		final Consumer<MigrationVersion> currentVersionStorer,
		final MicroMigrater migrater,
		final EmbeddedStorageManager storageManager
	)
	{
		super(
			currentVersionGetter,
			currentVersionSetter,
			currentVersionStorer,
			migrater,
			new MigrationEmbeddedStorageManager(storageManager, migrater));
	}
	
	/**
	 * Simple Constructor.
	 *
	 * @param versionedObject which provides getter and setter for the current version. This object will be stored
	 *                           after
	 *                        the {@link VersionAgnosticMigrationScript}s are executed.
	 * @param migrater        does the actual migration with the given {@link VersionAgnosticMigrationScript}
	 * @param storageManager  for the {@link VersionAgnosticMigrationScript}s to use. Is not used for the storing of
	 *                          the
	 *                        new version.
	 */
	public MigrationManager(
		final Versioned versionedObject,
		final MicroMigrater migrater,
		final EmbeddedStorageManager storageManager
	)
	{
		super(versionedObject, migrater, new MigrationEmbeddedStorageManager(storageManager, migrater));
	}
}
