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

import java.util.Objects;

import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


/**
 * Exception that should be used if two scripts with the same version exist.
 */
public class VersionAlreadyRegisteredException extends RuntimeException
{
	/**
	 * The already registered script with the same version
	 */
	private final MigrationVersion alreadyRegisteredVersion;
	/**
	 * The version of the already registered script
	 */
	private final VersionAgnosticMigrationScript<?, ?> alreadyRegisteredScript;
	/**
	 * The script with the same version as {@link #alreadyRegisteredScript}, which should be registered as well
	 */
	private final VersionAgnosticMigrationScript<?, ?> newScriptToRegister;
	
	/**
	 * @param alreadyRegisteredVersion The version of the already registered script
	 * @param alreadyRegisteredScript  The already registered script with the same version
	 * @param newScriptToRegister      The script with the same version as alreadyRegisteredScript, which should be
	 *                                 registered as well
	 */
	public VersionAlreadyRegisteredException(
		final MigrationVersion alreadyRegisteredVersion,
		final VersionAgnosticMigrationScript<?, ?> alreadyRegisteredScript,
		final VersionAgnosticMigrationScript<?, ?> newScriptToRegister
	)
	{
		super("Version " + alreadyRegisteredVersion.toString()
			+ " is already registered. Versions must be unique within the migrater.");
		this.alreadyRegisteredVersion = Objects.requireNonNull(alreadyRegisteredVersion);
		this.alreadyRegisteredScript = Objects.requireNonNull(alreadyRegisteredScript);
		this.newScriptToRegister = Objects.requireNonNull(newScriptToRegister);
	}
	
	/**
	 * @return the version of the already registered script
	 */
	public MigrationVersion getAlreadyRegisteredVersion()
	{
		return this.alreadyRegisteredVersion;
	}
	
	/**
	 * @return the already registered script with the same version
	 */
	public VersionAgnosticMigrationScript<?, ?> getAlreadyRegisteredScript()
	{
		return this.alreadyRegisteredScript;
	}
	
	/**
	 * @return the script with the same version as {@link #getAlreadyRegisteredScript()}, which should be registered as
	 * well
	 */
	public VersionAgnosticMigrationScript<?, ?> getNewScriptToRegister()
	{
		return this.newScriptToRegister;
	}
}
