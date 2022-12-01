package de.johannes_rabauer.micromigration.scripts;

import java.util.Objects;
import java.util.function.Consumer;

import de.johannes_rabauer.micromigration.version.MigrationVersion;


/**
 * Provides a simple way to create a migration script with the necessary version
 * and {@link Consumer}.
 * 
 * @author Johannes Rabauer
 *
 */
public class SimpleTypedMigrationScript<T,E> implements MigrationScript<T,E>
{
	private final MigrationVersion version ;
	private final Consumer<Context<T,E>> consumer;
	
	/**
	 * @param version of the datastore after this script is executed
	 * @param consumer which is executed to reach the given datastore version
	 */
	public SimpleTypedMigrationScript(
		final MigrationVersion     version ,
		final Consumer<Context<T,E>> consumer
	)
	{
		Objects.requireNonNull(version );
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
	public void migrate(Context<T,E> context)
	{
		this.consumer.accept(context);
	}
}
