package numbers;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static final List<String> PROPERTIES = Arrays.asList("BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY",
            "SQUARE", "SUNNY", "JUMPING", "HAPPY", "SAD", "EVEN", "ODD");

    static final List<String> MINUS_PROPERTIES = Arrays.asList("-BUZZ", "-DUCK", "-PALINDROMIC", "-GAPFUL", "-SPY",
            "-SQUARE", "-SUNNY", "-JUMPING", "-HAPPY", "-SAD", "-EVEN", "-ODD");

    public static void main(String[] args) {
        // Greeting the user
        System.out.println("Welcome to Amazing Numbers!\n");
        printSupportedRequests();

        while (true) {
            // getting and checking user input
            String[] numbers = checkingUserInput();
            System.out.println();

            if (numbers[0].equals("-1")) {
                System.out.println("Goodbye!");
                break;
            }

            if (numbers.length == 2) { // Properties aren't included
                long number = Long.parseLong(numbers[0]);
                long range = Long.parseLong(numbers[1]);

                if (range == 0L) {
                    outputOneNumberProperties(number);
                } else {
                    long limit = number + range;
                    for (; number < limit; number++) outputProperties(number);
                    System.out.println();
                }
            } else { // Properties are included
                long number = Long.parseLong(numbers[0]);
                long range = Long.parseLong(numbers[1]);
                List<String> property = Arrays.asList(Arrays.copyOfRange(numbers, 2, numbers.length));

                int counter = 0;

                for (; counter < range; number++) {
                    int propertyCounter = numPropertiesSatisfied(property, number);
                    if (propertyCounter == property.size()) {
                        outputProperties(number);
                        counter++;
                    }
                }
                System.out.println();
            }
        }
    }

    public static String[] checkingUserInput() {
        Pattern pattern = Pattern.compile("^[0-9]*[1-9]+$|^[1-9]+[0-9]*$");
        while (true) {
            String userInput = gettingUserInput();

            if (userInput.isEmpty()) {
                System.out.println();
                printSupportedRequests();
                continue;
            }

            String[] userInputs = userInput.split(" ");

            // Check if first input is natural or zero
            String firstNumber = userInputs[0];

            if (firstNumber.equals("0") && userInputs.length == 1) {
                return new String[] {"-1"};
            } else if (!pattern.matcher(firstNumber).matches()) {
                System.out.println("The first parameter should be a natural number or zero if only one parameter is provided.\n");
                continue;
            } else if (userInputs.length == 1) {
                String[] numbers = {firstNumber, "0"};
                return numbers;
            }

            // If this while loop continues, that means userInputs has more than one value
            String secondNumber = userInputs[1];

            // Checking the second number
            if (!pattern.matcher(secondNumber).matches()) {
                System.out.println("The second parameter should be a natural number.\n");
                continue;
            }

            if (userInputs.length == 2) { // Properties weren't included
                    String[] numbers = new String[] {firstNumber, secondNumber};
                    return numbers;
            } else { // Properties were included
                List<String> originalProperties = Arrays.asList(Arrays.copyOfRange(userInputs, 2, userInputs.length));
                ArrayList<String> properties = new ArrayList<String>();
                ArrayList<String> unavailableProperties = new ArrayList<String>();

                // Checking if any property the user provided is invalid
                for (String property: originalProperties) {
                    if (!PROPERTIES.contains(property.toUpperCase()) &&
                            !MINUS_PROPERTIES.contains(property.toUpperCase())) unavailableProperties.add(property.toUpperCase());
                    properties.add(property.toUpperCase());
                }

                if (!unavailableProperties.isEmpty()) { // Some properties were invalid
                    if (unavailableProperties.size() == 1) {
                        System.out.println("The property " + unavailableProperties + " is wrong.");
                    } else {
                        System.out.println("The properties " + unavailableProperties + " are wrong.");
                    }
                    System.out.println("Available properties: " + PROPERTIES);
                    System.out.println();
                } else { // All properties provided were valid
                    // Checking if some were mutually exclusive
                    ArrayList<List<String>> mutuallyExclusive = areMutuallyExclusive(properties);
                    if (!mutuallyExclusive.isEmpty()) {
                        System.out.print("The request contains mutually exclusive properties: ");
                        // Print out the mutually exclusive properties
                        for (List<String> mutuallyExclusiveProperties: mutuallyExclusive) {
                            System.out.print(mutuallyExclusiveProperties + " ");
                        }
                        System.out.println("\nThere are no numbers with these properties.\n");
                    } else { // Everything is good :)
                        ArrayList<String> temp = new ArrayList<String>();
                        temp.add(firstNumber);
                        temp.add(secondNumber);
                        temp.addAll(properties);
                        String[] numbers = new String[temp.size()];
                        numbers = temp.toArray(numbers);
                        return numbers;
                    }
                }
            }
        }
    }

    public static String gettingUserInput() {
        System.out.println("Enter a request:");
        String userInput = scanner.nextLine();
        return userInput;
    }

    public static boolean isOdd(long number) {
        return number % 2 == 1;
    }

    public static boolean isEven(long number) {
        return number % 2 == 0;
    }

    public static boolean isBuzzNumber(long number) {
        boolean isDivisibleBy7 = (number % 7) == 0;
        boolean endsWithSeven = String.valueOf(number).endsWith("7");

        return isDivisibleBy7 || endsWithSeven;
    }

    public static boolean isDuckNumber(long number) {
        String stringNumber = String.valueOf(number);
        if (stringNumber.length() == 1) {
            return false;
        } else {
            return stringNumber.substring(1).contains("0");
        }
    }

    public static boolean isPalindromicNumber(long number) {
        String stringNumber = String.valueOf(number);
        StringBuilder stringToReverse = new StringBuilder(stringNumber);
        stringToReverse.reverse();
        return stringNumber.equals(stringToReverse.toString());
    }

    public static boolean isGapfulNumber(long number) {
        String stringNumber = String.valueOf(number);
        if (stringNumber.length() <= 2) {
            return false;
        } else {
            // Join the first and last digits
            String firstDigit = Character.toString(stringNumber.charAt(0));
            String lastDigit = Character.toString(stringNumber.charAt(stringNumber.length() - 1));
            String joinedDigits = firstDigit.concat(lastDigit);

            // Test Gapful property
            return number % Long.parseLong(joinedDigits) == 0;
        }
    }

    public static boolean isSpyNumber(long number) {
        String stringNumber = String.valueOf(number);
        long sumOfDigits = 0;
        long productOfDigits = 1;

        for (char ch: stringNumber.toCharArray()) {
            int digit = ch - '0';
            sumOfDigits += digit;
            productOfDigits *= digit;
        }

        return sumOfDigits == productOfDigits;
    }

    public static boolean isSquareNumber(long number) {
        double sqrtNumber = Math.sqrt(number);
        return (sqrtNumber) == (int) sqrtNumber;
    }

    public static boolean isSunnyNumber(long number) {
        return isSquareNumber(number + 1);
    }

    public static boolean isJumpingNumber(long number) {
        String stringNumber = String.valueOf(number);
        int prevDigit = Integer.parseInt(String.valueOf(stringNumber.charAt(0)));

        for (int i = 1; i < stringNumber.length(); i++) {
            int currDigit = Integer.parseInt(String.valueOf(stringNumber.charAt(i)));
            if (Math.abs(currDigit - prevDigit) != 1) {
                return false;
            }
            prevDigit = currDigit;
        }

        return true;
    }

    public static boolean isHappyNumber(long number) {
        String stringNumber = String.valueOf(number);

        do {
            int sum = 0;

            for (char ch: stringNumber.toCharArray()) {
                sum += (int) Math.pow(Character.getNumericValue(ch), 2);
            }

            stringNumber = Integer.toString(sum);
        } while (stringNumber.length() != 1);

        return stringNumber.equals("1");
    }

    public static boolean isSadNumber(long number) {
        return !isHappyNumber(number);
    }

    public static int numPropertiesSatisfied(List<String> requiredProperties, long number) {
        int counter = 0;

        // Increase counter if property is included in requiredProperties and number has this property
        if (requiredProperties.contains("BUZZ") && isBuzzNumber(number)) counter += Collections.frequency(requiredProperties, "BUZZ");
        if (requiredProperties.contains("DUCK") && isDuckNumber(number)) counter += Collections.frequency(requiredProperties, "DUCK");
        if (requiredProperties.contains("PALINDROMIC") && isPalindromicNumber(number)) counter += Collections.frequency(requiredProperties, "PALINDROMIC");
        if (requiredProperties.contains("GAPFUL") && isGapfulNumber(number)) counter += Collections.frequency(requiredProperties, "GAPFUL");
        if (requiredProperties.contains("SPY") && isSpyNumber(number)) counter += Collections.frequency(requiredProperties, "SPY");
        if (requiredProperties.contains("SQUARE") && isSquareNumber(number)) counter += Collections.frequency(requiredProperties, "SQUARE");
        if (requiredProperties.contains("SUNNY") && isSunnyNumber(number)) counter += Collections.frequency(requiredProperties, "SUNNY");
        if (requiredProperties.contains("JUMPING") && isJumpingNumber(number)) counter += Collections.frequency(requiredProperties, "JUMPING");
        if (requiredProperties.contains("HAPPY") && isHappyNumber(number)) counter += Collections.frequency(requiredProperties, "HAPPY");
        if (requiredProperties.contains("SAD") && isSadNumber(number)) counter += Collections.frequency(requiredProperties, "SAD");
        if (requiredProperties.contains("EVEN") && isEven(number)) counter += Collections.frequency(requiredProperties, "EVEN");
        if (requiredProperties.contains("ODD") && isOdd(number)) counter += Collections.frequency(requiredProperties, "ODD");

        // Increase counter if -property is included in requiredProperties and number doesn't have this property
        if (requiredProperties.contains("-BUZZ") && !isBuzzNumber(number)) counter += Collections.frequency(requiredProperties, "-BUZZ");
        if (requiredProperties.contains("-DUCK") && !isDuckNumber(number)) counter += Collections.frequency(requiredProperties, "-DUCK");
        if (requiredProperties.contains("-PALINDROMIC") && !isPalindromicNumber(number)) counter += Collections.frequency(requiredProperties, "-PALINDROMIC");
        if (requiredProperties.contains("-GAPFUL") && !isGapfulNumber(number)) counter += Collections.frequency(requiredProperties, "-GAPFUL");
        if (requiredProperties.contains("-SPY") && !isSpyNumber(number)) counter += Collections.frequency(requiredProperties, "-SPY");
        if (requiredProperties.contains("-SQUARE") && !isSquareNumber(number)) counter += Collections.frequency(requiredProperties, "-SQUARE");
        if (requiredProperties.contains("-SUNNY") && !isSunnyNumber(number)) counter += Collections.frequency(requiredProperties, "-SUNNY");
        if (requiredProperties.contains("-JUMPING") && !isJumpingNumber(number)) counter += Collections.frequency(requiredProperties, "-JUMPING");
        if (requiredProperties.contains("-HAPPY") && !isHappyNumber(number)) counter += Collections.frequency(requiredProperties, "-HAPPY");
        if (requiredProperties.contains("-SAD") && !isSadNumber(number)) counter += Collections.frequency(requiredProperties, "-SAD");
        if (requiredProperties.contains("-EVEN") && !isEven(number)) counter += Collections.frequency(requiredProperties, "-EVEN");
        if (requiredProperties.contains("-ODD") && !isOdd(number)) counter += Collections.frequency(requiredProperties, "-ODD");

        return counter;
    }

    public static ArrayList<List<String>> areMutuallyExclusive(List<String> requiredProperties) {
        ArrayList<List<String>> mutuallyExclusive = new ArrayList<List<String>>();

        List<String> m1 = Arrays.asList("ODD", "EVEN");
        List<String> m2 = Arrays.asList("DUCK", "SPY");
        List<String> m3 = Arrays.asList("SUNNY", "SQUARE");
        List<String> m4 = Arrays.asList("SAD", "HAPPY");
        List<String> m5 = Arrays.asList("-SAD", "-HAPPY");
        List<String> m6 = Arrays.asList("-EVEN", "-ODD");

        // Checking if property and -property (like "SAD" and "-SAD") exist in requiredProperties
        for (String property: requiredProperties) {
            if (requiredProperties.contains("-" + property)) {
                List<String> toAdd = Arrays.asList(property, "-" + property);
                mutuallyExclusive.add(toAdd);
            }
        }

        // Checking if m1 - m6 exist in requiredProperties
        if (requiredProperties.containsAll(m1)) mutuallyExclusive.add(m1);
        if (requiredProperties.containsAll(m2)) mutuallyExclusive.add(m2);
        if (requiredProperties.containsAll(m3)) mutuallyExclusive.add(m3);
        if (requiredProperties.containsAll(m4)) mutuallyExclusive.add(m4);
        if (requiredProperties.containsAll(m5)) mutuallyExclusive.add(m5);
        if (requiredProperties.containsAll(m6)) mutuallyExclusive.add(m6);

        return mutuallyExclusive;
    }

    public static void outputProperties(long number) {
        // Creating the start of the statement
        StringBuilder statement = new StringBuilder(String.valueOf(number));
        statement.append(" is ");

        // Populating the statement
        if (isBuzzNumber(number)) statement.append("buzz, ");
        if (isDuckNumber(number)) statement.append("duck, ");
        if (isPalindromicNumber(number)) statement.append("palindromic, ");
        if (isGapfulNumber(number)) statement.append("gapful, ");
        if (isSpyNumber(number)) statement.append("spy, ");
        if (isSquareNumber(number)) statement.append("square, ");
        if (isSunnyNumber(number)) statement.append("sunny, ");
        if (isJumpingNumber(number)) statement.append("jumping, ");
        if (isHappyNumber(number)) statement.append("happy, ");
        if (isSadNumber(number)) statement.append("sad, ");
        if (isEven(number)) statement.append("even");
        if (isOdd(number)) statement.append("odd");

        System.out.println(statement);
    }

    public static void outputOneNumberProperties(long number) {
        System.out.println("Properties of " + number);
        System.out.println("\tbuzz: " + isBuzzNumber(number));
        System.out.println("\tduck: " + isDuckNumber(number));
        System.out.println("\tpalindromic: " + isPalindromicNumber(number));
        System.out.println("\tgapful: " + isGapfulNumber(number));
        System.out.println("\tspy: " + isSpyNumber(number));
        System.out.println("\tsquare: " + isSquareNumber(number));
        System.out.println("\tsunny: " + isSunnyNumber(number));
        System.out.println("\tjumping: " + isJumpingNumber(number));
        System.out.println("\thappy: " + isHappyNumber(number));
        System.out.println("\tsad: " + isSadNumber(number));
        System.out.println("\teven: " + isEven(number));
        System.out.println("\todd: " + isOdd(number));
        System.out.println();
    }

    public static void printSupportedRequests() {
        System.out.println("""
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be processed;
                - two natural numbers and properties to search for;
                - a property preceded by minus must not be present in numbers;
                - separate the parameters with one space;
                - enter 0 to exit.
                """);
    }

}
