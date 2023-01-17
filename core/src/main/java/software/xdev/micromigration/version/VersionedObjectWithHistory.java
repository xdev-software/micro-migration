package software.xdev.micromigration.version;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import software.xdev.micromigration.notification.ScriptExecutionNotificationWithoutScriptReference;


/**
 * This class is inserted as the root of the MicroStream datastore and contains only the 
 * current version, the actual root object and the history of executed scripts.
 * 
 * @author Johannes Rabauer
 * 
 */
public class VersionedObjectWithHistory extends VersionedObject implements VersionedAndKeeperOfHistory
{
	private final List<ScriptExecutionNotificationWithoutScriptReference> migrationHistory;

	/**
	 * @param actualRoot which is stored in the datastore and defined by the user
	 */
	public VersionedObjectWithHistory(final Object actualRoot)
	{
		super(actualRoot);
		this.migrationHistory = new ArrayList<>();
	}

	@Override
	public void addExecutedScript(final ScriptExecutionNotificationWithoutScriptReference executedScriptInformation)
	{
		this.migrationHistory.add(Objects.requireNonNull(executedScriptInformation));
	}

	@Override
	public List<ScriptExecutionNotificationWithoutScriptReference> getMigrationHistory()
	{
		return this.migrationHistory;
	}
}
