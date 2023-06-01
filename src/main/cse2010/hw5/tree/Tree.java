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
     * 루트노드를 리턴해주는 메서드
     */
    Position<T> root();

    /**
     * Returns the parent position of {@code position} (or null if {@code }position} is the root).
     * @param position a node position
     * @return the parent position
     * 인자로 넣은 노드의 부모노드를 리턴해주는 메소드
     */
    Position<T> parent(Position<T> position);

    /**
     * Returns the number of children of the {@code }position}.
     * @param position a node position
     * @return the number of children
     * 인자로 넣은 노드의 자식 노드의 개수를 리턴해주는 메서드
     */
    int numChildren(Position<T> position);

    /**
     * Returns a list containing the children of position {@code position} (if any).
     * @param position a node position
     * @return a list of child positions
     * 인자로 넣은 노드의 하위 항목을 포함하는 리스트를 반환한다.
     */
    List<Position<T>> children(Position<T> position);

    /**
     * Returns true if position {@code position} has at least one child.
     * @param position a node position
     * @return true if internal position, false otherwise
     * 인터널 노드인지 아닌지 확인하는 메서드
     */
    default boolean isInternal(Position<T> position) {
        return numChildren(position) > 0;
    }

    /**
     * Returns true if position {@code }position} does not have any children.
     * @param position a node position
     * @return true if external position, false otherwise
     * external 노드인지 확인하는 메서드
     */
    default boolean isExternal(Position<T> position) {
        return numChildren(position) == 0;
    }

    /**
     * Returns true if position {@code position} is the root of the tree.
     * @param position a node position
     * @return true if root position, false otherwise
     * root 노드인지 확인하는 메서드
     */
    default boolean isRoot(Position<T> position) {
        return position == root();
    }

    /**
     * Returns the number of positions (and hence elements) that are contained in the tree.
     * @return the number of positions in the tree
     * 노드의 개수를 리턴하는 메서드
     */
    int size();

    /**
     * Returns true if the tree does not contain any positions (and thus no elements).
     * @return true if empty tree
     * 트리가 비어있는지 확인하는 메서드
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns a list of all positions in the tree.
     * @return a list of all positions in the tree
     * 트리에 있는 모든 노드들을 리스트로 만들어서 리턴해주는 메서드
     */
    default List<Position<T>> positions() {
        return levelOrder();
    }

    /**
     * Get the depth of the tree.
     * @return the depth of the tree
     * 트리의 깊이를 리턴해주는 메서드
     */
    default int depth() {
        return height();
    }

    /**
     * Returns the number of levels separating Position {@code position} from the root.
     * @param position a node position
     * @return the depth of a position
     * 해당 노드의 깊이가 얼마인지 리턴해주는 메서드
     */
    default int depth(Position<T> position) {
        if (isRoot(position)) return 0;

        return 1 + depth(parent(position));
    }

    /**
     * Returns the height of the tree.
     * @return the height of the tree
     * 트리의 높이를 리턴해주는 메서드
     */
    default int height() {
        return height(root());
    }

    /**
     * Returns the height of the subtree rooted at position {@code }position}.
     * @param position a node position
     * @return the height of the subtree rooted at {@code position}
     * 해당 노드의 높이가 얼마인지 리턴해주는 메서드
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
     * preorder로 노드들을 리스트로 만들어 리턴하는 메서드
     */
    default List<Position<T>> preOrder() {
        List<Position<T>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            preOrder(root(), snapshot); // 트리가 비어있지 않다면 root노드로 시작하고, 만들어진 리스트를 반환한다.
        }

        return snapshot;
    }

    /**
     * Adds positions of the subtree rooted at {@code position} to the given
     * {@code snapshot} in preorder.
     * @param position a node position
     * @param snapshot a list of visited positions so far
     * 인자에 주어진 노드를 인자에 주어진 리스트에 집어넣은 뒤 두 자식노드들도 같은 것을 반복한다.
     * children이 없다면 빈 리스트가 주어지기에 마지막줄이 실행되지 않는다.
     */
    default void preOrder(Position<T> position, List<Position<T>> snapshot) {
        snapshot.add(position); // 호출한것을 먼저 저장하고

        children(position).forEach(child -> preOrder(child, snapshot)); // 다음 저장할 것들을 호출한다.
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
     * postorder로 노드들을 리스트에 저장한다.
     */
    default void postOrder(Position<T> position, List<Position<T>> snapshot) {
        for (Position<T> child : children(position)) {
            postOrder(child, snapshot); // 호출을 끝까지 한 뒤
        }
        snapshot.add(position); // 그 후 저장한다.
    }

    /**
     * Return the list of all positions in the tree visited in level-order.
     * @return the list of all positions visited in level-order
     * 같은 level의 노드들을 리스트에 모두 저장하고 다음 level로 넘어간다.
     */
    default List<Position<T>> levelOrder() {
        List<Position<T>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            Queue<Position<T>> queue = new LinkedList<>();
            queue.add(root());

            while (!queue.isEmpty()) {
                Position<T> position = queue.remove(); // 큐에 들어있는 노드를 지우면서 position 변수에 저장한다.
                snapshot.add(position); // 저장한 노드를 snapshot에 저장한다.
                queue.addAll(children(position)); // 그리고 position 변수에 저장된 노드의 자식노드를 큐에 저장한다.
            }
        }
        return snapshot;
    }

    /**
     * Associate a new root with the value {@code element} as the root of the tree.
     * The tree must have been an empty tree before you call this method.
     * @param element the element of the root
     * @return the position of the root
     * 트리의 루트를 지정합니다. 이 메서드를 사용하려면 반드시 트리가 비어있어야합니다.
     */
    Position<T> addRoot(T element);
}
