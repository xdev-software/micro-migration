# Micro migration Reflection
Provides a migrater based on reflection

Currently only holds one class `ReflectiveMigrater` which uses the [Reflections library](https://github.com/ronmamo/reflections)
to find all instances of the `MicroMigrationScript` in a defined package.

Because it is using the additional dependency, this class is extracted in this own repository and can be added only if needed 
to the [core module](https://github.com/xdev-software/micro-migration#maven).

## Usage

### Maven

Simply add the dependency to your `pom.xml`:
```
<dependency>
    <groupId>software.xdev</groupId>
    <artifactId>micro-migration-reflection</artifactId>
    <version>1.0.0</version>
</dependency>
```
