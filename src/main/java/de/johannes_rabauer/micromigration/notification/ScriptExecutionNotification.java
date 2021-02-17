package de.johannes_rabauer.micromigration.notification;

import java.time.LocalDateTime;

import de.johannes_rabauer.micromigration.migrater.MicroMigrater;
import de.johannes_rabauer.micromigration.scripts.MigrationScript;
import de.johannes_rabauer.micromigration.version.MigrationVersion;

/**
 * Contains data about the execution of a script by a {@link MicroMigrater}.
 * 
 * @author Johannes Rabauer
 * 
 */
public class ScriptExecutionNotification 
{
	private MigrationScript<?> executedScript;
	private MigrationVersion   sourceVersion ;
	private MigrationVersion   targetVersion ;
	private LocalDateTime      startDate     ;
	private LocalDateTime      endDate       ;
	
	public ScriptExecutionNotification(
		MigrationScript<?> executedScript,
		MigrationVersion   sourceVersion , 
		MigrationVersion   targetVersion ,
		LocalDateTime      startDate     , 
		LocalDateTime      endDate
	) 
	{
		super();
		this.executedScript = executedScript;
		this.sourceVersion  = sourceVersion ;
		this.targetVersion  = targetVersion ;
		this.startDate      = startDate     ;
		this.endDate        = endDate       ;
	}

	public MigrationScript<?> getExecutedScript() 
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
