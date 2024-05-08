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
package software.xdev.micromigration.scripts;

import java.util.Comparator;

import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.versionagnostic.VersionAgnosticMigrationEmbeddedStorageManager;


/**
 * Interface for scripts to migrate / update datastores.
 * <p>
 * One script is supposed to bring a datastore from a lower version to the target version. After the
 * {@link VersionAgnosticMigrationScript#migrate(Context)} method is called, the target version is reached.
 */
public interface VersionAgnosticMigrationScript<T, E extends VersionAgnosticMigrationEmbeddedStorageManager<?, ?>>
{
	/**
	 * @return the version of the datastore after this script is executed.
	 */
	MigrationVersion getTargetVersion();
	
	/**
	 * Execute logic to migrate the given datastore to a newer version of the store. After executing the
	 * {@link #getTargetVersion()} is reached.
	 *
	 * @param context that holds necessary data for the migration
	 */
	void migrate(Context<T, E> context);
	
	/**
	 * Provides a {@link Comparator} that compares the {@link #getTargetVersion()} of the given scripts
	 */
	Comparator<VersionAgnosticMigrationScript<?, ?>> COMPARATOR =
		(o1, o2) -> MigrationVersion.COMPARATOR.compare(o1.getTargetVersion(), o2.getTargetVersion());
}
