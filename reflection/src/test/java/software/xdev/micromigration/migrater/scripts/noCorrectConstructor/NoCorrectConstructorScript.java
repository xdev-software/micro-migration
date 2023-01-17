package software.xdev.micromigration.migrater.scripts.noCorrectConstructor;

import java.util.logging.Logger;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;


public class NoCorrectConstructorScript implements
	software.xdev.micromigration.scripts.VersionAgnosticMigrationScript<Object, MigrationEmbeddedStorageManager>
{
	private final String argument;
	
	public NoCorrectConstructorScript(final String argument)
	{
		this.argument = argument;
	}
	
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(1);
	}

	@Override
	public void migrate(final Context<Object, MigrationEmbeddedStorageManager> context)
	{
		Logger.getGlobal().info(this.argument);
	}
}
