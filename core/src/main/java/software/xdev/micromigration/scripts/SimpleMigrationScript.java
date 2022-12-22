package software.xdev.micromigration.scripts;

import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticMigrationEmbeddedStorageManager;
import software.xdev.micromigration.version.MigrationVersion;

import java.util.function.Consumer;


/**
 * Provides a simple way to create a migration script with the necessary version
 * and {@link Consumer}.
 * 
 * @author Johannes Rabauer
 *
 */
public class SimpleMigrationScript<E extends VersionAgnosticMigrationEmbeddedStorageManager<?,?>> extends SimpleTypedMigrationScript<Object,E>
{
	/**
	 * @param targetVersion to which the script is updating the object
	 * @param consumer which consumes the object and updates it to the target version
	 */
	public SimpleMigrationScript(
		final MigrationVersion targetVersion ,
		final Consumer<Context<Object,E>> consumer
	) 
	{
		super(targetVersion, consumer);
	}
}
