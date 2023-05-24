package cse2010.hw5.syntaxtree;

import java.util.Arrays;
import java.util.List;

public class Utils {
    /**
     * We will only consider binary operators and non-negative integer operands.
     */
    static List<Character> operators = List.of('*', '/', '+', '-', '(', ')');

    /**
     * Parse an infix or postfix {@code expression} and returns an array of tokens.
     * @param expression an infix (or postfix) arithmetic expression
     * @return  an array of tokens comprising the {@code expression}
     *
     * Note: '*', '/', '+', '-', '(', ')', numbers, and a single letter [a-zA-Z]
     * are considered as separate tokens.
     *
     * Example:
     *      (1 + 2) * (a - 12) => ["(", "1", "+", "2", ")", "*", "(", "a", "-", "12", ")"]
     * Valid input expression examples:
     *      a + b * c - d
     *      (a + b * c - d)
     *      a + (b * c - d)
     *      a + ((b * c) - d)
     *      (a + ((b * c) - d))
     *      (a + b) * (c - d)
     *      ((a + b) * (c - d))
     *      ...
     *      where a, b, c, d can be either a letter or numbers
     */
    public static String[] parse(String expression) {
        String[] tokens = new String[100];
        int index = 0;
        int i = 0;
        while (i < expression.length()) {
            char ch = expression.charAt(i);
            if (operators.contains(ch) || Character.isLetter(ch)) {
                tokens[index++] = String.valueOf(ch);
                i++;
            } else if (ch == ' ') {
                i++;
            } else if (Character.isDigit(ch)) {
                int start = i++;
                while (i < expression.length()) {
                    if (Character.isDigit(expression.charAt(i))) { // look ahead
                        i++;
                    } else {
                        break;
                    }
                }
                tokens[index++] = expression.substring(start, i);
            } else {
                throw new IllegalArgumentException("unknown character " + ch);
            }
        }
        return Arrays.copyOf(tokens, index);
    }

    /**
     * Check whether the `token` is composed of only digits.
     * @param token a token
     * @return true if {@code token} represents a number; false, otherwise
     */
    public static boolean isNumeric(String token) {
        return token.chars().allMatch(Character::isDigit);
    }
}

