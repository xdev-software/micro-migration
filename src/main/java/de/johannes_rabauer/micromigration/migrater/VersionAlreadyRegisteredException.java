package de.johannes_rabauer.micromigration.migrater;

import java.util.Objects;

import de.johannes_rabauer.micromigration.version.MigrationVersion;
import de.johannes_rabauer.micromigration.scripts.MigrationScript;


public class VersionAlreadyRegisteredException extends Error
{
	private static final long serialVersionUID = 2153008832167067975L;
	
	private MigrationVersion alreadyRegisteredVersion;
	private MigrationScript<?,?> alreadyRegisteredScript ;
	private MigrationScript<?,?> newScriptToRegister     ;
	
	public VersionAlreadyRegisteredException(
		MigrationVersion   alreadyRegisteredVersion,
		MigrationScript<?,?> alreadyRegisteredScript ,
		MigrationScript<?,?> newScriptToRegister
	) 
	{
		super("Version " + alreadyRegisteredVersion.toString() + " is already registered. Versions must be unique within the migrater.");
		this.alreadyRegisteredVersion = Objects.requireNonNull(alreadyRegisteredVersion);
		this.alreadyRegisteredScript  = Objects.requireNonNull(alreadyRegisteredScript) ;
		this.newScriptToRegister      = Objects.requireNonNull(newScriptToRegister)     ;
	}

	public MigrationVersion getAlreadyRegisteredVersion() 
	{
		return alreadyRegisteredVersion;
	}

	public MigrationScript<?,?> getAlreadyRegisteredScript()
	{
		return alreadyRegisteredScript;
	}

	public MigrationScript<?,?> getNewScriptToRegister()
	{
		return newScriptToRegister;
	}
}
