package software.xdev.micromigration.migrater.scripts.reflectiveVersion;

import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.ReflectiveVersionMigrationScript;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


public class v1_ValidScript extends ReflectiveVersionMigrationScript<Object, EmbeddedStorageManager>
{
	@Override
	public void migrate(Context<Object, EmbeddedStorageManager> context)
	{
		//Do nothing
	}
}
