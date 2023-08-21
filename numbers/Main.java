package numbers;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Greeting the user
        System.out.println("""
        Welcome to Amazing Numbers!

        Supported requests:
        - enter a natural number to know its properties;
        - enter 0 to exit.
        """);

        while (true) {
            // getting and checking user input
            long number = checkingUserInput();
            System.out.println();
            if (number == -1L) {
                System.out.println("Goodbye!");
                break;
            }

            // Checking if number is odd or even
            boolean[] parity = checkParity(number);
            boolean isEven = parity[0];
            boolean isOdd = parity[1];

            // Checking if number is a buzz number
            boolean isBuzz = isBuzzNumber(number);

            // Checking if number is a duck number
            boolean isDuck = isDuckNumber(number);

            // Checking if number is a palindromic number
            boolean isPalindromic = isPalindromicNumber(number);

            // Print the result
            System.out.println("Properties of " + number);
            System.out.println("\teven: " + isEven);
            System.out.println("\todd: " + isOdd);
            System.out.println("\tbuzz: " + isBuzz);
            System.out.println("\tduck: " + isDuck);
            System.out.println("\tpalindromic: " + isPalindromic);
            System.out.println();
        }
    }

    public static long checkingUserInput() {
        Pattern pattern = Pattern.compile("^[0-9]*[1-9]+$|^[1-9]+[0-9]*$");
        while (true) {
            String userInput = gettingUserInput();
            if (userInput.equals("0")) {
                return -1L;
            } else if (userInput == null || !pattern.matcher(userInput).matches()) {
                System.out.println("The first parameter should be a natural number or zero.\n");
            } else {
                long number = Long.parseLong(userInput);
                return number;
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

}
