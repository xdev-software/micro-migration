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
package software.xdev.micromigration.scripts;

import java.util.Objects;
import java.util.function.Consumer;

import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.versionagnostic.VersionAgnosticMigrationEmbeddedStorageManager;


/**
 * Provides a simple way to create a migration script with the necessary version and {@link Consumer}.
 */
public class SimpleTypedMigrationScript<T, E extends VersionAgnosticMigrationEmbeddedStorageManager<?, ?>>
	implements VersionAgnosticMigrationScript<T, E>
{
	private final MigrationVersion version;
	private final Consumer<Context<T, E>> consumer;
	
	/**
	 * @param version  of the datastore after this script is executed
	 * @param consumer which is executed to reach the given datastore version
	 */
	public SimpleTypedMigrationScript(
		final MigrationVersion version,
		final Consumer<Context<T, E>> consumer
	)
	{
		Objects.requireNonNull(version);
		Objects.requireNonNull(consumer);
		this.version = version;
		this.consumer = consumer;
	}
	
	@Override
	public MigrationVersion getTargetVersion()
	{
		return this.version;
	}
	
	@Override
	public void migrate(final Context<T, E> context)
	{
		this.consumer.accept(context);
	}
}
