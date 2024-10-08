/*Natural Foundation of Informatics Technology
 * Group 12: Sameer Karodia, Zaid Mostafa, Ajay Ramsaran, Muhammad Saad, Ebubechukwu Agwagah
 * Assignment Part A: Number Systems
 */

import java.util.*;
import java.lang.Math;

public class Main{
    public static void main (String[] args) {
        Scanner sc = new Scanner (System.in);
        int source_base, target_base;
        String input_number;

        //getting the users input (Input number, source base, and target base)
        System.out.println("**Welcome to our Number System Converter**\nHere you can convert any digit (including negative and fractional) from one of our approved number systems to another!\n\n**NOTE any invalid input will be disregarded");

        //getting source base
        System.out.println("What is your source base (2: Binary, 10: Decimal, 16: Hexadecimal): ");
        while (true) {
            try {
                source_base = sc.nextInt();
                if (source_base == 2 || source_base == 10 || source_base == 16) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 2, 10, or 16.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number (2, 10, or 16).");
                sc.next(); // Clear the invalid input
            }
        }


        //getting target base
        System.out.println("What is your target base (2: Binary, 10: Decimal, 16: Hexadecimal): ");
        while (true) {
            try {
                target_base = sc.nextInt();
                if (target_base == 2 || target_base == 10 || target_base == 16) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 2, 10, or 16.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number (2, 10, or 16).");
                sc.next(); // Clear the invalid input
            }
        }


        //getting input number
        System.out.println("What is your number: ");

        while (true) {
            input_number = sc.next();
            if (ValidateInput(input_number, source_base)) {
                // After the input is validated, print confirmation
                System.out.println("valid input number");
                break;  // Valid input number, exit loop
            }
            System.out.println("Invalid input number. Please try again.");
        }


        //GETTING AN ARRAY OF THE INPUT NUMBER PROVIDED
        ArrayList<Integer>Input_array=digitSplit(input_number);
        //TESTING ARRAY
        System.out.println("Full array is: ");
        for (int i=0;i<Input_array.size();i++){
            System.out.print(Input_array.get(i)+" ");

        }

        //INTEGER PART OF INPUT NUMBER
        ArrayList<Integer>Input_array_integers=digitSplitInteger(Input_array);
        //TESTING ARRAY
        System.out.print("\nIntegers of array are: ");
        for (int i=0;i<Input_array_integers.size();i++){
            System.out.print(Input_array_integers.get(i)+" ");

        }

        //FRACTION PART OF INPUT NUMBER
        ArrayList<Integer>Input_array_fraction=digitSplitFraction(Input_array);
        //TESTING ARRAY
        System.out.print("\nFraction of array is: ");
        for (int i=0;i<Input_array_fraction.size();i++){
            System.out.print(Input_array_fraction.get(i)+" ");

        }

        double test=convert_number(input_number,source_base,target_base,Input_array_integers,Input_array_fraction);
        System.out.print("\nDecimal value is: "+test);

    }


    //Description: Validates the users number, checks if the number given adheres to the restrictions of their source_base
    //Parameters: input_number and source_base
    //Return: returns true if the users number adheres to the source, and false otherwise
    public static boolean ValidateInput(String input_number, int source_base){

        String valid_nums="";
        //Flag to make sure only 1 negative number and 1 decimal point can be used
        boolean hasDecimal_point=false;
        boolean hasNegative_sign=false;

        // Define valid characters based on the source_base given
        if(source_base == 2){
            valid_nums="01";
        }
        else if(source_base == 10){
            valid_nums="0123456789";
        }
        else if(source_base == 16){
            valid_nums="0123456789abcdefABCDEF";
        }
        else{
            return false;
        }

        // Loop through each character of the input_number
        for (int i=0; i<input_number.length(); i++){
            char z=input_number.charAt(i);

            //checking ONLY first value for a negative sign
            if(z=='-' && i==0 && !hasNegative_sign){
                hasNegative_sign=true;
                continue;
            }

            //if character is a decimal then changes flag to not allow anymore decimals
            if(z=='.' && !hasDecimal_point){
                hasDecimal_point=true;
                continue;
            }


            //checking to see if the character is valid for source_base
            if(valid_nums.indexOf(z)==-1){
                return false;
            }
        }
        return true;
    }


