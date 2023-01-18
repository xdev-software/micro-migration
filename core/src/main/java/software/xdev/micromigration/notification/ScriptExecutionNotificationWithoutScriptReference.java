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
