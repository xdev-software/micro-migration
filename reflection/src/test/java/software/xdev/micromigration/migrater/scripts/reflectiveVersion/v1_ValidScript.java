package software.xdev.micromigration.migrater.scripts.reflectiveVersion;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.ReflectiveVersionMigrationScript;


public class v1_ValidScript extends ReflectiveVersionMigrationScript<Object, MigrationEmbeddedStorageManager>
{
	@Override
	public void migrate(Context<Object, MigrationEmbeddedStorageManager> context)
	{
		//Do nothing
	}
}
