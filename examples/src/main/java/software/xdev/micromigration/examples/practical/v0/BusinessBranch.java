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
}
