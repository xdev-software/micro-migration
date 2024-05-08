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
package software.xdev.micromigration.examples.explicit;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.xdev.micromigration.eclipsestore.MigrationEmbeddedStorage;
import software.xdev.micromigration.eclipsestore.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.examples.explicit.scripts.UpdateToV1_0;
import software.xdev.micromigration.examples.explicit.scripts.UpdateToV1_1;
import software.xdev.micromigration.migrater.ExplicitMigrater;


/**
 * The most basic usage of micro migration. Here two {@link MigrationScript}s are explicitly registered and subsequently
 * executed. Easy.
 */
public final class MainExplicit
{
	private static final Logger LOG = LoggerFactory.getLogger(MainExplicit.class);
	
	public static void main(final String[] args)
	{
		final ExplicitMigrater migrater = new ExplicitMigrater(
			new UpdateToV1_0(),
			new UpdateToV1_1()
		);
		final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(migrater);
		LOG.info(storageManager.root().toString());
		if(storageManager.root() == null)
		{
			storageManager.setRoot("Hello World! @ " + new Date());
		}
		storageManager.storeRoot();
		storageManager.shutdown();
	}
	
	private MainExplicit()
	{
	}
}
