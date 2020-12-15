package de.johannes_rabauer.micromigration.migrater;

import java.util.TreeSet;

import de.johannes_rabauer.micromigration.scripts.AutoRegisteringMigrationScript;
import de.johannes_rabauer.micromigration.scripts.MicroMigrationScript;

/**
 * Executes all the scripts which can register themselves.<br/>
 * <b>Is not usable yet!
 * @author Johannes Rabauer
 * 
 */
@Deprecated
public class AutoRegisteringMigrater implements MicroMigrater
{
	private final static TreeSet<MicroMigrationScript> SORTED_SCRIPTS = new TreeSet<>(MicroMigrationScript.COMPARATOR);
	
	public final static AutoRegisteringMigrater INSTANCE = new AutoRegisteringMigrater();
	
	private AutoRegisteringMigrater()
	{

	}
	
	public static final void registerScript(AutoRegisteringMigrationScript script)
	{
		SORTED_SCRIPTS.add(script);
	}

	@Override
	public TreeSet<MicroMigrationScript> getSortedScripts() {
		return SORTED_SCRIPTS;
	}
}
