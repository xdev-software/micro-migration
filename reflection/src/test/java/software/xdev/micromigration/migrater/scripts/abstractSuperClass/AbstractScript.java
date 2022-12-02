package software.xdev.micromigration.migrater.scripts.abstractSuperClass;

import software.xdev.micromigration.scripts.MigrationScript;
import software.xdev.micromigration.version.MigrationVersion;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


public abstract class AbstractScript implements MigrationScript<Object, EmbeddedStorageManager>
{
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(1);
	}
}
