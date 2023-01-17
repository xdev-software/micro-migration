package software.xdev.micromigration.migrater;

import java.util.Objects;

import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;


/**
 * Exception that should be used if two scripts with the same version exist.
 * @author Johannes Rabauer
 */
public class VersionAlreadyRegisteredException extends Error
{
	private static final long serialVersionUID = 2153008832167067975L;

	/**
	 * The already registered script with the same version
	 */
	private final MigrationVersion alreadyRegisteredVersion;
	/**
	 * The version of the already registered script
	 */
	private final VersionAgnosticMigrationScript<?,?> alreadyRegisteredScript ;
	/**
	 * The script with the same version as {@link #alreadyRegisteredScript},
	 * which should be registered as well
	 */
	private final VersionAgnosticMigrationScript<?,?> newScriptToRegister     ;

	/**
	 * @param alreadyRegisteredVersion The version of the already registered script
	 * @param alreadyRegisteredScript The already registered script with the same version
	 * @param newScriptToRegister The script with the same version as alreadyRegisteredScript,
	 * which should be registered as well
	 */
	public VersionAlreadyRegisteredException(
		final MigrationVersion alreadyRegisteredVersion,
		final VersionAgnosticMigrationScript<?, ?> alreadyRegisteredScript,
		final VersionAgnosticMigrationScript<?, ?> newScriptToRegister
	) 
	{
		super("Version " + alreadyRegisteredVersion.toString() + " is already registered. Versions must be unique within the migrater.");
		this.alreadyRegisteredVersion = Objects.requireNonNull(alreadyRegisteredVersion);
		this.alreadyRegisteredScript  = Objects.requireNonNull(alreadyRegisteredScript) ;
		this.newScriptToRegister      = Objects.requireNonNull(newScriptToRegister)     ;
	}

	/**
	 * @return the version of the already registered script
	 */
	public MigrationVersion getAlreadyRegisteredVersion() 
	{
		return this.alreadyRegisteredVersion;
	}

	/**
	 * @return the already registered script with the same version
	 */
	public VersionAgnosticMigrationScript<?,?> getAlreadyRegisteredScript()
	{
		return this.alreadyRegisteredScript;
	}

	/**
	 * @return the script with the same version as {@link #getAlreadyRegisteredScript()},
	 * which should be registered as well
	 */
	public VersionAgnosticMigrationScript<?,?> getNewScriptToRegister()
	{
		return this.newScriptToRegister;
	}
}
