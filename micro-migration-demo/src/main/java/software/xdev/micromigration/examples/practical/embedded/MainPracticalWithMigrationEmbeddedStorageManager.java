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
package software.xdev.micromigration.examples.practical.embedded;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.xdev.micromigration.eclipsestore.MigrationEmbeddedStorage;
import software.xdev.micromigration.eclipsestore.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.examples.practical.v0.BusinessBranch;
import software.xdev.micromigration.migrater.ExplicitMigrater;


/**
 * A practical example of usage in a few steps:
 * <ul>
 * <li> v0.0: Storage is created without any updates. Only stores a new {@link BusinessBranch}
 * <li> v1.0: The BusinessBranch has a new implementation {@link BusinessBranch}.
 * The old branch is converted to the new implementation through the {@link UpdateToV1_0} script.
 * <li> v2.0: A new customer is added through the {@link UpdateToV2_0} script.
 * </ul>
 * The storage is restarted after every update to simulate a complete lifecycle of the datastore.
 */
public final class MainPracticalWithMigrationEmbeddedStorageManager
{
	private static final Logger LOG = LoggerFactory.getLogger(MainPracticalWithMigrationEmbeddedStorageManager.class);
	
	public static void main(final String[] args)
	{
		// V0.0
		final ExplicitMigrater emptyMigrater = new ExplicitMigrater();
		try(final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(emptyMigrater))
		{
			storageManager.setRoot(BusinessBranch.createDummyBranch());
			storageManager.storeRoot();
			LOG.info(storageManager.root().toString());
		}
		
		// V1.0
		final ExplicitMigrater migraterWithV1 = new ExplicitMigrater(new UpdateToV1_0());
		try(final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(migraterWithV1))
		{
			LOG.info(storageManager.root().toString());
		}
		
		// V2.0
		final ExplicitMigrater migraterWithV2 = new ExplicitMigrater(
			new UpdateToV1_0(),
			new UpdateToV2_0()
		);
		try(final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(migraterWithV2))
		{
			LOG.info(storageManager.root().toString());
		}
	}
	
	private MainPracticalWithMigrationEmbeddedStorageManager()
	{
	}
}
