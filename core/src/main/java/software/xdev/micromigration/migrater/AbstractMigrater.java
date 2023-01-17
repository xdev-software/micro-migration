package software.xdev.micromigration.migrater;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.function.Consumer;

import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticMigrationEmbeddedStorageManager;
import software.xdev.micromigration.notification.ScriptExecutionNotificationWithScriptReference;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


/**
 * Provides the basic functionality to apply {@link VersionAgnosticMigrationScript}s to a datastore.
 *
 * @author Johannes Rabauer
 */
public abstract class AbstractMigrater implements MicroMigrater
{
	private final List<Consumer<ScriptExecutionNotificationWithScriptReference>> notificationConsumers =
		new ArrayList<>();
	private Clock clock = Clock.systemDefaultZone();
	
	@Override
	public void registerNotificationConsumer(final Consumer<ScriptExecutionNotificationWithScriptReference> notificationConsumer)
	{
		this.notificationConsumers.add(notificationConsumer);
	}
	
	@Override
	public <E extends VersionAgnosticMigrationEmbeddedStorageManager<?, ?>> MigrationVersion migrateToNewest(
		final MigrationVersion fromVersion,
		final E storageManager,
		final Object root
	)
	{
		Objects.requireNonNull(fromVersion);
		Objects.requireNonNull(storageManager);
		
		final TreeSet<? extends VersionAgnosticMigrationScript<?, ?>> sortedScripts = this.getSortedScripts();
		if(sortedScripts.size() > 0)
		{
			return this.migrateToVersion(
				fromVersion,
				this.getSortedScripts().last().getTargetVersion(),
				storageManager,
				root
			);
		}
		return fromVersion;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E extends VersionAgnosticMigrationEmbeddedStorageManager<?, ?>> MigrationVersion migrateToVersion
		(
			final MigrationVersion fromVersion,
			final MigrationVersion targetVersion,
			final E storageManager,
			final Object objectToMigrate
		)
	{
		Objects.requireNonNull(fromVersion);
		Objects.requireNonNull(targetVersion);
		Objects.requireNonNull(storageManager);
		
		MigrationVersion updateVersionWhichWasExecuted = fromVersion;
		for(final VersionAgnosticMigrationScript<?, ?> script : this.getSortedScripts())
		{
			final VersionAgnosticMigrationScript<?, E> castedScript = (VersionAgnosticMigrationScript<?, E>)script;
			if(MigrationVersion.COMPARATOR.compare(fromVersion, script.getTargetVersion()) < 0)
			{
				if(MigrationVersion.COMPARATOR.compare(script.getTargetVersion(), targetVersion) <= 0)
				{
					LocalDateTime startDate = null;
					final MigrationVersion versionBeforeUpdate = updateVersionWhichWasExecuted;
					if(!this.notificationConsumers.isEmpty())
					{
						startDate = LocalDateTime.now(this.clock);
					}
					updateVersionWhichWasExecuted =
						this.migrateWithScript(castedScript, storageManager, objectToMigrate);
					if(!this.notificationConsumers.isEmpty())
					{
						final ScriptExecutionNotificationWithScriptReference scriptNotification =
							new ScriptExecutionNotificationWithScriptReference(
								script,
								versionBeforeUpdate,
								updateVersionWhichWasExecuted,
								startDate,
								LocalDateTime.now(this.clock)
							);
						this.notificationConsumers.forEach(consumer -> consumer.accept(scriptNotification));
					}
				}
			}
		}
		return updateVersionWhichWasExecuted;
	}
	
	@SuppressWarnings("unchecked")
	private <T,E extends VersionAgnosticMigrationEmbeddedStorageManager<?,?>> MigrationVersion migrateWithScript(
		final VersionAgnosticMigrationScript<T, E> script,
		final E storageManager,
		final Object objectToMigrate
	)
	{
		final T castedObjectToMigrate = (T)objectToMigrate;
		script.migrate(new Context<>(castedObjectToMigrate, storageManager));
		return script.getTargetVersion();
	}
	
	/**
	 * Checks if the given {@link VersionAgnosticMigrationScript} is not already registered in the
	 * {@link #getSortedScripts()}.
	 *
	 * @param scriptToCheck It's target version is checked, if it is not already registered.
	 * @throws VersionAlreadyRegisteredException if script is already registered.
	 */
	protected void checkIfVersionIsAlreadyRegistered(final VersionAgnosticMigrationScript<?, ?> scriptToCheck)
	{
		// Check if same version is not already registered
		for(final VersionAgnosticMigrationScript<?, ?> alreadyRegisteredScript : this.getSortedScripts())
		{
			if(MigrationVersion.COMPARATOR.compare(
				alreadyRegisteredScript.getTargetVersion(),
				scriptToCheck.getTargetVersion()) == 0)
			{
				// Two scripts with the same version are not allowed to get registered.
				throw new VersionAlreadyRegisteredException(
					alreadyRegisteredScript.getTargetVersion(),
					alreadyRegisteredScript,
					scriptToCheck
				);
			}
		}
	}
	
	/**
	 * Change used clock for notifications from {@link #registerNotificationConsumer(Consumer)}.
	 *
	 * @param clock is used when a
	 *              {@link software.xdev.micromigration.notification.ScriptExecutionNotificationWithoutScriptReference}
	 *              is created
	 * @return self
	 */
	public AbstractMigrater withClock(final Clock clock)
	{
		this.clock = clock;
		return this;
	}
}
