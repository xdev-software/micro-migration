package software.xdev.micromigration.migrater;

import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;

import java.util.TreeSet;

/**
 * Contains all the available scripts to migrate the datastore to a certain version.
 * <p>
 * This class needs explicit scripts which are then included in the migration process.
 * 
 * @author Johannes Rabauer
 * 
 */
public class ExplicitMigrater extends AbstractMigrater
{
	private final TreeSet<VersionAgnosticMigrationScript<?,?>> sortedScripts = new TreeSet<>(
		VersionAgnosticMigrationScript.COMPARATOR);
	
	/**
	 * @param scripts are all the scripts that are executed, if the current version is lower than this of the script<br>
	 * Versions of the scripts must be unique. That means that no version is allowed multiple times in the migrater.
	 * @throws VersionAlreadyRegisteredException if two scripts have the same version
	 */
	public ExplicitMigrater(VersionAgnosticMigrationScript<?,?>...scripts) throws VersionAlreadyRegisteredException
	{
		for (VersionAgnosticMigrationScript<?,?> script : scripts)
		{
			checkIfVersionIsAlreadyRegistered(script);
			this.sortedScripts.add(script);
		}
	}

	@Override
	public TreeSet<VersionAgnosticMigrationScript<?,?>> getSortedScripts() {
		return this.sortedScripts;
	}
}
