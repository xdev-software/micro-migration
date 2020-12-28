package de.johannes_rabauer.micromigration.migrater;

import java.util.TreeSet;

import de.johannes_rabauer.micromigration.scripts.MigrationScript;

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
	private final TreeSet<MigrationScript<?>> sortedScripts = new TreeSet<>(MigrationScript.COMPARATOR);
	
	/**
	 * @param scripts are all the scripts that are executed, if the current version is lower than this of the script
	 */
	public ExplicitMigrater(MigrationScript<?> ...scripts)
	{
		for (MigrationScript<?> script : scripts) 
		{
			this.sortedScripts.add(script);
		}
	}

	@Override
	public TreeSet<MigrationScript<?>> getSortedScripts() {
		return this.sortedScripts;
	}
}
