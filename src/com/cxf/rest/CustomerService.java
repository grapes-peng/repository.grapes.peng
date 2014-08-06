package com.cxf.rest;

import javax.jws.WebService;
import javax.ws.rs.core.Response;

import com.cxf.rest.dto.Customer;

@WebService
public interface CustomerService {

	Response addCustomer(Customer customer);

	Customer getCustomer(String id, String userName);

}
