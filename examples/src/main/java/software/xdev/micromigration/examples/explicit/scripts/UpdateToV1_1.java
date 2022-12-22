package software.xdev.micromigration.examples.explicit.scripts;

import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;

import java.util.Date;


public class UpdateToV1_1 implements
	software.xdev.micromigration.scripts.VersionAgnosticMigrationScript<String, software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager>
{
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(1,1);
	}
	
	@Override
	public void migrate(Context<String, MigrationEmbeddedStorageManager> context)
	{
		System.out.println("Update " + getTargetVersion().toString() + " executed.");
		context.getStorageManager().setRoot("Hello World! @ " + new Date() + " Update 1.1");
	}
}
