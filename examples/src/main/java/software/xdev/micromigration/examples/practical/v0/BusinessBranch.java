package software.xdev.micromigration.examples.practical.v0;

import java.util.ArrayList;
import java.util.List;

public class BusinessBranch
{
	public final List<Customer> customers = new ArrayList<>();
	
	@Override
	public String toString() {
		String toString = "Branch v0\nCustomers:";
		for (Customer customer : customers) {
			toString += "\n  " + customer.name;
		}
		return toString;
	}

	public static BusinessBranch createDummyBranch()
	{
		BusinessBranch branch = new BusinessBranch();
		Customer customer1 = new Customer();
		customer1.name   = "Mick Fleetwood";
		customer1.number = 1;
		customer1.street = "Fleetwood Street";
		customer1.city   = "Redruth";
		branch.customers.add(customer1);
		Customer customer2 = new Customer();
		customer2.name   = "Lindsey Buckingham";
		customer2.number = 2;
		customer2.street = "Mac Street";
		customer2.city   = "Palo Alto";
		branch.customers.add(customer2);
		return branch;
	}
}
