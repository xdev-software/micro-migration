package de.johannes_rabauer.micromigration.scripts;

import java.util.Objects;
import java.util.function.BiConsumer;

import de.johannes_rabauer.micromigration.version.MigrationVersion;
import one.microstream.storage.types.EmbeddedStorageManager;

/**
 * Provides a simple way to create a migration script with the necessary version
 * and {@link BiConsumer}.
 * 
 * @author Johannes Rabauer
 *
 */
public class SimpleMigrationScript implements MigrationScript 
{
	private final MigrationVersion                      version;
	private final BiConsumer<Object, EmbeddedStorageManager> consumer;
	
	/**
	 * @param version of the datastore after this script is executed
	 * @param consumer which is executed to reach the given datastore version
	 */
	public SimpleMigrationScript(
		final MigrationVersion                      version,
		final BiConsumer<Object, EmbeddedStorageManager> consumer
	)
	{
		Objects.requireNonNull(version);
		Objects.requireNonNull(consumer);
		this.version  = version ;
		this.consumer = consumer;
	}
	
	@Override
	public MigrationVersion getTargetVersion()
	{
		return this.version;
	}
	
	@Override
	public void execute(Object root, EmbeddedStorageManager storageManager) 
	{
		this.consumer.accept(root, storageManager);
	}

}
