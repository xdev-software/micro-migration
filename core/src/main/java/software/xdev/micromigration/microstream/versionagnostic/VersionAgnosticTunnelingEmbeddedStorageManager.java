package software.xdev.micromigration.microstream.versionagnostic;

import software.xdev.micromigration.version.Versioned;


/**
 * Wrapper class for the MicroStream {@link VersionAgnosticEmbeddedStorageManager} interface.
 * <p>
 * Basically it intercepts storing the root object and places a {@link Versioned}
 * in front of it. This means the root object of the datastore is then versioned.<br>
 * Internally uses the {@link VersionAgnosticMigrationManager} to do the actual migration.
 * 
 * @author Johannes Rabauer
 * 
 */
 public interface VersionAgnosticTunnelingEmbeddedStorageManager<T>
	extends VersionAgnosticEmbeddedStorageManager<T>, AutoCloseable
{
	T start();
	Object root();
	Object setRoot(Object newRoot);
	long storeRoot();
	boolean shutdown();
	boolean isAcceptingTasks();
	boolean isRunning();
	boolean isStartingUp();
	boolean isShuttingDown();
	void checkAcceptingTasks();
	long initializationTime();
	long operationModeTime();
	boolean isActive();
	boolean issueGarbageCollection(long nanoTimeBudget);
	boolean issueFileCheck(long nanoTimeBudget);
	long store(Object instance);
	T getNativeStorageManager();
	void close();
}
