public class Item {
   // private fields for the item object class
    private final String name;
    private final double weight;
    private final int value;
    private boolean included;
    
    // constructor for the item
    public Item(String name, double weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.included = false;
    }
    
    // constructor for copy items
    public Item(Item other) {
        this.name = other.name;
        this.weight = other.weight;
        this.value = other.value;
        this.included = other.included;
    }

    // method to return weight of item
    public double getWeight() {
        return this.weight;
    }

    // method to return value of item
    public int getValue() {
        return this.value;
    }

    // method to return whether item is included or not
    public boolean isIncluded() {
        return this.included;
    }

    // method to set item to included
    public void setIncluded(boolean included) {
        this.included = included;
    }
    
    // method to return object in a string format
    public String toString() {
        return this.name + "(" + this.weight + "lbs. $" + this.value + ")";
    }
}