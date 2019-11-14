package cn.liubinbin.ai.index;

/**
 * Created by bin on 2019/11/14.
 *
 * @Description: TODO
 */
public class Post {
    private int docId;
    private int offset;

    public Post(int docId, int offset) {
        this.docId = docId;
        this.offset = offset;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
