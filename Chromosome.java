import java.util.ArrayList;
import java.util.Random;

/* class that extends an ArrayList of type item which makes this class an ArrayList as well
implements comparable class to compare 2 different chromosomes */
public class Chromosome extends ArrayList<Item> 
implements Comparable<Chromosome> {

    // rng value used for frequent random number generation
    private static Random rng = new Random();

    public static long dummy = 0;

    // empty constructor method because this class inherits ArrayList<Item>
    public Chromosome() {

    }

    // for each loop to add a copy of a new item to the Chromosome ArrayList for every item in items
    public Chromosome(ArrayList<Item> items) {
        for (Item item: items) {
            // rng returning integer between 0 and 2 non-inclusive
            int randNum = rng.nextInt(2);
            // instantiating copies of items and not the same exact item
            Item copy = new Item(item);
            // randomly setting the included values to true or false
            if (randNum == 1) {
               copy.setIncluded(true);
            } 
            else {
                copy.setIncluded(false);
            }
            // adding the items to the Chromosome ArrayList
            this.add(copy);
        }
    }

    // crossover method between this chromosome and another
    public Chromosome crossover(Chromosome other) {
        // creating a child chromosome to inherit copies of their parents' items
        Chromosome childChromosome = new Chromosome();
        // for loop to cycle through the size of the chromosome
        for (int i = 0; i < this.size(); i++) {
            int randNum = rng.nextInt(10);
            // random probability for child to get one parents item
            if (randNum <= 4) {
                // copying item form parent and adding it to child chromosome
                Item copy = new Item(this.get(i));
                childChromosome.add(copy);
            }
            else {
                Item copy = new Item(other.get(i));
                childChromosome.add(copy);
            } 
        }
        return childChromosome;

    }

    // method to mutate the chromosome and randomly flip the item's isIncluded value
    public void mutate() {
        // for loop to cycle through size of chromosome
        for (int i = 0; i < this.size(); i++) {
            int randNum = rng.nextInt(10);
            // 1 in 10 chance that the selected chromosome is mutated and it's isIncluded value flips
            if (randNum == 0) {
                if (get(i).isIncluded() == true) {
                    get(i).setIncluded(false);
                }
                else {
                    get(i).setIncluded(true);
                }
            }
        }
    }

    // getting the fitness of the chromosome
    public int getFitness() {
        // intantiating variables before the for loop
        double weight = 0;
        double totalWeight = 0;
        int value = 0;
        int totalValue = 0;
        dummy = 0;
        for (int i=0; i < this.size()*1000; i++) {
            dummy += i;
        }
        // for loop to cycle along the size of the chromosome
        for (int i = 0; i < this.size(); i++) {
            weight = get(i).getWeight();
            value = get(i).getValue();
            // adding the total values and weights if they are included
            if (get(i).isIncluded() == true) {
                // adding the total values and weights of all of the items in the chromosome
                totalValue += value;
                totalWeight += weight;
            }   
        }
        // if the total weight of the chromosome is > 10 then it returns 0
        if (totalWeight > 10) {
            return 0;
        }
        // else it returns total value of all items in chromosome
        else {
            return totalValue;
        }
    }

    // compareTo method that compares two chromosomes against one another
    public int compareTo(Chromosome other) {
        // moves position in array list closer to beginning if it has better fitness
        if (this.getFitness() > other.getFitness()) {
            return -1;
        }
        // moves position in array list farther from beginning if it has worse fitness
        else if (this.getFitness() < other.getFitness()) {
            return 1;
        }
        // if fitness is equal does nothing
        else {
            return 0;
        }
    }

    // prints ArrayList of items (aka chromosome)
    public String toString() {
        // instantiating string to add to loop
        String toString = "";
        /* for loop cycles through chromosome and if isIncluded is true then adds the item to the string 
        as well as its fitness */
        for (int i = 0; i < this.size(); i++) {
            if (get(i).isIncluded() == true) {
                toString += get(i).toString() + " ";
            }
        }
        // adding the fitness at the end of the string
        toString += this.getFitness();
        return toString;
    }
}