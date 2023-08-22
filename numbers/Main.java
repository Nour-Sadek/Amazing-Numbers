package numbers;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Greeting the user
        System.out.println("Welcome to Amazing Numbers!\n");
        printSupportedRequests();

        while (true) {
            // getting and checking user input
            long[] numbers = checkingUserInput();
            System.out.println();

            if (numbers[0] == -1L) {
                System.out.println("Goodbye!");
                break;
            }

            long range = numbers[1];

            if (range == 0L || range == 1L) {
                long number = numbers[0];
                outputOneNumberProperties(number);
            } else {
                long number = numbers[0];
                long limit = number + range;
                for (; number < limit; number++) {
                    outputProperties(number);
                }
                System.out.println();
            }
        }
    }

    public static long[] checkingUserInput() {
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
                    return new long[] {-1L};
                } else if (!pattern.matcher(input).matches()) {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                } else {
                    long[] numbers = new long[] {Long.parseLong(input), 0L};
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
                    long[] numbers = new long[] {Long.parseLong(firstNumber), Long.parseLong(secondNumber)};
                    return numbers;
                }
            } else {
                System.out.println("Please don't input more than two parameters!\n");
            }
        }
    }

    public static String gettingUserInput() {
        System.out.println("Enter a request:");
        String userInput = scanner.nextLine();
        return userInput;
    }

    public static boolean[] checkParity(long number) {
        boolean[] statement = new boolean[2];
        if (number % 2 == 0) {
            statement[0] = true;
        } else {
            statement[1] = true;
        }
        return statement;
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

    public static void outputProperties(long number) {
        // Creating the start of the statement
        StringBuilder statement = new StringBuilder(String.valueOf(number));
        statement.append(" is ");

        // Checking if number is odd or even
        boolean[] parity = checkParity(number);
        boolean isEven = parity[0];
        boolean isOdd = parity[1];

        // Populating the statement
        if (isBuzzNumber(number)) statement.append("buzz, ");
        if (isDuckNumber(number)) statement.append("duck, ");
        if (isPalindromicNumber(number)) statement.append("palindromic, ");
        if (isGapfulNumber(number)) statement.append("gapful, ");
        if (isEven) statement.append("even");
        if (isOdd) statement.append("odd");

        System.out.println(statement);
    }

    public static void outputOneNumberProperties(long number) {
        // Checking if number is odd or even
        boolean[] parity = checkParity(number);
        boolean isEven = parity[0];
        boolean isOdd = parity[1];

        // Print the result
        System.out.println("Properties of " + number);
        System.out.println("\tbuzz: " + isBuzzNumber(number));
        System.out.println("\tduck: " + isDuckNumber(number));
        System.out.println("\tpalindromic: " + isPalindromicNumber(number));
        System.out.println("\tgapful: " + isGapfulNumber(number));
        System.out.println("\teven: " + isEven);
        System.out.println("\todd: " + isOdd);
        System.out.println();
    }

    public static void printSupportedRequests() {
        System.out.println("""
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be processed;
                - separate the parameters with one space;
                - enter 0 to exit.
                """);
    }

}
