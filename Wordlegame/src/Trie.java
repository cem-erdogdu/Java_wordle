class TrieNode {
    TrieNode[] children;
    boolean isEndOfWord;

    public TrieNode() {
        children = new TrieNode[26];
        isEndOfWord = false;
    }
}

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - 'a';
            TrieNode node = current.children[index];
            if (node == null) {
                node = new TrieNode();
                current.children[index] = node;
            }
            current = node;
        }
        current.isEndOfWord = true;
    }

    public boolean contains(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - 'a';
            TrieNode node = current.children[index];
            if (node == null) {
                return false;
            }
            current = node;
        }
        return current.isEndOfWord;
    }
}