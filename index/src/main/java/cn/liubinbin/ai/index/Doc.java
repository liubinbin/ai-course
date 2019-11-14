package cn.liubinbin.ai.index;

/**
 * Created by bin on 2019/11/14.
 *
 * @Description: TODO
 */
public class Doc {

    private String title;
    private String content;

    public Doc(String title, String content) {
        this.title = title;
        this.content = content;
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
}
