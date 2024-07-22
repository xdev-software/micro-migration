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
package software.xdev.micromigration.version;

import java.util.Objects;


/**
 * Simple container to hold a specific object and a correlating version for it.
 *
 * @param <T> type of the object that's contained
 */
public class VersionedObject<T> implements Versioned
{
	private MigrationVersion currentVersion;
	private T actualObject;
	
	/**
	 * @param actualObject set the actual object which is versioned
	 */
	public VersionedObject(final T actualObject)
	{
		this.actualObject = actualObject;
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
	 * @param actualObject which is versioned
	 */
	public void setObject(final T actualObject)
	{
		this.actualObject = actualObject;
	}
	
	/**
	 * @return the actual object which is versioned
	 */
	public T getObject()
	{
		return this.actualObject;
	}
	
	@Override
	public String toString()
	{
		return this.currentVersion.toString() + "\n" + this.actualObject;
	}
}
