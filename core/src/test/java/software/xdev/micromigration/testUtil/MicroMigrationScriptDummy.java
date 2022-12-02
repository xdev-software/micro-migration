package software.xdev.micromigration.testUtil;

import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.MigrationScript;
import software.xdev.micromigration.version.MigrationVersion;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


public class MicroMigrationScriptDummy implements MigrationScript<Object, EmbeddedStorageManager>
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
