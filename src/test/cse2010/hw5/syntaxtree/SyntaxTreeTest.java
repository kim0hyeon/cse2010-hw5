package cse2010.hw5.syntaxtree;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static cse2010.hw5.tree.TreeUtils.toElements;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SyntaxTreeTest {

    static String[] postfix_inputs = {
            "10 2 +",
            "1 20 + 31 49 + *",
            "1 2 + 3 4 2 / 5 * + *"
    };

    static String[] infix_inputs = {
            "10 + 2",
            "(10+ 2)",
            "(1 + 20) * (31 + 49)",
            "((1 + 20) * (31 + 49))",
            "(1 + 2) * (3 + ((4 / 2) * 5))"
    };

    static List<List<String>> expectedTrees = List.of(
            List.of("10", "+", "2"),
            List.of("1", "+", "20", "*", "31", "+", "49"),
            List.of("1", "+", "2", "*", "3", "+", "4", "/", "2", "*", "5")
    );

    static String[] expectedInfixes = {
            "(10 + 2)",
            "((1 + 20) * (31 + 49))",
            "((1 + 2) * (3 + ((4 / 2) * 5)))"
    };

    static String[] expectedPrefixes = {
            "+ 10 2",
            "* + 1 20 + 31 49",
            "* + 1 2 + 3 * / 4 2 5"
    };

    static String[] expectedResults = {
            "12.0",
            "1680.0",
            "39.0"
    };

    static String[] expectedIndents = {
            "+\n"
                    + "  10\n"
                    + "  2\n",

            "*\n"
                    + "  +\n"
                    + "    1\n"
                    + "    20\n"
                    + "  +\n"
                    + "    31\n"
                    + "    49\n",

            "*\n"
                    + "  +\n"
                    + "    1\n"
                    + "    2\n"
                    + "  +\n"
                    + "    3\n"
                    + "    *\n"
                    + "      /\n"
                    + "        4\n"
                    + "        2\n"
                    + "      5\n"
    };

    static Stream<Arguments> postfixAndAnswer() {
        return Stream.of(
                arguments(postfix_inputs[0], expectedTrees.get(0)),
                arguments(postfix_inputs[1], expectedTrees.get(1)),
                arguments(postfix_inputs[2], expectedTrees.get(2))
        );
    }

    @ParameterizedTest
    @MethodSource("postfixAndAnswer")
    void should_build_syntax_tree_from_postfix_expression(String input, List<String> expected) {
        SyntaxTree tree = TreeBuilder.buildFromPostfix(input);
        assertEquals(expected, toElements(tree.inOrder()));
    }

    static Stream<Arguments> postfixInputAndPrefixAnswer() {
        return Stream.of(
                arguments(postfix_inputs[0], expectedPrefixes[0]),
                arguments(postfix_inputs[1], expectedPrefixes[1]),
                arguments(postfix_inputs[2], expectedPrefixes[2])
        );
    }

    @ParameterizedTest
    @MethodSource("postfixInputAndPrefixAnswer")
    void should_convert_to_prefix(String input, String expected) {
        assertEquals(expected, TreeBuilder.buildFromPostfix(input).toPreFix());
    }

    static Stream<Arguments> postfixInputAndPostfixAnswer() {
        return Stream.of(
                arguments(postfix_inputs[0], postfix_inputs[0]),
                arguments(postfix_inputs[1], postfix_inputs[1]),
                arguments(postfix_inputs[2], postfix_inputs[2])
        );
    }

    @ParameterizedTest
    @MethodSource("postfixInputAndPostfixAnswer")
    void should_convert_to_postfix(String input, String expected) {
        assertEquals(expected, TreeBuilder.buildFromPostfix(input).toPostFix());
    }

    static Stream<Arguments> postfixInputAndInfixAnswer() {
        return Stream.of(
                arguments(postfix_inputs[0], expectedInfixes[0]),
                arguments(postfix_inputs[1], expectedInfixes[1]),
                arguments(postfix_inputs[2], expectedInfixes[2])
        );
    }

    @ParameterizedTest
    @MethodSource("postfixInputAndInfixAnswer")
    void should_fully_parenthesize_infix(String input, String expected) {
        assertEquals(expected, TreeBuilder.buildFromPostfix(input).toInfix());
    }

    static Stream<Arguments> postfixInputAndEvaluationAnswer() {
        return Stream.of(
                arguments(postfix_inputs[0], expectedResults[0]),
                arguments(postfix_inputs[1], expectedResults[1]),
                arguments(postfix_inputs[2], expectedResults[2])
        );
    }

    @ParameterizedTest
    @MethodSource("postfixInputAndEvaluationAnswer")
    void should_evaluate_expression(String input, String expected) {
        assertEquals(Double.parseDouble(expected), TreeBuilder.buildFromPostfix(input).evaluate(), 0.1);
    }

    static Stream<Arguments> postfixInputAndIndentationAnswer() {
        return Stream.of(
                arguments(postfix_inputs[0], expectedIndents[0]),
                arguments(postfix_inputs[1], expectedIndents[1]),
                arguments(postfix_inputs[2], expectedIndents[2])
        );
    }

    @ParameterizedTest
    @MethodSource("postfixInputAndIndentationAnswer")
    void should_print_indented_tree(String input, String expected) {
        assertEquals(expected, TreeBuilder.buildFromPostfix(input).showTree());
    }

    static Stream<Arguments> infixAndAnswer() {
        return Stream.of(
                arguments(infix_inputs[0], expectedTrees.get(0)),
                arguments(infix_inputs[1], expectedTrees.get(0)),
                arguments(infix_inputs[2], expectedTrees.get(1)),
                arguments(infix_inputs[3], expectedTrees.get(1)),
                arguments(infix_inputs[4], expectedTrees.get(2))
        );
    }

    @ParameterizedTest
    @MethodSource("infixAndAnswer")
    void should_build_syntax_tree_from_infix_expression(String input, List<String> expected) {
        SyntaxTree tree = TreeBuilder.buildFromInfix(input);
        assertEquals(expected, toElements(tree.inOrder()));
    }

    /**/

    @Test
    void demo1() {
        SyntaxTree tree1 = TreeBuilder.buildFromPostfix("a b *");
        printTreeInfos(tree1);

        SyntaxTree tree2 = TreeBuilder.buildFromInfix("a * b");
        assertAll(
                () -> assertEquals(tree1.toPreFix(), tree2.toPreFix()),
                () -> assertEquals(tree1.toPostFix(), tree2.toPostFix()),
                () -> assertEquals(tree1.toInfix(), tree2.toInfix()),
                () -> assertEquals(tree1.showTree(), tree2.showTree())
        );
    }

    @Test
    void demo2() {
        SyntaxTree tree1 = TreeBuilder.buildFromPostfix("a b + c d - *");
        printTreeInfos(tree1);

        SyntaxTree tree2 = TreeBuilder.buildFromInfix("(a + b) * (c - d)");
        assertAll(
                () -> assertEquals(tree1.toPreFix(), tree2.toPreFix()),
                () -> assertEquals(tree1.toPostFix(), tree2.toPostFix()),
                () -> assertEquals(tree1.toInfix(), tree2.toInfix()),
                () -> assertEquals(tree1.showTree(), tree2.showTree())
        );    }

    @Test
    void demo3() {
        SyntaxTree tree1 = TreeBuilder.buildFromPostfix("a b * c 1 - e 20 f * + / +");
        printTreeInfos(tree1);

        SyntaxTree tree2 = TreeBuilder.buildFromInfix("a * b + (c - 1) / (e + 20 * f)");
        assertAll(
                () -> assertEquals(tree1.toPreFix(), tree2.toPreFix()),
                () -> assertEquals(tree1.toPostFix(), tree2.toPostFix()),
                () -> assertEquals(tree1.toInfix(), tree2.toInfix()),
                () -> assertEquals(tree1.showTree(), tree2.showTree())
        );
    }

    @Test
    void demo4() {
        SyntaxTree tree1 = TreeBuilder.buildFromPostfix("4 5 * 201 1 - 20 20 4 * + / +");
        printTreeInfos(tree1);

        SyntaxTree tree2 = TreeBuilder.buildFromInfix("4 * 5 + (201 - 1) / (20 + 20 * 4)");
        assertAll(
                () -> assertEquals(tree1.toPreFix(), tree2.toPreFix()),
                () -> assertEquals(tree1.toPostFix(), tree2.toPostFix()),
                () -> assertEquals(tree1.toInfix(), tree2.toInfix()),
                () -> assertEquals(tree1.showTree(), tree2.showTree())
        );
    }

    private void printTreeInfos(SyntaxTree tree) {
        System.out.println(tree.showTree());
        System.out.println(tree.toPostFix());
        System.out.println(tree.toPreFix());
        System.out.println(tree.toInfix());
    }

}

