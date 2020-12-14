package de.johannes_rabauer.micromigration.testUtil;

import de.johannes_rabauer.micromigration.MicroMigrationScript;
import de.johannes_rabauer.micromigration.MigrationEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;

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
	public void execute(Object root, MigrationEmbeddedStorageManager storageManager) {
		// Don't do anything.
	}

}
