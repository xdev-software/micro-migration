/*
 * Copyright © 2021 XDEV Software (https://xdev.software)
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

import java.time.LocalDateTime;

import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.version.MigrationVersion;


/**
 * Contains data about the execution of a script by a {@link MicroMigrater}.
 */
public abstract class AbstractScriptExecutionNotification
{
	private final MigrationVersion sourceVersion;
	private final MigrationVersion targetVersion;
	private final LocalDateTime startDate;
	private final LocalDateTime endDate;
	
	/**
	 * @param sourceVersion original version of the object before executing the script
	 * @param targetVersion version of the object after executing the script
	 * @param startDate     time when the script was started
	 * @param endDate       time when the script has finished
	 */
	protected AbstractScriptExecutionNotification(
		final MigrationVersion sourceVersion,
		final MigrationVersion targetVersion,
		final LocalDateTime startDate,
		final LocalDateTime endDate
	)
	{
		super();
		this.sourceVersion = sourceVersion;
		this.targetVersion = targetVersion;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	/**
	 * @return the original version of the object before executing the script
	 */
	public MigrationVersion getSourceVersion()
	{
		return this.sourceVersion;
	}
	
	/**
	 * @return the version of the object after executing the script
	 */
	public MigrationVersion getTargetVersion()
	{
		return this.targetVersion;
	}
	
	/**
	 * @return the time when the script was started
	 */
	public LocalDateTime getStartDate()
	{
		return this.startDate;
	}
	
	/**
	 * @return time when the script has finished
	 */
	public LocalDateTime getEndDate()
	{
		return this.endDate;
	}
}
