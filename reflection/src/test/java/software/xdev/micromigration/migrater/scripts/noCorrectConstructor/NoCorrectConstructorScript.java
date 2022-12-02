package software.xdev.micromigration.migrater.scripts.noCorrectConstructor;

import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.MigrationScript;
import software.xdev.micromigration.version.MigrationVersion;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


public class NoCorrectConstructorScript implements MigrationScript<Object, EmbeddedStorageManager>
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
