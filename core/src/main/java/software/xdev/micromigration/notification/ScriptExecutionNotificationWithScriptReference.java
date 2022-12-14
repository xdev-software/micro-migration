package software.xdev.micromigration.notification;

import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;

import java.time.LocalDateTime;

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
	 * @param sourceVersion original version of the object before executing the script
	 * @param targetVersion version of the object after executing the script
	 * @param startDate time when the script was started
	 * @param endDate time when the script has finished
	 */
	public ScriptExecutionNotificationWithScriptReference(
		VersionAgnosticMigrationScript<?,?> executedScript,
		MigrationVersion     sourceVersion ,
		MigrationVersion     targetVersion ,
		LocalDateTime        startDate     ,
		LocalDateTime        endDate
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
		return executedScript;
	}

}
