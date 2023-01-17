package software.xdev.micromigration.examples.notification;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorage;
import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.migrater.ExplicitMigrater;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;

import java.util.Date;
import java.util.logging.Logger;


/**
 * Shows the basic registration of migration notifications
 * ({@link ScriptExecutionNotification}).
 *
 * @author Johannes Rabauer
 */
public class MainNotification
{
	public static void main(String[] args) 
	{
		final ExplicitMigrater migrater = new ExplicitMigrater(
				new MainNotification.UpdateToV1_0()
		);
		migrater.registerNotificationConsumer(
			scriptExecutionNotification -> Logger.getGlobal().info("Script " + scriptExecutionNotification.getExecutedScript().getClass().getSimpleName() + " executed.")
		);
		final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(migrater);
		Logger.getGlobal().info(storageManager.root().toString());
		if(storageManager.root() == null)
		{
			storageManager.setRoot("Hello World! @ " + new Date());
		}
		storageManager.storeRoot();
		storageManager.shutdown();
	}

	static class UpdateToV1_0 implements
		software.xdev.micromigration.scripts.VersionAgnosticMigrationScript<String, MigrationEmbeddedStorageManager>
	{
		@Override
		public MigrationVersion getTargetVersion()
		{
			return new MigrationVersion(1,0);
		}

		@Override
		public void migrate(Context<String, MigrationEmbeddedStorageManager> context)
		{
			context.getStorageManager().setRoot("Hello World! @ " + new Date() + " Update 1.0");
		}
	}
}
