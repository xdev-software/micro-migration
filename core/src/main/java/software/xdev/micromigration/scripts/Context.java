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

/**
 * Container that holds necessary information for the execution of an {@link VersionAgnosticMigrationScript}
 * 
 * @author Johannes Rabauer
 */
public class Context<T, E>
{
	private final T migratingObject;
	private final E storageManager ;

	/**
	 * @param migratingObject that must be migrated to a new version
	 * @param storageManager where the migratingObject is stored
	 */
	public Context(
		final T                                        migratingObject,
		final E storageManager
	) 
	{
		super();
		this.migratingObject = migratingObject;
		this.storageManager  = storageManager;
	}

	/**
	 * @return the current object where the migration is executed upon
	 */
	public T getMigratingObject() 
	{
		return migratingObject;
	}
	
	/**
	 * @return the responsible storage manager for the migrating object
	 */
	public E getStorageManager()
	{
		return storageManager;
	}
}
