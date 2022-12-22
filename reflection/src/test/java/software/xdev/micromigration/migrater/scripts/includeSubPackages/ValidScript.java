package software.xdev.micromigration.migrater.scripts.includeSubPackages;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;


public class ValidScript implements
	software.xdev.micromigration.scripts.VersionAgnosticMigrationScript<Object, software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager>
{
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(1);
	}

	@Override
	public void migrate(Context<Object, MigrationEmbeddedStorageManager> context)
	{
		//Do nothing
	}
}
