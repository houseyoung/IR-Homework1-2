import Entity.TF;
import Entity.TFIDF;
import org.apache.poi.hssf.record.formula.functions.Int;

import java.util.LinkedHashMap;
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
     * 从问题List中依次获取Query，并将词、词的出现次数依次输出至TXT
     * @param questionList
     * @param queryPath
     * @throws Exception
     */
    public static void getQuery(List<String> questionList, String queryPath) throws Exception {
        //问题计数
        int countQuestion = 1;

        for (String question : questionList) {
            //从问题进行切词，并将切出的词、词的出现次数存入Map中
            Map<String, Integer> queryMap = WordSegmentation.wordSegmentation(question);
            //输出词、词的出现次数至TXT文档
            Output.outputQuery(queryMap, queryPath, "Query" + countQuestion + ".txt");

            //问题计数自增
            countQuestion++;
        }
    }
}
