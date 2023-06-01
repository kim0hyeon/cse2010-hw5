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
    public static String[] parse(String expression) { // 연산자, 문자, 숫자를 String type의 토큰들로 잘라 배열로 리턴한다.
        String[] tokens = new String[100];
        int index = 0;
        int i = 0;
        while (i < expression.length()) {
            char ch = expression.charAt(i); // 지정한 부분의 캐릭터가 캐릭터인지 연산자인지 확인하는것
            if (operators.contains(ch) || Character.isLetter(ch)) { // 캐릭터가 연산자이거나 문자일 경우
                tokens[index++] = String.valueOf(ch); // 문자를 문자열 타입으로 변환시켜서 토큰에 집어넣는다.
                i++;
            } else if (ch == ' ') {
                i++;
            } else if (Character.isDigit(ch)) { // 캐릭터가 숫자일경우
                int start = i++;
                while (i < expression.length()) {
                    if (Character.isDigit(expression.charAt(i))) { // look ahead
                        i++;
                    } else {
                        break;
                    }
                }
                tokens[index++] = expression.substring(start, i); // 토큰에 숫자를 집어넣는다.
            } else { // 연산자, 빈칸, 숫자 모두 아닌경우 오류를 출력시킨다.
                throw new IllegalArgumentException("unknown character " + ch);
            }
        }
        return Arrays.copyOf(tokens, index); // String type의 토큰들의 배열을 복사해 리턴해준다.
    }

    /**
     * Check whether the `token` is composed of only digits.
     * @param token a token
     * @return true if {@code token} represents a number; false, otherwise
     */
    public static boolean isNumeric(String token) { // 숫자가 맞는지 확인하는 메서드
        return token.chars().allMatch(Character::isDigit);
    }
}

