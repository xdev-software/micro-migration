/*
 * Copyright © 2021 XDEV Software GmbH (https://xdev.software)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package software.xdev.micromigration.microstream.versionagnostic;

/**
 * Wrapper class for the MicroStream {@code one.microstream.storage.embedded.types.EmbeddedStorageManager} interface.
 * <p>
 * It's simply an interface to not directly depend on the MicroStream-Framework, but still use its functionality.
 * For the separate Versions of MicroStream, a separate Maven-Module was created which implements this
 * interface with the actual EmbeddedStorageManager.
 * <p>
 * {@code VersionAgnostic} because it should be independent of the actual MicroStream implementation used.
 *
 * @param <T> Represents the actually used MicroStream EmbeddedStorageManager
 * 
 * @author Johannes Rabauer
 * 
 */
 public interface VersionAgnosticTunnelingEmbeddedStorageManager<T>
	extends AutoCloseable
{
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 * @return what the actual EmbeddedStorageManager returns
	 */
	T start();
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 * @return what the actual EmbeddedStorageManager returns
	 */
	Object root();
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 * @param newRoot whatever the actual EmbeddedStorageManager uses this for
	 * @return what the actual EmbeddedStorageManager returns
	 */
	Object setRoot(Object newRoot);
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 * @return what the actual EmbeddedStorageManager returns
	 */
	long storeRoot();
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 * @return what the actual EmbeddedStorageManager returns
	 */
	boolean shutdown();
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 * @return what the actual EmbeddedStorageManager returns
	 */
	boolean isAcceptingTasks();
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 * @return what the actual EmbeddedStorageManager returns
	 */
	boolean isRunning();
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 * @return what the actual EmbeddedStorageManager returns
	 */
	boolean isStartingUp();
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 * @return what the actual EmbeddedStorageManager returns
	 */
	boolean isShuttingDown();
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 */
	void checkAcceptingTasks();
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 * @return what the actual EmbeddedStorageManager returns
	 */
	long initializationTime();
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 * @return what the actual EmbeddedStorageManager returns
	 */
	long operationModeTime();
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 * @return what the actual EmbeddedStorageManager returns
	 */
	boolean isActive();
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 * @param nanoTimeBudget whatever the actual EmbeddedStorageManager uses this for
	 * @return what the actual EmbeddedStorageManager returns
	 */
	boolean issueGarbageCollection(long nanoTimeBudget);
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 * @param nanoTimeBudget whatever the actual EmbeddedStorageManager uses this for
	 * @return what the actual EmbeddedStorageManager returns
	 */
	boolean issueFileCheck(long nanoTimeBudget);
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 * @param instance whatever the actual EmbeddedStorageManager uses this for
	 * @return what the actual EmbeddedStorageManager returns
	 */
	long store(Object instance);
	/**
	 * @return the actual MicroStream EmbeddedStorageManager
	 */
	T getNativeStorageManager();
	/**
	 * Simply relais the method-call to the MicroStream EmbeddedStorageManager
	 */
	void close();
}
