package software.xdev.micromigration.migrater.scripts.includeSubPackages.subpackage;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


public class ValidScriptInSubpackage implements VersionAgnosticMigrationScript<Object, EmbeddedStorageManager>
{
	@Override
	public MigrationVersion getTargetVersion() 
	{
		return new MigrationVersion(2);
	}

	@Override
	public void migrate(Context<Object, EmbeddedStorageManager> context)
	{
		//Do nothing
	}
}
