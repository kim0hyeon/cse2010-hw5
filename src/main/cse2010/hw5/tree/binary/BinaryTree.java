package cse2010.hw5.tree.binary;

import cse2010.hw5.tree.Position;
import cse2010.hw5.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface for a binary tree, in which each node has at most two children.
 * 바이너리 트리의 인터페이스이고, 각 internal node는 모두 자식노드를 2개 가지고 있다.
 */
public interface BinaryTree<T> extends Tree<T> {
    /**
     * Returns the position of {@code position}'s left child (or null if no child exists).
     * @param position a node position
     * @return the left child position of the position {@code position}
     * 인자로 선택한 노드의 왼쪽 자식 노드를 리턴한다. (levelOrder에서 쓸 수 있을것같은 느낌)
     */
    Position<T> left(Position<T> position);

    /**
     * Returns the position of {@code position}'s right child (or null if no child exists).
     * @param position a node position
     * @return the right child position of the position {@code position}
     * 인자로 선택한 노드의 오른쪽 자식 노드를 리턴한다.
     */
    Position<T> right(Position<T> position);

    /**
     * Returns the position of {@code position}'s sibling (or null if no sibling exists).
     * @param position a node position
     * @return the sibling position of the position {@code position}
     * 형제를 리턴한다.
     */
    default Position<T> sibling(Position<T> position) {
        Position<T> parent = parent(position);
        if (parent == null) return null;    // position must be the root, 자신이 루트노드일경우 형제가 없기에 null 리턴
        if (position == left(parent))       // position is a left child, 인자로 받은 노드의 부모노드
            return right(parent);           // (right child might be null)
        else // position is a right child
            return left(parent);            // (left child might be null)
    }

    /**
     * Returns the number of children of Position {@code position}.
     * @param position a node position
     * @return the number of children
     * 자식 노드의 개수가 몇개인지 확인하는 메서드
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
     * 선택한 노드의 자식 노드를 리스트로 묶어서 출력한다.
     * 자식이 없으면 둘다 빈 arrayList가 리턴된다.
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
     * inorder 형식으로 list를 출력한다.
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
     * inorder 트리에 주어진 노드들을 집어넣는다.
     */
    default void inOrder(Position<T> position, List<Position<T>> snapshot) {
        if (left(position) != null) {
            inOrder(left(position), snapshot); // 선택한 노드의 왼쪽 자식이 있다면 리스트에 넣는다.
        }
        snapshot.add(position); // 선택한 노드를 리스트에 넣는다.
        if (right(position) != null) {
            inOrder(right(position), snapshot); // 선택한 노드의 오른쪽 자식이 있다면 리스트에 넣는다.
        }
    }

    /**
     * Returns the list of all positions (in inorder sequence by default) in the tree.
     * @return the list of all positions in the tree
     * tree를 inorder 형식으로 변화시킨 리스트를 리턴한다.
     */
    default List<Position<T>> positions() {
        return inOrder();
    }

    /**
     * Check if the {@code position} has a left child.
     * @param position a node position
     * @return true if the {@code position} has a left child, false otherwise
     * 왼쪽 자식노드가 있는지 확인하는 메서드
     */
    default boolean hasLeft(Position<T> position) {
        return left(position) != null;
    }

    /**
     * Check if the {@code position} has a right child.
     * @param position a node position
     * @return true if the {@code position} has a right child, false otherwise
     * 오른쪽 자식 노드가 있는지 확인하는 메서드
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
