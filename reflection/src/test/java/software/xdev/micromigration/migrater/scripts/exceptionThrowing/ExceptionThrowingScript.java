package software.xdev.micromigration.migrater.scripts.exceptionThrowing;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


public class ExceptionThrowingScript implements VersionAgnosticMigrationScript<Object, EmbeddedStorageManager>
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
	public void migrate(Context<Object, EmbeddedStorageManager> context)
	{
		//Do nothing
	}
}
