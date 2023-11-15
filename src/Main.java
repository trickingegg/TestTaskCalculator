import java.util.Map;
import java.util.Scanner;

import static java.util.Map.entry;

public class Main {
    private static final Map<String, Integer> RomanMap = Map.ofEntries(
            entry("I", 1),
            entry("II", 2),
            entry("III", 3),
            entry("IV", 4),
            entry("V", 5),
            entry("VI", 6),
            entry("VII", 7),
            entry("VIII", 8),
            entry("IX", 9),
            entry("X", 10),
            entry("XL", 40),
            entry("L", 50),
            entry("XC", 90),
            entry("C", 100)
    );

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a mathematical expression: ");
        String operation = in.nextLine();
        in.close();

        try {
            System.out.println("result: " + calc(operation));
        } catch (Exception e){
            System.out.println("Error during calculation: " + e.getMessage());
        }
    }

    public static String calc(String input) throws Exception {
        var numberA = 0d;
        var numberB = 0d;
        var result = 0;
        var parts = input.split(" ");

        if (parts.length != 3)
            throw new Exception("Mathematical expression must consist of two numbers");

        var isRoman = RomanMap.containsKey(parts[0]);
        if (isRoman != RomanMap.containsKey(parts[2]))
            throw new NumberFormatException("Both numbers must be the same type: Arabic or Roman");

        if (isRoman) {
            numberA = RomanMap.get(parts[0]);
            numberB = RomanMap.get(parts[2]);
        }
        else {
            numberA = Double.parseDouble(parts[0]);
            numberB = Double.parseDouble(parts[2]);

            if (numberA % 1 > 0 || numberB % 1 > 0)
                throw new NumberFormatException("Numbers must be integers");
            else if (numberA < 1 || numberA > 10 || numberB < 1 || numberB > 10)
                throw new Exception("Numbers must be from 1 to 10 inclusive");
        }

        var operator = parts[1];
        result = switch (operator) {
            case "+" -> (int) (numberA + numberB);
            case "-" -> (int) (numberA - numberB);
            case "*" -> (int) (numberA * numberB);
            case "/" -> (int) (numberA / numberB);
            default -> throw new Exception("Invalid operator");
        };

        if (isRoman)
            return convertToRoman(result);

        return String.valueOf(result);
    }

    public static String convertToRoman(int number) {
        if (number < 1 || number > 100)
            throw new IllegalArgumentException("With Roman input the result can't be less than 1");

        StringBuilder romanNumeral = new StringBuilder();

        for (String symbol : RomanMap.keySet()) {
            var value = RomanMap.get(symbol);
            if (number == value) {
                romanNumeral.append(symbol);
                number -= value;
            } else if (number < RomanMap.get("C") && number > RomanMap.get("XC")) {
                romanNumeral.append("XC");
                number -= 90;
            } else if (number > RomanMap.get("L") && number < RomanMap.get("XC")) {
                romanNumeral.append("L");
                number -= 50;
            } else if (number > RomanMap.get("XL") && number < RomanMap.get("L")) {
                romanNumeral.append("XL");
                number -= 40;
            } else if (number > RomanMap.get("X") && number < RomanMap.get("XL")) {
                romanNumeral.append("X");
                number -= 10;
            }
        }

        return romanNumeral.toString();
    }
}