/*
 * Copyright © 2021 XDEV Software GmbH (https://xdev.software)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
public class VersionedRootWithHistory extends VersionedRoot implements VersionedAndKeeperOfHistory
{
	private final List<ScriptExecutionNotificationWithoutScriptReference> migrationHistory;

	/**
	 * @param actualRoot which is stored in the datastore and defined by the user
	 */
	public VersionedRootWithHistory(final Object actualRoot)
	{
		super(actualRoot);
		this.migrationHistory = new ArrayList<>();
	}

	@Override
	public void addExecutedScript(final ScriptExecutionNotificationWithoutScriptReference executedScriptInformation)
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
