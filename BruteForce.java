// BruteForce class that gets the best subset recursively and prints it to the console
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BruteForce {
    // getOptimalSet method passing in the items
    public static ArrayList<Item> getOptimalSet(ArrayList<Item> items) {
        // if the size of the ArrayList is bigger than 10, throws exception
        if (items.size() > 10) {
            throw new IllegalArgumentException("Too many items in your ArrayList!");
        }
        // if the size of the items is 0 or 1, it returns the list
        if (items.size() == 0 || items.size() == 1) {
            return items;
        }
        // declaring the bestSubset to be the items before the process starts
        ArrayList<Item> bestSubset = items;
        // declaring bestFitness
        int bestFitness = 0;
        // for each loop that cycles through all the items in the list
        for (Item i: items) {
            // declaring a new list
            ArrayList<Item> items2 = new ArrayList<>();
            // making items2 a copy of items
            for (int j = 0; j < items.size(); j++) {
                items2.add(items.get(j));
            }
            // removing an item and then getting the optimal set
            items2.remove(i);
            items2 = getOptimalSet(items2);
            /* if the fitness of items2 is better than best fitness, set bestSubset to items2
            and the bestFitness to items2's fitness */
            if (getFitness(items2) > bestFitness) {
                bestSubset = items2;
                bestFitness = getFitness(items2);
            }
        }
        /* checking if the fitness of items if greater than the best fitness
        if it is, return items. If not then returning bestSubset */
        if (getFitness(items) > bestFitness) {
            return items;
        }
        return bestSubset;
    }       
    // getting fitness of item
    public static int getFitness(ArrayList<Item> items) {
        // intantiating variables before the for loop
        double weight = 0;
        double totalWeight = 0;
        int value = 0;
        int totalValue = 0;
        // for loop to cycle along the size of the chromosome
        for (Item i: items) {
            weight = i.getWeight();
            value = i.getValue();
            // adding the total values and weights of all of the items in the chromosome
            totalValue += value;
            totalWeight += weight;
          
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

    public static void main(String[] args) throws FileNotFoundException {
        // creation of new file, ArrayList, and scanner to read file
        File newFile = new File("items.txt");
        ArrayList<Item> items = new ArrayList<>();
        Scanner scan = new Scanner(newFile);
        // Delimiter to parse through commas and new lines to allow the scanner to store values
        scan.useDelimiter(",|\n");
        // while loop to scan file
        while (scan.hasNextLine()) {
            String name = scan.next();
            // parsing the space and commas to only get specific values from file
            double weight = Double.parseDouble(scan.next());
            int value = Integer.parseInt(scan.next().trim());
            Item newItem = new Item(name, weight, value);
            items.add(newItem);
        }
        scan.close();
        // setting the optimal ArrayList
        ArrayList<Item> optimal = getOptimalSet(items);
        System.out.println(optimal+ "\nFitness: " +getFitness(optimal));
    }
}