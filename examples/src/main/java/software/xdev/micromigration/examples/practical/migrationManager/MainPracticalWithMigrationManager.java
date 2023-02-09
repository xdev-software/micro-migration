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
package software.xdev.micromigration.examples.practical.migrationManager;

import java.util.logging.Logger;

import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.xdev.micromigration.examples.practical.v0.BusinessBranch;
import software.xdev.micromigration.microstream.MigrationManager;
import software.xdev.micromigration.migrater.ExplicitMigrater;
import software.xdev.micromigration.version.VersionedObject;

/**
 * A practical example of usage in a few steps:
 * <ul>
 * <li> v0.0: Storage is created without any updates. Only stores a new {@link BusinessBranch}
 * <li> v1.0: The BusinessBranch has a new implementation {@link software.xdev.micromigration.examples.practical.v1AndHigher.BusinessBranch}.
 * The old branch is converted to the new implementation through the {@link UpdateToV1_0} script.
 * <li> v2.0: A new customer is added through the {@link UpdateToV2_0} script.
 * </ul>
 * The storage is restarted after every update to simulate a complete lifecycle of the datastore.
 * @author Johannes Rabauer
 *
 */
public class MainPracticalWithMigrationManager 
{
	/**
	 * Suppressed Warning "unchecked" because it is given, that the correct object is returned.
	 */
	@SuppressWarnings("unchecked")
	public static void main(final String[] args)
	{
		//V0.0
		try(final EmbeddedStorageManager storageManager = EmbeddedStorage.start())
		{
			final VersionedObject<BusinessBranch> versionedBranch = new VersionedObject<>(BusinessBranch.createDummyBranch());
			storageManager.setRoot(versionedBranch);
			storageManager.storeRoot();
			Logger.getGlobal().info(storageManager.root().toString());
		}
		
		
		//V1.0
		try(final EmbeddedStorageManager storageManager = EmbeddedStorage.start())
		{
			final ExplicitMigrater migraterWithV1 = new ExplicitMigrater(new UpdateToV1_0());
			final VersionedObject<BusinessBranch> versionedBranch = (VersionedObject<BusinessBranch>)storageManager.root();
			new MigrationManager(versionedBranch, migraterWithV1, storageManager)
				.migrate(versionedBranch);
			Logger.getGlobal().info(storageManager.root().toString());
		}
		
		
		//V2.0
		try(final EmbeddedStorageManager storageManager = EmbeddedStorage.start())
		{
			final ExplicitMigrater migraterWithV2 = new ExplicitMigrater(new UpdateToV1_0(), new UpdateToV2_0());
			final VersionedObject<BusinessBranch> versionedBranch = (VersionedObject<BusinessBranch>)storageManager.root();
			new MigrationManager(versionedBranch, migraterWithV2, storageManager)
				.migrate(versionedBranch);
			Logger.getGlobal().info(storageManager.root().toString());
		}
	}
}
