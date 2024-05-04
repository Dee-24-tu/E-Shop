package petiteshoestore;

import java.util.ArrayList;   
import java.util.List; 

public class Cart {  
	private List<CartItem> items;

	public Cart() {      
		 items = new ArrayList<>();
	}           
  
	public void addItem(Shoes shoe, int size, int quantity) {
		CartItem cartItem = new CartItem(shoe, size, quantity);
        items.add(cartItem);
	}

	public void removeItem(CartItem cartItem) {
		items.remove(cartItem);
	}  
  
    public List<CartItem> getItems() {     
        return items; 
    }
	
	public void processPurchase(int shoeId, int size, int quantity, Inventory inventory) {
	    Shoes shoe = inventory.getProduct(shoeId);
	    
	    if (shoe != null && shoe.getShoeSize().contains(size)) {
	    	
	        // Check if the requested quantity is available
	        if (shoe.getQuantity() >= quantity) {
	        	
	            // Decrease the quantity of the selected shoe
	            shoe.decreaseQuantity(quantity);
	            
	            // Display purchase details
	            System.out.println("Purchase successful:");
	            System.out.println("Product Name: " + shoe.getShoeType() + " (Size " + size + ")");
	            System.out.println("Quantity: " + quantity);
	            System.out.println("Total Price: $" + (shoe.getPrice() * quantity));
	            
	        } else {
	            System.out.println("Insufficient quantity available for " + shoe.getShoeType() + " (Size " + size + "). Available quantity: " + shoe.getQuantity());
	        }
	    } else {
	        System.out.println("Product not found or invalid size selected.");
	    }
	}

	public double getTotalPrice() {
		 double total = 0;  
		 for (CartItem item : items) {
			 total += item.getShoe().getPrice() * item.getQuantity();
		 }
		 return total;
	}
}

