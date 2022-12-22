package software.xdev.micromigration.version;

import software.xdev.micromigration.notification.ScriptExecutionNotificationWithoutScriptReference;

import java.util.List;


/**
 * Interface used by the MigrationManagers for easier versioning of objects
 * and to keep and read the migration history.
 * 
 * @author Johannes Rabauer
 * 
 */
public interface VersionedAndKeeperOfHistory extends Versioned
{
	/**
	 * Adds the information about the executed script to the history book.
	 * @param executedScriptInformation information about the executed script
	 */
	void addExecutedScript(ScriptExecutionNotificationWithoutScriptReference executedScriptInformation);

	/**
	 * @return the complete migration history. That means information about every executed script.
	 */
	List<ScriptExecutionNotificationWithoutScriptReference> getMigrationHistory();
}
