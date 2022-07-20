package com.example.fast.easy.common;

/**
 * @author paul
 * @Date 2022/7/20 23:38
 */
class Trie {
    //
    //
    private Trie[] children;
    private boolean isEnd;


    public Trie() {
        children = new Trie[26];
        isEnd = false;
    }

    //插入
    public void insert(String word) {
        Trie node = this;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - 'a';
            if (node.children[index] == null) {
                node.children[index] = new Trie();
            }
            node = node.children[index];
        }
        node.isEnd = true;
    }

    //查询某个单词
    public boolean search(String word) {
        Trie node = searchPrefix(word);
        return node != null && node.isEnd;
    }

    //查询前缀是否存在
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

    //查询前缀，并返回前缀
    private Trie searchPrefix(String prefix) {
        Trie node = this;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            int index = ch - 'a';
            if (node.children[index] == null) {
                return null;
            }
            node = node.children[index];
        }
        return node;
    }

    public static void main(String[] args) {





    }
}

