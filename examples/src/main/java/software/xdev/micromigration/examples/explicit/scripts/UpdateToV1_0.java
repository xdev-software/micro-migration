package software.xdev.micromigration.examples.explicit.scripts;

import java.util.Date;

import software.xdev.micromigration.microstream.v5.MigrationScriptV5;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


public class UpdateToV1_0 implements MigrationScriptV5<String>
{
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(1,0);
	}

	@Override
	public void migrate(Context<String, EmbeddedStorageManager> context)
	{
		System.out.println("Update " + getTargetVersion().toString() + " executed.");
		context.getStorageManager().setRoot("Hello World! @ " + new Date() + " Update 1.0");
	}
}
