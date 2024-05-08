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
package software.xdev.micromigration.version;

import java.util.Objects;

/**
 * This class is inserted as the root of the MicroStream datastore and contains only the current version and the actual
 * root object.
 */
public class VersionedRoot implements Versioned
{
	private MigrationVersion currentVersion;
	private Object actualRoot;
	
	/**
	 * @param actualRoot which is stored in the datastore and defined by the user
	 */
	public VersionedRoot(final Object actualRoot)
	{
		this.actualRoot = actualRoot;
		this.currentVersion = new MigrationVersion(0);
	}
	
	@Override
	public void setVersion(final MigrationVersion version)
	{
		Objects.requireNonNull(version);
		this.currentVersion = version;
	}
	
	@Override
	public MigrationVersion getVersion()
	{
		return this.currentVersion;
	}
	
	/**
	 * @param actualRoot which is stored in the datastore and defined by the user
	 */
	public void setRoot(final Object actualRoot)
	{
		this.actualRoot = actualRoot;
	}

	/**
	 * @return the actual root, that's defined by the user
	 */
	public Object getRoot()
	{
		return this.actualRoot;
	}
}
