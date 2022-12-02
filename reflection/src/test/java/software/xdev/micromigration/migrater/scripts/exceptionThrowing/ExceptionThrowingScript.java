package software.xdev.micromigration.migrater.scripts.exceptionThrowing;

import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.MigrationScript;
import software.xdev.micromigration.version.MigrationVersion;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


public class ExceptionThrowingScript implements MigrationScript<Object, EmbeddedStorageManager>
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
