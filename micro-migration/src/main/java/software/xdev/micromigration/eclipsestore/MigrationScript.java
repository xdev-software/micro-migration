/*
 * Copyright © 2021 XDEV Software (https://xdev.software)
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
package software.xdev.micromigration.eclipsestore;

import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;


/**
 * Interface for scripts to migrate / update datastores.
 * <p>
 * One script is supposed to bring a datastore from a lower version to the target version. After the
 * {@link VersionAgnosticMigrationScript#migrate(Context)} method is called, the target version is reached.
 * <p>
 * This is a shorthand for {@link VersionAgnosticMigrationScript} for this specific MicroStream version.
 */
public interface MigrationScript<T> extends VersionAgnosticMigrationScript<T, MigrationEmbeddedStorageManager>
{
}
