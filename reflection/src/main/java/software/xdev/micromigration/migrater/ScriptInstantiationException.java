package software.xdev.micromigration.migrater;

/**
 * Holds information about exceptions if a script class can not be instantiated.
 * 
 * @author Johannes Rabauer
 *
 */
public class ScriptInstantiationException extends Exception 
{
	private static final long serialVersionUID = 7087560201226697433L;
	
    public ScriptInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }
}
