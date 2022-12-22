package software.xdev.micromigration.notification;

import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.version.MigrationVersion;

import java.time.LocalDateTime;


/**
 * Contains data about the execution of a script by a {@link MicroMigrater}.
 * 
 * @author Johannes Rabauer
 */
public abstract class AbstractScriptExecutionNotification
{
	private final MigrationVersion     sourceVersion ;
	private final MigrationVersion     targetVersion ;
	private final LocalDateTime        startDate     ;
	private final LocalDateTime        endDate       ;

	/**
	 * @param sourceVersion original version of the object before executing the script
	 * @param targetVersion version of the object after executing the script
	 * @param startDate time when the script was started
	 * @param endDate time when the script has finished
	 */
	public AbstractScriptExecutionNotification(
		MigrationVersion     sourceVersion ,
		MigrationVersion     targetVersion ,
		LocalDateTime        startDate     ,
		LocalDateTime        endDate
	) 
	{
		super();
		this.sourceVersion  = sourceVersion ;
		this.targetVersion  = targetVersion ;
		this.startDate      = startDate     ;
		this.endDate        = endDate       ;
	}

	/**
	 * @return the original version of the object before executing the script
	 */
	public MigrationVersion getSourceVersion() 
	{
		return sourceVersion;
	}

	/**
	 * @return the version of the object after executing the script
	 */
	public MigrationVersion getTargetVersion() 
	{
		return targetVersion;
	}

	/**
	 * @return the time when the script was started
	 */
	public LocalDateTime getStartDate() 
	{
		return startDate;
	}

	/**
	 * @return time when the script has finished
	 */
	public LocalDateTime getEndDate() 
	{
		return endDate;
	}
}
