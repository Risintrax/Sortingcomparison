/**
 * @author Ali Azhari and Wassim. 
 * Course: CS 526 
 * File:SortingComparison.java
 * Fall 2017
 *
 */
package hw9;

import java.util.Arrays;
import java.util.Random;

/**
 * class that compares 4 algorithms and measures their running time as input size grows
 *
 */
public class SortingComparison {

	// array to hold string of names of algorithms
	static String[] algorithms = { "Insertion   ", "Merge       ", "Quick       ", "Heapsort    " };

	public SortingComparison() {

	}

	// ****************** insertion-sort *****************************
	/**
	 * @param data
	 * insertion sort algorithm of an array of integers into nondecreasing order. 
	 * Copied from the textbook "Data Structures and Algorithms in Java". page 111
	 * 
	 */
	public static void insertionSort(int[] data) {

		int length = data.length;  // length of array
		// outer loop that scans integers starting from index 1 to last integer
		for (int k = 1; k < length; k++) {  
			int cur = data[k];
			int j = k;
			while (j > 0 && data[j - 1] > cur) {
				data[j] = data[j - 1];
				j--;
			}
			data[j] = cur;
		}

	}

	// **************** merge-sort *********************
	/**
	 * @param S1
	 * @param S2
	 * @param S
	 * Merge sort  contents of arrays S1 and S2 into property sized array S
	 * Copied from the textbook "Data Structures and Algorithms in Java". page 537 
	 */
	public static void merge(int[] S1, int[] S2, int[] S) {
		int i = 0, j = 0;
		while (i + j < S.length) {
			if (j == S2.length || (i < S1.length && S1[i] < S2[j]))
				S[i + j] = S1[i++];
			else
				S[i + j] = S2[j++];
		}
	}

	/**
	 * @param S
	 * Merge-sort contents of array S
	 * Copied from the textbook "Data Structures and Algorithms in Java". page 538
	 */
	public static void mergeSort(int[] S) {
		int n = S.length;
		if (n < 2)
			return;
		// divide
		int mid = n / 2;
		int[] S1 = Arrays.copyOfRange(S, 0, mid);
		int[] S2 = Arrays.copyOfRange(S, mid, n);
		// conquer (with recursion)
		mergeSort(S1);
		mergeSort(S2);
		// merge results
		merge(S1, S2, S);
	}
	// ************** end of merge-sort **********************

	// *************** begin quick-sort **********************

	/**
	 * @param S
	 * @param a
	 * @param b
	 * 
	 * Quick sort, sort the subarray S[a..b] inclusive
	 * Copied from the textbook "Data Structures and Algorithms in Java". page 553
	 */
	public static void quickSortInPlace(int[] S, int a, int b) {
		if (a >= b)
			return; // subarray is trivially sorted
		int left = a;
		int right = b - 1;
		int pivot = S[b];
		
		int temp;
		while (left <= right) {
			// scan until reaching value equal or larger than pivot (or right marker)
			while (left <= right && S[left] < pivot)
				left++;
			// scan until reaching value equal or smaller than pivot (or left marker)
			while (left <= right && S[right] > pivot)
				right--;
			if (left <= right) { // indices did not stricly cross
				// so swap values and shrink range
				temp = S[left];
				S[left] = S[right];
				S[right] = temp;
				left++;
				right--;
			}
		}
		// put pivot into its final place (curently marked by left index)
		temp = S[left];
		S[left] = S[b];
		S[b] = temp;
		// make recursive calls
		quickSortInPlace(S, a, left - 1);
		quickSortInPlace(S, left + 1, b);

	}

	// **************** heap-sort *********************
	/**
	 * @param arr
	 */
	public static void heapSortInPlace(int[] arr) {
		heapify(arr);
		int n = arr.length - 1;
		for (int i = n; i > 0; i--) {
			swap(arr, 0, i);
			n = n - 1;
			downheap(arr, 0, n);
		}
	}

