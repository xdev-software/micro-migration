package software.xdev.micromigration.examples.practical.migrationManager;

import software.xdev.micromigration.examples.practical.v0.BusinessBranch;
import software.xdev.micromigration.examples.practical.v0.Customer;
import software.xdev.micromigration.examples.practical.v1AndHigher.Address;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.version.VersionedObject;


public class UpdateToV1_0 implements
	software.xdev.micromigration.scripts.VersionAgnosticMigrationScript<VersionedObject<Object>, MigrationEmbeddedStorageManager>
{
	@Override
	public MigrationVersion getTargetVersion()
	{
		return new MigrationVersion(1,0);
	}

	@Override
	public void migrate(Context<VersionedObject<Object>, MigrationEmbeddedStorageManager> context)
	{
		System.out.println("Executing Script for v1.0...");
		VersionedObject<Object> versionedBranch = context.getMigratingObject();
		BusinessBranch oldBranch =
				(BusinessBranch) versionedBranch.getObject();
		software.xdev.micromigration.examples.practical.v1AndHigher.BusinessBranch newBranch =
				new software.xdev.micromigration.examples.practical.v1AndHigher.BusinessBranch();
		for (Customer oldCustomer : oldBranch.customers)
		{
			software.xdev.micromigration.examples.practical.v1AndHigher.Customer newCustomer =
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
		System.out.println("Done executing Script for v1.0");
	}
}
