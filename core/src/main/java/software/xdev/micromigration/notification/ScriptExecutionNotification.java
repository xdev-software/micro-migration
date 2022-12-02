package software.xdev.micromigration.notification;

import java.time.LocalDateTime;

import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.scripts.MigrationScript;
import software.xdev.micromigration.version.MigrationVersion;

/**
 * Contains data about the execution of a script by a {@link MicroMigrater}.
 * 
 * @author Johannes Rabauer
 * 
 */
public class ScriptExecutionNotification 
{
	private MigrationScript<?,?> executedScript;
	private MigrationVersion     sourceVersion ;
	private MigrationVersion     targetVersion ;
	private LocalDateTime        startDate     ;
	private LocalDateTime        endDate       ;
	
	public ScriptExecutionNotification(
		MigrationScript<?,?> executedScript,
		MigrationVersion     sourceVersion ,
		MigrationVersion     targetVersion ,
		LocalDateTime        startDate     ,
		LocalDateTime        endDate
	) 
	{
		super();
		this.executedScript = executedScript;
		this.sourceVersion  = sourceVersion ;
		this.targetVersion  = targetVersion ;
		this.startDate      = startDate     ;
		this.endDate        = endDate       ;
	}

	public MigrationScript<?,?> getExecutedScript()
	{
		return executedScript;
	}

	public MigrationVersion getSourceVersion() 
	{
		return sourceVersion;
	}

	public MigrationVersion getTargetVersion() 
	{
		return targetVersion;
	}

	public LocalDateTime getStartDate() 
	{
		return startDate;
	}

	public LocalDateTime getEndDate() 
	{
		return endDate;
	}
}
