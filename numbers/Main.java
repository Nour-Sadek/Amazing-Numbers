package numbers;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // getting and checking user input
        int number = checkingUserInput();

        // Checking if number is odd or even
        String oddOrEven = statingOddOrEven(number);
        System.out.println(oddOrEven);

        // Checking if number is a buzz number
        isBuzzNumber(number);
    }

    public static int checkingUserInput() {
        Pattern pattern = Pattern.compile("[1-9]([0-9]+)?");
        while (true) {
            String userInput = gettingUserInput();
            if (userInput == null || !pattern.matcher(userInput).matches()) {
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

    public static String statingOddOrEven(int number) {
        String statement;
        if (number % 2 == 0) {
            statement = "This number is Even.";
        } else {
            statement = "This number is Odd.";
        }
        return statement;
    }

    public static void isBuzzNumber(int number) {
        boolean isDivisibleBy7;
        boolean endsWithSeven;

        // Checking if number is divisible by 7
        isDivisibleBy7 = (number % 7) == 0;

        // Checking if number ends with 7
        endsWithSeven = String.valueOf(number).endsWith("7");

        // Determining if number is a buzz number
        statingIfBuzz(isDivisibleBy7, endsWithSeven);
        System.out.println("Explanation:");

        // Determing the explanation
        outputExplanation(isDivisibleBy7, endsWithSeven, number);
    }

    public static void statingIfBuzz(boolean isDivisibleBy7, boolean endsWithSeven) {
        boolean isBuzz = isDivisibleBy7 || endsWithSeven;
        if (isBuzz) {
            System.out.println("It is a Buzz number.");
        } else {
            System.out.println("It is not a Buzz number.");
        }
    }

    public static void outputExplanation(boolean isDivisibleBy7, boolean endsWithSeven, int number) {
        if (isDivisibleBy7 && endsWithSeven) {
            System.out.println(number + " is divisible by 7 and ends with 7.");
        } else if (!isDivisibleBy7 && endsWithSeven) {
            System.out.println(number + " ends with 7.");
        } else if (isDivisibleBy7 && !endsWithSeven) {
            System.out.println(number + " is divisible by 7.");
        } else {
            System.out.println(number + " is neither divisible by 7 nor does it end with 7.");
        }
    }

}
