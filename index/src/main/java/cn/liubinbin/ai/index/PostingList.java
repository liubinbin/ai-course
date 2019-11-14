package cn.liubinbin.ai.index;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by bin on 2019/11/14.
 *
 * @Description: 倒排列表
 */
public class PostingList {

    private ConcurrentSkipListMap<String, Post> postingList;

    public PostingList() {
        this.postingList = new ConcurrentSkipListMap<String, Post>();
    }


}
