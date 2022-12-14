package software.xdev.micromigration.migrater;

import java.util.Objects;

import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.scripts.MigrationScript;


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
	private final MigrationVersion     alreadyRegisteredVersion;
	/**
	 * The version of the already registered script
	 */
	private final MigrationScript<?,?> alreadyRegisteredScript ;
	/**
	 * The script with the same version as {@link #alreadyRegisteredScript},
	 * which should be registered as well
	 */
	private final MigrationScript<?,?> newScriptToRegister     ;

	/**
	 * @param alreadyRegisteredVersion The version of the already registered script
	 * @param alreadyRegisteredScript The already registered script with the same version
	 * @param newScriptToRegister The script with the same version as alreadyRegisteredScript,
	 * which should be registered as well
	 */
	public VersionAlreadyRegisteredException(
		MigrationVersion     alreadyRegisteredVersion,
		MigrationScript<?,?> alreadyRegisteredScript ,
		MigrationScript<?,?> newScriptToRegister
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
		return alreadyRegisteredVersion;
	}

	/**
	 * @return the already registered script with the same version
	 */
	public MigrationScript<?,?> getAlreadyRegisteredScript()
	{
		return alreadyRegisteredScript;
	}

	/**
	 * @return the script with the same version as {@link #getAlreadyRegisteredScript()},
	 * which should be registered as well
	 */
	public MigrationScript<?,?> getNewScriptToRegister()
	{
		return newScriptToRegister;
	}
}
