package de.johannes_rabauer.micromigration.migrater;

import java.util.TreeSet;

import de.johannes_rabauer.micromigration.MicroMigrationScript;
import de.johannes_rabauer.micromigration.version.MicroStreamVersionedRoot;
import one.microstream.storage.types.EmbeddedStorageManager;

/**
 * Executes all the available scripts to migrate the datastore to a certain version.
 * <p>
 * This class needs explicit scripts which are then included in the migration process.
 * 
 * @author Johannes Rabauer
 * 
 */
public class ExplicitMigrater implements MicroMigrater
{
	private final TreeSet<MicroMigrationScript> sortedScripts = new TreeSet<>(MicroMigrationScript.COMPARATOR);
	
	public ExplicitMigrater(MicroMigrationScript ...scripts)
	{
		for (MicroMigrationScript script : scripts) 
		{
			this.sortedScripts.add(script);
		}
	}

	@Override
	public TreeSet<MicroMigrationScript> getSortedScripts() {
		return this.sortedScripts;
	}
}
