import java.util.*;

public class PA2Source {
    public static void main(String args[]) {
        // List whose elements are unsorted arrays
        LinkedList<double[]> unsorted = new LinkedList<>();

        // List whose elements are sorted arrays
        LinkedList<double[]> sorted = new LinkedList<>();

        // Creating 9 arrays
        for (int i = 1; i < 10; ++i) {
            // Array size increases by 1000 every time
            int length = i * 1000;
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
            // Add sorted array to List of sorted arrays
            sorted.add(mergeSort(array));
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
