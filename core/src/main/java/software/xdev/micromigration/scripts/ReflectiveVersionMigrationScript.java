package software.xdev.micromigration.scripts;

import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticMigrationEmbeddedStorageManager;
import software.xdev.micromigration.version.MigrationVersion;

import java.util.ArrayList;


/**
 * Script which creates the target version of the script through the class name.
 * <p>
 * Class name has to be in the scheme:<br>
 * <code>
 * vM_Classname<br>
 * vM_m_Classname<br>
 * vM_m_m_Classname<br>
 * </code>
 * Where <code>v</code> is short for version and is a constant (just a char),<br>
 * <code>M</code> is a integer for the major version,<br>
 * <code>m</code> is a integer for the minor version<br>
 * <code>Classname</code> is a custom String that the user can choose.<br>
 * This scheme can basically be extended infinetly. For example: <code>v1_1_2_2_MyUpdateScript</code>
 * <p>
 * Therefore the character <code>_</code> can only be used as a seperator of versions
 * and may not be used for other purposes.
 * <p>
 * If the class name has the wrong format, an {@link Error} is thrown.
 * 
 * @author Johannes Rabauer
 *
 */
public abstract class ReflectiveVersionMigrationScript<T,E extends VersionAgnosticMigrationEmbeddedStorageManager<?,?>> implements VersionAgnosticMigrationScript<T,E>
{
	private final static char   PREFIX                     = 'v';
	private final static String VERSION_SEPERATOR          = "_";
	private final static String WRONG_FORMAT_ERROR_MESSAGE = "Script has invalid class name. Either rename the class to a valid script class name, or implement method getTargetVersion().";

    private final MigrationVersion version;
    
    /**
     * @throws Error if the class name has the wrong format
     */
    public ReflectiveVersionMigrationScript() 
    {
    	this.version = createTargetVersionFromClassName();
	}
    
    private MigrationVersion createTargetVersionFromClassName()
    {
		final String implementationClassName = this.getClass().getSimpleName();
		if(PREFIX != implementationClassName.charAt(0))
		{
			throw new Error(WRONG_FORMAT_ERROR_MESSAGE);				
		}
		final String implementationClassNameWithoutPrefix = implementationClassName.substring(1);
		final String[] classNameParts = implementationClassNameWithoutPrefix.split(VERSION_SEPERATOR);
		if(classNameParts.length < 2)
		{
			throw new Error(WRONG_FORMAT_ERROR_MESSAGE);			
		}
		try
		{
			final ArrayList<Integer> versionNumbers = new ArrayList<>();
			for(int i = 0; i < classNameParts.length - 1; i++)
			{
				versionNumbers.add(Integer.parseInt(classNameParts[i]));
			}
			return new MigrationVersion(versionNumbers);	
		}
		catch (NumberFormatException e)
		{
			throw new Error(WRONG_FORMAT_ERROR_MESSAGE);			
		}
    }
    
	@Override
	public MigrationVersion getTargetVersion()
	{
		return this.version;
	}

}
