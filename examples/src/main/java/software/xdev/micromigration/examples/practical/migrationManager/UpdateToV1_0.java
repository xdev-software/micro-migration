package software.xdev.micromigration.examples.practical.migrationManager;

import software.xdev.micromigration.examples.practical.v0.BusinessBranch;
import software.xdev.micromigration.examples.practical.v0.Customer;
import software.xdev.micromigration.examples.practical.v1AndHigher.Address;
import software.xdev.micromigration.microstream.v5.MigrationScriptV5;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.version.VersionedObject;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


public class UpdateToV1_0 implements MigrationScriptV5<VersionedObject<Object>>
{
	@Override
	public MigrationVersion getTargetVersion()
	{
		return new MigrationVersion(1,0);
	}

	@Override
	public void migrate(Context<VersionedObject<Object>, EmbeddedStorageManager> context)
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