    //Descripton: computes the conversion of the input_number from  source_base to target_base
    //Parameters: input_number, source_base and target_base
    //Return: the input_number in the target_base
    public static double convert_number(String input_number, int source_base, int target_base,ArrayList<Integer> Input_array_integers,ArrayList<Integer> Input_array_fraction){
        double decimal_int_value = 0;
        double decimal_fraction_value=0;
        double decimal_value=0;

        //code to convert from source_base to decimal//
        //Binary to Decimal//
        if (source_base==2){
            int power=0;

            //converting integer part
            for(int i=Input_array_integers.size()-1; i>0; i--){   //Starting from last digit in the integer part and stopping before the first number due to it representing negative or not//
                double currentDigit=Input_array_integers.get(i);
                decimal_int_value+=currentDigit*(Math.pow(2,power));
                power++;    //Add 1 to power each time to move up the multiplication//
            }

            //converting fraction part
            //change power to negative for fractions
            power=-1;
            for (int i=0;i<Input_array_fraction.size();i++){  //Starting from first digit in fraction part and multiplying by negative power
                double currentDigit=Input_array_fraction.get(i);
                decimal_fraction_value+=currentDigit*(Math.pow(2,power));
                power--;    //Add 1 to power each time to move up the multiplication//
            }

            //add up both the integer and fraction part together
            decimal_value=decimal_fraction_value+decimal_int_value;

            //applying negative value if negative sign is found
            if(Input_array_integers.get(0)==-1){
                decimal_value=decimal_value*-1;
            }
        }

        //Hexadecimal to Decimal//
        if (source_base==16){
            int power=0;

            //converting integer part
            for(int i=Input_array_integers.size()-1; i>0; i--){   //Starting from last digit in the input number and stopping before the first number due to it representing negative or not//
                double currentDigit=Input_array_integers.get(i);
                decimal_int_value+=currentDigit*(Math.pow(16,power));
                power++;    //Add 1 to power each time to move up the multiplication//
            }

            //converting fraction part
            //change power to negative for fractions
            power=-1;
            for (int i=0;i<Input_array_fraction.size();i++){  //Starting from first digit in fraction part and multiplying by negative power
                double currentDigit=Input_array_fraction.get(i);
                decimal_fraction_value+=currentDigit*(Math.pow(16,power));
                power--;    //Add 1 to power each time to move up the multiplication//

            }

            //add up both the integer and fraction part together
            decimal_value=decimal_int_value+decimal_fraction_value;

            //applying negative value if negative sign is found
            if(Input_array_integers.get(0)==-1){
                decimal_value=decimal_value*-1;
            }
        }


        //returning to convert form source to target_base
        return decimal_value;
    }

    //Description: splits the input_number into its digits, and stores them in an array of integers,
    //the first entry is reserved for 1 or -1 to denote negative/positive values,
    // the number 100 is used as a decimal point to separate whole and fractional parts
    //Parameters: input_number
    //Return: an array with each digit of the input_number,
    public static ArrayList<Integer> digitSplit(String input_number){
        //Initialize: array for splitting the input string, and ArrayList for storing the integers
        String[] array_input = input_number.split("");
        ArrayList<Integer>input_number_digits = new ArrayList<>();

        //loop through the users number, and converts each digit/char to their integer representation
        for(int i = 0; i<array_input.length;i++){

            //leftmost integer reserved for positive or negative
            if(i==0){
                if(array_input[0].equals("-")){
                    input_number_digits.add(-1);
                }
                else{input_number_digits.add(1);}
            }

            //converts the rest of the digits/hexa
            switch ((array_input[i]).toUpperCase()){
                case "-": break;
                case "A": input_number_digits.add(10); break;
                case "B": input_number_digits.add(11); break;
                case "C": input_number_digits.add(12); break;
                case "D": input_number_digits.add(13); break;
                case "E": input_number_digits.add(14); break;
                case "F": input_number_digits.add(15); break;
                //sets up an identifiable split between whole and fractional portions
                case ".": input_number_digits.add(100); break;
                default: input_number_digits.add(Integer.parseInt(array_input[i]));
            }
        }
        //finally return the final arrayList
        return input_number_digits;


    }
    public static ArrayList<Integer> digitSplitInteger(ArrayList<Integer> Input_array) {
        ArrayList<Integer> integerPart = new ArrayList<>();

        // Goes through the input array and spits out everything before the decimal point (100)
        for (int digit : Input_array) {

            if (digit == 100) {
                break;  // Stop at the decimal point
            }
            integerPart.add(digit);  // Add digits before the decimal point
        }

        return integerPart;
    }

    public static ArrayList<Integer> digitSplitFraction(ArrayList<Integer> Input_array) {
        ArrayList<Integer> fractionalPart = new ArrayList<>();
        //flag for checking if found a decimal
        boolean foundDecimalPoint=false;

        // Going through each value in the input array
        for (int digit : Input_array) {
            // Once it sees (100), starts adding the digits after it
            if (digit == 100) {
                foundDecimalPoint = true;
                continue;  // Skip the decimal point (100) itself
            }

            if (foundDecimalPoint) {
                fractionalPart.add(digit);  // Add all digits after the decimal point
            }
        }

        return fractionalPart;
    }

}

