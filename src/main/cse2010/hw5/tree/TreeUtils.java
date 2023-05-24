package cse2010.hw5.tree;

import java.util.List;
import java.util.stream.Collectors;

public class TreeUtils {
    /**
     * Generate a space used for indentation.
     * @param level indent level starting from 0 meaning no indentation
     * @param stride a token corresponding to a unit indentation level
     * @return space for indentation
     */
    public static String spaces(int level, String stride) {
        return stride.repeat(Math.max(0, level));
    }

    /**
     * Generate a space used for indentation.
     * @param level indentation level starting from 0 meaning no indentation
     * @return spaces for indentation
     */
    public static String spaces(int level) {
        return spaces(level, "  ");
    }

    /**
     * Convert a list of Positions to a list of Elements.
     * @param positions a list of node positions
     * @param <T> the element type of the position
     * @return a list of node elements
     */
    public static <T> List<T> toElements(List<Position<T>> positions) {
        return positions.stream().map(Position::getElement).collect(Collectors.toList());
    }

}
