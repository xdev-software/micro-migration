package de.johannes_rabauer.micromigration.migrater;

import java.util.TreeSet;

import de.johannes_rabauer.micromigration.MicroMigrationScript;
import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;

public interface MicroMigrater 
{
	public TreeSet<MicroMigrationScript> getSortedScripts();
	
	public default void migrateToNewest(MicroMigrationVersion fromVersion)
	{
		migrateToVersion(fromVersion, getSortedScripts().last().getTargetVersion());
	}	
	
	public default void migrateToVersion
	(
		MicroMigrationVersion fromVersion,
		MicroMigrationVersion targetVersion
	)
	{
		for (MicroMigrationScript script : this.getSortedScripts()) 
		{
			if(MicroMigrationVersion.COMPARATOR.compare(fromVersion, script.getTargetVersion()) <= 0)
			{
				if(MicroMigrationVersion.COMPARATOR.compare(script.getTargetVersion(), targetVersion) <= 0)
				{
					script.execute();
				}
			}
		}
	}
}
