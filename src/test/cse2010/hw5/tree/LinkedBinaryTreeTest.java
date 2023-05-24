package cse2010.hw5.tree;

import static cse2010.hw5.tree.TreeUtils.toElements;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import cse2010.hw5.tree.binary.BinaryTree;
import cse2010.hw5.tree.binary.linked.LinkedBinaryTree;
import cse2010.hw5.tree.binary.linked.LinkedBinaryTree.Node;
import org.junit.jupiter.api.Test;

public class LinkedBinaryTreeTest {

    @Test
    void should_create_empty_tree() {
        // Given

        // When
        LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<>();

        // Then
        assertNull(tree.root());
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
    }

    @Test
    void should_create_binary_tree_with_only_root() {
        // Given
        LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<>();

        // When
        tree.addRoot(1);

        // Then
        assertAll(
                () -> assertNotNull(tree.root()),
                () -> assertTrue(tree.isRoot(tree.root())),
                () -> assertNull(tree.parent(tree.root())),
                () -> assertEquals(1, tree.root().getElement()),
                () -> assertFalse(tree.isEmpty()),
                () -> assertEquals(1, tree.size()),
                () -> assertNull(tree.left(tree.root())),
                () -> assertNull(tree.right(tree.root())),
                () -> assertNull(tree.sibling(tree.root())),
                () -> assertTrue(tree.isExternal(tree.root())),
                () -> assertFalse(tree.isInternal(tree.root())),
                () -> assertFalse(tree.hasLeft(tree.root())),
                () -> assertFalse(tree.hasRight(tree.root())),
                () -> assertEquals(0, tree.height()),
                () -> assertEquals(0, tree.depth()),
                () -> assertEquals(0, tree.numChildren(tree.root())),
                () -> assertEquals(emptyList(), tree.children(tree.root())),
                () -> assertEquals(List.of(1), toElements(tree.inOrder())),
                () -> assertEquals(List.of(1), toElements(tree.preOrder())),
                () -> assertEquals(List.of(1), toElements(tree.postOrder())),
                () -> assertEquals(List.of(1), toElements(tree.levelOrder())),
                () -> assertEquals(tree.inOrder(), tree.positions()),
                () -> {
                    List<Position<Integer>> positions = new ArrayList<>();
                    tree.inOrder(tree.root(), positions);
                    assertEquals(List.of(1), toElements(positions));
                }
        );
    }

    @Test
    void should_add_children_to_a_given_position() {
        // Given
        LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        tree.addRoot(1);

        // When
        tree.addLeft(tree.root(), 2);
        tree.addRight(tree.root(), 3);

        // Then
        assertAll(
                () -> assertTrue(tree.isInternal(tree.root())),
                () -> assertFalse(tree.isExternal(tree.root())),
                () -> assertTrue(tree.hasLeft(tree.root())),
                () -> assertEquals(2, tree.left(tree.root()).getElement()),
                () -> assertTrue(tree.hasRight(tree.root())),
                () -> assertEquals(3, tree.right(tree.root()).getElement()),
                () -> assertEquals(1, tree.parent(tree.left(tree.root())).getElement()),
                () -> assertEquals(1, tree.parent(tree.right(tree.root())).getElement()),
                () -> assertEquals(3, tree.sibling(tree.left(tree.root())).getElement()),
                () -> assertEquals(2, tree.sibling(tree.right(tree.root())).getElement()),
                () -> assertEquals(3, tree.size()),
                () -> assertEquals(1, tree.height()),
                () -> assertEquals(1, tree.depth()),
                () -> assertEquals(2, tree.numChildren(tree.root())),
                () -> assertEquals(List.of(2, 3), toElements(tree.children(tree.root()))),
                () -> assertEquals(List.of(2, 1, 3), toElements(tree.inOrder())),
                () -> assertEquals(List.of(1, 2, 3), toElements(tree.preOrder())),
                () -> assertEquals(List.of(2, 3, 1), toElements(tree.postOrder())),
                () -> assertEquals(List.of(1, 2, 3), toElements(tree.levelOrder())),
                () -> assertEquals(tree.inOrder(), tree.positions()),
                () -> {
                    List<Position<Integer>> positions = new ArrayList<>();
                    tree.inOrder(tree.root(), positions);
                    assertEquals(List.of(2, 1, 3), toElements(positions));
                }
        );
    }

