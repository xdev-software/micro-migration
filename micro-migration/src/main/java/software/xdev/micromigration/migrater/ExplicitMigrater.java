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
package software.xdev.micromigration.migrater;

import java.util.TreeSet;

import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;


/**
 * Contains all the available scripts to migrate the datastore to a certain version.
 * <p>
 * This class needs explicit scripts which are then included in the migration process.
 */
public class ExplicitMigrater extends AbstractMigrater
{
	private final TreeSet<VersionAgnosticMigrationScript<?, ?>> sortedScripts = new TreeSet<>(
		VersionAgnosticMigrationScript.COMPARATOR);
	
	/**
	 * @param scripts are all the scripts that are executed, if the current version is lower than this of the
	 *                   script<br>
	 *                Versions of the scripts must be unique. That means that no version is allowed multiple times in
	 *                the migrater.
	 * @throws VersionAlreadyRegisteredException if two scripts have the same version
	 */
	@SuppressWarnings("PMD.UseArraysAsList")
	public ExplicitMigrater(final VersionAgnosticMigrationScript<?, ?>... scripts)
	{
		for(final VersionAgnosticMigrationScript<?, ?> script : scripts)
		{
			this.checkIfVersionIsAlreadyRegistered(script);
			this.sortedScripts.add(script);
		}
	}
	
	@Override
	public TreeSet<VersionAgnosticMigrationScript<?, ?>> getSortedScripts()
	{
		return this.sortedScripts;
	}
}
