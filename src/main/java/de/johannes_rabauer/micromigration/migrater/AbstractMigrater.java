package de.johannes_rabauer.micromigration.migrater;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.TreeSet;
import java.util.function.Consumer;

import de.johannes_rabauer.micromigration.notification.ScriptExecutionNotification;
import de.johannes_rabauer.micromigration.scripts.Context;
import de.johannes_rabauer.micromigration.scripts.MigrationScript;
import de.johannes_rabauer.micromigration.version.MigrationVersion;
import one.microstream.storage.types.EmbeddedStorageManager;

public abstract class AbstractMigrater implements MicroMigrater
{	
	private Consumer<ScriptExecutionNotification> notificationConsumer = null;
	
	/**
	 * Registers a callback to take action when a script is executed.
	 * @param notificationConsumer is executed when a script is used from this migrater.
	 */
	public void setNotificationConsumer(Consumer<ScriptExecutionNotification> notificationConsumer)
	{
		this.notificationConsumer = notificationConsumer;
	}
	
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
					LocalDateTime startDate = null;
					MigrationVersion versionBeforeUpdate = updateVersionWhichWasExecuted;
					if(this.notificationConsumer != null)
					{
						startDate = LocalDateTime.now();
					}
					updateVersionWhichWasExecuted = migrateWithScript(script, storageManager, objectToMigrate);
					if(this.notificationConsumer != null)
					{
						this.notificationConsumer.accept(
							new ScriptExecutionNotification(
								script                       ,
								versionBeforeUpdate          , 
								updateVersionWhichWasExecuted, 
								startDate                    , 
								LocalDateTime.now()
							)								
						);
					}
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
