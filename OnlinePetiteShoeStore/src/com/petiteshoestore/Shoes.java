package petiteshoestore;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Shoes {
	// Attributes
    private int shoeId;
    private String shoeType; 
    private double price;  
    private List<Integer> size; 
    private int quantity;
    private Map<Integer, Integer> sizeQuantityMap = new HashMap<>();

    // Constructor
    public Shoes(int shoeId, String shoeType, double price, List<Integer> size, int quantity) {
        this.shoeId = shoeId;
        this.shoeType = shoeType; 
        this.price = price;
        this.size = size;
        this.quantity = quantity;
    }

    // Getter and setter methods
    public void setQuantityForSize(int size, int quantity) {
        sizeQuantityMap.put(size, quantity);
    }

    public int getQuantityForSize(int size) {
        return sizeQuantityMap.getOrDefault(size, 0);
    }
    public int getShoeId() {
        return shoeId;
    }  
  
    public void setProduct(int shoeId) {
        this.shoeId = shoeId;
    }

    public String getShoeType() {
        return shoeType;
    }

    public void setShoeType(String shoeType) {
        this.shoeType = shoeType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Integer> getShoeSize() {
        return size;
    }

    public void setShoeSize(List<Integer> size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }   
  
    // Override toString 
    @Override
    public String toString() {
        return "Shoe [Shoe ID=" + shoeId + ", Shoe Type=" + shoeType + ", Size=" + size + ", Price=" + price + "]";
    }
  
    public String getShoeName() {  
        return shoeType + " (Size " + size.get(0) + ")";
    }

    public void decreaseQuantity(int quantity) {
        if (quantity <= this.quantity) {
            this.quantity -= quantity;
            System.out.println("Quantity updated. Remaining quantity for " + shoeType + " (Size " + size.get(0) + "): " + this.quantity);
        } else {
            System.out.println("Sorry, we only have " + this.quantity + " available for " + shoeType + " (Size " + size.get(0) + ").");
        }
    }
}