    @Test
    void should_attach_subtrees_as_children_to_a_given_position_in_a_tree() {
        // Given
        LinkedBinaryTree<Integer> leftChild = new LinkedBinaryTree<>();
        leftChild.addRoot(2);
        LinkedBinaryTree<Integer> rightChild = new LinkedBinaryTree<>();
        rightChild.addRoot(3);
        LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        tree.addRoot(1);

        // When
        tree.attach(tree.root(), leftChild, rightChild);

        // Then
        assertAll(
                () -> assertTrue(tree.isInternal(tree.root())),
                () -> assertFalse(tree.isExternal(tree.root())),
                () -> assertTrue(tree.hasLeft(tree.root())),
                () -> assertEquals(2, tree.left(tree.root()).getElement()),
                () -> assertTrue(tree.hasRight(tree.root())),
                () -> assertEquals(3, tree.right(tree.root()).getElement()),
                () -> assertEquals(1, tree.parent(tree.left(tree.root())).getElement()),
                () -> assertEquals(1, tree.parent(tree.right(tree.root())).getElement()),
                () -> assertEquals(3, tree.sibling(tree.left(tree.root())).getElement()),
                () -> assertEquals(2, tree.sibling(tree.right(tree.root())).getElement()),
                () -> assertFalse(tree.isInternal(tree.left(tree.root()))),
                () -> assertTrue(tree.isExternal(tree.left(tree.root()))),
                () -> assertFalse(tree.isInternal(tree.right(tree.root()))),
                () -> assertTrue(tree.isExternal(tree.right(tree.root()))),
                () -> assertEquals(3, tree.size()),
                () -> assertEquals(1, tree.height()),
                () -> assertEquals(1, tree.depth()),
                () -> assertEquals(2, tree.numChildren(tree.root())),
                () -> assertEquals(List.of(2, 3), toElements(tree.children(tree.root()))),
                () -> assertEquals(List.of(2, 1, 3), toElements(tree.inOrder())),
                () -> assertEquals(List.of(1, 2, 3), toElements(tree.preOrder())),
                () -> assertEquals(List.of(2, 3, 1), toElements(tree.postOrder())),
                () -> assertEquals(List.of(1, 2, 3), toElements(tree.levelOrder())),
                () -> assertEquals(tree.inOrder(), tree.positions()),
                () -> {
                    List<Position<Integer>> positions = new ArrayList<>();
                    tree.inOrder(tree.root(), positions);
                    assertEquals(List.of(2, 1, 3), toElements(positions));
                }
        );
    }

    @Test
    void should_invalidate_attached_trees_after_attaching() {
        // Given
        LinkedBinaryTree<Integer> child = new LinkedBinaryTree<>();
        child.addRoot(2);
        LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        tree.addRoot(1);

        // When
        tree.attach(tree.root(), child, null);

        // Then
        assertAll(
                () -> assertNull(child.root()),
                () -> assertTrue(child.isEmpty()),
                () -> assertEquals(0, child.size())
        );
    }

    @Test
    void demo_tree_construction_another_way() {
        // This is just for testing!!
        Node<Integer> root =
                new Node<>(3, null,
                        new Node<>(1, null,
                                null,
                                new Node<>(2, null, null, null)
                        ),
                        new Node<>(5, null,
                                new Node<>(4, null, null, null),
                                null
                        )
                );
        BinaryTree<Integer> tree = new LinkedBinaryTree<>(root);

        assertAll(
                () -> assertEquals(5, tree.size()),
                () -> assertEquals(2, tree.depth()),
                () -> assertEquals(2, tree.height()),
                () -> assertEquals(2, tree.numChildren(tree.root())),
                () -> assertEquals(1, tree.numChildren(tree.left(tree.root()))),
                () -> assertEquals(1, tree.numChildren(tree.right(tree.root()))),
                () -> assertEquals(List.of(1, 2, 3, 4, 5), toElements(tree.inOrder())),
                () -> assertEquals(List.of(3, 1, 2, 5, 4), toElements(tree.preOrder())),
                () -> assertEquals(List.of(2, 1, 4, 5, 3), toElements(tree.postOrder())),
                () -> assertEquals(List.of(3, 1, 5, 2, 4), toElements(tree.levelOrder())),

                () -> assertEquals(3, tree.parent(tree.left(tree.root())).getElement()),
                () -> assertEquals(1, tree.numChildren(tree.left(tree.root()))),
                () -> assertEquals(3, tree.parent(tree.right(tree.root())).getElement()),
                () -> assertEquals(5, tree.parent(tree.left(tree.right(tree.root()))).getElement())
        );
    }
}
