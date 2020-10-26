package com.capg;

public class InvoiceService {

	private static final double MINIMUM_COST_PER_KILOMETER = 10;
	private static final int COST_PER_TIME = 1;
	private static final double MINIMUM_FARE = 5;
	private static final double COST_PER_KM_FOR_PREMIUM = 15;
	private static final int COST_PER_TIME_FOR_PREMIUM = 2;
	private static final int MINIMUM_FARE_FOR_PREMIUM = 20;
	private RideRepository rideRepository;
	
	public InvoiceService() {
		this.rideRepository = new RideRepository();
	}
	
	public double calculateFare(double distance, int time) {
		double totalFare = distance*MINIMUM_COST_PER_KILOMETER + time*COST_PER_TIME;
		return Math.max(totalFare, MINIMUM_FARE);
	}

	public InvoiceSummary calculateFare(Ride[] rides) {
		double totalFare = 0;
		for(Ride ride:rides) {
			totalFare += this.calculateFare(ride.distance, ride.time);
		}
		return new InvoiceSummary(rides.length, totalFare);
	}
	
	public void addRides(String userId, Ride[] rides) {
		rideRepository.addRide(userId, rides);
	}

	public InvoiceSummary getInvoiceSummary(String userId) {
		return this.calculateFare(rideRepository.getRides(userId));
	}

	public InvoiceSummary getInvoiceSummaryForPremium(String userId) {
		return this.calculatePremiumFares(rideRepository.getRides(userId));
	}

	private InvoiceSummary calculatePremiumFares(Ride[] rides) {
		double totalFare =0;
		for(Ride ride:rides) {
			totalFare += this.calculatePremiumFares(ride.distance,ride.time);
		}
		InvoiceSummary invoiceSummary = new InvoiceSummary(rides.length, totalFare);
		return invoiceSummary;
	}

	private double calculatePremiumFares(double distance, int time) {
		double fare = distance * COST_PER_KM_FOR_PREMIUM + time * COST_PER_TIME_FOR_PREMIUM;
		return Math.max(fare, MINIMUM_FARE_FOR_PREMIUM);
	}
	
}
