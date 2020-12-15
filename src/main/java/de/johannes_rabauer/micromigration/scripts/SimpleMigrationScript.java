package de.johannes_rabauer.micromigration.scripts;

import java.util.function.BiConsumer;

import de.johannes_rabauer.micromigration.MigrationEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;

public class SimpleMigrationScript implements MicroMigrationScript 
{
	private final MicroMigrationVersion                               version;
	private final BiConsumer<Object, MigrationEmbeddedStorageManager> consumer;
	
	public SimpleMigrationScript(
		final MicroMigrationVersion                               version,
		final BiConsumer<Object, MigrationEmbeddedStorageManager> consumer
	)
	{
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
