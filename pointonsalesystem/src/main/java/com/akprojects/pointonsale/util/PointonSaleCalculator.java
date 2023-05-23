package com.akprojects.pointonsale.util;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.akprojects.pointonsale.models.Product;

public class PointonSaleCalculator {
	
	//	productA = $20; productB = $40; productC = $50;
	// we can modify the code for adding new products and price
	
	final int wrapFee = 1; // per unit
	final int shippingFee = 5; // per package(10unit)	

	String[] productNames;
	int[] productPrice;
	int []quantity; 
	boolean []giftWrapped;

	int totalProducts;	
	
	private int subTotal;
	private ArrayList<Product> cart;
	private String discountName;
	private double discountAmount;
	private int shippingCharge;
	private int giftwrapCharge;
	private double grandTotal;
	
	
	public PointonSaleCalculator(ArrayList<String> products, ArrayList<Integer> prices, ArrayList<Integer> quantities,
			ArrayList<Boolean> giftwrap) {
		
		totalProducts = products.size();
		int i=0;
		
		cart = new ArrayList<Product>();
		productNames = new String[totalProducts];
		productPrice = new int[totalProducts];
		quantity= new int[totalProducts];
		giftWrapped= new boolean[totalProducts];
		
		
		for(String p: products)
			productNames[i++] = p;
		
		i=0;
		for(Integer p: prices)
			productPrice[i++] = p;
		
		i=0;
		for(Integer q: quantities)
			quantity[i++] = q;
		
		i=0;
		for(Boolean g: giftwrap)
			giftWrapped[i++] =g;
		
		subTotal=0;
		giftwrapCharge=0;
		shippingCharge=0;
		grandTotal=0;
		
		calculate();
		
	}
	
	private void calculate() {
		
		// Product Name, Price, Quantity, Total
		for(int i=0; i<totalProducts; i++) {
			cart.add( new Product(productNames[i], productPrice[i], quantity[i], productPrice[i]*quantity[i]));
			subTotal+= productPrice[i]*quantity[i];
		}
		
		
		int totalQuantity=0;
		for(int q: quantity)
			totalQuantity+=q;
		
		// Discount Name and Amount
		setDiscount(productPrice, quantity, totalQuantity, subTotal); 
		
		// not working after deployment
		// Shipping Charge
		shippingCharge= (int)(Math.ceil((double)totalQuantity/10) * shippingFee) ;
		
		// Gift Wrap Charge
		for(int i=0; i<totalProducts; i++) 
			giftwrapCharge += giftWrapped[i] ? quantity[i] : 0;
		giftwrapCharge *= wrapFee;
		
		// Sub Total
		subTotal+= shippingCharge + giftwrapCharge;
		
		grandTotal = subTotal- discountAmount;
		
	}
	
	// The discount name applied & the discount amount.
	private void setDiscount(int[] productPrice, int[] quantity,int totalQuantity, int subTotal) {

		HashMap<String, Double> discounts = new HashMap<String, Double>();
		
		discounts.put("Flat_10_discount", flat_10_discount(subTotal));
		discounts.put("Bulk_5_discount", bulk_5_discount(productPrice, quantity));
		discounts.put("Bulk_10_discount", bulk_10_discount(totalQuantity, subTotal));
		discounts.put("Tiered_50_discount", tiered_50_discount(productPrice, quantity, totalQuantity));
		
		
		Entry<String, Double> discount = new AbstractMap.SimpleEntry<String, Double>("No Discount", 0.0);
		
		for(Entry<String, Double> e: discounts.entrySet()) {
			if(e.getValue() > discount.getValue())
				discount = e;
		}
	
		discountName = discount.getKey();
		discountAmount = discount.getValue();	
		
	}
	
	private double flat_10_discount(int subTotal) {
		return subTotal>200 ? 10:0;
	}
	
	private double bulk_5_discount(int[] productPrice, int[] quantity) {

		double discount, maxDiscount=0;
		
		for(int i=0; i<totalProducts; i++) {
			if(quantity[i] > 10) {
								
				// item i's total price - 5% discount
				discount = (double) (productPrice[i] * quantity[i])  * 5/100;
				
				if(discount > maxDiscount) 
					maxDiscount = discount;
			}
		}
		
		return maxDiscount;
	}
	
	private double bulk_10_discount(int totalQuantity, int subTotal) {
		return totalQuantity> 20? subTotal/10 : 0;
	}
	
	private double tiered_50_discount(int[] productPrice, int[] quantity, int totalQuantity ) {
		
		if(totalQuantity <=30) return 0; // Not Applicable
	
		double discount, maxDiscount=0;
	
		for(int i=0; i<totalProducts; i++) {

			if(quantity[i]>15) {
				
			// 50% Discount on below 15 products 
			discount= ((double)(quantity[i] - 15) * productPrice[i]) /2; 
	
			if(discount > maxDiscount) 
				maxDiscount = discount;
			
			}
		}
	
		return maxDiscount;
	}


	public ArrayList<Product> getCart() {
		return cart;
	}

	public String getDiscountName() {
		return discountName;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public int getShippingCharge() {
		return shippingCharge;
	}

	public int getGiftwrapCharge() {
		return giftwrapCharge;
	}

	public int getSubTotal() {
		return subTotal;
	}	
	public double getGrandTotal() {
		return grandTotal;
	}
	
}









