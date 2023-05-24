package cse2010.hw5.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Top-level general tree interface.
 * @param <T> the element type of the position
 */
public interface Tree<T> {
    /**
     * Returns the root position of the tree (or null if empty).
     * @return the root position
     */
    Position<T> root();

    /**
     * Returns the parent position of {@code position} (or null if {@code }position} is the root).
     * @param position a node position
     * @return the parent position
     */
    Position<T> parent(Position<T> position);

    /**
     * Returns the number of children of the {@code }position}.
     * @param position a node position
     * @return the number of children
     */
    int numChildren(Position<T> position);

    /**
     * Returns a list containing the children of position {@code position} (if any).
     * @param position a node position
     * @return a list of child positions
     */
    List<Position<T>> children(Position<T> position);

    /**
     * Returns true if position {@code position} has at least one child.
     * @param position a node position
     * @return true if internal position, false otherwise
     */
    default boolean isInternal(Position<T> position) {
        return numChildren(position) > 0;
    }

    /**
     * Returns true if position {@code }position} does not have any children.
     * @param position a node position
     * @return true if external position, false otherwise
     */
    default boolean isExternal(Position<T> position) {
        return numChildren(position) == 0;
    }

    /**
     * Returns true if position {@code position} is the root of the tree.
     * @param position a node position
     * @return true if root position, false otherwise
     */
    default boolean isRoot(Position<T> position) {
        return position == root();
    }

    /**
     * Returns the number of positions (and hence elements) that are contained in the tree.
     * @return the number of positions in the tree
     */
    int size();

    /**
     * Returns true if the tree does not contain any positions (and thus no elements).
     * @return true if empty tree
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns a list of all positions in the tree.
     * @return a list of all positions in the tree
     */
    default List<Position<T>> positions() {
        return levelOrder();
    }

    /**
     * Get the depth of the tree.
     * @return the depth of the tree
     */
    default int depth() {
        return height();
    }

    /**
     * Returns the number of levels separating Position {@code position} from the root.
     * @param position a node position
     * @return the depth of a position
     */
    default int depth(Position<T> position) {
        if (isRoot(position)) return 0;

        return 1 + depth(parent(position));
    }

    /**
     * Returns the height of the tree.
     * @return the height of the tree
     */
    default int height() {
        return height(root());
    }

    /**
     * Returns the height of the subtree rooted at position {@code }position}.
     * @param position a node position
     * @return the height of the subtree rooted at {@code position}
     */
    default int height(Position<T> position) {
        int h = 0;
        for (Position<T> child : children(position)) {
            h = Math.max(h, 1 + height(child));
        }
        return h;
    }

    /**
     * Returns the list of positions of the tree, reported in preorder.
     * @return the list of positions visited in preorder
     */
    default List<Position<T>> preOrder() {
        List<Position<T>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            preOrder(root(), snapshot);
        }

        return snapshot;
    }

    /**
     * Adds positions of the subtree rooted at {@code position} to the given
     * {@code snapshot} in preorder.
     * @param position a node position
     * @param snapshot a list of visited positions so far
     */
    default void preOrder(Position<T> position, List<Position<T>> snapshot) {
        snapshot.add(position);

        children(position).forEach(child -> preOrder(child, snapshot));
    }

    /**
     * Returns a list of positions of the tree, reported in postorder.
     * @return an list of positions visited in postorder
     */
    default List<Position<T>> postOrder() {
        List<Position<T>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            postOrder(root(), snapshot);
        }
        return snapshot;
    }

    /**
     * Adds positions of the subtree rooted at {@code position} to the given
     * {@code snapshot} in postorder.
     * @param position a node position
     * @param snapshot a list of visited positions so far
     */
    default void postOrder(Position<T> position, List<Position<T>> snapshot) {
        for (Position<T> child : children(position)) {
            postOrder(child, snapshot);
        }
        snapshot.add(position);
    }

    /**
     * Return the list of all positions in the tree visited in level-order.
     * @return the list of all positions visited in level-order
     */
    default List<Position<T>> levelOrder() {
        List<Position<T>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            Queue<Position<T>> queue = new LinkedList<>();
            queue.add(root());

            while (!queue.isEmpty()) {
                Position<T> position = queue.remove();
                snapshot.add(position);
                queue.addAll(children(position));
            }
        }
        return snapshot;
    }

    /**
     * Associate a new root with the value {@code element} as the root of the tree.
     * The tree must have been an empty tree before you call this method.
     * @param element the element of the root
     * @return the position of the root
     */
    Position<T> addRoot(T element);
}
