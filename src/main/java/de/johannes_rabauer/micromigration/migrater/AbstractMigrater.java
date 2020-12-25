package de.johannes_rabauer.micromigration.migrater;

import java.util.Objects;
import java.util.TreeSet;

import de.johannes_rabauer.micromigration.scripts.Context;
import de.johannes_rabauer.micromigration.scripts.MigrationScript;
import de.johannes_rabauer.micromigration.version.MigrationVersion;
import one.microstream.storage.types.EmbeddedStorageManager;

public abstract class AbstractMigrater implements MicroMigrater
{	
	@Override
	public MigrationVersion migrateToNewest(
		MigrationVersion       fromVersion   ,
		EmbeddedStorageManager storageManager,
		Object                 root
	)
	{
		Objects.requireNonNull(fromVersion);
		Objects.requireNonNull(storageManager);
		
		TreeSet<? extends MigrationScript<?>> sortedScripts = getSortedScripts();
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
	
	@Override
	public MigrationVersion migrateToVersion
	(
		MigrationVersion       fromVersion    ,
		MigrationVersion       targetVersion  ,
		EmbeddedStorageManager storageManager ,
		Object                 objectToMigrate
	)
	{
		Objects.requireNonNull(fromVersion);
		Objects.requireNonNull(targetVersion);
		Objects.requireNonNull(storageManager);
		
		MigrationVersion updateVersionWhichWasExecuted = fromVersion;
		for (MigrationScript<?> script : this.getSortedScripts()) 
		{
			if(MigrationVersion.COMPARATOR.compare(fromVersion, script.getTargetVersion()) < 0)
			{
				if(MigrationVersion.COMPARATOR.compare(script.getTargetVersion(), targetVersion) <= 0)
				{
					updateVersionWhichWasExecuted = migrateWithScript(script, storageManager, objectToMigrate);
				}
			}
		}
		return updateVersionWhichWasExecuted;
	}
	
	@SuppressWarnings("unchecked")
	private <T> MigrationVersion migrateWithScript(
		MigrationScript<T>     script         ,
		EmbeddedStorageManager storageManager ,
		Object                 objectToMigrate
	)
	{
		T castedObjectToMigrate = (T) objectToMigrate;
		script.migrate(new Context<>(castedObjectToMigrate, storageManager));
		return script.getTargetVersion();
	}

}
