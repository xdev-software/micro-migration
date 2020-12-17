package de.johannes_rabauer.micromigration.scripts;

import java.util.Objects;
import java.util.function.BiConsumer;

import de.johannes_rabauer.micromigration.MigrationEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;

/**
 * Provides a simple way to create a migration script with the necessary version
 * and {@link BiConsumer}.
 * 
 * @author Johannes Rabauer
 *
 */
public class SimpleMigrationScript implements MicroMigrationScript 
{
	private final MicroMigrationVersion                               version;
	private final BiConsumer<Object, MigrationEmbeddedStorageManager> consumer;
	
	/**
	 * @param version of the datastore after this script is executed
	 * @param consumer which is executed to reach the given datastore version
	 */
	public SimpleMigrationScript(
		final MicroMigrationVersion                               version,
		final BiConsumer<Object, MigrationEmbeddedStorageManager> consumer
	)
	{
		Objects.requireNonNull(version);
		Objects.requireNonNull(consumer);
		this.version  = version ;
		this.consumer = consumer;
	}
	
	@Override
	public MicroMigrationVersion getTargetVersion()
	{
		return this.version;
	}
	
	@Override
	public void execute(Object root, MigrationEmbeddedStorageManager storageManager) 
	{
		this.consumer.accept(root, storageManager);
	}

}