	/**
	 * @param a
	 * @param j: j is the starting index to start the downheap from
	 * @param max: max is the last index of the array
	 * downheap method:
	 * 
	 * Moves the entry at index j lower, if necessary, to restore the heap property
	 * Algorithm copied from textbook page 378 but modified to fit this homework.
	 */
	protected static void downheap(int[] a, int j, int max) {

		// while loop starts from the left child of j, 
		// and as long as it is less than the length of array	
		while (2 * j + 1 <= max) {
			int leftIndex = 2 * j + 1;
			int smallChildIndex = leftIndex;  // if there a left child then make it the smallestchild so fat
			if (2 * j + 2 < max) { // if there is a right child
				int rightIndex = 2 * j + 2;  
				if (a[leftIndex] <= a[rightIndex])  // check whether the left or the right is smaller
					smallChildIndex = rightIndex;
			}
			if (a[smallChildIndex] <= a[j])
				break;
			// swap
			int temp = a[j];
			a[j] = a[smallChildIndex];
			a[smallChildIndex] = temp;
			j = smallChildIndex;
		}
	}

	/**
	 * @param a
	 * 
	 * Perfrms a bottom-up construction of the heap in linear time
	 * Copied from the textbook "Data Structures and Algorithms in Java". page 382
	 */
	protected static void heapify(int[] a) {

		int n = a.length;
		int startIndex = (n - 2) / 2;  // start at Parent of last entry
		for (int j = startIndex; j >= 0; j--) { // loop until processing the root
			downheap(a, j, a.length);
		}
	}

	/**
	 * @param a
	 * @param i
	 * @param j
	 * 
	 * Swap method. it swaps i and j elements of the array a
	 */
	protected static void swap(int a[], int i, int j) {
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	// *************************end of heap sort and its utilities

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		long startTime, endTime, elapsedTime; 
		int array[];
		int[] temp;
		long[][] results = new long[4][10];
		for (int n = 10000; n <= 100000; n += 10000) {
			// for (int n = 10000; n <= 100000; n++) {
			array = new int[n];
			results[0][n / 10000 - 1] = n;
			Random r = new Random();
			for (int j = 0; j < n; j++) {
				array[j] = r.nextInt(1000000) + 1;
			}

			// *********************** Insertion *****************************
			temp = array.clone();
			System.out.print("Sorting " + n + "  by insertion....");
			startTime = System.currentTimeMillis();
			insertionSort(temp);
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			results[0][n / 10000 - 1] = elapsedTime;
			System.out.println("elapsed time is: " + elapsedTime);

			// ************************* Merge *****************************
			temp = array.clone();
			System.out.print("Sorting " + n + "  by merge....");
			startTime = System.currentTimeMillis();
			mergeSort(temp);
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			results[1][n / 10000 - 1] = elapsedTime;
			System.out.println("elapsed time is: " + elapsedTime);

			// *************************Quick-sort******************************
			temp = array.clone();
			System.out.print("Sorting " + n + "  by quick....");
			startTime = System.currentTimeMillis();
			quickSortInPlace(temp, 0, temp.length - 1);
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			results[2][n / 10000 - 1] = elapsedTime;
			System.out.println("elapsed time is: " + elapsedTime);

			// ************************ Heap-sort*******************************
			temp = array.clone();
			System.out.print("Sorting " + n + "  by heapsort....");
			startTime = System.currentTimeMillis();
			heapSortInPlace(temp);
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			results[3][n / 10000 - 1] = elapsedTime;
			System.out.println("elapsed time is: " + elapsedTime);
		}

		// *********** Printing a table of the results ************************
		System.out.println(
				"\n|---------------------------------------------------------------------------------------------|");
		System.out.print("|n/Algorithm ");
		for (int i = 10000; i <= 100000; i += 10000)
			System.out.printf("| %-6s", i);
		// System.out.print("| " + i + " ");
		System.out.println(
				"\n|---------------------------------------------------------------------------------------------|");

		for (int i = 0; i < 4; i++) {
			{
				System.out.print("|" + algorithms[i]);

				for (int j = 0; j < 10; j++)
					System.out.printf("| %-6s", results[i][j]);
				System.out.println(
						"\n|---------------------------------------------------------------------------------------------|");

			}

		}
	}
}
