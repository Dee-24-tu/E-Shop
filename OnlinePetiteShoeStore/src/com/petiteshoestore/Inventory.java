package petiteshoestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;  
 
public class Inventory {
	// Attributes
    private List<Shoes> products; 
    private Map<Integer, Shoes> productMap;

    // Constructor   
    public Inventory() { 
        this.products = new ArrayList<>();  
        this.productMap = new HashMap<>();
        // Call a method to initialise inventory with products
        initialiseInventory();
    }

    // Method to initialise the inventory 
    private void initialiseInventory() {
        List<Integer> sizes = new ArrayList<>();
        sizes.add(2);
        sizes.add(3);
        sizes.add(4);  

        addShoes(new Shoes(1, "Flats", 79.99, sizes, 10));
        addShoes(new Shoes(2, "Heels", 49.99, sizes, 10));
        addShoes(new Shoes(3, "Boots", 34.99, sizes, 10));
        addShoes(new Shoes(4, "Wedges", 54.99, sizes, 10));
        addShoes(new Shoes(5, "Sneaker", 24.99, sizes, 10));
    }

 // Method to add and remove a product to the inventory
    private void addShoes(Shoes shoes) {
        products.add(shoes); 
        productMap.put(shoes.getShoeId(), shoes); 
    } 

    public void addToCart(int shoeId, int size, int quantity, Cart cart) {
        Shoes product = productMap.get(shoeId);
        if (product != null && product.getQuantity() > 0) {
            product.decreaseQuantity(1);   
            cart.addItem(product, size, quantity);
        } else {  
            System.out.println("Product not found in inventory or out of stock.");
        }
    }
    
    public Shoes removeProduct(int shoeId) {
        Shoes removedProduct = productMap.remove(shoeId);
        products.remove(removedProduct);
        return removedProduct;
    }

    // Method to display available products
    public void displayProducts() {
        System.out.println("\nAvailable Shoes:");
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Shoes shoe : products) {
                System.out.println(shoe);
            }
        } 
    }
    
    public Shoes getProduct(int shoeId) {
        return productMap.get(shoeId);
    }

    // Method to get the total number of products in the inventory
    public int getProductCount() {
        return products.size();
    }

    // Method to process a purchase  
    public void processPurchase(int shoeId, int size, int quantity) {
        Shoes shoe = productMap.get(shoeId);
        if (shoe != null && shoe.getShoeSize().contains(size)) {
            if (shoe.getQuantity() >= quantity) {
                shoe.decreaseQuantity(quantity);
            } else {
                System.out.println("Insufficient stock for " + shoe.getShoeType() + " (Size " + size + "). Available quantity: " + shoe.getQuantity());
            }
        } else {
            System.out.println("Invalid product or size selection.");
        }
    }
    
    public int getQuantity(int shoeId, int size) {
        Shoes shoe = getProduct(shoeId);
        if (shoe != null && shoe.getShoeSize().contains(size)) {
            return shoe.getQuantityForSize(size);
        } else {
            return 0; 
        }
    }
   
 // Method to display purchase details  
    public String displayPurchaseDetails(int shoeId, int size, int quantity, Cart cart) {
        Shoes shoe = productMap.get(shoeId);
        if (shoe != null && shoe.getShoeSize().contains(size)) {
            double totalPrice = shoe.getPrice() * quantity;
            int totalItemsInCart = 0; 
            for (CartItem cartItem : cart.getItems()) {
            	if (cartItem.getShoe().getShoeId() == shoeId && cartItem.getSize() == size) {
                    totalItemsInCart += cartItem.getQuantity();
                }
            }
            return "\nCurrent Purchase Details:\n" +
                    "Product Name: " + shoe.getShoeType() + " (Size " + size + ")\n" +
                    "Quantity: " + quantity + "\n" +
                    "Total Price: $" + totalPrice +
                    "\nTotal Items in Cart: " + totalItemsInCart;
        } else {
            return "Invalid product or size selection.";  
        }
    } 
}
