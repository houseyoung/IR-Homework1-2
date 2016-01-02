import Entity.TF;
import Entity.TFIDF;

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
     * 根据question及tfidfList建立向量空间模型中的查询向量
     *
     * @param question
     * @param tfidfList
     * @throws Exception
     */
    public static Map queryVector(String question, List<TFIDF> tfidfList) throws Exception {
        //获取resources文件夹的路径
        String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";
        //设置文档文件夹的路径
        String docPath = resourcesPath + "Doc/";

        //从问题进行切词，并将切出的词、词的出现次数存入queryMap中
        Map<String, Double> queryMap = WordSegmentation.wordSegmentation(question);

        //对Query计算TF值
        List<TF> queryTFList = CalcTF.calcTF(queryMap);

        //对Query计算TFIDF值
        List<TFIDF> queryTFIDFList = CalcTFIDF.calcTFIDF(queryTFList, docPath);

        //将TFIDF存入queryMap
        for (TFIDF tfidf : queryTFIDFList) {
            queryMap.put(tfidf.getTerm(), tfidf.getTfIDF());
        }

        //将Term记录在termMap中，用LinkedHashMap可以实现按顺序输出
        Map<String, Double> termMap = new LinkedHashMap<String, Double>();

        //将所有文档切出的词存入termMap，tf*idf计为0
        for (TFIDF tfidf : tfidfList) {
            termMap.put(tfidf.getTerm(), 0.0);
        }

        //将termMap中存在的而queryMap中不存在的词插入queryMap中，tf*idf计为0
        for (Map.Entry<String, Double> entry : termMap.entrySet()) {
            boolean flag = queryMap.containsKey(entry.getKey());
            if (flag == false) {
                queryMap.put(entry.getKey(), 0.0);
            }
        }

        return queryMap;
    }

    /**
     * 根据queryMap、tfidfList及文档编号建立向量空间模型中的第X个文档的向量
     *
     * @param queryMap
     * @param tfidfList
     * @param docNumber
     * @return
     * @throws Exception
     */
    public static Map documentVector(Map<String, Double> queryMap, List<TFIDF> tfidfList, int docNumber) throws Exception {
        //将queryMap中所有的tf*idf都置为0，存入termMap
        Map<String, Double> termMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : queryMap.entrySet()) {
            termMap.put(entry.getKey(), 0.0);
        }

        for (TFIDF tfidf : tfidfList) {
            //将本次检索的文档切出的词及tf*idf，存入termMap
            if (tfidf.getDocNumber() == docNumber) {
                termMap.put(tfidf.getTerm(), tfidf.getTfIDF());
            }
        }

        return termMap;
    }
}
