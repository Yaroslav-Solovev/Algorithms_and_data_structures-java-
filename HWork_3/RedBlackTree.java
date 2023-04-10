package HWork_3;

import java.util.Iterator;
import java.util.Stack;

public class RedBlackTree<K extends Comparable<K>, V> implements Iterable<RedBlackTree<K, V>.Entry<K, V>>{
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public abstract class Entry<K, V> {
        public abstract K key();
        public abstract V value();
    }

    private class Node<K, V> extends Entry<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        boolean color;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.color = RED;
        }

        public K key() {
            return key;
        }

        public V value() {
            return value;
        }
    }

    Node<K, V> root;

    public void put(K key, V value) {
        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node<K, V> put(Node<K, V> node, K key, V value) {
        if (node == null) {
            return new Node<K, V>(key, value);
        }
        
        if (isFourNode(node)) {
            node = splitFourNode(node);
        }

        assert !isFourNode(node);

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            node.value = value;
        }
        else if (cmp < 0) {
            node.left = put(node.left, key, value);
        }
        else /* if (cmp > 0) */ {
            node.right = put(node.right, key, value);
        }

        if (isRed(node.right)) {
            node = leanLeft(node);
        }

        assert !isRed(node.right);

        if (node.left != null) {
            assert node.left.key.compareTo(node.key) < 0;
        }

        if (node.right != null) {
            assert node.right.key.compareTo(node.key) > 0;
        }

        assert node != null;
        return node;
    }

    private boolean isFourNode(Node<K, V> node) {
        return isRed(node.left) && isRed(node.left.left);
    }

    private boolean isRed(Node<K, V> node) {
        if (node == null) {
            return false;
        }
        else {
            return node.color == RED;
        }
    }

    private Node<K, V> splitFourNode(Node<K, V> node) {
        /* Сценарий: node - черный
         *           node.left - красный
         *           node.left.left - красный
         */
        assert !isRed(node);
        assert isRed(node.left);
        assert isRed(node.left.left);

        node = rotateRight(node);
        node.left.color = BLACK;

        return node;
    }

    private Node<K, V> leanLeft(Node<K, V> node) {
        /*
         * Сценарий: node.right - красный
         */
        assert isRed(node.right);

        node = rotateLeft(node);
        node.color = node.left.color;
        node.left.color = RED;

        return node;
    }

    private Node<K, V> rotateLeft(Node<K, V> node) {
        Node<K, V> top = node.right;
        node.right = top.left;
        top.left = node;
        
        return top;
    }

    private Node<K, V> rotateRight(Node<K, V> node) {
        Node<K, V> top = node.left;
        node.left = top.right;
        top.right = node;

        return top;
    }

    public Iterator<Entry<K, V>> iterator() {
        return new InorderEntryIterator(root);
    }

    private class InorderEntryIterator implements Iterator<Entry<K, V>> {
        Stack<Node<K, V>> stack;

        InorderEntryIterator(Node<K, V> root) {
            stack = new Stack<Node<K, V>>();
            explore(root);
        }

        private void explore(Node<K, V> node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        public boolean hasNext() {
            return !stack.empty();
        }

        public Entry<K, V> next() {
            Node<K, V> node = stack.pop();

            if (node.right != null) {
                explore(node.right);
            }

            return node;
        }

        public void remove() {
            throw new UnsupportedOperationException("Erorr: Невозможно удалить узлы отсюда!");
        }
    }

    public static void main(String[] args) {
        RedBlackTree<Character, Integer> tree = new RedBlackTree<Character, Integer>();
        /* Визуализация древа:
         *        c
         *       / \
         */
        tree.put('c', 0);

        assert tree.root != null;
        assert tree.root.key == 'c';
        assert tree.root.value == 0;
        assert tree.root.color == BLACK;

        /* Визуализация древа: берем ключ побольше и убеждаемся, что дерево повернуто
         *       i
         *      / \
         *     c
         *    / \
         */
        tree.put('i', 0);

        assert tree.root.key == 'i';
        assert tree.root.left.key == 'c';

        assert tree.root.color == BLACK;
        assert tree.root.left.color == RED;

        assert tree.root.right == null;
        assert tree.root.left.right == null;
        assert tree.root.left.left == null;

        /* Визуализация древа: снова ключ побольше, и проверяем, что дерево еще осталось
         *       v
         *      / \
         *     i
         *    / \
         *   c
         *  / \
         */
        tree.put('v', 0);

        assert tree.root.key == 'v';
        assert tree.root.left.key == 'i';
        assert tree.root.left.left.key == 'c';

        assert tree.root.color == BLACK;
        assert tree.root.left.color == RED;
        assert tree.root.left.left.color == RED;

        assert tree.root.right == null;
        assert tree.root.left.right == null;
        assert tree.root.left.left.right == null;
        assert tree.root.left.left.left == null;

        /* Визуализация древа: Последняя проверка, четыре узла должны быть разделены
         * Попробуйте вставить что-нибудь между i и v
         *            i
         *          /   \
         *         c     v
         *        / \   / \
         *             m 
         *            / \
         */
        
        tree.put('m', 0);
        
        assert tree.root.key == 'i';
        assert tree.root.right.key == 'v';
        assert tree.root.left.key == 'c';
        assert tree.root.right.left.key == 'm';
        
        assert tree.root.color == BLACK;
        assert tree.root.left.color == BLACK;
        assert tree.root.right.color == BLACK;
        assert tree.root.right.left.color == RED;
        
        assert tree.root.left.left == null;
        assert tree.root.left.right == null;
        assert tree.root.right.left.left == null;
        assert tree.root.right.left.right == null;
        assert tree.root.right.right == null;

        System.out.println("Все работает!");

        System.out.println("В порядке итерации.");
        for (RedBlackTree<Character, Integer>.Entry<Character, Integer> entry: tree) {
            System.out.print(entry.key().toString() + " ");
        }

        System.out.println();
    }
}
