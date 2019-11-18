package cn.liubinbin.ai.index;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Search {

    private Index index;
    private int limit = 10;
    private ShowType showType = ShowType.TITLE;

    public Search() {
        this.index = new Index();
        this.index.build();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("we are building index, please wait a sec");
        Search search = new Search();

        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String queryStr = null;
            System.out.print("Query: ");
            queryStr = br.readLine().trim();
            Query query = Parser.parse(queryStr);
            if (query == null) {
                System.out.println("WARNING: query format is not right, you should input query like \"hello\" or \"hello or world\" or \"hello and world\"");
                continue;
            }
            System.out.println("parsed query: " + query);
            Set<Doc> docs = search.query(query);
            search.prettyShow(docs);
        }
    }

    public Set<Doc> query(Query query) {
        int count = 0;
        Set<Doc> docResult = new HashSet<Doc>();
        Set<Integer> docIds = queryIds(query);
        if (docIds != null) {
            for (Integer docId : docIds) {
                if (count > limit) {
                    break;
                }
                if (showType.equals(ShowType.ID)) {
                    docResult.add(new Doc(docId, null, null));
                } else if (showType.equals(ShowType.TITLE)) {
                    docResult.add(new Doc(docId, index.getDoc(docId).getTitle(), null));
                }
                if (showType.equals(ShowType.CONTENT)) {
                    docResult.add(index.getDoc(docId));
                }
                count++;
            }
        }
        return docResult;
    }

    public Set<Integer> queryIds(Query query) {
        Set<Integer> docIdResult = new HashSet<Integer>();
        if (query.isSingle()) {
            return index.search(query.getWordA());
        } else {
            Set<Integer> docA = index.search(query.getWordA());
            Set<Integer> docB = index.search(query.getWordB());
            docIdResult.addAll(docA);
            if (query.getBoolFlag().equals(BoolFlag.AND)) {
                docIdResult.retainAll(docB);
                return docIdResult;
            } else if (query.getBoolFlag().equals(BoolFlag.OR)) {
                docIdResult.addAll(docB);
                return docIdResult;
            }
        }
        return null;
    }

    public void prettyShow(Set<Doc> docs) {
        for (Doc doc : docs) {
            System.out.println("id: " + doc.getDocId() + " title: " + doc.getTitle() + " content: " + doc.getContent());
        }
    }
}
