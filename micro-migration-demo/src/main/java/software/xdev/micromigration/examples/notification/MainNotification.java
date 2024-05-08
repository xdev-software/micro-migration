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
package software.xdev.micromigration.examples.notification;

import java.util.Date;
import java.util.logging.Logger;

import software.xdev.micromigration.eclipsestore.MigrationEmbeddedStorage;
import software.xdev.micromigration.eclipsestore.MigrationEmbeddedStorageManager;
import software.xdev.micromigration.migrater.ExplicitMigrater;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


/**
 * Shows the basic registration of migration notifications ({@link ScriptExecutionNotification}).
 */
@SuppressWarnings("java:S2629")
public final class MainNotification
{
	public static void main(final String[] args)
	{
		final ExplicitMigrater migrater = new ExplicitMigrater(
			new MainNotification.UpdateToV1_0()
		);
		migrater.registerNotificationConsumer(
			scriptExecutionNotification -> Logger.getGlobal()
				.info("Script " + scriptExecutionNotification.getExecutedScript().getClass().getSimpleName()
					+ " executed.")
		);
		final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(migrater);
		Logger.getGlobal().info(storageManager.root().toString());
		if(storageManager.root() == null)
		{
			storageManager.setRoot("Hello World! @ " + new Date());
		}
		storageManager.storeRoot();
		storageManager.shutdown();
	}
	
	@SuppressWarnings({"checkstyle:TypeName", "java:S101"})
	static class UpdateToV1_0 implements VersionAgnosticMigrationScript<String, MigrationEmbeddedStorageManager>
	{
		@Override
		public MigrationVersion getTargetVersion()
		{
			return new MigrationVersion(1, 0);
		}
		
		@Override
		public void migrate(final Context<String, MigrationEmbeddedStorageManager> context)
		{
			context.getStorageManager().setRoot("Hello World! @ " + new Date() + " Update 1.0");
		}
	}
	
	private MainNotification()
	{
	}
}
