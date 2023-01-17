package software.xdev.micromigration.examples.practical.embedded;

import java.util.logging.Logger;

import software.xdev.micromigration.examples.practical.v1AndHigher.Address;
import software.xdev.micromigration.examples.practical.v1AndHigher.BusinessBranch;
import software.xdev.micromigration.examples.practical.v1AndHigher.Customer;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.microstream.MigrationScript;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;


public class UpdateToV1_0 implements MigrationScript<software.xdev.micromigration.examples.practical.v0.BusinessBranch>
{
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(1,0);
	}
	
	@Override
	public void migrate(final Context<software.xdev.micromigration.examples.practical.v0.BusinessBranch, MigrationEmbeddedStorageManager> context)
	{
		Logger.getGlobal().info("Executing Script for v1.0...");
		final software.xdev.micromigration.examples.practical.v0.BusinessBranch oldBranch = context.getMigratingObject();
		final BusinessBranch newBranch =
				new BusinessBranch();
		for (final software.xdev.micromigration.examples.practical.v0.Customer oldCustomer : oldBranch.customers)
		{
			final Customer newCustomer =
					new Customer();
			newCustomer.name = oldCustomer.name;
			newCustomer.address = new Address();
			newCustomer.address.number = oldCustomer.number;
			newCustomer.address.street = oldCustomer.street;
			newCustomer.address.city   = oldCustomer.city  ;
			newBranch.customers.add(newCustomer);
		}
		context.getStorageManager().setRoot(newBranch);
		context.getStorageManager().storeRoot();
		Logger.getGlobal().info("Done executing Script for v1.0");
	}
}
