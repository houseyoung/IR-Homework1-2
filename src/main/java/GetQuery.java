import java.util.List;
import java.util.Map;

/**
 * GetQuery
 *
 * @author: yangch
 * @time: 2015/11/21 14:02
 */
public class GetQuery {
    /**
     * 从问题中得到Query，并输出至文本文档
     * @param question
     * @return
     * @throws Exception
     */
    public static Map getQuery(String question) throws Exception{
        //切词
        Map<String, Double> queryMap = WordSegmentation.wordSegmentation(question);

        return queryMap;
    }
}
