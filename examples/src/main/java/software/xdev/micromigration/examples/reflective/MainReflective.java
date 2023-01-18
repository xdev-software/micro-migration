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
package software.xdev.micromigration.examples.reflective;

import java.util.Date;
import java.util.logging.Logger;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorage;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.migrater.ReflectiveMigrater;
import software.xdev.micromigration.migrater.ScriptInstantiationException;

/**
 * Shows the usage of the {@link ReflectiveMigrater}. Very simple.
 *
 * @author Johannes Rabauer
 */
public class MainReflective
{
	public static void main(final String[] args)
	{
		try {
			final ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.examples.reflective.scripts");
			final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(migrater);
			Logger.getGlobal().info(storageManager.root().toString());
			if(storageManager.root() == null)
			{
				storageManager.setRoot("Hello World! @ " + new Date());
			}
			storageManager.storeRoot();
			storageManager.shutdown();			
		} 
		catch (final IllegalArgumentException | SecurityException | ScriptInstantiationException e)
		{
			throw new Error("Could not initiate migration script", e);
		}
	}
}
