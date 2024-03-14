import java.util.Scanner;
import java.util.HashSet;
import java.util.Arrays;

public class Cryptarithmitic_Solver {

    static int[] domain = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    static int str1Value=0 , str2Value=0, outputValue=0;
    static String total = "";
    static Pair[] pairsArray = new Pair[256];
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        HashSet<Character> uniqueChars = new HashSet<>();
        System.out.println("Welcome to the cryptarithmitic calculator\nEnter an equation:");
        String str1 = input.nextLine().toUpperCase();
        System.out.println("*");
        String str2 = input.nextLine().toUpperCase();
        System.out.println("=");
        String output = input.nextLine().toUpperCase();
        String combined = str1+str2+output;

        for (char c : combined.toCharArray()) {
            uniqueChars.add(c);
        }

        for (Character c : uniqueChars) {
            total = total + c;
        }
        
        if (total.length()<=10) {

            char[] sortedChars = total.toCharArray();
            Arrays.sort(sortedChars);
            total = new String(sortedChars);
            
            for(int i=0;i<sortedChars.length;i++) {
            	pairsArray[(int)sortedChars[i]]= new Pair(sortedChars[i],domain[i]);
            	domain[i]=-(int)sortedChars[i];
            }
            
            for(int i=0;i<Math.pow(10, total.length());i++) {
            	if(solve(str1, str2, output)) {
            		System.out.print("\n{");
                	for(int j=0;j<total.length()-1;j++) {
                		System.out.print(total.charAt(j)+",");
                	}
                	System.out.print(total.charAt(total.length()-1)+"} = ");
                	System.out.print("{");
                	for(int j=0;j<total.length()-1;j++) {
                		System.out.print(pairsArray[total.charAt(j)].value+",");
                	}
                	System.out.print(pairsArray[total.charAt(total.length()-1)].value+"}\n");
                	System.out.println(str1 + " * " + str2 + " = " + output);
                	System.out.println(str1Value+" * "+str2Value+" = "+outputValue);
                	break;
                }else if(shiftValues(total.length()-1)) {
                	continue;
                }
                else {
                	System.out.println("No Solution Found");
                	break;
                }
            }
        	
        }
        else {
        	System.out.println("Invaild input");
        }
        
        }
        
    
    private static boolean solve(String str1, String str2, String output) {
    	str1Value=0;
    	str2Value=0;
    	outputValue=0;
    	for (int i=0;i<str1.length();i++) {
    		str1Value += (pairsArray[str1.charAt(str1.length()-(i+1))].value)*Math.pow(10, i);
    		
    	}
    	for (int i=0;i<str2.length();i++) {
    		str2Value += (pairsArray[str2.charAt(str2.length()-(i+1))].value)*Math.pow(10, i);
    	}
    	for (int i=0;i<output.length();i++) {
    		outputValue += (pairsArray[output.charAt(output.length()-(i+1))].value)*Math.pow(10, i);
    	}
    	if(str1Value*str2Value==outputValue) {
    		return true;	
    	}
    	return false;
    }
    private static boolean shiftValues(int letter) {
    	if((letter == 0)&&(pairsArray[total.charAt(letter)].value==9)) {
    		return false;
    	}
    	if(pairsArray[total.charAt(letter)].value>checkMaxVaild()) {
    		int temp = pairsArray[total.charAt(letter)].value;
    		domain[temp]=temp;
    		pairsArray[total.charAt(letter)].value = -1;
    		shiftValues(letter-1);
    	}
    	for(int i=0;i<domain.length;i++) {
    		if(pairsArray[total.charAt(letter)].value==-1) {
    			if(domain[i]>=0) {
    				pairsArray[total.charAt(letter)].value = domain[i];	
    				domain[i]=-(int)total.charAt(letter);
    				break;
    			}
    		}
    		if(-(int)total.charAt(letter)==domain[i]) {
    			domain[i]=i;
    			pairsArray[total.charAt(letter)].value=-1;
    		}
    		
		}
    	return true;
    	
    }
    private static int checkMaxVaild() {
    	int max=-1;
    	for(int i=0;i<10;i++) {
    		if(domain[i]>0) {
    			max = i;
    		}
    	}
    	return max;
    }
}
