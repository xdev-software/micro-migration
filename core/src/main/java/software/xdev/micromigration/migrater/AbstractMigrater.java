package software.xdev.micromigration.migrater;

import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticEmbeddedStorageManager;
import software.xdev.micromigration.notification.ScriptExecutionNotificationWithScriptReference;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.function.Consumer;


/**
 * Provides the basic functionality to apply {@link VersionAgnosticMigrationScript}s to
 * a datastore.
 *
 * @author Johannes Rabauer
 */
public abstract class AbstractMigrater implements MicroMigrater
{	
	private List<Consumer<ScriptExecutionNotificationWithScriptReference>> notificationConsumers = new ArrayList<>();
	
	/**
	 * Registers a callback to take action when a script is executed.
	 * @param notificationConsumer is executed when a script is used from this migrater.
	 */
	public void registerNotificationConsumer(Consumer<ScriptExecutionNotificationWithScriptReference> notificationConsumer)
	{
		this.notificationConsumers.add(notificationConsumer);
	}
	
	@Override
	public MigrationVersion migrateToNewest(
		MigrationVersion                      fromVersion   ,
		VersionAgnosticEmbeddedStorageManager storageManager,
		Object                                root
	)
	{
		Objects.requireNonNull(fromVersion);
		Objects.requireNonNull(storageManager);
		
		TreeSet<? extends VersionAgnosticMigrationScript<?,?>> sortedScripts = getSortedScripts();
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
		MigrationVersion                      fromVersion    ,
		MigrationVersion                      targetVersion  ,
		VersionAgnosticEmbeddedStorageManager storageManager ,
		Object                                objectToMigrate
	)
	{
		Objects.requireNonNull(fromVersion);
		Objects.requireNonNull(targetVersion);
		Objects.requireNonNull(storageManager);
		
		MigrationVersion updateVersionWhichWasExecuted = fromVersion;
		for (VersionAgnosticMigrationScript<?,?> script : this.getSortedScripts())
		{
			if(MigrationVersion.COMPARATOR.compare(fromVersion, script.getTargetVersion()) < 0)
			{
				if(MigrationVersion.COMPARATOR.compare(script.getTargetVersion(), targetVersion) <= 0)
				{
					LocalDateTime startDate = null;
					MigrationVersion versionBeforeUpdate = updateVersionWhichWasExecuted;
					if(!this.notificationConsumers.isEmpty())
					{
						startDate = LocalDateTime.now();
					}
					updateVersionWhichWasExecuted = migrateWithScript(script, storageManager, objectToMigrate);
					if(!this.notificationConsumers.isEmpty())
					{
						ScriptExecutionNotificationWithScriptReference scriptNotification =
							new ScriptExecutionNotificationWithScriptReference(
								script,
								versionBeforeUpdate,
								updateVersionWhichWasExecuted,
								startDate,
								LocalDateTime.now()
							);
						this.notificationConsumers.forEach(consumer -> consumer.accept(scriptNotification));
					}
				}
			}
		}
		return updateVersionWhichWasExecuted;
	}
	
	@SuppressWarnings("unchecked")
	private <T,E> MigrationVersion migrateWithScript(
		VersionAgnosticMigrationScript<T,E> script         ,
		VersionAgnosticEmbeddedStorageManager storageManager ,
		Object                                objectToMigrate
	)
	{
		T castedObjectToMigrate = (T) objectToMigrate;
		script.migrate(new Context<>(castedObjectToMigrate, storageManager));
		return script.getTargetVersion();
	}
	
	/**
	 * Checks if the given {@link VersionAgnosticMigrationScript} is not already registered in the
	 * {@link #getSortedScripts()}.
	 * @throws VersionAlreadyRegisteredException if script is already registered.
	 * @param scriptToCheck It's target version is checked, if it is not already registered.
	 */
	protected void checkIfVersionIsAlreadyRegistered(VersionAgnosticMigrationScript<?,?> scriptToCheck)
	{
		//Check if same version is not already registered
		for (VersionAgnosticMigrationScript<?,?> alreadyRegisteredScript : this.getSortedScripts())
		{
			if(MigrationVersion.COMPARATOR.compare(alreadyRegisteredScript.getTargetVersion(), scriptToCheck.getTargetVersion()) == 0)
			{
				//Two scripts with the same version are not allowed to get registered.
				throw new VersionAlreadyRegisteredException(
						alreadyRegisteredScript.getTargetVersion(),
						alreadyRegisteredScript,
						scriptToCheck
				);
			}
		}
	}

}
