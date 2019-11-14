package cn.liubinbin.ai.index;

/**
 * Created by bin on 2019/11/14.
 *
 * @Description: TODO
 */
public class Doc {

    private Integer docId;
    private String title;
    private String content;

    public Doc(Integer docId, String title, String content) {
        this.docId = docId;
        this.title = title;
        this.content = content;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "{" + docId + ";" + title + ";" + content + "}";
    }
}
