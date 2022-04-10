import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BruteForce {

    public static ArrayList<Item> getOptimalSet(ArrayList<Item> items) {
        if (items.size() > 10) {
            throw new IllegalArgumentException("Too many items in your ArrayList!");
        }

        if (items.size() == 0 || items.size() == 1) {
            return items;
        }
        ArrayList<Item> items2 = items;
        ArrayList<Item> bestSubset = items;
        int bestFitness = getFitness(items);

        for (Item i: items) {
            items2.remove(i);
            items2 = getOptimalSet(items2);
            if (getFitness(items2) > bestFitness) {
                bestSubset = items2;
            }
        }
        return bestSubset;
    }
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
        System.out.println(getOptimalSet(items));
    }
}