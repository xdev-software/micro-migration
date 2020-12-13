package de.johannes_rabauer.micromigration.migrater;

import java.util.TreeSet;

import de.johannes_rabauer.micromigration.MicroMigrationScript;
import de.johannes_rabauer.micromigration.MigrationEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;

public interface MicroMigrater 
{
	public TreeSet<MicroMigrationScript> getSortedScripts();
	
	public default MicroMigrationVersion migrateToNewest(
		MicroMigrationVersion           fromVersion   ,
		MigrationEmbeddedStorageManager storageManager,
		Object                          root
	)
	{
		TreeSet<MicroMigrationScript> sortedScripts = getSortedScripts();
		if(sortedScripts.size() > 0)
		{
			return migrateToVersion(
				fromVersion                                 , 
				getSortedScripts().last().getTargetVersion(),
				storageManager                              ,
				root
			);
		}
		return fromVersion;
	}	
	
	public default MicroMigrationVersion migrateToVersion
	(
		MicroMigrationVersion           fromVersion   ,
		MicroMigrationVersion           targetVersion ,
		MigrationEmbeddedStorageManager storageManager,
		Object                          root
	)
	{
		MicroMigrationVersion updateVersionWhichWasExecuted = fromVersion;
		for (MicroMigrationScript script : this.getSortedScripts()) 
		{
			if(MicroMigrationVersion.COMPARATOR.compare(fromVersion, script.getTargetVersion()) < 0)
			{
				if(MicroMigrationVersion.COMPARATOR.compare(script.getTargetVersion(), targetVersion) <= 0)
				{
					script.execute(root, storageManager);
					updateVersionWhichWasExecuted = script.getTargetVersion();
				}
			}
		}
		return updateVersionWhichWasExecuted;
	}
}
