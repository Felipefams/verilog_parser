import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import entities.Arq;

public class logicalExpressionParser {
	/*
	 * Example code in verilog
	 * 
	 * module f ( output s,input x,input y, input z );
	 * assign s =(~(~x | ~y) & ~(x & y)) | ( ~((y & z) | ~x));
	 * endmodule // f
	 * module test_f;
	 * reg x;
	 * reg y;
	 * reg z;
	 * wire a;
	 * f modulo ( a, x, y, z);
	 * initial
	 * begin : main
	 * $display("Test module");
	 * $display("   x    y    z =   r1   r2");
	 * $monitor("%4b %4b %4b = %4b ", x, y, z, a);
	 * #1 x=1'b0; y=1'b0; z=1'b0;
	 * #1 x=1'b0; y=1'b0; z=1'b1;
	 * #1 x=1'b0; y=1'b1; z=1'b0;
	 * #1 x=1'b0; y=1'b1; z=1'b1;
	 * #1 x=1'b1; y=1'b0; z=1'b0;
	 * #1 x=1'b1; y=1'b0; z=1'b1;
	 * #1 x=1'b1; y=1'b1; z=1'b0;
	 * #1 x=1'b1; y=1'b1; z=1'b1;
	 * end
	 * endmodule // test_f5
	 */
	/*
	 * if the values on the logical expression
	 * are letters, count as 1+ variable, by doing
	 * that we can allow the user to choose the amount of
	 * variables he want without the need to inform it.
	 */
	public static List<Character> countVariables(String s) {
		List<Character> list = new ArrayList<Character>();
		for (int i = 0; i < s.length(); i++) {
			if (Character.isLetter(s.charAt(i))) {
				if (!list.contains(s.charAt(i))) {
					list.add(s.charAt(i));
				}
			}
		}
		return list;
	}

	public static String makeModule(String module, List<Character> list) {
		return makeModule(module, list, 0);
	}

	/*
	 * generate the module part, considering the amount of
	 * variables the user wants
	 */
	public static String makeModule(String module, List<Character> list, int n) {
		// yet to write
		if (n < list.size()) {
			String s = module + ", input " + list.get(n);
			return makeModule(s, list, n + 1);
		}
		return module;
	}

	public static String makeReg(List<Character> list) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append("reg " + list.get(i) + ";\n");
		}
		return sb.toString();
	}

	/*
	generates all N bits gray's code
	*/	
	public static List<String> generateGrayCode(int n) {
		// 'list' will store all generated codes
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

	public static String divideGrayCode(List<String> binaryList, List<Character> charList){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < binaryList.size(); i++){
			sb.append("#1 ");
			for(int j = 0; j < charList.size(); j++){
				sb.append(charList.get(j) + "=1'b" + binaryList.get(i).charAt(j) + "; ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static String makeModule2(String module, List<Character> list) {
		return makeModule2(module, list, 0);
	}

	public static String makeModule2(String module, List<Character> list, int n) {
		// yet to write
		if (n < list.size()) {
			String s = module + ", " + list.get(n);
			return makeModule2(s, list, n + 1);
		}
		return module;
	}

	public static String generateMonitor(List<Character> charList){
		StringBuilder sb = new StringBuilder("$monitor(\" ");
		for(int i = 0; i < charList.size(); i++){
			sb.append("%4b ");
		}
		// sb.append("\");");
		return sb.toString();
	}

	public static String generateDisplay(List<Character> charList){
		StringBuilder sb = new StringBuilder("$display(\" ");
		for(int i = 0; i < charList.size(); i++){
			sb.append("   " + charList.get(i) + " ");
		}
		sb.append("=    wire_a \");");
		return sb.toString();
	}

	public static void main(String[] args) {
		FastReader fr = new FastReader();
		// so basically we are making a list with the variable names the user is using
		List<Character> list = new ArrayList<Character>();
		List<String> binaryList = new ArrayList<>();
		System.out.println("input logical expression (and = &, not = ~, or = | ): ");
		String expr = fr.nextLine();
		System.out.println("filename(with .v at the end):");
		String filename = fr.next();
		// counting the amount of variables
		list = countVariables(expr);
		final int variables = list.size();
		// module name
		// String module = "module f ( output s " ;
		String module = makeModule("module f ( output s ", list) + ");\n" + "assign s = " + expr + ";\n" + "endmodule //f\n";// this is the complete module part
		// make n values based on the value on the list of variables
		String reg = makeReg(list);
		binaryList = generateGrayCode(variables);	
		String testCases = divideGrayCode(binaryList, list);
		String testModule = "module test_f;\n" + reg + "wire wire_a;\n" + makeModule2("f module_test (wire_a", list) + ");\n";
		String testMonitor = generateMonitor(list) + "= %4b\"" + makeModule2(" ", list) + ", wire_a);";
		String testDisplay = generateDisplay(list);

		String final_Result = module + testModule + "initial\nbegin : main\n$display(\"test module\");\n" + testDisplay + "\n" + testMonitor + "\n" + testCases + "end\nendmodule //test";
		Arq.openWriteClose(filename, final_Result);
		// try{
		// 	Runtime.getRuntime().exec(new String[] {"iverilog -o " + filename + "vp " + filename});
		// 	Runtime.getRuntime().exec(new String[]{"vvp " + filename + "vp"});
		// }catch(Exception e){
		// 	System.out.println("You did something wrong");
		// 	e.printStackTrace();
		// }
	}

	// class for I/O
	static class FastReader {
		// BufferedReader br;
		StringTokenizer st;
		private static BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in, StandardCharsets.ISO_8859_1));

		public static void setCharset(String charset_) {
			br = new BufferedReader(new InputStreamReader(System.in, Charset.forName(charset_)));
		}

		public FastReader() {
			br = new BufferedReader(
					new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
