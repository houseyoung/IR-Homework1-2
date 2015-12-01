import java.util.*;

/**
 * CalcSimilarity
 *
 * @author: yangch
 * @time: 2015/11/24 19:12
 */
public class CalcSimilarity {
    /**
     * 根据查询向量、文档向量进行检索，使用余弦法计算相似度
     * @param queryMap
     * @param termMap
     * @return
     * @throws Exception
     */
    public static Double calcSimilarity(Map<String, Double> queryMap, Map<String, Double> termMap) throws Exception {
        //余弦法的三个参数
        Double Q2 = 0.0;
        Double D2 = 0.0;
        Double DQ = 0.0;

        //计算Q^2
        for (Map.Entry<String, Double> entry : queryMap.entrySet()) {
            Q2 += entry.getValue() * entry.getValue();
        }

        //计算D^2、D*Q
        for (Map.Entry<String, Double> entry : termMap.entrySet()) {
            D2 += entry.getValue() * entry.getValue();
            DQ += entry.getValue() * queryMap.get(entry.getKey());
        }

        //使用余弦法计算相似度
        Double similarity = DQ / Math.sqrt(Q2 * D2);

        return similarity;
    }
}
