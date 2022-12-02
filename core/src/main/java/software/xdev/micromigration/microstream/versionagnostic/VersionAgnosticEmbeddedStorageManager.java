package software.xdev.micromigration.microstream.versionagnostic;

public interface VersionAgnosticEmbeddedStorageManager<T> {
	public long store(final Object instance);
	public T getNativeStorageManager();
}
