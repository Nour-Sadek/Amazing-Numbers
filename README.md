# Amazing Numbers

### About

We use numbers every day. But do you know how many different properties they have? 
Let's take a look at some of the amazing properties of numbers. 

This program can identify whether a number has each of the following 12 properties:
- *Even*
- *Odd*
- *Buzz*: A number is a buzz number if it is either divisible by 7 or ends with 7.
- *Duck*: A number is a duck number if it is a positive number that contains zeroes. Note that a number with a leading 0 is not a duck number.
- *Palindromic*: A number is a palindromic number if it is symmetrical; it stays the same regardless of whether we read it from left or right.
- *Gapful*: A number is a gapful number if it at least contains 3 digits and is divisible by the cancatenation of its first and last digit without a remainder. For example, 7881 is a gapful number since __7__ 88 __1__ __%__ 71 == 0.
- *Spy*: A number is a spy number if the sum of all digits is equal to the product of all digits.
- *Square*: A number is a square number or a perfect square if it is an integer that is the square of an integer.
- *Sunny*: A number __N__ is a sunny number if __N + 1__ is a perfect square number.
- *Jumping*: A number is a jumping number if the adjacent digits inside the number differ by 1. Note that the difference between 9 and 0 is not considered as 1. For example, 78987 and 4343456 are jumping numbers, but 796 and 89098 are not.
- *Happy*: A number is a happy number if it is a number that reaches 1 after a sequence during which the number is replaced by the sum of each digit squared. For example, 13 is a happy number since __1__^2 + __3__^2 = 10 which leads to __1__^2 + __0__^2 = __1__.
- *Sad*: A sad number is a number that is not happy.

Once the program starts, the user will be given a list of all possible requests.

Below is a possible run of the program:


    Welcome to Amazing Numbers!

    Supported requests:
    - enter a natural number to know its properties;
    - enter two natural numbers to obtain the properties of the list:
        * the first parameter represents a starting number;
        * the second parameter shows how many consecutive numbers are to be processed;
    - two natural numbers and properties to search for;
    - a property preceded by minus must not be present in numbers;
    - separate the parameters with one space;
    - enter 0 to exit.

    Enter a request:
    89

    Properties of 89
    buzz: false
    duck: false
    palindromic: false
    gapful: false
    spy: false
    square: false
    sunny: false
    jumping: true
    happy: false
    sad: true
    even: false
    odd: true

    Enter a request:
    1 -87
    The second parameter should be a natural number.

    Enter a request:
    3426 5 duck

    3430 is buzz, duck, sad, even
    3440 is duck, sad, even
    3450 is duck, gapful, sad, even
    3460 is duck, sad, even
    3470 is duck, sad, even

    Enter a request:
    8293 3 sad palindromic even

    8338 is palindromic, sad, even
    8448 is palindromic, gapful, sad, even
    8558 is palindromic, sad, even

    Enter a request:
    105 5

    105 is buzz, duck, gapful, sad, odd
    106 is duck, sad, even
    107 is buzz, duck, sad, odd
    108 is duck, gapful, sad, even
    109 is duck, happy, odd

    Enter a request:
    879654 5 sad -odd gapful

    879680 is duck, gapful, sad, even
    879760 is buzz, duck, gapful, sad, even
    879840 is duck, gapful, sad, even
    879866 is gapful, sad, even
    879920 is duck, gapful, sad, even

    Enter a request:
    4563 8 sod -odd palindromic -even
    The property [SOD] is wrong.
    Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD, EVEN, ODD]

    Enter a request:
    4563 8 sad -odd palindromic -even
    The request contains mutually exclusive properties: [-EVEN, -ODD]
    There are no numbers with these properties.

    Enter a request:
    exit
    The first parameter should be a natural number or zero if only one parameter is provided.

    Enter a request:
    0

    Goodbye!

# General Info

To learn more about this project, please visit
[HyperSkill Website - Amazing Numbers](https://hyperskill.org/projects/184).

This project's difficulty has been labelled as __Hard__ where this is how
HyperSkill describes each of its four available difficulty levels:

- __Easy Projects__ - if you're just starting
- __Medium Projects__ - to build upon the basics
- __Hard Projects__ - to practice all the basic concepts and learn new ones
- __Challenging Projects__ - to perfect your knowledge with challenging tasks

This repository contains:

    numbers package - Contains the numbers.Main java class that contains the program.

Project was built using java version 8 update 381

# How to Run

Download the numbers repository to your local repository and open the project in your choice IDE and run the project.
