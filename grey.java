
// Java program to generate n-bit Gray codes
import java.util.*;

class GfG {

    // This function generates all n bit Gray codes and prints the
    // generated codes
    static List<String> generateGrayCode(int n) {
        // 'arr' will store all generated codes
        List<String> list = new ArrayList<String>();

        // start with one-bit pattern
        list.add("0");
        list.add("1");

        // Every iteration of this loop generates 2*i codes from previously
        // generated i codes.
        int i, j;
        for (i = 2; i < (1 << n); i = i << 1) {
            // Enter the previously generated codes again in arr[] in reverse
            // order. Nor arr[] has double number of codes.
            for (j = i - 1; j >= 0; j--)
                list.add(list.get(j));

            // append 0 to the first half
            for (j = 0; j < i; j++)
                list.set(j, "0" + list.get(j));

            // append 1 to the second half
            for (j = i; j < 2 * i; j++)
                list.set(j, "1" + list.get(j));
        }
        return list;
    }

    // Driver program to test above function
    public static void main(String[] args) {
        List <String> list = new ArrayList<>();
        list = generateGrayCode(3);
        for(String x : list){
            System.out.println(x);
        }
    }
}
