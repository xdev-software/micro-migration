package software.xdev.micromigration.migrater.scripts.abstractSuperClass;

import software.xdev.micromigration.scripts.Context;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


public class ValidScript extends AbstractScript
{
	@Override
	public void migrate(Context<Object, EmbeddedStorageManager> context)
	{
		//Do nothing
	}
}
