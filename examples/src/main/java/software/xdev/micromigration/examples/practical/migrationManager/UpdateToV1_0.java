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
