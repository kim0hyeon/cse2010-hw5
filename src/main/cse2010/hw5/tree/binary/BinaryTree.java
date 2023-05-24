package cse2010.hw5.tree.binary;

import cse2010.hw5.tree.Position;
import cse2010.hw5.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface for a binary tree, in which each node has at most two children.
 */
public interface BinaryTree<T> extends Tree<T> {
    /**
     * Returns the position of {@code position}'s left child (or null if no child exists).
     * @param position a node position
     * @return the left child position of the position {@code position}
     */
    Position<T> left(Position<T> position);

    /**
     * Returns the position of {@code position}'s right child (or null if no child exists).
     * @param position a node position
     * @return the right child position of the position {@code position}
     */
    Position<T> right(Position<T> position);

    /**
     * Returns the position of {@code position}'s sibling (or null if no sibling exists).
     * @param position a node position
     * @return the sibling position of the position {@code position}
     */
    default Position<T> sibling(Position<T> position) {
        Position<T> parent = parent(position);
        if (parent == null) return null;    // position must be the root
        if (position == left(parent))       // position is a left child
            return right(parent);           // (right child might be null)
        else // position is a right child
            return left(parent);            // (left child might be null)
    }

    /**
     * Returns the number of children of Position {@code position}.
     * @param position a node position
     * @return the number of children
     */
    default int numChildren(Position<T> position) {
        int count = 0;
        if (left(position) != null)
            count++;
        if (right(position) != null)
            count++;
        return count;
    }

    /**
     * Returns a list of the positions representing {@code position}'s children.
     * @param position a node position
     * @return a list of the child positions
     */
    default List<Position<T>> children(Position<T> position) {
        List<Position<T>> snapshot = new ArrayList<>(2); // max capacity of 2
        if (left(position) != null)
            snapshot.add(left(position));
        if (right(position) != null)
            snapshot.add(right(position));
        return snapshot;
    }

    /**
     * Returns a list of positions of the tree, reported in inorder.
     * @return a list of positions of the tree, reported in inorder
     */
    default List<Position<T>> inOrder() {
        List<Position<T>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            inOrder(root(), snapshot);    // fill the snapshot recursively
        }
        return snapshot;
    }

    /**
     * Adds positions visited in inorder sequence, starting from {@code position},
     * to the given snapshot.
     * @param position a node position
     * @param snapshot a list of positions visited so far
     */
    default void inOrder(Position<T> position, List<Position<T>> snapshot) {
        if (left(position) != null) {
            inOrder(left(position), snapshot);
        }
        snapshot.add(position);
        if (right(position) != null) {
            inOrder(right(position), snapshot);
        }
    }

    /**
     * Returns the list of all positions (in inorder sequence by default) in the tree.
     * @return the list of all positions in the tree
     */
    default List<Position<T>> positions() {
        return inOrder();
    }

    /**
     * Check if the {@code position} has a left child.
     * @param position a node position
     * @return true if the {@code position} has a left child, false otherwise
     */
    default boolean hasLeft(Position<T> position) {
        return left(position) != null;
    }

    /**
     * Check if the {@code position} has a right child.
     * @param position a node position
     * @return true if the {@code position} has a right child, false otherwise
     */
    default boolean hasRight(Position<T> position) {
        return right(position) != null;
    }

    /**
     * Check if the {@code position} is a left child.
     * @param position a node position
     * @return true if the {@code position} is a left child, false otherwise
     */
    boolean isLeftChild(Position<T> position);

    /**
     * Check if the {@code position} is a right child.
     * @param position a node position
     * @return true if the {@code position} is a right child, false otherwise
     */
    boolean isRightChild(Position<T> position);

}
