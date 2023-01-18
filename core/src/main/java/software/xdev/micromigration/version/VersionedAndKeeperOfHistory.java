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

import java.util.List;

import software.xdev.micromigration.notification.ScriptExecutionNotificationWithoutScriptReference;

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
