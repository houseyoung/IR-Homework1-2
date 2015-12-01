import Entity.TF;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * VSM
 *
 * @author: yangch
 * @time: 2015/12/1 9:53
 */
public class VSM {
    /**
     * 根据question及tfList建立向量空间模型中的查询向量
     * @param question
     * @param tfList
     * @throws Exception
     */
    public static Map queryVector(String question, List<TF> tfList) throws Exception {
        //从问题进行切词，并将切出的词、词的出现次数存入queryMap中
        Map<String, Double> queryMap = WordSegmentation.wordSegmentation(question);

        //将Term记录在termMap中，用LinkedHashMap可以实现按顺序输出
        Map<String, Double> termMap = new LinkedHashMap<String, Double>();

        //将所有文档切出的词存入termMap，出现次数计为0
        for (TF tf : tfList) {
            termMap.put(tf.getTerm(), 0.0);
        }

        //将termMap中存在的而queryMap中不存在的词插入queryMap中，出现次数计为0
        for (Map.Entry<String, Double> entry : termMap.entrySet()) {
            boolean flag = queryMap.containsKey(entry.getKey());
            if (flag == false) {
                queryMap.put(entry.getKey(), 0.0);
            }
        }

        return queryMap;
    }

    /**
     * 根据queryMap、tfList及文档编号建立向量空间模型中的第X个文档的向量
     * @param queryMap
     * @param tfList
     * @param doc
     * @return
     * @throws Exception
     */
    public static Map documentVector(Map<String, Double> queryMap, List<TF> tfList, int doc) throws Exception {
        //将queryMap中所有的value都置为0，存为termMap
        Map<String, Double> termMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : queryMap.entrySet()) {
            termMap.put(entry.getKey(), 0.0);
        }

        for (TF tf : tfList) {
            //将本次检索的文档切出的词及词的出现次数，存入termMap
            if (tf.getDocNumber() == doc) {
                termMap.put(tf.getTerm(), tf.getTermCount());
            }
        }

        return termMap;
    }
}
