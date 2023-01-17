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

import software.xdev.micromigration.examples.practical.v0.BusinessBranch;
import software.xdev.micromigration.examples.practical.v0.Customer;
import software.xdev.micromigration.examples.practical.v1AndHigher.Address;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.microstream.MigrationScript;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.version.VersionedObject;


public class UpdateToV1_0 implements MigrationScript<VersionedObject<Object>>
{
	@Override
	public MigrationVersion getTargetVersion()
	{
		return new MigrationVersion(1,0);
	}

	@Override
	public void migrate(final Context<VersionedObject<Object>, MigrationEmbeddedStorageManager> context)
	{
		Logger.getGlobal().info("Executing Script for v1.0...");
		final VersionedObject<Object> versionedBranch = context.getMigratingObject();
		final BusinessBranch oldBranch =
				(BusinessBranch) versionedBranch.getObject();
		final software.xdev.micromigration.examples.practical.v1AndHigher.BusinessBranch newBranch =
				new software.xdev.micromigration.examples.practical.v1AndHigher.BusinessBranch();
		for (final Customer oldCustomer : oldBranch.customers)
		{
			final software.xdev.micromigration.examples.practical.v1AndHigher.Customer newCustomer =
					new software.xdev.micromigration.examples.practical.v1AndHigher.Customer();
			newCustomer.name = oldCustomer.name;
			newCustomer.address = new Address();
			newCustomer.address.number = oldCustomer.number;
			newCustomer.address.street = oldCustomer.street;
			newCustomer.address.city   = oldCustomer.city  ;
			newBranch.customers.add(newCustomer);
		}
		versionedBranch.setObject(newBranch);
		context.getStorageManager().store(versionedBranch);
		Logger.getGlobal().info("Done executing Script for v1.0");
	}
}
