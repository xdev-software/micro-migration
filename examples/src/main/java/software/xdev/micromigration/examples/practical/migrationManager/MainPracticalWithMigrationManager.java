package software.xdev.micromigration.examples.practical.migrationManager;

import software.xdev.micromigration.microstream.MigrationManager;
import software.xdev.micromigration.examples.practical.v0.BusinessBranch;
import software.xdev.micromigration.examples.practical.v0.Customer;
import software.xdev.micromigration.migrater.ExplicitMigrater;
import software.xdev.micromigration.version.VersionedObject;
import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


/**
 * A practical example of usage in a few steps:
 * <ul>
 * <li> v0.0: Storage is created without any updates. Only stores a new {@link BusinessBranch}
 * <li> v1.0: The BusinessBranch has a new implementation {@link software.xdev.micromigration.examples.practical.v1AndHigher.BusinessBranch}.
 * The old branch is converted to the new implementation through the {@link UpdateToV1_0} script.
 * <li> v2.0: A new customer is added through the {@link UpdateToV2_0} script.
 * </ul>
 * The storage is restarted after every update to simulate a complete lifecycle of the datastore.
 * @author Johannes Rabauer
 *
 */
public class MainPracticalWithMigrationManager 
{
	/**
	 * Suppressed Warning "unchecked" because it is given, that the correct object is returned.
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) 
	{
		//V0.0
		try(EmbeddedStorageManager storageManager = EmbeddedStorage.start())
		{
			VersionedObject<BusinessBranch> versionedBranch = new VersionedObject<>(BusinessBranch.createDummyBranch());
			storageManager.setRoot(versionedBranch);
			storageManager.storeRoot();
			System.out.println(storageManager.root().toString());
		}
		
		
		//V1.0
		try(EmbeddedStorageManager storageManager = EmbeddedStorage.start())
		{
			final ExplicitMigrater migraterWithV1 = new ExplicitMigrater(new UpdateToV1_0());
			VersionedObject<BusinessBranch> versionedBranch = (VersionedObject<BusinessBranch>)storageManager.root();
			new MigrationManager(versionedBranch, migraterWithV1, storageManager)
				.migrate(versionedBranch);
			System.out.println(storageManager.root().toString());
		}
		
		
		//V2.0
		try(EmbeddedStorageManager storageManager = EmbeddedStorage.start())
		{
			final ExplicitMigrater migraterWithV2 = new ExplicitMigrater(new UpdateToV1_0(), new UpdateToV2_0());
			VersionedObject<BusinessBranch> versionedBranch = (VersionedObject<BusinessBranch>)storageManager.root();
			new MigrationManager(versionedBranch, migraterWithV2, storageManager)
				.migrate(versionedBranch);
			System.out.println(storageManager.root().toString());
		}
	}
}
