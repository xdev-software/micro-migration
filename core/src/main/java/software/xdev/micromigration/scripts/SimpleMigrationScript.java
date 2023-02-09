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

import java.util.function.Consumer;

import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticMigrationEmbeddedStorageManager;
import software.xdev.micromigration.version.MigrationVersion;

/**
 * Provides a simple way to create a migration script with the necessary version
 * and {@link Consumer}.
 * 
 * @author Johannes Rabauer
 *
 */
public class SimpleMigrationScript<E extends VersionAgnosticMigrationEmbeddedStorageManager<?,?>> extends SimpleTypedMigrationScript<Object,E>
{
	/**
	 * @param targetVersion to which the script is updating the object
	 * @param consumer which consumes the object and updates it to the target version
	 */
	public SimpleMigrationScript(
		final MigrationVersion targetVersion ,
		final Consumer<Context<Object,E>> consumer
	) 
	{
		super(targetVersion, consumer);
	}
}
