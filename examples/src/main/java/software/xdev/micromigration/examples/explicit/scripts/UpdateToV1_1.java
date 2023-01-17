package software.xdev.micromigration.examples.explicit.scripts;

import java.util.Date;
import java.util.logging.Logger;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.microstream.MigrationScript;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;


public class UpdateToV1_1 implements MigrationScript<String>
{
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(1,1);
	}
	
	@Override
	public void migrate(final Context<String, MigrationEmbeddedStorageManager> context)
	{
		Logger.getGlobal().info("Update " + this.getTargetVersion().toString() + " executed.");
		context.getStorageManager().setRoot("Hello World! @ " + new Date() + " Update 1.1");
	}
}
