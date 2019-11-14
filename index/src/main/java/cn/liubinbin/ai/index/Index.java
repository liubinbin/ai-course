package cn.liubinbin.ai.index;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Index {

    private final String rootDataPath = "D:\\src\\ai-course\\index\\data\\";
    private final int newsCount = 10000;
    private Lexicon lexicon;
    private PostingList postingList;
    private DocCollection docCollection;

    public Index() {
        HanLP.Config.ShowTermNature = false;
        this.lexicon = new Lexicon();
        this.postingList = new PostingList();
        this.docCollection = new DocCollection();
    }

    public static void main(String[] args) {
        Index index = new Index();
        long time1 = System.currentTimeMillis();
        index.build();
        System.out.println("build use " + (System.currentTimeMillis() - time1));
    }

    public void build() {
        for (int docId = 1; docId <= newsCount; docId++) {
            String tempFilePath = rootDataPath + docId;
            File file = new File(tempFilePath);
            if (file.exists()) {
                String title = "";
                String content = "";
                InputStreamReader inputStreamReader;
                BufferedReader bufferedReader;
                try {
                    // get content from file
                    inputStreamReader = new InputStreamReader(
                            new FileInputStream(tempFilePath));
                    bufferedReader = new BufferedReader(inputStreamReader);
                    String lineTxt = null;
                    // the first line is title
                    title = bufferedReader.readLine();
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        content += lineTxt;
                    }
                    Doc doc = new Doc(docId, title, null);
                    docCollection.addDoc(doc);
                    // segment
                    List<Term> segResult = SpeedTokenizer.segment(title + content);
                    Integer wordId;
                    Post post;
                    for (Term term : segResult) {
                        // add to lexicon
                        wordId = lexicon.addIfAbsent(term.word);
                        post = new Post(docId, term.offset);
                        // add word and post to postinglist
                        postingList.addWordPost(wordId, post);
                    }
//                    System.out.println("segResult.size: " + segResult.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        System.out.println("-- build done --");
        System.out.println("lexicon.getWordSize: " + lexicon.getWordSize());
        System.out.println("postingList.getWordSize: " + postingList.getWordSize());
        System.out.println("postingList.getPostSize: " + postingList.getPostSize());
//        lexicon.printLexicon();
    }

    public Set<Integer> search(String word) {
        // get wordId
        Integer wordId = this.lexicon.getWordId(word);
        if (wordId == null) {
            return new HashSet<>();
        }
        // get doc
        Set<Post> posts = this.postingList.searchByWordId(wordId);
        Set<Integer> docIds = new HashSet<>();
        for (Post post : posts) {
            docIds.add(post.getDocId());
        }
        return docIds;
    }

    public Doc getDoc(Integer docId) {
        return docCollection.getDoc(docId);
    }
}
