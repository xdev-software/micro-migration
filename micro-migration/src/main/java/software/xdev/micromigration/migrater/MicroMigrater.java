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

import java.util.TreeSet;
import java.util.function.Consumer;

import software.xdev.micromigration.notification.ScriptExecutionNotificationWithScriptReference;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.versionagnostic.VersionAgnosticMigrationEmbeddedStorageManager;


/**
 * Executes all the available scripts to migrate the datastore to a certain version.
 */
public interface MicroMigrater
{
	/**
	 * @return all the contained {@link VersionAgnosticMigrationScript}s, sorted by their {@link MigrationVersion}
	 * ascending.
	 */
	TreeSet<? extends VersionAgnosticMigrationScript<?, ?>> getSortedScripts();
	
	/**
	 * Executes all the scripts that are available to the migrater. Only scripts with a higher target version than the
	 * given fromVersion are executed.<br> Scripts are executed one after another from the lowest to the highest
	 * version.
	 * <p>
	 * <b>Example:</b><br>
	 * Current version is 1.0.0<br> Scripts for v1.1.0, v2.0.0 and v1.2.1 are available<br> Scripts are chain executed
	 * like v1.1.0 then v1.2.1 then v2.0.0
	 *
	 * @param fromVersion    is the current version of the datastore. Scripts for lower versions then the fromVersion
	 *                       are not executed.
	 * @param storageManager is relayed to the scripts {@link VersionAgnosticMigrationScript#migrate(Context)} method.
	 *                       This way the script can call
	 *                       {@link VersionAgnosticMigrationEmbeddedStorageManager#store(Object)} or another method on
	 *                       the storage manager.
	 * @param root           is relayed to the scripts {@link VersionAgnosticMigrationScript#migrate(Context)} method.
	 *                       This way the script can change something within the root object.
	 * @param <E>            the {@link VersionAgnosticMigrationEmbeddedStorageManager} which contains the migrating
	 *                       object
	 * @return the target version of the last executed script
	 */
	<E extends VersionAgnosticMigrationEmbeddedStorageManager<?, ?>> MigrationVersion migrateToNewest(
		MigrationVersion fromVersion,
		E storageManager,
		Object root
	);
	
	/**
	 * Executes all the scripts that are available to the migrater until the given targetVersion is reached. Only
	 * scripts with a higher target version than the given fromVersion are executed.<br> Scripts are executed one after
	 * another from the lowest to the highest version.
	 * <p>
	 * <b>Example:</b><br>
	 * Current version is 1.0.0<br> Scripts for v1.1.0, v2.0.0 and v1.2.1 are available<br> Scripts are chain executed
	 * like v1.1.0 then v1.2.1 then v2.0.0
	 *
	 * @param fromVersion     is the current version of the datastore. Scripts for lower versions then the fromVersion
	 *                        are not executed.
	 * @param targetVersion   is the highest allowed script version. Scripts which have a higher version won't be
	 *                        exectued.
	 * @param storageManager  is relayed to the scripts {@link VersionAgnosticMigrationScript#migrate(Context)} method.
	 *                        This way the script can call EmbeddedStorageManager#store or another method on the
	 *                        storage
	 *                        manager.
	 * @param objectToMigrate is relayed to the scripts {@link VersionAgnosticMigrationScript#migrate(Context)} method.
	 *                        This way the script can change something within the object to migrate.
	 * @param <E>             the {@link VersionAgnosticMigrationEmbeddedStorageManager} which contains the migrating
	 *                        object
	 * @return the target version of the last executed script
	 */
	<E extends VersionAgnosticMigrationEmbeddedStorageManager<?, ?>> MigrationVersion migrateToVersion(
		MigrationVersion fromVersion,
		MigrationVersion targetVersion,
		E storageManager,
		Object objectToMigrate
	);
	
	/**
	 * Registers a callback to take action when a script is executed.
	 *
	 * @param notificationConsumer is executed when a script is used from this migrater.
	 */
	void registerNotificationConsumer(
		Consumer<ScriptExecutionNotificationWithScriptReference> notificationConsumer);
}
