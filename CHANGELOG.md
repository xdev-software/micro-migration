# 3.0.2
* Migrated deployment to _Sonatype Maven Central Portal_ [#155](https://github.com/xdev-software/standard-maven-template/issues/155)
* Updated dependencies

# 3.0.1

* Fix for ExplicitMigrater without any scripts

# 3.0.0

* Added support for EclipseStore v2
* Having an empty version for the first migration-script is now allowed

# 2.0.0
_Major refactoring_
* Consolidated all previous modules into a single one
* Dropped support for MicroStream as EclipseStore is it's successor
* Refactored and adjusted code accordingly
* Slimed down dependencies
* Minimal required Java version: 17

# 1.0.0
* Added support for EclipseStore v1
* Updated plenty of libraries

# 0.0.9
* Added support for MicroStream v8

# 0.0.8
* Access to the native embedded manager is now possible in the scripts (see ``VersionAgnosticMigrationEmbeddedStorageManager#getNativeStorageManager``)

# 0.0.7
* A lot of refactoring of the module structure
* A migration history is now available and automatically stored

# 0.0.6
* Tried a new release-action...again.

# 0.0.5
* Tried a new release-action.

# 0.0.4
* Fixed setup. 0.0.3 was not working with the release setup.

# 0.0.3
* Restructured the complete maven modules. Multiple MicroStream-Versions are now supported.
* Added plenty of documentation

# 0.0.2
* Updated MicroStream from v4 to v5.
