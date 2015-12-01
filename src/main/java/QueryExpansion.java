import java.util.List;
import java.util.Map;

/**
 * QueryExpansion
 *
 * @author: yangch
 * @time: 2015/12/1 9:22
 */
public class QueryExpansion {
    /**
     * 使用Rocchio公式进行基于用户相关反馈的查询扩展，返回查询扩展后的查询向量
     * @param termMapList
     * @param queryMap
     * @param questionNumber
     * @return
     * @throws Exception
     */
    public static Map queryExpansion(List<Map<String, Double>> termMapList, Map<String, Double> queryMap, int questionNumber) throws Exception{
        for (int docNumber = 1; docNumber <= termMapList.size(); docNumber++) {
            //依次从List中取出termMap
            Map<String, Double> termMap = termMapList.get(docNumber - 1);

            //若为相关文档，则将对应的数值相加
            if (docNumber == GetAnswer.getAnswerByQuestion(questionNumber)) {
                for (Map.Entry<String, Double> entry : queryMap.entrySet()) {
                    Double newValue = entry.getValue() + termMap.get(entry.getKey());
                    entry.setValue(newValue);
                }
            }

            //若为不相关文档，则将对应的数值减去不相关文档的平均数
            else {
                for (Map.Entry<String, Double> entry : queryMap.entrySet()) {
                    Double newValue = entry.getValue() - (termMap.get(entry.getKey()) / 29);
                    entry.setValue(newValue);
                }
            }
        }

        return queryMap;
    }
}
