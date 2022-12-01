package de.johannes_rabauer.micromigration.testUtil;

import de.johannes_rabauer.micromigration.scripts.Context;
import de.johannes_rabauer.micromigration.scripts.MigrationScript;
import de.johannes_rabauer.micromigration.version.MigrationVersion;
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
