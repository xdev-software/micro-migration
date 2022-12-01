package de.johannes_rabauer.micromigration.scripts;

import java.util.function.Consumer;

import de.johannes_rabauer.micromigration.version.MigrationVersion;


/**
 * Provides a simple way to create a migration script with the necessary version
 * and {@link Consumer}.
 * 
 * @author Johannes Rabauer
 *
 */
public class SimpleMigrationScript extends SimpleTypedMigrationScript<Object,Object>
{
	public SimpleMigrationScript(
		final MigrationVersion version ,
		final Consumer<Context<Object,Object>> consumer
	) 
	{
		super(version, consumer);
	}
}
