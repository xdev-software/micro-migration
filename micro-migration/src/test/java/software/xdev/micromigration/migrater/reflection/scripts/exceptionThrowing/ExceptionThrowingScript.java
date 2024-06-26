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
package software.xdev.micromigration.migrater.reflection.scripts.exceptionThrowing;

import software.xdev.micromigration.eclipsestore.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


public class ExceptionThrowingScript implements VersionAgnosticMigrationScript<Object, MigrationEmbeddedStorageManager>
{
	public ExceptionThrowingScript() throws Exception
	{
		throw new Exception();
	}
	
	@Override
	public MigrationVersion getTargetVersion()
	{
		return new MigrationVersion(1);
	}
	
	@Override
	public void migrate(final Context<Object, MigrationEmbeddedStorageManager> context)
	{
		// Do nothing
	}
}
