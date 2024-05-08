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
package software.xdev.micromigration.examples.practical.v0;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BusinessBranch
{
	public final List<Customer> customers = new ArrayList<>();
	
	@Override
	public String toString()
	{
		return "Branch v0\n"
			+ "Customers:\n"
			+ this.customers.stream()
			.map(c -> c.name)
			.collect(Collectors.joining("\n"));
	}
	
	public static BusinessBranch createDummyBranch()
	{
		final BusinessBranch branch = new BusinessBranch();
		final Customer customer1 = new Customer();
		customer1.name = "Mick Fleetwood";
		customer1.number = 1;
		customer1.street = "Fleetwood Street";
		customer1.city = "Redruth";
		branch.customers.add(customer1);
		final Customer customer2 = new Customer();
		customer2.name = "Lindsey Buckingham";
		customer2.number = 2;
		customer2.street = "Mac Street";
		customer2.city = "Palo Alto";
		branch.customers.add(customer2);
		return branch;
	}
}
