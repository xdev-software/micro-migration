# micro-migration
Migration Lib for MicroStream.

This library delivers an easy concept to keep your MicroStream datastore versioned
with migration scripts written in plain java.

It can be used on a brand new datastore or introduced later, after a MicroStream datastore is already in use.
Only the storage manager (`MigrationEmbeddedStorageManager`) knows about the versioning. 
The rest of application does not know about the version and can have no regards about it.

## Usage example
Extensive examples can be found in its [own repository](https://github.com/JohannesRabauer/micro-migration-examples).
A simple example where scripts need to be registered in the `ExplicitMigrater`:
```java
public static void main(String[] args) 
{
    final ExplicitMigrater migrater = new ExplicitMigrater(new UpdateToV1_0());
    final MigrationEmbeddedStorageManager storageManager = MigrationEmbeddedStorage.start(migrater);
    //Do some business logic
    storageManager.shutdown();
}
```
The update scripts can look like this:
```java
public class UpdateToV1_0 implements MicroMigrationScript
{
	public MicroMigrationVersion getTargetVersion(){
		return new MicroMigrationVersion(1,0);
	}

	public void execute(Object root, MigrationEmbeddedStorageManager storageManager){
        //Logic of the update
		storageManager.setRoot("Update 1.0");
	}
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
final ReflectiveMigrater migrater = new ReflectiveMigrater("de.johannes_rabauer.micromigration.examples.reflective.scripts");
```
Since the `ReflectiveMigrater` uses the [Reflections library](https://github.com/ronmamo/reflections) it is extracted to its [own repository](https://github.com/JohannesRabauer/micro-migration-reflection).