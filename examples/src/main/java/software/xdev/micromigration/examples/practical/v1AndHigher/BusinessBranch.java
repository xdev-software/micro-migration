package software.xdev.micromigration.examples.practical.v1AndHigher;

import java.util.ArrayList;
import java.util.List;


public class BusinessBranch 
{
	public final List<Customer> customers = new ArrayList<>();
	
	@Override
	public String toString() {
		String toString = "Branch v1 and higher\nCustomers:";
		for (Customer customer : customers) {
			toString += "\n  " + customer.name;
		}
		return toString;
	}
}
