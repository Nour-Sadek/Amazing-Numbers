package numbers;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Arrays;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static final List<String> PROPERTIES = Arrays.asList(new String[] {"BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY",
            "SQUARE", "SUNNY", "EVEN", "ODD"});

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

            if (numbers.length == 2) {
                long range = Long.parseLong(numbers[1]);

                if (range == 0L) {
                    long number = Long.parseLong(numbers[0]);
                    outputOneNumberProperties(number);
                } else {
                    long number = Long.parseLong(numbers[0]);
                    long limit = number + range;
                    for (; number < limit; number++) {
                        outputProperties(number);
                    }
                    System.out.println();
                }
            } else if (numbers.length == 3) { // There is one property included
                long number = Long.parseLong(numbers[0]);
                long range = Long.parseLong(numbers[1]);
                List<String> property = Arrays.asList(numbers[2]);

                int counter = 0;

                for (; counter < range; number++) {
                    int propertyCounter = numPropertiesSatisfied(property, number);
                    if (propertyCounter == 1) {
                        outputProperties(number);
                        counter++;
                    }
                }
                System.out.println();
            } else { // There are two properties included
                long number = Long.parseLong(numbers[0]);
                long range = Long.parseLong(numbers[1]);
                List<String> property = Arrays.asList(numbers[2], numbers[3]);

                int counter = 0;

                for (; counter < range; number++) {
                    int propertyCounter = numPropertiesSatisfied(property, number);
                    if (propertyCounter == 2) {
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
            if (userInputs.length == 1) {
                String input = userInputs[0];

                if (input.equals("0")) {
                    return new String[] {"-1"};
                } else if (!pattern.matcher(input).matches()) {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                } else {
                    String[] numbers = new String[] {input, "0"};
                    return numbers;
                }
            } else if (userInputs.length == 2) {
                String firstNumber = userInputs[0];
                String secondNumber = userInputs[1];

                // Checking the first number
                if (firstNumber.equals("0")) {
                    System.out.println("If two parameters are given, the first number should not be zero.\n");
                    continue;
                } else if (!pattern.matcher(firstNumber).matches()) {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                    continue;
                }

                // Checking the second number
                if (!pattern.matcher(secondNumber).matches()) {
                    System.out.println("The second parameter should be a natural number.\n");
                } else {
                    String[] numbers = new String[] {firstNumber, secondNumber};
                    return numbers;
                }
            } else if (userInputs.length == 3) {
                String firstNumber = userInputs[0];
                String secondNumber = userInputs[1];
                String property = userInputs[2];

                // Checking the first number
                if (firstNumber.equals("0")) {
                    System.out.println("If two parameters are given, the first number should not be zero.\n");
                    continue;
                } else if (!pattern.matcher(firstNumber).matches()) {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                    continue;
                }

                // Checking the second number
                if (!pattern.matcher(secondNumber).matches()) {
                    System.out.println("The second parameter should be a natural number.\n");
                    continue;
                }

                // Checking the property
                if (!PROPERTIES.contains(property.toUpperCase())) {
                    System.out.println("The property [" + property.toUpperCase() + "] is wrong.");
                    System.out.println("Available properties: " + PROPERTIES);
                    System.out.println();
                } else {
                    String[] numbers = new String[] {firstNumber, secondNumber, property.toUpperCase()};
                    return numbers;
                }
            } else if (userInputs.length == 4) {
                String firstNumber = userInputs[0];
                String secondNumber = userInputs[1];
                List<String> property = Arrays.asList(userInputs[2].toUpperCase(), userInputs[3].toUpperCase());

                // Checking the first number
                if (firstNumber.equals("0")) {
                    System.out.println("If two parameters are given, the first number should not be zero.\n");
                    continue;
                } else if (!pattern.matcher(firstNumber).matches()) {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                    continue;
                }

                // Checking the second number
                if (!pattern.matcher(secondNumber).matches()) {
                    System.out.println("The second parameter should be a natural number.\n");
                    continue;
                }

                // Checking the properties
                if (!PROPERTIES.contains(property.get(0)) && !PROPERTIES.contains(property.get(1))) {
                    System.out.println("The properties " + property + " are wrong.");
                    System.out.println("Available properties: " + PROPERTIES);
                    System.out.println();
                } else if (!PROPERTIES.contains(property.get(0))) {
                    outputWrongPropertyMessage(property.get(0));
                } else if (!PROPERTIES.contains(property.get(1))) {
                    outputWrongPropertyMessage(property.get(1));
                } else if (areMutuallyExclusive(property)) {
                    System.out.println("The request contains mutually exclusive properties: " + property);
                    System.out.println("There are no numbers with these properties.\n");
                } else {
                    String[] numbers = new String[] {firstNumber, secondNumber, property.get(0), property.get(1)};
                    return numbers;
                }

            } else {
                System.out.println("Please don't input more than four parameters!\n");
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
        boolean isDivisibleBy7;
        boolean endsWithSeven;

        // Checking if number is divisible by 7
        isDivisibleBy7 = (number % 7) == 0;

        // Checking if number ends with 7
        endsWithSeven = String.valueOf(number).endsWith("7");

        // Determining if number is a buzz number
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

    public static int numPropertiesSatisfied(List<String> requiredProperties, long number) {
        int counter = 0;
        if (requiredProperties.contains("BUZZ")) {
            if (isBuzzNumber(number)) {
                counter++;
            }
        }
        if (requiredProperties.contains("DUCK")) {
            if (isDuckNumber(number)) {
                counter++;
            }
        }
        if (requiredProperties.contains("PALINDROMIC")) {
            if (isPalindromicNumber(number)) {
                counter++;
            }
        }
        if (requiredProperties.contains("GAPFUL")) {
            if (isGapfulNumber(number)) {
                counter++;
            }
        }
        if (requiredProperties.contains("SPY")) {
            if (isSpyNumber(number)) {
                counter++;
            }
        }
        if (requiredProperties.contains("SQUARE")) {
            if (isSquareNumber(number)) {
                counter++;
            }
        }
        if (requiredProperties.contains("SUNNY")) {
            if (isSunnyNumber(number)) {
                counter++;
            }
        }
        if (requiredProperties.contains("EVEN")) {
            if (isEven(number)) {
                counter++;
            }
        }
        if (requiredProperties.contains("ODD")) {
            if (isOdd(number)) {
                counter++;
            }
        }
        return counter;
    }

    public static boolean areMutuallyExclusive(List<String> requiredProperties) {
        List<String> m1 = Arrays.asList(new String[] {"ODD", "EVEN"});
        List<String> m2 = Arrays.asList(new String[] {"DUCK", "SPY"});
        List<String> m3 = Arrays.asList(new String[] {"SUNNY", "SQUARE"});

        return (m1.containsAll(requiredProperties) || m2.containsAll(requiredProperties) || m3.containsAll(requiredProperties));
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
