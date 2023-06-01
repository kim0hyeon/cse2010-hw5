package cse2010.hw5.tree.binary.linked;

import cse2010.hw5.tree.Position;
import cse2010.hw5.tree.binary.BinaryTree;

/**
 * Link-based implementation of binary cse2010.trees.
 * @param <T> element type for the node
 */
public class LinkedBinaryTree<T> implements BinaryTree<T> {
    /**
     * Linked-implemented node structure for the binary tree.
     * @param <T> the element type of position
     */
    public static class Node<T> implements Position<T> {
        private T element;
        private Node<T> parent;
        private Node<T> left;
        private Node<T> right;

        /**
         * Construct a new node.
         * @param element an element to be stored at this node
         * @param parent the parent node
         * @param left the left child
         * @param right the right child
         */
        public Node(T element, Node<T> parent, Node<T> left, Node<T> right) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            if (this.left != null)
                this.left.setParent(this);
            this.right = right;
            if (this.right != null)
                this.right.setParent(this);
        }

        /**
         * Construct a new node.
         * @param element an element to be stored at this node.
         */
        public Node(T element) {
            this(element, null, null, null);
        }

        /**
         * Returns element at this node
         * @return element at this node
         */
        @Override
        public T getElement() {
            return this.element;
        }

        /**
         * Set the element of this node.
         * @param element element to be set
         */
        public void setElement(T element) {
            this.element = element;
        }

        /**
         * Returns the parent.
         * @return the parent
         */
        public Node<T> getParent() {
            return parent;
        }

        /**
         * Set {@code parent} as this node's parent node.
         * @param parent the parent node
         */
        public void setParent(Node<T> parent) {
            this.parent = parent;
        }

        /**
         * Returns the left child node.
         * @return the left child node
         */
        public Node<T> getLeft() {
            return left;
        }

        /**
         * Set {@code left} as this node's left child node.
         * @param left left child node
         */
        public void setLeft(Node<T> left) {
            this.left = left;
        }

        /**
         * Returns the right child node.
         * @return the left child node
         */
        public Node<T> getRight() {
            return right;
        }

        /**
         * Set {@code right} as this node's right child node.
         * @param right right child node
         */
        public void setRight(Node<T> right) {
            this.right = right;
        }

        /**
         * Returns a brief string representation for this node.
         * @return a brief string representation for this node
         */
        @Override
        public String toString() {
            return "Node{" + element + ", " + (parent == null ? "null" : parent.getElement()) +
                ", " + (left == null ? "null" : left.toString()) +
                ", " + (right == null ? "null" : right.toString()) +
                '}';
        }

