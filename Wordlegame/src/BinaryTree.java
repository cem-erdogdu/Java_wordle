
public class BinaryTree {

    class Node {
        String value;
        Node left;
        Node right;

        public Node(String value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }

        public void print() {
            if (left != null) {
                left.print();
            }

            System.out.println(value);

            if (right != null) {
                right.print();
            }
        }
    }

    Node root;

    public BinaryTree() {
        root = null;
    }

    public boolean insert(String value) {
        if (contains(value)) {
            return false;
        }

        root = insert(root, value);
        return true;
    }

    private Node insert(Node node, String value) {
        if (node == null) {
            node = new Node(value);
            return node;
        }

        if (value.compareTo(node.value) < 0) {
            node.left = insert(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = insert(node.right, value);
        }

        return node;
    }

    public boolean contains(String value) {
        return contains(root, value);
    }

    private boolean contains(Node node, String value) {
        if (node == null) {
            return false;
        }

        if (node.value.equals(value)) {
            return true;
        } else if (value.compareTo(node.value) < 0) {
            return contains(node.left, value);
        } else {
            return contains(node.right, value);
        }
    }

    public void print() {
        root.print();
    }

}

