package software.xdev.micromigration.migrater.scripts.valid;

import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.MigrationScript;
import software.xdev.micromigration.version.MigrationVersion;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


public class ValidScript implements MigrationScript<Object, EmbeddedStorageManager>
{
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
