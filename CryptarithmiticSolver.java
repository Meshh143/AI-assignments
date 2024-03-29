import java.util.Scanner;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Arrays;

public class CryptarithmiticSolver {

    // Define class variables
    static int[] domain = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    static BigInteger str1Value = BigInteger.ZERO;
    static BigInteger str2Value = BigInteger.ZERO;
    static BigInteger outputValue = BigInteger.ZERO;
    static String numStr1 =""; 
	static String numStr2 =""; 
	static String numOutput =""; 
    static String total = "";
    static Pair[] pairsArray = new Pair[256]; // Assuming ASCII characters

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        HashSet<Character> uniqueChars = new HashSet<>();

        System.out.println("Welcome to the cryptarithmitic calculator");
        while (true) {
            try {
            	// Reset domain array
                for (int i = 0; i < 10; i++) {
                    domain[i] = i;
                }

                // Get user input
                System.out.print("Enter String 1 = ");
                String str1 = input.nextLine().toUpperCase().trim();
                System.out.println("*");
                System.out.print("Enter String 2 = ");
                String str2 = input.nextLine().toUpperCase().trim();
                System.out.println("=");
                System.out.print("Solution String = ");
                String output = input.nextLine().toUpperCase().trim();
                String combined = str1 + str2 + output;

                // Validate input
                if (!(validateInput(str1)&&validateInput(str2)&&validateInput(output))) {
                    System.out.println("Invalid input. Please enter valid alphabetic characters.");
                    continue;
                }

                // Clear previous data
                uniqueChars.clear();
                total = "";

                // Extract unique characters
                for (char c : combined.toCharArray()) {
                    uniqueChars.add(c);
                }

                // Concatenate unique characters
                for (Character c : uniqueChars) {
                    total = total + c;
                }

                // If number of unique characters is less than or equal to 10
                if (total.length() <= 10) {
                    char[] sortedChars = total.toCharArray();
                    Arrays.sort(sortedChars);
                    total = new String(sortedChars);

                    // Initialize pairsArray and domain
                    for (int i = 0; i < sortedChars.length; i++) {
                        pairsArray[(int) sortedChars[i]] = new Pair(sortedChars[i], domain[i]);
                        domain[i] = -(int) sortedChars[i];
                    }

                    // Solve the cryptarithmic problem
                    for (int i = 0; i < Math.pow(10, total.length()); i++) {
                        if (solve(str1, str2, output)) {
                            // Solution found
                            System.out.println("Solution found:");
                            System.out.print("{");
                            for (int j = 0; j < total.length() - 1; j++) {
                                System.out.print(total.charAt(j) + ", ");
                            }
                            System.out.print(total.charAt(total.length() - 1) + "} = {");
                            for (int j = 0; j < total.length() - 1; j++) {
                                System.out.print(pairsArray[total.charAt(j)].value + ", ");
                            }
                            System.out.println(pairsArray[total.charAt(total.length() - 1)].value + "}");
                            System.out.println(str1 + " * " + str2 + " = " + output);
                            System.out.println(numStr1 + " * " + numStr2 + " = " + numOutput);
                            System.out.println("*******************************");
                            break;
                        } else if (!shiftValues(total.length() - 1)) {
                            System.out.println("No Solution Found");
                            System.out.println("*******************************");
                            break;
                        }
                    }
                } else {
                    System.out.println("Invalid input. Total number of unique characters exceeds 10.");
                    System.out.println("*******************************");
                }

            } catch (Exception e) {
                System.out.println("Error occurred!");
                System.out.println(e);
                e.printStackTrace();
                System.out.println("*******************************");
            }
        }
    }

    // Method to solve the cryptarithmic problem
    private static boolean solve(String str1, String str2, String output) {
    	numStr1 =""; 
    	numStr2 =""; 
    	numOutput =""; 
    	str1Value = BigInteger.ZERO;
        str2Value = BigInteger.ZERO;
        outputValue = BigInteger.ZERO;

        // Calculate numeric values for strings
        for (int i = 0; i < str1.length(); i++) {
        	numStr1 += (pairsArray[str1.charAt(i)].value);
        }
        str1Value = new BigInteger(numStr1);

        for (int i = 0; i < str2.length(); i++) {
            numStr2 += (pairsArray[str2.charAt(i)].value);
        }
        str2Value = new BigInteger(numStr2);

        for (int i = 0; i < output.length(); i++) {
            numOutput += (pairsArray[output.charAt(i)].value);
        }
        outputValue = new BigInteger(numOutput);

        // Check if the equation is satisfied\
        return (str1Value.multiply(str2Value)).equals(outputValue);
    }

    // Method to shift values for backtracking
    private static boolean shiftValues(int letter) {
        if ((letter == 0) && (pairsArray[total.charAt(letter)].value == 9)) {
            return false;
        }
        if (pairsArray[total.charAt(letter)].value > checkMaxValid()) {
            int temp = pairsArray[total.charAt(letter)].value;
            domain[temp] = temp;
            pairsArray[total.charAt(letter)].value = -1;
            if (!shiftValues(letter - 1)) return false;
        }
        for (int i = 0; i < domain.length; i++) {
            if (pairsArray[total.charAt(letter)].value == -1) {
                if (domain[i] >= 0) {
                    pairsArray[total.charAt(letter)].value = domain[i];
                    domain[i] = -(int) total.charAt(letter);
                    break;
                }
            }
            if (-(int) total.charAt(letter) == domain[i]) {
                domain[i] = i;
                pairsArray[total.charAt(letter)].value = -1;
            }

        }
        return true;
    }

    // Method to find the maximum valid value in domain
    private static int checkMaxValid() {
        int max = -1;
        for (int i = 0; i < 10; i++) {
            if (domain[i] > 0) {
                max = i;
            }
        }
        return max;
    }

    // Method to validate input
    private static boolean validateInput(String input) {
    	if(input=="") return false;
        for (char c : input.toCharArray()) {
            if (input =="" || !Character.isAlphabetic(c)) {
                return false;
            }
        }
        return true;
    }
}
