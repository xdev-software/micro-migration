package software.xdev.micromigration.migrater.scripts.abstractSuperClass;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


public abstract class AbstractScript implements VersionAgnosticMigrationScript<Object, EmbeddedStorageManager>
{
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(1);
	}
}
