import org.apache.cxf.jaxrs.client.WebClient;

import com.cxf.rest.dto.Customer;

public class ClientWS {

	private static final String baseAddress = "http://localhost:8080/";

	public static void main(String args[]) throws Exception {

		WebClient client = WebClient.create(baseAddress);
		client.path("pengtao/webservice/customerservice/customers/add/")
				.accept("application/xml").type("application/xml");
		// String info = client.get(String.class);
		// System.out.println(info);
		//
		// Customer cus = client.get(Customer.class);
		// System.out.println(cus.getName());
		Customer c = new Customer();
		c.setId("123");
		client.post(c);

	}

}