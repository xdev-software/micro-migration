package software.xdev.micromigration.examples.practical.embedded;

import java.util.logging.Logger;

import software.xdev.micromigration.examples.practical.v0.BusinessBranch;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorage;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
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
	public static void main(final String[] args)
	{
		//V0.0
		final ExplicitMigrater emptyMigrater = new ExplicitMigrater();
		try(final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(emptyMigrater))
		{
			storageManager.setRoot(BusinessBranch.createDummyBranch());
			storageManager.storeRoot();
			Logger.getGlobal().info(storageManager.root().toString());
		}
		
		
		//V1.0
		final ExplicitMigrater migraterWithV1 = new ExplicitMigrater(new UpdateToV1_0());
		try(final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(migraterWithV1))
		{
			Logger.getGlobal().info(storageManager.root().toString());
		}
		
		
		//V2.0
		final ExplicitMigrater migraterWithV2 = new ExplicitMigrater(
				new UpdateToV1_0(),
				new UpdateToV2_0()
		);
		try(final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(migraterWithV2))
		{
			Logger.getGlobal().info(storageManager.root().toString());
		}
	}
	

}
