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
package software.xdev.micromigration.examples.reflective.scripts;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.xdev.micromigration.eclipsestore.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.eclipsestore.MigrationScript;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.version.MigrationVersion;


@SuppressWarnings({"checkstyle:TypeName", "java:S101"})
public class UpdateToV1_0 implements MigrationScript<String>
{
	private static final Logger LOG = LoggerFactory.getLogger(UpdateToV1_0.class);
	
	@Override
	public MigrationVersion getTargetVersion()
	{
		return new MigrationVersion(1, 0);
	}
	
	@Override
	public void migrate(final Context<String, MigrationEmbeddedStorageManager> context)
	{
		LOG.info("Update {} executed", this.getTargetVersion());
		context.getStorageManager().setRoot("Hello World! @ " + new Date() + " Update 1.0");
	}
}
