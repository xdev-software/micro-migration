package software.xdev.micromigration.notification;

import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;


/**
 * Same as {@link ScriptExecutionNotificationWithScriptReference} but instead of referencing
 * the {@link VersionAgnosticMigrationScript} directly, only the name of the script is
 * extracted through the class name.
 * <p>
 * <i>"Why?!"</i> - If you want to persist say a history of your applied scripts in your database and
 * you reference your scripts directly, these classes are referenced in your datastore.
 * That shouldn't be a problem. Except when you refactor or delete these scripts.
 * Usually what's really important is the name of the script.
 *
 * @author Johannes Rabauer
 */
public class ScriptExecutionNotificationWithoutScriptReference extends AbstractScriptExecutionNotification
{
	private final String executedScriptName;
	
	/**
	 * @param originalNotification where the reference to the script is deleted and the class name is extracted.
	 */
	public ScriptExecutionNotificationWithoutScriptReference(final ScriptExecutionNotificationWithScriptReference originalNotification)
	{
		super(
			originalNotification.getSourceVersion(),
			originalNotification.getTargetVersion(),
			originalNotification.getStartDate(),
			originalNotification.getEndDate()
		);
		this.executedScriptName = originalNotification.getExecutedScript().getClass().getSimpleName();
	}

	/**
	 * @return the <b>name</b> of the script that was extracted.
	 */
	public String getExecutedScriptName()
	{
		return this.executedScriptName;
	}
}
