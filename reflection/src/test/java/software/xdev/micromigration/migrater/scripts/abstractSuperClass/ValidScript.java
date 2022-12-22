package software.xdev.micromigration.migrater.scripts.abstractSuperClass;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.scripts.Context;


public class ValidScript extends AbstractScript
{
	@Override
	public void migrate(Context<Object, MigrationEmbeddedStorageManager> context)
	{
		//Do nothing
	}
}
