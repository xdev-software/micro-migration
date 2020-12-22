package de.johannes_rabauer.micromigration.testUtil;

import de.johannes_rabauer.micromigration.scripts.MigrationScript;
import de.johannes_rabauer.micromigration.version.MigrationVersion;
import one.microstream.storage.types.EmbeddedStorageManager;

public class MicroMigrationScriptDummy implements MigrationScript 
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
	public void execute(Object root, EmbeddedStorageManager storageManager) {
		// Don't do anything.
	}

}
