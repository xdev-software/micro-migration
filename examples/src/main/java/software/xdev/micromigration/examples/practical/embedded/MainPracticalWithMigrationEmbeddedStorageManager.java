package software.xdev.micromigration.examples.practical.embedded;

import software.xdev.micromigration.microstream.v5.MigrationEmbeddedStorage;
import software.xdev.micromigration.microstream.v5.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.examples.practical.v0.BusinessBranch;
import software.xdev.micromigration.examples.practical.v0.Customer;
import software.xdev.micromigration.migrater.ExplicitMigrater;


/**
 * A practical example of usage in a few steps:
 * <ul>
 * <li> v0.0: Storage is created without any updates. Only stores a new {@link BusinessBranch}
 * <li> v1.0: The BusinessBranch has a new implementation {@link BusinessBranch}.
 * The old branch is converted to the new implementation through the {@link UpdateToV1_0} script.
 * <li> v2.0: A new customer is added through the {@link UpdateToV2_0} script.
 * </ul>
 * The storage is restarted after every update to simulate a complete lifecycle of the datastore.
 * @author Johannes Rabauer
 *
 */
public class MainPracticalWithMigrationEmbeddedStorageManager 
{
	public static void main(String[] args) 
	{
		//V0.0
		final ExplicitMigrater emptyMigrater = new ExplicitMigrater();
		try(MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(emptyMigrater))
		{
			storageManager.setRoot(createDummyBranch());
			storageManager.storeRoot();
			System.out.println(storageManager.root().toString());
		}
		
		
		//V1.0
		final ExplicitMigrater migraterWithV1 = new ExplicitMigrater(new UpdateToV1_0());
		try(MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(migraterWithV1))
		{
			System.out.println(storageManager.root().toString());
		}
		
		
		//V2.0
		final ExplicitMigrater migraterWithV2 = new ExplicitMigrater(
				new UpdateToV1_0(),
				new UpdateToV2_0()
		);
		try(MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(migraterWithV2))
		{
			System.out.println(storageManager.root().toString());
		}
	}
	
	private static BusinessBranch createDummyBranch()
	{
		BusinessBranch branch = new BusinessBranch();
		Customer customer1 = new Customer();
		customer1.name   = "Mick Fleetwood";
		customer1.number = 1;
		customer1.street = "Fleetwood Street";
		customer1.city   = "Redruth";
		branch.customers.add(customer1);
		Customer customer2 = new Customer();
		customer2.name   = "Lindsey Buckingham";
		customer2.number = 2;
		customer2.street = "Mac Street";
		customer2.city   = "Palo Alto";
		branch.customers.add(customer2);
		return branch;
	}
}
