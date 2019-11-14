package cn.liubinbin.ai.index;

/**
 * Created by bin on 2019/11/14.
 *
 * @Description: TODO
 */
public class Parser {

    public static Query parse(String queryStr) {
        queryStr = queryStr.trim();
        String[] querySplit = queryStr.split(" ");
        if (querySplit.length == 1) {
            return new Query(querySplit[0].trim());
        } else if (querySplit.length == 3) {
            BoolFlag boolFlag = BoolFlag.parse(querySplit[1].trim());
            if (boolFlag != null) {
                return new Query(querySplit[0].trim(), boolFlag, querySplit[2].trim());
            }
        }
        return null;
    }
}
