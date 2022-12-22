package software.xdev.micromigration.migrater.scripts.exceptionThrowing;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;


public class ExceptionThrowingScript implements
	software.xdev.micromigration.scripts.VersionAgnosticMigrationScript<Object, MigrationEmbeddedStorageManager>
{
	public ExceptionThrowingScript() throws Exception
	{
		throw new Exception();
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
