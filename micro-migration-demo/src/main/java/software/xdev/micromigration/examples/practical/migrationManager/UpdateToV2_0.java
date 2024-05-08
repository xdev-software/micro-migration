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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.xdev.micromigration.eclipsestore.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.eclipsestore.MigrationScript;
import software.xdev.micromigration.examples.practical.v1AndHigher.BusinessBranch;
import software.xdev.micromigration.examples.practical.v1AndHigher.Customer;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.version.VersionedObject;


@SuppressWarnings({"checkstyle:TypeName", "java:S101"})
public class UpdateToV2_0 implements MigrationScript<VersionedObject<BusinessBranch>>
{
	private static final Logger LOG = LoggerFactory.getLogger(UpdateToV2_0.class);
	
	@Override
	public MigrationVersion getTargetVersion()
	{
		return new MigrationVersion(2, 0);
	}
	
	@Override
	public void migrate(final Context<VersionedObject<BusinessBranch>, MigrationEmbeddedStorageManager> context)
	{
		LOG.info("Executing Script for v2.0...");
		final VersionedObject<BusinessBranch> versionedBranch = context.getMigratingObject();
		final BusinessBranch branch = versionedBranch.getObject();
		final Customer newCustomer = new Customer();
		newCustomer.name = "Stevie Nicks";
		newCustomer.address.number = 5;
		newCustomer.address.street = "Fleetwood Street";
		newCustomer.address.city = "Phoenix";
		branch.customers.add(newCustomer);
		context.getStorageManager().store(branch.customers);
		LOG.info("Done executing Script for v2.0");
	}
}
