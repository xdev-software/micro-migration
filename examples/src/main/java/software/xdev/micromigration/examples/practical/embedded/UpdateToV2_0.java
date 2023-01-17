package software.xdev.micromigration.examples.practical.embedded;

import java.util.logging.Logger;

import software.xdev.micromigration.examples.practical.v1AndHigher.BusinessBranch;
import software.xdev.micromigration.examples.practical.v1AndHigher.Customer;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.microstream.MigrationScript;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;


public class UpdateToV2_0 implements MigrationScript<BusinessBranch>
{
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(2,0);
	}
	
	@Override
	public void migrate(final Context<BusinessBranch, MigrationEmbeddedStorageManager> context)
	{
		Logger.getGlobal().info("Executing Script for v2.0...");
		final BusinessBranch branch = context.getMigratingObject();
		final Customer newCustomer = new Customer();
		newCustomer.name = "Stevie Nicks";
		newCustomer.address.number = 5;
		newCustomer.address.street = "Fleetwood Street";
		newCustomer.address.city = "Phoenix";
		branch.customers.add(newCustomer);
		context.getStorageManager().store(branch.customers);
		Logger.getGlobal().info("Done executing Script for v2.0");
	}
}
