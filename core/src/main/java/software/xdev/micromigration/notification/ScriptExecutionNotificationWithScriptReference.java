package software.xdev.micromigration.notification;

import java.time.LocalDateTime;

import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;

/**
 * Contains data about the execution of a script by a {@link MicroMigrater}.
 * 
 * @author Johannes Rabauer
 */
public class ScriptExecutionNotificationWithScriptReference extends AbstractScriptExecutionNotification
{
	private final VersionAgnosticMigrationScript<?,?> executedScript;
	
	/**
	 * @param executedScript script that was executed
	 * @param sourceVersion  original version of the object before executing the script
	 * @param targetVersion  version of the object after executing the script
	 * @param startDate      time when the script was started
	 * @param endDate        time when the script has finished
	 */
	public ScriptExecutionNotificationWithScriptReference(
		final VersionAgnosticMigrationScript<?, ?> executedScript,
		final MigrationVersion sourceVersion,
		final MigrationVersion targetVersion,
		final LocalDateTime startDate,
		final LocalDateTime        endDate
	) 
	{
		super(
			sourceVersion,
		    targetVersion,
		    startDate    ,
		    endDate
		);
		this.executedScript = executedScript;
	}

	/**
	 * @return the script that was executed
	 */
	public VersionAgnosticMigrationScript<?,?> getExecutedScript()
	{
		return this.executedScript;
	}

}
