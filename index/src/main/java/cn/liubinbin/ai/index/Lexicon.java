package cn.liubinbin.ai.index;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bin on 2019/11/14.
 *
 * @Description: 单词词典
 */
public class Lexicon {

    private HashMap<String, Integer> data;
    private AtomicInteger counter;

    public Lexicon() {
        this.data = new HashMap<String, Integer>(45000);
        this.counter = new AtomicInteger(0);
    }

    public static void main(String[] args) {
        Lexicon lexicon = new Lexicon();
        lexicon.addIfAbsent("我的");
        lexicon.printLexicon();
        lexicon.addIfAbsent("我");
        lexicon.printLexicon();
        lexicon.addIfAbsent("我");
        lexicon.printLexicon();
    }

    public synchronized Integer addIfAbsent(String word) {
        if (!this.data.containsKey(word)) {
            Integer count = this.counter.incrementAndGet();
            this.data.put(word, count);
            return count;
        } else {
            return this.data.get(word);
        }
    }

    public Integer getWordId(String word) {
        if (!this.data.containsKey(word)) {
            return null;
        } else {
            return this.data.get(word);
        }
    }

    public int getWordSize() {
        return this.data.size();
    }

    public void printLexicon() {
        System.out.println("-- Lexicon --");
        System.out.println(this.data);
    }
}
