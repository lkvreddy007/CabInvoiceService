package com.capg;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InvoiceServiceTest {

	InvoiceService invoiceService = null;
	
	@Before
	public void setUp() throws Exception {
		invoiceService = new InvoiceService();
	}
	
	@Test 
	public void givenDistanceAnd_Time_ShouldReturnTotalFare() {
		double distance = 2.0;
		int time = 5;
		double fare = invoiceService.calculateFare(distance,time);
		Assert.assertEquals(25,fare,0.0);
	}
	
	@Test 
	public void givenLessDistanceOrTime_ShouldReturnMinFare() {		double distance = 0.1;
		int time = 1;
		double fare = invoiceService.calculateFare(distance, time);
		Assert.assertEquals(5, fare, 0.0);
	}
	
	@Test
	public void givenMultipleRides_ShouldReturnInvoiceSummary() {
		Ride[] rides = { new Ride(2.0, 5),
				 		 new Ride(0.1,1)
			   		   };
		InvoiceSummary summary = invoiceService.calculateFare(rides);
		InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
		Assert.assertEquals(expectedInvoiceSummary, summary);
	}
	
	@Test
	public void givenUserIdAndRides_ShouldReturnInvoiceSummary() {
		String userId = "a@bc.com";
		Ride[] rides = { new Ride(2.0, 5),
				 		 new Ride(0.1,1)
			   		   };
		invoiceService.addRides(userId, rides);
		InvoiceSummary summary = invoiceService.getInvoiceSummary(userId);
		InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
		Assert.assertEquals(expectedInvoiceSummary, summary);
	}
	
	@Test
	public void givenUserId_ShouldReturnInvoiceBasedOnRideType() {
		String userId = "a@bc.com";
		Ride[] rides = { new Ride(2.0, 5),
				 		 new Ride(0.1,1)
			   		   };
		invoiceService.addRides(userId, rides);
		InvoiceSummary summary = invoiceService.getInvoiceSummaryForPremium(userId);
		InvoiceSummary expecterInvoiceSummary = new InvoiceSummary(2, 60);
		Assert.assertEquals(expecterInvoiceSummary, summary);
	}
}
