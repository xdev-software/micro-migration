package software.xdev.micromigration.migrater.scripts.noCorrectConstructor;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;

import java.util.logging.Logger;


public class NoCorrectConstructorScript implements
	software.xdev.micromigration.scripts.VersionAgnosticMigrationScript<Object, MigrationEmbeddedStorageManager>
{
	private final String argument;
	
	public NoCorrectConstructorScript(String argument)
	{
		this.argument = argument;
	}
	
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(1);
	}

	@Override
	public void migrate(Context<Object, MigrationEmbeddedStorageManager> context)
	{
		Logger.getGlobal().info(this.argument);
	}
}
