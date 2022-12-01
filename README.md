# Micro migration
When you think about default database setup, you probably imagine something like this:

![Imaginative system layout](./docs/MigrationSequence_1.png "Imaginative system layout")

Yet in reality most workflows involve different systems like test systems and prodution systems. 
In code this workflow is represented with version-control systems and different branches.

![Code workflow](./docs/MigrationSequence_2.png "Code workflow")

For this code workflow to behave correctly, for each system a seperate datastore is needed.
To keep these datastores to represent the correspondend data for the code is a hassle.

![Code workflow with datastore](./docs/MigrationSequence_3.png "Code workflow with datastore")

That's why migration frameworks like [Flyway](https://flywaydb.org) and [Liquibase](https://www.liquibase.org/) exist.
Unfortunately both these frameworks are designed to support any type of SQL databases but no NoSQL
databases like [MicroStream](https://microstream.one/). This led to the creation of this library.

This library delivers an easy concept to keep your MicroStream datastore versioned
with migration scripts written in plain java.
It's easy to create code, that automatically brings an datastore with an older version to
the version, suited to the current code.

![Migrate datastore to new version](./docs/MigrationSequence_4.png "Migrate datastore to new version")

## Approaches
There are two possible usages with the Micro migration library:

The **first**, easier approach is to use the `MigrationEmbeddedStorageManager`.
It can be used on a brand new datastore or introduced later, after a MicroStream datastore is already in use.
Only the storage manager (`MigrationEmbeddedStorageManager`) knows about the versioning. 
The rest of application does not know about the version and can have no regards about it.

### `MigrationEmbeddedStorageManager`
Extensive examples can be found in its [own repository](https://github.com/JohannesRabauer/micro-migration-examples).
A simple example where scripts need to be registered in the `ExplicitMigrater`:

```java
public static void main(String[] args) 
{
    ExplicitMigrater migrater = new ExplicitMigrater(new UpdateToV1_0());
    MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(migrater);
    //Do some business logic
    storageManager.shutdown();
}
```

The update scripts can look like this:

```java
public class UpdateToV1_0 implements MigrationScript<Object>
{
	@Override
	public MigrationVersion getTargetVersion(){
		return new MigrationVersion(1);
	}
	
	@Override
	public void migrate(Context<String> context) 
	{
		//Logic of the update
		context.getStorageManager().setRoot("Update 1.0");
	}
}
```

### `MigrationManager`
Although the approach with the `MigrationEmbeddedStorageManager` is pretty easy to handle, it is intrusive
in the way, that it replaces the root entry point of the MicroStream datastore and inserts its own `VersionedRoot` as root. Some users might find this too entrusive.

That's why a second approach can be used, where the `MigrationManager` is used. This class is also used internally by
the `MigrationEmbeddedStorageManager`. 

```java
public static void main(String[] args) 
{
	ExplicitMigrater migrater = new ExplicitMigrater(new UpdateToV1_0());
	EmbeddedStorageManager storageManager = EmbeddedStorage.start();
		VersionedObject<Object> versionedRoot =(VersionedObject<Object>)storageManager.root();
		new MigrationManager(versionedRoot, migrater, storageManager).migrate(versionedBranch);
	//Do some business logic
	storageManager.shutdown();
}
```

## Migrater
### `ExplicitMigrater`
Scripts for the migrations must be registered in a `MicroMigrater`. 
The simplest way for this is to use the `ExplicitMigrater` and just put the scripts in the constructor.
A downside of this method is that you need to register all scripts (new or old) manually in the constructor.

```java
final ExplicitMigrater migrater = new ExplicitMigrater(
    new UpdateToV1_0(),
    new UpdateToV1_1()
);
```

### `ReflectiveMigrater`
For a more convenient usage the `ReflectiveMigrater` was built. 
You simply instanciate a object of this class with the package name of your `MicroMigrationScript`s.
The `ReflectiveMigrater` will search for any implementations of `MicroMigrationScript` in the given package.
This way scripts can simply be placed in the same package and on startup of the application all scripts are loaded in.

```java
final ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.examples.reflective.scripts");
```
Since the `ReflectiveMigrater` uses the [Reflections library](https://github.com/ronmamo/reflections) it is extracted to its [own repository](https://github.com/JohannesRabauer/micro-migration-reflection).
