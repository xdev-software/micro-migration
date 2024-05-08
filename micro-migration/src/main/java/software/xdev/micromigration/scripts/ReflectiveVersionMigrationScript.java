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
package software.xdev.micromigration.scripts;

import java.util.ArrayList;
import java.util.List;

import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.versionagnostic.VersionAgnosticMigrationEmbeddedStorageManager;


/**
 * Script which creates the target version of the script through the class name.
 * <p>
 * Class name has to be in the scheme:<br>
 * <code>
 * vM_Classname<br> vM_m_Classname<br> vM_m_m_Classname<br>
 * </code>
 * Where <code>v</code> is short for version and is a constant (just a char),<br>
 * <code>M</code> is a integer for the major version,<br>
 * <code>m</code> is a integer for the minor version<br>
 * <code>Classname</code> is a custom String that the user can choose.<br>
 * This scheme can basically be extended infinetly. For example: <code>v1_1_2_2_MyUpdateScript</code>
 * <p>
 * Therefore the character <code>_</code> can only be used as a seperator of versions and may not be used for other
 * purposes.
 * <p>
 * If the class name has the wrong format, an {@link Error} is thrown.
 */
public abstract class ReflectiveVersionMigrationScript<
	T,
	E extends VersionAgnosticMigrationEmbeddedStorageManager<?, ?>>
	implements VersionAgnosticMigrationScript<T, E>
{
	private static final char PREFIX = 'v';
	private static final String VERSION_SEPERATOR = "_";
	private static final String WRONG_FORMAT_ERROR_MESSAGE =
		"Script has invalid class name. Either rename the class to a valid script class name, or implement method "
			+ "getTargetVersion().";
	
	private final MigrationVersion version;
	
	/**
	 * @throws Error if the class name has the wrong format
	 */
	protected ReflectiveVersionMigrationScript()
	{
		this.version = this.createTargetVersionFromClassName();
	}
	
	private MigrationVersion createTargetVersionFromClassName()
	{
		final String implementationClassName = this.getClass().getSimpleName();
		if(PREFIX != implementationClassName.charAt(0))
		{
			throw new IllegalArgumentException(WRONG_FORMAT_ERROR_MESSAGE);
		}
		final String implementationClassNameWithoutPrefix = implementationClassName.substring(1);
		final String[] classNameParts = implementationClassNameWithoutPrefix.split(VERSION_SEPERATOR);
		if(classNameParts.length < 2)
		{
			throw new IllegalArgumentException(WRONG_FORMAT_ERROR_MESSAGE);
		}
		try
		{
			final List<Integer> versionNumbers = new ArrayList<>();
			for(int i = 0; i < classNameParts.length - 1; i++)
			{
				versionNumbers.add(Integer.parseInt(classNameParts[i]));
			}
			return new MigrationVersion(versionNumbers);
		}
		catch(final NumberFormatException e)
		{
			throw new IllegalArgumentException(WRONG_FORMAT_ERROR_MESSAGE, e);
		}
	}
	
	@Override
	public MigrationVersion getTargetVersion()
	{
		return this.version;
	}
}
