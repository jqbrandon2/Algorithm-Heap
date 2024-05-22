import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/*  Student Name:	Brandon Evans
 *  Date:			9/8/23
 *  Class:          CS 3103 - Algorithms
 *  Filename:       UAHeap.java
 *  Description:    Implementation of a priority queue using a heap (by A. Mackey)
 */

public class UAHeap {

    static final int INITIAL_SIZE = 1;		//just to declare the array with the default starting point

    private int[] a;		//This will be used to store the values of the heap

	private int size;
	private int index;

    // Default, no-argument constructor
    public UAHeap() {
       this(INITIAL_SIZE);
    }

    // Construct a UAHeap with the size provided
    public UAHeap(int size) {
    	this.size = size;
    	this.index = 0;
    	a = new int[size];
    }

    // this method simply returns the array a, so do not modify this one
    public int[] getArray() {
	    return this.a;
    }


	//The main method should be already configured to run with the appropriate settings.  Do not alter this method.
	public static void main(String[] args) throws IOException {
		if ( args.length < 3 ) {
			System.out.println("Invalid syntax:   java  UAHeap  inputfile  outputfile  inputsize");
			System.exit(100);
		}

		int inputSize = 0;

		try {
			inputSize = Integer.parseInt(args[2]);
		} catch(Exception ex) {
			System.out.println("Invalid input size");
			System.exit(100);
		}

		UAHeap h = new UAHeap(inputSize);
		System.out.println("Step 1: Loading the array");
		h.loadData(args[0]);

		if (h.getArray().length < 20) {
			System.out.println("Start:  " + Arrays.toString(h.getArray()));
			h.buildMinHeap();
			System.out.println("Heap:   " + Arrays.toString(h.getArray()));
			h.getSortedHeap();
			System.out.println("Sorted:   " + Arrays.toString(h.getArray()));
			System.out.println("\n\n");

			h.saveDataToFile(args[1]);
		} else {
			int[] a1 = h.getArray();
			System.out.print("Start (first 20 only): " );
			for ( int i = 0; i < 20; i++ ) {
				System.out.print( a1[i] + " ");
			}
			System.out.println();


			h.buildMinHeap();
			int[] a2 = h.getArray();
			System.out.print("Heap (first 20 only): " );
			for ( int i = 0; i < 20; i++ ) {
				System.out.print( a2[i] + " ");
			}
			System.out.println();

			System.out.println("Step 2: Running Heapsort");

			System.out.println("\n\n");
			h.getSortedHeap();
			h.saveDataToFile(args[1]);

			int[] a3 = h.getArray();
			System.out.print("Sorted (first 20 only): " );
			for ( int i = 0; i < 20; i++ ) {
				System.out.print( a3[i] + " ");
			}
			System.out.println();
			System.out.print("Sorted (last 20 only): " );
			for ( int i = a3.length-20; i < a3.length; i++ ) {
				System.out.print( a3[i] + " ");
			}
			System.out.println();

			System.out.println("Complete. Saved output to " + args[1]);
		}



		System.out.println();
	}




	// this method should return the height of your heap (you need to calculate it)
    public int getHeight() {
		return (int)Math.ceil(Math.log(size + 1) / Math.log(2)) - 1 ;

    }

    // Retrieves the min value from the heap
    public int getMinValue() {
		return a[0];
    }

    // Removes and returns the min value from the heap while preserving the heap properties
    public int removeMinValue() {
		
    	int min = a[0];
    	
    	if(size < 0) {
			System.out.println("heap underflow");
		}
    	a[0] = a[--size];
    	heapifyDown(0);
    	return min;

    }

    // Builds the heap structure by starting from ((int) n/3)
    public void buildMinHeap() {
    	
    	for(int i = Math.floorDiv(a.length, 3); i>=1; i--) {
    		heapifyDown(i);
    	}
    	
    }

    // Insert a value into the heap
    public void insertValue(int value) {
    	size = size + 1;
    	a[size] = Integer.MIN_VALUE;
    	heapifyUp(a[size],value);

    }

    // Returns the number of elements within the heap
    public int size() {
		return a.length;
    }

    // Reorganizes an element at the given index moving downward (if needed)
    public void heapifyDown(int index) {
    	
    	int smallest = index;
    	int l = getLeft(index);
    	int r = getRight(index);
    	
    	if(l < size && a[l] < a[index]) {
    		smallest = l;
    	} 
    	
    	if(r < size && a[r] < a[smallest]) {
    		smallest = r;
    	}
    	
    	if(smallest != index) {
    		swap(a[index], a[smallest]);
    		heapifyDown(smallest);
    	}
    	
    }

    // Decreases the value at the specified index and moves it upward (if needed).
    public void heapifyUp(int index, int value) {
    	
    	if(value < a[index]) {
    		System.out.println("new key is smaller than current key");
    	}
    	
    	a[index] = value;
    	
    	while(index > 0 && a[getParent(index)] > a[index]) {
    		swap(a[index], a[getParent(index)]);
    		index = getParent(index);
    	}
    	
    }

    // Loads data into the heap from a file (space-delimited)
    public void loadData(String filename) throws IOException {
    	
    	
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			
		
			
			while ((line = br.readLine()) != null) {
				String[] value = line.split(" ");
				
				for(int i = 0; i<value.length; i++) {
					a[i] = Integer.parseInt(value[i]);
				}
				
			}
	}


    // Writes the contents of the heap into the specified file (space-delimited)
    public void saveDataToFile(String filename) throws IOException {
    	
    	FileWriter writer = new FileWriter(filename);
    	
    	for(int i = 0; i<a.length; i++) {
    		writer.write(a[i] + " ");
    	}
    	writer.close();
    }

    // Returns the values from the heap in ascending order (and saves it to the array a)
    public int[] getSortedHeap() {
		
    	int[] val = new int [10];
    	
    	for(int i = 0; i<a.length; i++) {
			val[i] = removeMinValue();
		}
		a = val;
    	
		return a;

    }

    /***********************************************************************
     * Location for additional methods needed to complete this problem set.
     ***********************************************************************/
    
    public int getParent(int index)  {
    	return (index - 1) / 2;
    }
    
    public int getLeft(int index) {
    	return 2*index + 1;
    }
    
    public int getRight(int index) {
    	return 2*index + 2;
    }
    
    public void swap(int a, int b) {
    	int temp;
    	temp = a;
    	a = b;
    	b = temp;
    }

}