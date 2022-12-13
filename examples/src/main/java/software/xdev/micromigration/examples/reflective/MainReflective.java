package software.xdev.micromigration.examples.reflective;

import java.util.Date;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorage;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.migrater.ReflectiveMigrater;
import software.xdev.micromigration.migrater.ScriptInstantiationException;


public class MainReflective
{
	public static void main(String[] args) 
	{
		try {
			final ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.examples.reflective.scripts");
			final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(migrater);
			System.out.println(storageManager.root());
			if(storageManager.root() == null)
			{
				storageManager.setRoot("Hello World! @ " + new Date());
			}
			storageManager.storeRoot();
			storageManager.shutdown();			
		} 
		catch (IllegalArgumentException | SecurityException | ScriptInstantiationException e)
		{
			throw new Error("Could not initiate migration script", e);
		}
	}
}
