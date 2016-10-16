import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PA2Source {
    public static void main(String args[]) {
        // List whose elements are unsorted arrays
        LinkedList<double[]> unsorted = new LinkedList<>();

        // List whose elements are sorted arrays
        LinkedList<double[]> sorted = new LinkedList<>();

        // List whose elements are execution times
        LinkedList<Long> times = new LinkedList<>();

        // List whose elements are Strings to be in a file
        LinkedList<String> output = new LinkedList<>();
        output.add("Input size n for Array_i, Value of n * logn, Time spent (nanoseconds), Value of (n * logn)/time");

        // Creating 9 arrays
        for (int i = 1; i < 10; ++i) {
            // Array size increases by 100000 every time
            int length = i * 100000;
            double[] array = new double[length];
            // Fill array with random real numbers
            for (int j = 0; j < length; ++j) {
                Random gen = new Random();
                array[j] = gen.nextDouble();
            }
            // Add array to unsorted List of arrays
            unsorted.add(array);
        }

        // Sort each array in List of unsorted arrays
        for (double[] array : unsorted) {
            // Timing of each mergeSort
            long start = System.nanoTime();
            double[] tarr = mergeSort(array);
            long end = System.nanoTime();
            long time = end - start;
            times.add(time);

            // Add sorted array to List of sorted arrays
            sorted.add(mergeSort(array));

            // Create output line
            int n = tarr.length;
            double logn = Math.log((double)n);
            double nlogn = n * logn;
            String otpt = String.valueOf(n) + ", " + String.valueOf(logn) + ", " + String.valueOf(time) + ", " + String.valueOf(nlogn / time);
            output.add(otpt);
        }

        // Scanner for user input
        Scanner s = new Scanner(System.in);
        // User input [1,9]
        int t;

        // User input loop
        while (true) {
            System.out.print("Please input a number in range [1,9]: ");
            t = s.nextInt();
            if (t < 1 || t > 9) {
                System.out.println("Number out of range!");
                continue;
            }
            break;
        }

        // Output: print unsorted array first, then sorted array
        System.out.print("Unsorted: ");
        printArray(unsorted.get(t));
        System.out.print("Sorted:   ");
        printArray(sorted.get(t));

        try {
            Files.write(Paths.get("Mergesort_Time.csv"), output, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printArray(double[] array) {
        System.out.print("[ ");
        for (double d : array) {
            System.out.print(d + " ");
        }
        System.out.print("]\n");
    }

    private static double[] merge(double[] a, double[] b) {
        int length = a.length + b.length;
        double[] merged = new double[length];

        // Keep track of array indices
        int ai = 0, bi = 0, mi = 0;
        // Merge arrays
        while (true) {
            // Add smallest number
            if (a[ai] <= b[bi]) {
                merged[mi] = a[ai];
                mi += 1;
                if (ai + 1 < a.length) {
                    ai += 1;
                } else {
                    // Add remaining elements of `b` array
                    merged[mi] = b[bi];
                    bi += 1;
                    mi += 1;
                    while (bi < b.length) {
                        merged[mi] = b[bi];
                        bi += 1;
                        mi += 1;
                    }
                    break;
                }
            } else {
                merged[mi] = b[bi];
                mi += 1;
                if (bi + 1 < b.length) {
                    bi += 1;
                } else {
                    // Add remaining of `a` array
                    merged[mi] = a[ai];
                    ai += 1;
                    mi += 1;
                    while (ai < a.length) {
                        merged[mi] = a[ai];
                        ai += 1;
                        mi += 1;
                    }
                    break;
                }
            }
        }
        return merged;
    }

    private static double[] mergeSort(double[] a) {
        if (a.length <= 1) return a;

        int middle = a.length / 2;
        double[] left = Arrays.copyOfRange(a, 0, middle);
        double[] right = Arrays.copyOfRange(a, middle, a.length);

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }
}
