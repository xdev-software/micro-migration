package de.johannes_rabauer.micromigration.testUtil;

import de.johannes_rabauer.micromigration.scripts.MicroMigrationScript;
import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;
import one.microstream.storage.types.EmbeddedStorageManager;

public class MicroMigrationScriptDummy implements MicroMigrationScript 
{
	private final MicroMigrationVersion version;
	
	public MicroMigrationScriptDummy(MicroMigrationVersion version)
	{
		this.version = version;
	}

	@Override
	public MicroMigrationVersion getTargetVersion()
	{
		return this.version;
	}
	
	@Override
	public void execute(Object root, EmbeddedStorageManager storageManager) {
		// Don't do anything.
	}

}
