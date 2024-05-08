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
package software.xdev.micromigration.eclipsestore.util;

import software.xdev.micromigration.eclipsestore.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


public class MicroMigrationScriptDummy
	implements VersionAgnosticMigrationScript<Object, MigrationEmbeddedStorageManager>
{
	private final MigrationVersion version;
	
	public MicroMigrationScriptDummy(final MigrationVersion version)
	{
		this.version = version;
	}
	
	@Override
	public MigrationVersion getTargetVersion()
	{
		return this.version;
	}
	
	@Override
	public void migrate(final Context<Object, MigrationEmbeddedStorageManager> context)
	{
		// Don't do anything.
	}
}
