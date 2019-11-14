package cn.liubinbin.ai.index;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Search {

    private Index index;
    public Search() {

    }

    public static void main(String[] args) throws IOException {


        System.out.println("welcome bin search");
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String queryStr = null;
            System.out.print("Query: ");
            queryStr = br.readLine().trim();
            System.out.println("queryStr: " + queryStr);
        }
    }
}
