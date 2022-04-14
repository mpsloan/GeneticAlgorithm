import java.io.File;
import java.io.FileNotFoundException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GeneticAlgorithm {
    public static final int POP_SIZE = 100;
    public static final int NUM_EPOCHS = 1000;
    public static final int NUM_THREADS = 10;

    // method to read data from a file and add to ArrayList of type item
    public static ArrayList<Item> readData(String filename) throws FileNotFoundException {
        // creation of new file, ArrayList, and scanner to read file
        File newFile = new File(filename);
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
        return items;
    }

    // method that returns ArrayList of type chromosome based on population size passed in
    public static ArrayList<Chromosome> initializePopulation(ArrayList<Item> items, int POP_SIZE) {
        ArrayList<Chromosome> chromosomes = new ArrayList<>();
        for (int i = 0; i < POP_SIZE; i++) {
            // adding new chromosome objects to ArrayList
            Chromosome newChrom = new Chromosome(items);
            chromosomes.add(newChrom);
        }
        return chromosomes;
    }

    // main method
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        /* initializing ArrayLists of type item and chromosome item type reads the designated text 
        file and adds the items to an array List while the other two ArrayLists account for the 
        current generation as well as the next generation */
        ArrayList<Item> items = readData("items.txt");
        ArrayList<Chromosome> currentGen = new ArrayList<>();
        ArrayList<Chromosome> nextGen = new ArrayList<>();
        ArrayList<GeneticThread> threads = new ArrayList<>();

        for (int i = 0; i <  NUM_EPOCHS / NUM_THREADS; i++) {
            GeneticThread thread = new GeneticThread(currentGen);
            thread.start();
            threads.add(thread);
        }

        for (GeneticThread t: threads) {
            t.join();
        }
        
        currentGen = threads.get(0).getCurrentGen();

        for (GeneticThread t: threads) {
            if (currentGen.get(0).getFitness() < t.getCurrentGen().get(0).getFitness()) {
                currentGen = t.getCurrentGen();
            }
        }
        
        // sorting the current generation with compareTo method
        Collections.sort(currentGen);
        // printing out the first chromosome in the current generation
        System.out.println(currentGen.get(0));

    }
}
