package software.xdev.micromigration.migrater.scripts.errorThrowing;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.microstream.MigrationScript;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;


public class ErrorThrowingScript implements MigrationScript<Object>
{
	public ErrorThrowingScript()
	{
		throw new Error();
	}
	
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(1);
	}

	@Override
	public void migrate(Context<Object, MigrationEmbeddedStorageManager> context)
	{
		//Do nothing
	}
}
