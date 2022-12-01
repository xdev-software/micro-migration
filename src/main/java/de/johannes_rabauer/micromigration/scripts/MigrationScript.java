package de.johannes_rabauer.micromigration.scripts;

import java.util.Comparator;

import de.johannes_rabauer.micromigration.version.MigrationVersion;


/**
 * Interface for scripts to migrate / update datastores.
 * <p>
 * One script is supposed to bring a datastore from a lower version to the target version.
 * After the {@link MigrationScript#migrate(Context)} method is called,
 * the target version is reached.
 * 
 * @author Johannes Rabauer
 *
 */
public interface MigrationScript<T>
{	
	/**
	 * @return the version of the datastore after this script is executed.
	 */
	public MigrationVersion getTargetVersion();
	
	/**
	 * Execute logic to migrate the given datastore to a newer version of the store.
	 * After executing the {@link #getTargetVersion()} is reached.
	 * 
	 * @param context that holds necessary data for the migration
	 */
	public void migrate(Context<T> context);
	
	public static Comparator<MigrationScript<?>> COMPARATOR = new Comparator<MigrationScript<?>>() 
	{
		@Override
		public int compare(MigrationScript<?> o1, MigrationScript<?> o2) {
			return MigrationVersion.COMPARATOR.compare(o1.getTargetVersion(), o2.getTargetVersion());
		}
	};
}
