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
package software.xdev.micromigration.version;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MigrationVersionTest
{
	
	@Test
	void testToString_v1()
	{
		final MigrationVersion version = new MigrationVersion(1);
		assertEquals("v1", version.toString());
	}
	
	@Test
	void testToString_v11()
	{
		final MigrationVersion version = new MigrationVersion(1, 1);
		assertEquals("v1.1", version.toString());
	}
	
	@Test
	void testToString_v111()
	{
		final MigrationVersion version = new MigrationVersion(1, 1, 1);
		assertEquals("v1.1.1", version.toString());
	}
	
	@Test
	void testToString_v0()
	{
		final MigrationVersion version = new MigrationVersion(0);
		assertEquals("v0", version.toString());
	}
	
	@Test
	void testToString_vNull()
	{
		final MigrationVersion version = new MigrationVersion();
		assertEquals("v0", version.toString());
	}
	
}
