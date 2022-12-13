package software.xdev.micromigration.examples.practical.embedded;

import software.xdev.micromigration.examples.practical.v1AndHigher.Address;
import software.xdev.micromigration.microstream.MigrationScript;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.examples.practical.v1AndHigher.BusinessBranch;
import software.xdev.micromigration.examples.practical.v1AndHigher.Customer;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


public class UpdateToV1_0 implements MigrationScript<software.xdev.micromigration.examples.practical.v0.BusinessBranch>
{
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(1,0);
	}
	
	@Override
	public void migrate(Context<software.xdev.micromigration.examples.practical.v0.BusinessBranch, EmbeddedStorageManager> context)
	{
		System.out.println("Executing Script for v1.0...");
		software.xdev.micromigration.examples.practical.v0.BusinessBranch oldBranch = context.getMigratingObject();
		BusinessBranch newBranch =
				new BusinessBranch();
		for (software.xdev.micromigration.examples.practical.v0.Customer oldCustomer : oldBranch.customers)
		{
			Customer newCustomer =
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
		System.out.println("Done executing Script for v1.0");
	}
}
