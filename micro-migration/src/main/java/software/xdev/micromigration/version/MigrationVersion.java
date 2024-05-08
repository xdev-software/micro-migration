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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


/**
 * Defines one version of the MicroStream datastore.
 */
public class MigrationVersion
{
	private final int[] versions;
	
	/**
	 * @param versions as integers. For example 1.0.2 would be an array of [1,0,2]
	 */
	public MigrationVersion(final int... versions)
	{
		if(versions == null || versions.length == 0)
		{
			this.versions = new int[]{0};
		}
		else
		{
			this.versions = versions;
		}
	}
	
	/**
	 * @param versionsAsList as integers. For example 1.0.2 would be a list of [1,0,2]
	 */
	public MigrationVersion(final List<Integer> versionsAsList)
	{
		if(versionsAsList == null || versionsAsList.isEmpty())
		{
			this.versions = new int[]{0};
		}
		else
		{
			final int[] versionsAsArray = new int[versionsAsList.size()];
			for(int i = 0; i < versionsAsArray.length; i++)
			{
				versionsAsArray[i] = versionsAsList.get(i);
			}
			this.versions = versionsAsArray;
		}
	}
	
	/**
	 * @return versions as an array of integers. For example 1.0.2 would be an array of [1,0,2]
	 */
	public int[] getVersions()
	{
		return this.versions;
	}
	
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("v");
		for(final int version : this.versions)
		{
			sb.append(version).append(".");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(this.versions);
		return result;
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		if(this == obj)
		{
			return true;
		}
		if(obj == null)
		{
			return false;
		}
		if(this.getClass() != obj.getClass())
		{
			return false;
		}
		final MigrationVersion other = (MigrationVersion)obj;
		return Arrays.equals(this.versions, other.versions);
	}
	
	/**
	 * Provides a {@link Comparator} that compares the {@link #getVersions()} of the given versions
	 */
	public static Comparator<MigrationVersion> COMPARATOR =
		Comparator.comparing(MigrationVersion::getVersions, (o1, o2) -> Arrays.compare(o1, o2));
}
