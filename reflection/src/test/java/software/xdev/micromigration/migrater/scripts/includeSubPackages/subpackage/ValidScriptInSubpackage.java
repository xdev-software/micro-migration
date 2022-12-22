package software.xdev.micromigration.migrater.scripts.includeSubPackages.subpackage;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;


public class ValidScriptInSubpackage implements
	software.xdev.micromigration.scripts.VersionAgnosticMigrationScript<Object, MigrationEmbeddedStorageManager>
{
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(2);
	}

	@Override
	public void migrate(Context<Object, MigrationEmbeddedStorageManager> context)
	{
		//Do nothing
	}
}
