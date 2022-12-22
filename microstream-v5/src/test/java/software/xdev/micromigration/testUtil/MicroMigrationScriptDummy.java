package software.xdev.micromigration.testUtil;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


public class MicroMigrationScriptDummy implements VersionAgnosticMigrationScript<Object, EmbeddedStorageManager>
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
	public void migrate(Context<Object, EmbeddedStorageManager> context) {
		// Don't do anything.
	}
}
