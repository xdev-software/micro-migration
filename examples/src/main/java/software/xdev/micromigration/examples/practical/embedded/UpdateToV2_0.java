package software.xdev.micromigration.examples.practical.embedded;

import software.xdev.micromigration.examples.practical.v1AndHigher.BusinessBranch;
import software.xdev.micromigration.examples.practical.v1AndHigher.Customer;
import software.xdev.micromigration.microstream.v5.MigrationScriptV5;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


public class UpdateToV2_0 implements MigrationScriptV5<BusinessBranch>
{
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(2,0);
	}
	
	@Override
	public void migrate(Context<BusinessBranch, EmbeddedStorageManager> context)
	{
		System.out.println("Executing Script for v2.0...");
		final BusinessBranch branch = context.getMigratingObject();
		Customer newCustomer = new Customer();
		newCustomer.name = "Stevie Nicks";
		newCustomer.address.number = 5;
		newCustomer.address.street = "Fleetwood Street";
		newCustomer.address.city = "Phoenix";
		branch.customers.add(newCustomer);
		context.getStorageManager().store(branch.customers);
		System.out.println("Done executing Script for v2.0");
	}
}
