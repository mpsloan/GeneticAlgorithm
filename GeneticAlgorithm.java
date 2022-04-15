/*
Michael Sloan
CS 1181L-07
Luke Holt
April 14, 2022
This program is a multi-threaded take on the GeneticAlgorithm program. The GeneticAlgorithm program reads in a number 
of items (item class) from a .txt file and stores them as an ArrayList with other items. Each of these ArrayLists is 
called a chromosome (chromosome class). The program performs numerous methods in order to breed the most effective 
chromosome that can carry the greatest value of items while not exceeding 10 lbs. This program does this throughout 
many generations on each thread. Once the most fit chromosome on the thread is determined, it will be compared amongst 
the other threads and the most fit chromosome will be displayed to the console. This program works with items.txt as 
well as more_items.txt, but the POP_SIZE, as well as the NUM_EPOCHS size must be tweaked in order to eventually return 
the most desired chromosome for different variations. 
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GeneticAlgorithm {
    /* constants that I set in order to get the correct output, can be changed to whatever and program
    will adapt acoordingly */
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
        current generation as well as the threads */
        ArrayList<Item> items = readData("more_items.txt");
        ArrayList<Chromosome> currentGen = new ArrayList<>();
        ArrayList<GeneticThread> threads = new ArrayList<>();

        // for loop that creates the threads, starts them, and adds them to the ArrayList
        for (int i = 0; i < NUM_THREADS; i++) {
            // NUM_EPOCHS / NUM_THREADS tells the thread how long to run for
            GeneticThread thread = new GeneticThread(currentGen, NUM_EPOCHS / NUM_THREADS);
            thread.start();
            threads.add(thread);
        }
        // joining the threads together after they are all started
        for (GeneticThread t: threads) {
            t.join();
        }
        // setting the current generation to the first index of the currentGen ArrayList for the thread
        currentGen = threads.get(0).getCurrentGen();

        for (GeneticThread t: threads) {
            // if the current thread's currentGen has better fitness than the previous, reset the currentGen
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
