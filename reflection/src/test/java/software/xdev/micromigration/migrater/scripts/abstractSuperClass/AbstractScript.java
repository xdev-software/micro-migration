package software.xdev.micromigration.migrater.scripts.abstractSuperClass;

import software.xdev.micromigration.microstream.MigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


public abstract class AbstractScript implements MigrationScript<Object>
{
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(1);
	}
}
