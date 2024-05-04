package petiteshoestore;

public class CartItem {
    private Shoes shoe;
    private int size;
    private int quantity; 
    
    public CartItem(Shoes shoe, int size, int quantity) { 
        this.shoe = shoe;
        this.size = size;
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return "Product Name: " + shoe.getShoeType() + " (Size " + size + ")\n" +
               "Quantity: " + quantity + "\n" +
               "Total Price: $" + (shoe.getPrice() * quantity);
    }
     
    // Getters and Setters
    public Shoes getShoe() {
        return shoe;
    }

    public void setShoe(Shoes shoe) {
        this.shoe = shoe;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity; 
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

