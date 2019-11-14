package cn.liubinbin.ai.index;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by bin on 2019/11/14.
 *
 * @Description: 倒排列表
 */
public class PostingList {

    private ConcurrentSkipListMap<Integer, HashSet<Post>> postingList;

    public PostingList() {
        this.postingList = new ConcurrentSkipListMap<Integer, HashSet<Post>>();
    }

    public static void main(String[] args) {
        PostingList postingList = new PostingList();
        postingList.addWordPost(1, new Post(1, 2));
        postingList.addWordPost(1, new Post(2, 3));
        postingList.addWordPost(2, new Post(1, 4));
        System.out.println(postingList.getWordSize());
        postingList.printPostingList();
    }

    public synchronized void addWordPost(Integer word, Post post) {
        // 添加单词，保证单词一定存在
        this.postingList.putIfAbsent(word, new HashSet<Post>());
        // 将 Post 加入单词对应的列表里
        this.postingList.get(word).add(post);
    }

    public void printPostingList() {
        System.out.println("-- PostingList --");
        System.out.println(this.postingList);
    }

    public int getWordSize() {
        return this.postingList.size();
    }

    public int getPostSize() {
        int postSize = 0;
        for (Integer wordId : this.postingList.keySet()) {
            postSize += this.postingList.get(wordId).size();
        }
        return postSize;
    }

    public Set<Post> searchByWordId(Integer wordId) {
        return postingList.get(wordId);
    }
}
