package software.xdev.micromigration.examples.explicit;

import java.util.Date;

import software.xdev.micromigration.microstream.v5.MigrationEmbeddedStorage;
import software.xdev.micromigration.microstream.v5.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.examples.explicit.scripts.UpdateToV1_0;
import software.xdev.micromigration.examples.explicit.scripts.UpdateToV1_1;
import software.xdev.micromigration.migrater.ExplicitMigrater;


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
