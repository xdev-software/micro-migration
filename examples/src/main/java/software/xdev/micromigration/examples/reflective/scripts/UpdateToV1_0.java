package software.xdev.micromigration.examples.reflective.scripts;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.microstream.MigrationScript;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;

import java.util.Date;
import java.util.logging.Logger;


public class UpdateToV1_0 implements MigrationScript<String>
{
	@Override
	public MigrationVersion getTargetVersion()
	{
		return new MigrationVersion(1,0);
	}

	@Override
	public void migrate(Context<String, MigrationEmbeddedStorageManager> context)
	{
		Logger.getGlobal().info("Update " + getTargetVersion().toString() + " executed.");
		context.getStorageManager().setRoot("Hello World! @ " + new Date() + " Update 1.0");
	}
}