        private int count(Node<T> node) {
            setParent(node);
            if (getLeft() == null && getRight() == null) {
                return 1;
            }
            int count = 1;
            if (getLeft() != null)
                count += getLeft().count(this);
            if (getRight() != null)
                count += getRight().count(this);
            return count;
        }
    }

    protected Node<T> root;
    protected int size;

    /**
     * Factory method to create a new node storing the element
     * @param element an element
     * @param parent the parent
     * @param left the left child
     * @param right the right child
     * @return a newly created node
     */
    protected Node<T> createNode(T element, Node<T> parent, Node<T> left, Node<T> right) {
        return new Node<>(element, parent, left, right);
    }

    /**
     * Default constructor
     */
    public LinkedBinaryTree() {}

    /**
     * Build a linked binary tree using {@code root} position as a root.
     * @param root the root position
     */
    public LinkedBinaryTree(Position<T> root) {
        Node<T> node = validate(root);
        this.root = node;
        this.size = node.count(null);
    }

    /**
     * Validates the position and returns it as a node.
     * @param position a node position
     * @return a node representing the {@code position}
     * @throws IllegalArgumentException
     * 
     */
    protected Node<T> validate(Position<T> position) throws IllegalArgumentException {
        if (!(position instanceof Node)) {
            throw new IllegalArgumentException("Not valid position type!");
        }
        Node<T> node = (Node<T>) position;
        if (node.getParent() == node) {         // defined convention for defunct/removed node
            throw new IllegalArgumentException("This position is not longer in the tree");
        }
        return node;
    }

    /**
     * Return the number of nodes in the tree.
     * @return the number of nodes
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Return the root position of the tree.
     * @return the root position
     */
    @Override
    public Position<T> root() {
        return this.root;
    }

    /**
     * Return the parent position of a Position {@code position}.
     * @param position a node position
     * @return the parent position
     */
    @Override
    public Position<T> parent(Position<T> position) {
        Node<T> node = validate(position);
        return node.getParent();
    }

    /**
     * Return the left child position of a position {@code position}.
     * @param position a node position
     * @return the left child position
     */
    @Override
    public Position<T> left(Position<T> position) {
        Node<T> node = validate(position);
        return node.getLeft();
    }

    /**
     * Return the right child position of a position {@code position}.
     * @param position a node position
     * @return the right child position
     */
    @Override
    public Position<T> right(Position<T> position) {
        Node<T> node = validate(position);
        return node.getRight();
    }

    /**
     * Creates a root position with {@code element} and returns it.
     * @param element element of the root
     * @return a newly created root position
     * @throws IllegalStateException
     */
    public Position<T> addRoot(T element) throws IllegalStateException {
        if (!isEmpty()) {
            throw new IllegalStateException("Tree is not empty!");
        }
        root = createNode(element, null, null, null);
        size = 1;
        return root;
    }

    /**
     * Creates a new left child of position {@code position} storing element {@code element};
     * returns its position.
     * @param position a node position
     * @param element an element to be stored at position {@code position}
     * @return the new left child position of position {@code position}
     */
    public Position<T> addLeft(Position<T> position, T element) {
        Node<T> parent = validate(position);
        if (parent.getLeft() != null) {
            throw new IllegalArgumentException("This position already has a left child!");
        }
        Node<T> child = createNode(element, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }

    /**
     * Creates a new right child of position {@code position} storing element {@code element};
     * returns its position.
     * @param position a node position
     * @param element an element to be stored at position {@code position}
     * @return the new right child position of position {@code position}
     */
    public Position<T> addRight(Position<T> position, T element) {
        Node<T> parent = validate(position);
        if (parent.getRight() != null) {
            throw new IllegalArgumentException("This position already jas a right child!");
        }
        Node<T> right = createNode(element, parent, null, null);
        parent.setRight(right);
        size++;
        return right;
    }

    /**
     * Replaces the element at position {@code position} with {@code element}.
     * and returns the replaced element.
     * @param position a node position
     * @param element an element to replace the old element
     * @return the replaced old element
     */
    public T replace(Position<T> position, T element) {
        Node<T> node = validate(position);
        T removedElement = node.getElement();
        node.setElement(element);
        return removedElement;
    }

    /**
     * Attaches trees {@code t1} and {@code t2} as left and right subtrees of external position.
     * @param position a node position
     * @param t1 a subtree to be attached as a left child of the position {@code position}
     * @param t2 a subtree to be attached as a left child of the position {@code position}
     */
    public void attach(Position<T> position, LinkedBinaryTree<T> t1, LinkedBinaryTree<T> t2) {
        Node<T> node = validate(position);
        if (isInternal(position)) {
            throw new IllegalArgumentException("The position must be a leaf!");
        }
        size += (t1 != null ? t1.size() : 0) + (t2 != null ? t2.size() : 0);

        if (t1 != null && !t1.isEmpty()) {            // attach t1 as left subtree of node
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }

        if (t2 != null && !t2.isEmpty()) {            // attach t2 as right subtree of node
            t2.root.setParent(node);
            node.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }

    /**
     * Removes the node at a {@code position} and replaces it with its child, if any.
     * @param position a node position
     * @return the element of the removed position
     */
    public T remove(Position<T> position) {
        Node<T> node = validate(position);
        if (numChildren(position) == 2) {
            throw new IllegalArgumentException("This position has two children!");
        }

        Node<T> parent = node.getParent();
        Node<T> child = (node.getLeft() != null) ? node.getLeft() : node.getRight();
        if (child != null) {
            child.setParent(parent);    // child's grandparent becomes its parent
        }

        if (node == root) {
            root = child;               // child becomes root
        } else {
            if (node == parent.getLeft()) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
        size--;

        T removedElement = node.getElement();
        node.setElement(null);          // help garbage collection
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);           // our convention for defunct node

        return removedElement;
    }

    /**
     * Check if the {@code position} is a left child.
     * @param position a node position
     * @return true if the {@code position} is a left child, false otherwise
     */
    @Override
    public boolean isLeftChild(Position<T> position) {
        Node<T> node = validate(position);
        Node<T> parent = validate(node.getParent());
        return parent != null && node.getParent().getLeft() == node;
    }

    /**
     * Check if the {@code position} is a right child.
     * @param position a node position
     * @return true if the {@code position} is a right child, false otherwise
     */
    @Override
    public boolean isRightChild(Position<T> position) {
        Node<T> node = validate(position);
        Node<T> parent = validate(node.getParent());
        return parent != null && node.getParent().getRight() == node;
    }

    @Override
    public String toString() {
        return isEmpty() ?
            "Empty tree" : "LinkedBinaryTree{root=" + root + ", size=" + size + '}';
    }
}
