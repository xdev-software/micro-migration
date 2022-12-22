package software.xdev.micromigration.migrater.scripts.abstractSuperClass;

import software.xdev.micromigration.version.MigrationVersion;


public abstract class AbstractScript implements
	software.xdev.micromigration.scripts.VersionAgnosticMigrationScript<Object, software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager>
{
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(1);
	}
}
