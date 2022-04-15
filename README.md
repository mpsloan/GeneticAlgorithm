# GeneticAlgorithm
This program is a multi-threaded take on the GeneticAlgorithm program. The GeneticAlgorithm program reads in a number 
of items (item class) from a .txt file and stores them as an ArrayList with other items. Each of these ArrayLists is 
called a chromosome (chromosome class). The program performs numerous methods in order to breed the most effective 
chromosome that can carry the greatest value of items while not exceeding 10 lbs. This program does this throughout 
many generations on each thread. Once the most fit chromosome on the thread is determined, it will be compared amongst 
the other threads and the most fit chromosome will be displayed to the console. This program works with items.txt as 
well as more_items.txt, but the POP_SIZE, as well as the NUM_EPOCHS size must be tweaked in order to eventually return 
the most desired chromosome for different variations.
