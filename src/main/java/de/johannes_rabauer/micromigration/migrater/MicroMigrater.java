package de.johannes_rabauer.micromigration.migrater;

import java.util.TreeSet;

import de.johannes_rabauer.micromigration.MigrationEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.scripts.MicroMigrationScript;
import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;
import one.microstream.storage.types.EmbeddedStorageManager;

/**
 * Executes all the available scripts to migrate the datastore to a certain version.
 * 
 * @author Johannes Rabauer
 * 
 */
public interface MicroMigrater 
{
	public TreeSet<? extends MicroMigrationScript> getSortedScripts();
	
	/**
	 * Executes all the scripts that are available to the migrater.
	 * Only scripts with a higher target version than the given fromVersion are executed.<br>
	 * Scripts are executed one after another from the lowest to the highest version.
	 * <p>
	 * <b>Example:</b><br>
	 * Current version is 1.0.0<br>
	 * Scripts for v1.1.0, v2.0.0 and v1.2.1 are available<br>
	 * Scripts are chain executed like v1.1.0 then v1.2.1 then v2.0.0
	 * 
	 * @param fromVersion is the current version of the datastore. 
	 * Scripts for lower versions then the fromVersion are not executed.
	 * 
	 * @param storageManager is relayed to the scripts {@link MicroMigrationScript#execute(Object, EmbeddedStorageManager)}
	 * method. This way the script can call {@link EmbeddedStorageManager#store(Object)} or another method on the storage manager.
	 * 
	 * @param root is relayed to the scripts {@link MicroMigrationScript#execute(Object, MigrationEmbeddedStorageManager)}
	 * method. This way the script can change something within the root object.
	 * 
	 * @return the target version of the last executed script
	 */
	public default MicroMigrationVersion migrateToNewest(
		MicroMigrationVersion  fromVersion   ,
		EmbeddedStorageManager storageManager,
		Object                 root
	)
	{
		TreeSet<? extends MicroMigrationScript> sortedScripts = getSortedScripts();
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
	
	/**
	 * Executes all the scripts that are available to the migrater until the given targetVersion is reached.
	 * Only scripts with a higher target version than the given fromVersion are executed.<br>
	 * Scripts are executed one after another from the lowest to the highest version.
	 * <p>
	 * <b>Example:</b><br>
	 * Current version is 1.0.0<br>
	 * Scripts for v1.1.0, v2.0.0 and v1.2.1 are available<br>
	 * Scripts are chain executed like v1.1.0 then v1.2.1 then v2.0.0
	 * 
	 * @param fromVersion is the current version of the datastore. 
	 * Scripts for lower versions then the fromVersion are not executed.
	 * 
	 * @param targetVersion is the highest allowed script version. 
	 * Scripts which have a higher version won't be exectued.
	 * 
	 * @param storageManager is relayed to the scripts {@link MicroMigrationScript#execute(Object, EmbeddedStorageManager)}
	 * method. This way the script can call {@link EmbeddedStorageManager#store(Object)} or another method on the storage manager.
	 * 
	 * @param root is relayed to the scripts {@link MicroMigrationScript#execute(Object, MigrationEmbeddedStorageManager)}
	 * method. This way the script can change something within the root object.
	 * 
	 * @return the target version of the last executed script
	 */
	public default MicroMigrationVersion migrateToVersion
	(
		MicroMigrationVersion  fromVersion   ,
		MicroMigrationVersion  targetVersion ,
		EmbeddedStorageManager storageManager,
		Object                 root
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
