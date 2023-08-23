package numbers;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static final List<String> PROPERTIES = Arrays.asList("BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY",
            "SQUARE", "SUNNY", "JUMPING", "EVEN", "ODD");

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
                System.out.println("The first parameter should be a natural number or zero.\n");
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
                    if (!PROPERTIES.contains(property.toUpperCase())) {
                        unavailableProperties.add(property.toUpperCase());
                    }
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
                    if (areMutuallyExclusive(properties)) {
                        System.out.println("The request contains mutually exclusive properties: " + properties);
                        System.out.println("There are no numbers with these properties.\n");
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

    public static int numPropertiesSatisfied(List<String> requiredProperties, long number) {
        int counter = 0;

        if (requiredProperties.contains("BUZZ") && isBuzzNumber(number)) counter++;
        if (requiredProperties.contains("DUCK") && isDuckNumber(number)) counter++;
        if (requiredProperties.contains("PALINDROMIC") && isPalindromicNumber(number)) counter++;
        if (requiredProperties.contains("GAPFUL") && isGapfulNumber(number)) counter++;
        if (requiredProperties.contains("SPY") && isSpyNumber(number)) counter++;
        if (requiredProperties.contains("SQUARE") && isSquareNumber(number)) counter++;
        if (requiredProperties.contains("SUNNY") && isSunnyNumber(number)) counter++;
        if (requiredProperties.contains("JUMPING") && isJumpingNumber(number)) counter++;
        if (requiredProperties.contains("EVEN") && isEven(number)) counter++;
        if (requiredProperties.contains("ODD") && isOdd(number)) counter++;

        return counter;
    }

    public static boolean areMutuallyExclusive(List<String> requiredProperties) {
        List<String> m1 = Arrays.asList("ODD", "EVEN");
        List<String> m2 = Arrays.asList("DUCK", "SPY");
        List<String> m3 = Arrays.asList("SUNNY", "SQUARE");

        return (requiredProperties.containsAll(m1) || requiredProperties.containsAll(m2) || requiredProperties.containsAll(m3));
    }

    public static void outputWrongPropertyMessage(String property) {
        System.out.println("The property [" + property + "] is wrong.");
        System.out.println("Available properties: " + PROPERTIES);
        System.out.println();
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
        System.out.println("\teven: " + isEven(number));
        System.out.println("\todd: " + isOdd(number));
        System.out.println();
    }

    public static void printSupportedRequests() {
        System.out.println("""
                Supported requests:
                - enter a natural number to know its properties;\s
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be printed;
                - two natural numbers and a property to search for;
                - two natural numbers and two properties to search for;
                - separate the parameters with one space;
                - enter 0 to exit.
                """);
    }

}
