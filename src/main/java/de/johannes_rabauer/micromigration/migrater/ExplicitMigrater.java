package de.johannes_rabauer.micromigration.migrater;

import java.util.TreeSet;

import de.johannes_rabauer.micromigration.MicroMigrationScript;

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
