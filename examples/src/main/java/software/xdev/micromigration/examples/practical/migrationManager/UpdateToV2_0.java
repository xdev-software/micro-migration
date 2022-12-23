package software.xdev.micromigration.examples.practical.migrationManager;

import software.xdev.micromigration.examples.practical.v1AndHigher.BusinessBranch;
import software.xdev.micromigration.examples.practical.v1AndHigher.Customer;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.microstream.MigrationScript;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.version.VersionedObject;


public class UpdateToV2_0 implements MigrationScript<VersionedObject<BusinessBranch>>
{
	@Override
	public MigrationVersion getTargetVersion()
	{
		return new MigrationVersion(2,0);
	}

	@Override
	public void migrate(Context<VersionedObject<BusinessBranch>, MigrationEmbeddedStorageManager> context)
	{
		System.out.println("Executing Script for v2.0...");
		VersionedObject<BusinessBranch> versionedBranch = context.getMigratingObject();
		final BusinessBranch branch = versionedBranch.getObject();
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
