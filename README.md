# micro-migration
Migration Lib for MicroStream.

This library deliveres an easy concept to keep your MicroStream datastore versioned
with migration scripts written in plain java.

## Usage example
A simple example where scripts need to be registered in the `ExplicitMigrater`:
```java
public static void main(String[] args) 
{
    final ExplicitMigrater migrater = new ExplicitMigrater(
        new UpdateToV1_0(),
        new UpdateToV1_1()
    );
    final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(migrater);
    System.out.println(storageManager.root());
    if(storageManager.root() == null)
    {
        storageManager.setRoot("Hello World! @ " + new Date());
    }
    storageManager.storeRoot();
    storageManager.shutdown();
}
```
The update scripts can look like this:
```java
public class UpdateToV1_0 implements MicroMigrationScript
{
	public MicroMigrationVersion getTargetVersion() 
	{
		return new MicroMigrationVersion(1,0);
	}

	public void execute(
		Object                          root          ,
		MigrationEmbeddedStorageManager storageManager
	)
	{
		System.out.println("Update " + getTargetVersion().toString() + " executed.");
		storageManager.setRoot("Hello World! @ " + new Date() + " Update 1.0");
	}
}
```