package software.xdev.micromigration.version;

import software.xdev.micromigration.notification.ScriptExecutionNotificationWithoutScriptReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * This class is inserted as the root of the MicroStream datastore and contains only the 
 * current version, the actual root object and the history of executed scripts.
 * 
 * @author Johannes Rabauer
 * 
 */
public class VersionedRootWithHistory extends VersionedRoot implements VersionedAndKeeperOfHistory
{
	private final List<ScriptExecutionNotificationWithoutScriptReference> migrationHistory;

	/**
	 * @param actualRoot which is stored in the datastore and defined by the user
	 */
	public VersionedRootWithHistory(Object actualRoot)
	{
		super(actualRoot);
		this.migrationHistory = new ArrayList<>();
	}

	@Override
	public void addExecutedScript(ScriptExecutionNotificationWithoutScriptReference executedScriptInformation)
	{
		this.migrationHistory.add(Objects.requireNonNull(executedScriptInformation));
	}

	/**
	 * @return the complete migration history. That means information about every executed script.
	 */
	@Override
	public List<ScriptExecutionNotificationWithoutScriptReference> getMigrationHistory()
	{
		return this.migrationHistory;
	}
}
