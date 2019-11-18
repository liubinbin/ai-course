package cn.liubinbin.ai.index;

import java.util.HashMap;

/**
 * Created by bin on 2019/11/14.
 *
 * @Description: TODO
 */
public class DocCollection {

    private HashMap<Integer, Doc> data;

    public DocCollection() {
        this.data = new HashMap<Integer, Doc>(10000);
    }

    public void addDoc(Doc doc) {
        data.put(doc.getDocId(), doc);
    }

    public Doc getDoc(Integer docId) {
        return data.get(docId);
    }
}
