package petiteshoestore;

import java.util.List; 
import java.util.Scanner; 
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;

public class OnlineShoeStore {
    private Inventory inventory;
    private Cart cart;
    private static Scanner scanner = new Scanner(System.in);

    public OnlineShoeStore() { 
        inventory = new Inventory();   
        cart = new Cart(); 
    }

    public Cart getCart() {
     return cart; 
    }
   

	public void setCart(Cart cart) {
		this.cart = cart; 
	}

	private void writeToFile(String fileName, String data) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
	        
	        File file = new File(fileName);
	        if (!file.exists()) {
	            file.createNewFile();  // Create the file if it doesn't exist
	        }
	         
	        LocalDateTime now = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String formattedDate = now.format(formatter);
	        
	        writer.write(formattedDate + " - " + data);
	        writer.newLine();
	        
	        System.out.println("\nPurchase details written to " + fileName);
	    } catch (IOException e) {
	        System.out.println("Error writing to file: " + e.getMessage());
	        e.printStackTrace(); 
	    }
	}

	public void processPurchase(int shoeId, int size, int quantity) {
		Shoes shoe = inventory.getProduct(shoeId);
	    if (shoe != null && shoe.getShoeSize().contains(size)) {
	        if (shoe.getQuantity() >= quantity) {
	        	
	        	shoes.decreaseQuantity(quantity);
	        	
	            // Add to cart
	            cart.addItem(shoe, size, quantity);
	            
	            String purchaseDetails = "All Items sold:";
	            int itemNumber = 1;
	            for (CartItem cartItem : cart.getItems()) {
	                purchaseDetails += "\nItem " + itemNumber++;
	                purchaseDetails += "\n" + cartItem.toString();
	                purchaseDetails += "\n------------------------------";
	            }
	            
	            purchaseDetails += "\nTotal Price: $" + cart.getTotalPrice();
	            
	            purchaseDetails += "\n\nSelected Purchases:";
	            for (CartItem cartItem : cart.getItems()) {
	                Shoes purchasedShoe = cartItem.getShoe();
	                int remainingQuantity = inventory.getQuantity(purchasedShoe.getShoeId(), cartItem.getSize());
	                purchaseDetails += "\nQuantity updated. Remaining quantity for " + purchasedShoe.getShoeType() + " (Size " + cartItem.getSize() + "): " + remainingQuantity;
		            writeToFile("purchase_details.txt", "Quantity updated. Remaining quantity for " + purchasedShoe.getShoeType() + " (Size " + cartItem.getSize() + "): " + remainingQuantity);
	            }

	            // Write purchase details to file
	            writeToFile("purchase_details.txt", purchaseDetails);

	            // Updated inventory details to file
	            for (CartItem cartItem : cart.getItems()) {
	                Shoes purchasedShoe = cartItem.getShoe();
	                int remainingQuantity = inventory.getQuantity(purchasedShoe.getShoeId(), cartItem.getSize());
	               
	                writeToFile("purchase_details.txt", "Inventory Sold: " + cartItem.getQuantity() + " " + purchasedShoe.getShoeType() + " (Size " + cartItem.getSize() + ")");
	                writeToFile("purchase_details.txt", "Inventory in Stock: " + remainingQuantity + " " + purchasedShoe.getShoeType() + " (Size " + cartItem.getSize() + ")");
	            }
	       } else {
	            System.out.println("Insufficient stock for " + shoe.getShoeType() + " (Size " + size + "). Available quantity: " + shoe.getQuantity());
	        }
	   } else {
	        System.out.println("Invalid product or size selection.");
	    }
	}

	
    public void addToCart(int shoeId, int size, int quantity) {
    	Shoes product = inventory.getProduct(shoeId);
        if (product != null) {
            cart.addItem(product, size, quantity);
            System.out.println(product.getShoeType() + " added to cart.");
        } else {  
            System.out.println("Product not found in inventory.");
        }
    }
    
    public void removeFromCart(int index) {
    	List<CartItem> cartItems = cart.getItems();
    	 if (index >= 0 && index < cartItems.size()) {
    		 cart.removeItem(cartItems.get(index));
        } else {
        	System.out.println("Invalid item number. Please try again.");
        }
    }
 
    public void displayCart() {
        System.out.println("All Items in Cart:");

        if (cart.getItems().isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        
        int itemNumber = 1;
        for (CartItem item : cart.getItems())  {
        	 System.out.println("Item " + itemNumber++);
             System.out.println(item);
        	 System.out.println("-----------------------------");
        }
        System.out.println("Total Price: $" + cart.getTotalPrice());
    }
    
    public void checkout() { 
    	CheckOut.checkout(inventory, cart, scanner); 
    }
    
    public void manageCart() {
	    boolean continueShopping = true;
	    while (continueShopping) {
	        displayCart();
	        
	        System.out.println("\nMenu Options:");
	        System.out.println("1. Add item to cart");
	        System.out.println("2. Remove item from cart");
	        System.out.println("3. Continue to payment");
	        System.out.println("4. Exit");
	        
	        int choice = scanner.nextInt();
	        
	        switch (choice) {
	            case 1:
	            	inventory.displayProducts(); 
	                System.out.println("Enter the product number to add to cart:");
	                int addProduct = scanner.nextInt();
	              //  addToCart(addProduct); 
	                System.out.println("Enter the size:");
	                int size = scanner.nextInt();
	                
	                System.out.println("Enter the quantity:");
	                int quantity = scanner.nextInt();
	                
	                addToCart(addProduct, size, quantity);
	                break;
	            case 2:
	            	System.out.println("In the Basket:");
	            	int cartItemId = 1;
	            	for (CartItem item : cart.getItems()) {
	            		System.out.println(cartItemId + ". " + item);
	            		cartItemId++;
	                }
	                System.out.println("Enter the product number to remove from cart:\n");
	                int removeItemId = scanner.nextInt();
	                removeFromCart(removeItemId - 1);
	                break; 
	            case 3:
	            	CheckOut.checkout(inventory, cart, scanner);
	                continueShopping = false;
	                break;
	            case 4:
	                System.exit(0); // Exit the program
	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }
	    }
	}
	
	public static class CheckOut {
		
		public static void checkout(Inventory inventory, Cart cart, Scanner scanner) {
			// Display selected purchases from cart 
			System.out.println("\nSelected Purchases:");
			
			for (CartItem cartItem : cart.getItems()) {
			    // Update inventory with purchased quantity
				Shoes shoe = cartItem.getShoe(); 
				inventory.processPurchase(shoe.getShoeId(), cartItem.getSize(), cartItem.getQuantity());
			}  
	
			// Get payment method from the user  
			System.out.println("\nSelect payment method:");
			System.out.println("1. Pick up in store");
			System.out.println("2. Online payment (Credit Card)");
			int paymentMethod = scanner.nextInt();
		
			if (paymentMethod == 1) {
				System.out.println("Payment method: Pick up in store");
				System.out.println("Thank you for your purchase! Please pick up your items at our store."); 
				System.exit(0);  // Exit the program
			} else if (paymentMethod == 2) {
				System.out.println("Payment method: Online payment (Credit Card)");
				System.out.println("Enter your name:");  
				String name = scanner.next();
				System.out.println("Enter your address:");
				String address = scanner.next();
				System.out.println("Enter your email:");
				String email = scanner.next();
				System.out.println("Enter your credit card number:");
				String creditCard = scanner.next();
		
				// Process the credit card payment
				boolean paymentProcessed = processCreditCardPayment(creditCard);
		
				if (paymentProcessed) {
					System.out.println("Payment successful!");
					System.out.println("Thank you for your purchase! Your items will be delivered to:");
					System.out.println("Name: " + name);
					System.out.println("Address: " + address);
					System.out.println("Email: " + email);    
					              
				} else { 
					System.out.println("Payment failed. Please try again.");
					}
				}   
		}
		  
		private static boolean processCreditCardPayment(String creditCard) {
		    // Remove spaces and dashes from the credit card number
		    String cleanedCreditCard = creditCard.replaceAll("\\s|-", "");
	
		    // Check if the credit card number is numeric and has a valid length
		    if (!cleanedCreditCard.matches("\\d{13,16}")) { 
		        throw new IllegalArgumentException("Invalid credit card number format. Please enter a valid 13 to 16 digit number.");
		    }
	
		    // If both checks pass the payment is successful 
		    System.out.println("Thank you!");
		    System.out.println("Payment method successfull. Your Delivery is on the way");
		    System.exit(0);  // Exit the program
		    return true;
		}
	}

    public static void main(String[] args) {
        OnlineShoeStore store = new OnlineShoeStore(); 

        int selectedProduct;
        do {  
        	store.inventory.displayProducts();
        	
            System.out.println("\nEnter the product number you want to purchase (or 0 to go to checkout): ");  
            selectedProduct = scanner.nextInt();

            if (selectedProduct == 0) {
                break;
            }
            
            Shoes selectedShoe = store.inventory.getProduct(selectedProduct);
            if (selectedShoe == null) {
                System.out.println("Invalid product selection. Please try again.");
                continue;
            }

            System.out.println("Available sizes for " + selectedShoe.getShoeType() + ": " + selectedShoe.getShoeSize());

            // Get size from the user  
            System.out.println("Enter the size you want to purchase: ");
            int size = scanner.nextInt();

            // Validate size input
            if (!selectedShoe.getShoeSize().contains(size)) {
                System.out.println("Invalid size selection for " + selectedShoe.getShoeType() + ". Please try again.");
                continue;
            } 
  

            System.out.println("Enter the quantity you want to purchase: ");
            int quantity = scanner.nextInt();

            String purchaseDetails = store.inventory.displayPurchaseDetails(selectedProduct, size, quantity, store.getCart());
            System.out.println(purchaseDetails);  // Display purchase details to the user

            store.addToCart(selectedProduct, size, quantity);

        } while (selectedProduct != 0); 
        
        store.manageCart();
        store.displayCart();
        // Close the scanner
        scanner.close();
    } 
}
