package com.akprojects.pointonsale.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import com.akprojects.pointonsale.util.*;


@Controller
@RequestMapping("/zenshop")
public class CartController {
	
	@GetMapping("/landing")
	public String landing() {
		return "ZenShop/landing";
	}
	
	@GetMapping("/buyproducts")
	public String buyProducts() {
		return "ZenShop/buy-products";
	}
	
	@PostMapping("/addtocart")
	public ModelAndView addtoCart(
			
		@RequestParam(defaultValue="0") String aQuantity,
		@RequestParam(defaultValue="0") String bQuantity,
		@RequestParam(defaultValue="0") String cQuantity,
		@RequestParam(defaultValue = "false") boolean aGiftwrap,
		@RequestParam(defaultValue = "false") boolean bGiftwrap,
		@RequestParam(defaultValue = "false") boolean cGiftwrap
		
			) {

		// in future we can modify the code to add the products in database 
		// instead this
		
		ArrayList<String> products = new ArrayList<String>();
		products.add("Product A");
		products.add("Product B");
		products.add("Product C");
		
		ArrayList<Integer> prices = new ArrayList<Integer>();
		prices.add(20);
		prices.add(40);
		prices.add(50);
		
		ArrayList<Integer> quantities = new ArrayList<Integer>();
		
		quantities.add(  Integer.parseInt(aQuantity));
		quantities.add(  Integer.parseInt(bQuantity));
		quantities.add(  Integer.parseInt(cQuantity));
		
		ArrayList<Boolean> giftwrap = new ArrayList<Boolean>();
		giftwrap.add(aGiftwrap);
		giftwrap.add(bGiftwrap);
		giftwrap.add(cGiftwrap);
		
//		Cart cart = new Cart();
		
		// modify this to change charges
		PointonSaleCalculator pos = new PointonSaleCalculator(products, prices, quantities,giftwrap);
		
		
		ModelAndView mdv = new ModelAndView("ZenShop/billing");
		mdv.addObject("pos",pos);
		
		
		/////////////////////////
		
		
		
		//////////////////////////
		
		
		return mdv;
	}
	
	

}
