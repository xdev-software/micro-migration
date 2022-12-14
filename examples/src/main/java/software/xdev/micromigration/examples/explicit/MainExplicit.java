package software.xdev.micromigration.examples.explicit;

import software.xdev.micromigration.examples.explicit.scripts.UpdateToV1_0;
import software.xdev.micromigration.examples.explicit.scripts.UpdateToV1_1;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorage;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.microstream.MigrationScript;
import software.xdev.micromigration.migrater.ExplicitMigrater;

import java.util.Date;

/**
 * The most basic usage of micro migration.
 * Here two {@link MigrationScript}s are explicitly registered
 * and subsequently executed. Easy.
 *
 * @author Johannes Rabauer
 *
 */
public class MainExplicit
{
	public static void main(String[] args) 
	{
		final ExplicitMigrater migrater = new ExplicitMigrater(
				new UpdateToV1_0(),
				new UpdateToV1_1()
		);
		final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(migrater);
		System.out.println(storageManager.root());
		if(storageManager.root() == null)
		{
			storageManager.setRoot("Hello World! @ " + new Date());
		}
		storageManager.storeRoot();
		storageManager.shutdown();
	}
}
