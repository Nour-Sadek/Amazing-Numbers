package numbers;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // getting and checking user input
        int number = checkingUserInput();

        // Checking if number is odd or even
        boolean[] parity = checkParity(number);
        boolean isEven = parity[0];
        boolean isOdd = parity[1];

        // Checking if number is a buzz number
        boolean isBuzz = isBuzzNumber(number);

        // Checking if number is a duck number
        boolean isDuck = isDuckNumber(number);

        // Print the result
        System.out.println("Properties of " + number);
        System.out.println("\teven: " + isEven);
        System.out.println("\todd: " + isOdd);
        System.out.println("\tbuzz: " + isBuzz);
        System.out.println("\tduck: " + isDuck);
    }

    public static int checkingUserInput() {
        Pattern pattern = Pattern.compile("^[0-9]*[1-9]+$|^[1-9]+[0-9]*$");
        while (true) {
            String userInput = gettingUserInput();
            if (userInput == null || !pattern.matcher(userInput).matches() || userInput.equals("0")) {
                System.out.println("This number is not natural!\n");
            } else {
                int number = Integer.parseInt(userInput);
                return number;
            }
        }
    }

    public static String gettingUserInput() {
        System.out.println("Enter a natural number:");
        String userInput = scanner.nextLine();
        return userInput;
    }

    public static boolean[] checkParity(int number) {
        boolean[] statement = new boolean[2];
        if (number % 2 == 0) {
            statement[0] = true;
        } else {
            statement[1] = true;
        }
        return statement;
    }

    public static boolean isBuzzNumber(int number) {
        boolean isDivisibleBy7;
        boolean endsWithSeven;

        // Checking if number is divisible by 7
        isDivisibleBy7 = (number % 7) == 0;

        // Checking if number ends with 7
        endsWithSeven = String.valueOf(number).endsWith("7");

        // Determining if number is a buzz number
        return isDivisibleBy7 || endsWithSeven;
    }

    public static boolean isDuckNumber(int number) {
        String stringNumber = String.valueOf(number);
        if (stringNumber.length() == 1) {
            return false;
        } else {
            return stringNumber.substring(1).contains("0");
        }
    }

}
