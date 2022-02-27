/*
Michael Sloan
CS 1181L-07
Luke Holt
February 13, 2022
This program reads in a number of items (item class) from a .txt file and stores them as an ArrayList with other 
items. Each of these ArrayLists is called a chromosome (chromosome class). The program performs numerous methods 
in order to breed the most effective chromosome that can carry the greatest value of items while not exceeding 10 lbs.
This program does this throughout many generations and once it is determined the most fit chromosome will be displayed
to the console. This program works with items.txt as well as more_items.txt, but the population size, as well as the 
generation size must be tweaked in order to eventually return the most desired chromosome. 
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GeneticAlgorithm {
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
    public static ArrayList<Chromosome> initializePopulation(ArrayList<Item> items, int populationSize) {
        ArrayList<Chromosome> chromosomes = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            // adding new chromosome objects to ArrayList
            Chromosome newChrom = new Chromosome(items);
            chromosomes.add(newChrom);
        }
        return chromosomes;
    }

    // main method
    public static void main(String[] args) throws FileNotFoundException {
        /* initializing ArrayLists of type item and chromosome item type reads the designated text 
        file and adds the items to an array List while the other two ArrayLists account for the 
        current generation as well as the next generation */
        ArrayList<Item> items = readData("items.txt");
        ArrayList<Chromosome> currentGen = initializePopulation(items, 10);
        ArrayList<Chromosome> nextGen = new ArrayList<>();
        
        // for loop to cycle through the steps for 20 generations
        for (int l = 0; l < 20; l++) {
            // for each loop to cycle through each of the current gen chromosomes and add them to next gen
            for (Chromosome chromosome: currentGen) {
                nextGen.add(chromosome);
            }
            
            /* for loop to cycle through the current generation and add a child for every 2 parent chromomosomes via 
            crossover */
            for (int i = 0; i < currentGen.size() - 1; i+=2) {
                Chromosome child = currentGen.get(i).crossover(currentGen.get(i+1));
                nextGen.add(child);
            }
            
            // randomMutation variable to get 10 percent of next generation
            int randMut = nextGen.size() / 10;
            // for loop to cycle through and mutate 10 percent of the next generation
            for (int j = 0; j < randMut; j++) {
                // randomly generating the index
                int randIndex = (int) (Math.random() * nextGen.size()) + 0;
                nextGen.get(randIndex).mutate();
            }
            
            // sorting the next generation via the compareTo method in Chromosome class
            Collections.sort(nextGen);
            // clearing out the current generation
            currentGen.clear();
            
            // initializing a limit to act as iteration variable and break for each loop
            int limit = 0;
            // for each loop to add the next generation of chromosomes to the current generation
            for (Chromosome nextChrom: nextGen) {
                currentGen.add(nextChrom);
                // iterating limit
                limit += 1;
                // breaking out of loop once it cycles through top ten
                if (limit >= 9) {
                    break;
                }
                else {
                    continue;
                }
            }
        }
        // sorting the current generation with compareTo method
        Collections.sort(currentGen);
        // printing out the first chromosome in the current generation
        System.out.println(currentGen.get(0));

    }
}