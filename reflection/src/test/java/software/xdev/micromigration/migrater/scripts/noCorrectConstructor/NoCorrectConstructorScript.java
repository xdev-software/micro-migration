package software.xdev.micromigration.migrater.scripts.noCorrectConstructor;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


public class NoCorrectConstructorScript implements VersionAgnosticMigrationScript<Object, EmbeddedStorageManager>
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
	public void migrate(Context<Object, EmbeddedStorageManager> context)
	{
		System.out.println(this.argument);
	}
}
