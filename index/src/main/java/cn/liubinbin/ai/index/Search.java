package cn.liubinbin.ai.index;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Search {

    private Index index;
    private int limit = 10;
    private boolean showContet = false;

    public Search() {
        this.index = new Index();
        this.index.build();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("welcome bin search\n" +
                "we are building index, please wait a sec");
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
            System.out.println("docs: " + docs);
        }
    }

    public Set<Doc> query(Query query) {
        Set<Doc> result = new HashSet<>();
        if (query.isSingle()) {
            return index.search(query.getWordA());
        } else {
            Set<Doc> docA = index.search(query.getWordA());
            Set<Doc> docB = index.search(query.getWordB());
            result.addAll(docA);
            if (query.getBoolFlag().equals(BoolFlag.AND)) {
                result.retainAll(docB);
                return result;
            } else if (query.getBoolFlag().equals(BoolFlag.OR)) {
                result.addAll(docB);
                return result;
            }
        }
        return null;
    }
}
