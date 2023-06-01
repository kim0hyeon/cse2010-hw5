package cse2010.hw5.syntaxtree;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class TreeBuilder {
    private static Map<String, Integer> operators = new HashMap<>();
    static {
        // We will consider only four binary operators: *, /, +, -
        operators.put("(", 1);
        operators.put("*", 1);
        operators.put("/", 1);
        operators.put("+", 1);
        operators.put("-", 1);
        operators.put(")", 1);
        // you may put other entry/entries if needed ...
    }

    private static Stack<SyntaxTree> operandStack = new Stack<>();
    private static Stack<String> operatorStack = new Stack<>();

    /**
     * Construct a syntax free from infix arithmetic expression.
     * @param expression an infix arithmetic expression
     * @return the syntax tree
     */
    public static SyntaxTree buildFromInfix(String expression) {
        initStacks();

        /**
         * You code goes here ...
         */

        return null;
    }

    /**
     * Construct a syntax free from postfix arithmetic expression.
     * @param expression a postfix arithmetic expression
     * @return the syntax tree
     */
    public static SyntaxTree buildFromPostfix(String expression) {
        initStacks();

        /**
         * You code goes here ...
         */

        return new SyntaxTree();
    }

    /**
     * Reset stacks
     */
    private static void initStacks() { // 오퍼랜드 스택과 오퍼레이터 스택을 비운다.
        operandStack.clear();
        operatorStack.clear();
    }
}

