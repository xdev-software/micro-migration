package software.xdev.micromigration.testUtil;

import software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


public class MicroMigrationScriptDummy implements VersionAgnosticMigrationScript<Object, MigrationEmbeddedStorageManager>
{
	private final MigrationVersion version;
	
	public MicroMigrationScriptDummy(MigrationVersion version)
	{
		this.version = version;
	}

	@Override
	public MigrationVersion getTargetVersion()
	{
		return this.version;
	}

	@Override
	public void migrate(Context<Object, MigrationEmbeddedStorageManager> context) {
		// Don't do anything.
	}
}
