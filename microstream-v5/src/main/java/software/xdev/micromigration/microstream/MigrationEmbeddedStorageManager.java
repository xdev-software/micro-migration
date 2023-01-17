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

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticMigrationEmbeddedStorageManager;
import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.version.Versioned;


/**
 * Specific implementation of the {@link VersionAgnosticMigrationEmbeddedStorageManager} for one
 * specific MicroStream version.
 *
 * @see VersionAgnosticMigrationEmbeddedStorageManager
 *
 * @author Johannes Rabauer
 *
 */
public class MigrationEmbeddedStorageManager
	extends VersionAgnosticMigrationEmbeddedStorageManager<MigrationEmbeddedStorageManager, EmbeddedStorageManager>
{
	/**
	 * @param nativeManager which will be used as the underlying storage manager. 
	 * Almost all methods are only rerouted to this native manager. 
	 * Only {@link #start()}, {@link #root()} and {@link #setRoot(Object)} are intercepted 
	 * and a {@link Versioned} is placed between the requests.
	 * @param migrater which is used as source for the migration scripts
	 */
	public MigrationEmbeddedStorageManager(
		EmbeddedStorageManager nativeManager,
		MicroMigrater          migrater
	)
	{
		super(new TunnelingEmbeddedStorageManager(nativeManager), migrater);
	}
}
