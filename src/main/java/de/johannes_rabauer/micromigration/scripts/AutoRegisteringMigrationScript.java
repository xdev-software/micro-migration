package de.johannes_rabauer.micromigration.scripts;

import de.johannes_rabauer.micromigration.migrater.AutoRegisteringMigrater;

/**
 * Extending classes can easily auto register at the {@link AutoRegisteringMigrater}.<br/>
 * <b>Is not usable yet!
 * 
 * @author Johannes Rabauer
 *
 */
@Deprecated
public abstract class AutoRegisteringMigrationScript implements MicroMigrationScript 
{
	protected static AutoRegisteringMigrationScript registerSelf(AutoRegisteringMigrationScript script)
	{
		AutoRegisteringMigrater.registerScript(script);
		return script;
	}
}
