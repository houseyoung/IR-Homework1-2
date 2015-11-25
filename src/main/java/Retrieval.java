import Entity.DocSort;
import Entity.TF;

import java.util.*;

/**
 * Retrieval
 *
 * @author: yangch
 * @time: 2015/11/24 19:12
 */
public class Retrieval {
    /**
     * 根据问题进行检索，使用余弦法计算相似度，将文档编号、相似度存储至List<DocSort>
     *
     * @param question
     * @param tfList
     * @throws Exception
     */
    public static List<DocSort> getRetrieval(String question, List<TF> tfList) throws Exception {
        //从问题进行切词，并将切出的词、词的出现次数存入Map中
        Map<String, Double> queryMap = WordSegmentation.wordSegmentation(question);

        //将Term记录在Map中，用LinkedHashMap可以实现按顺序输出
        Map<String, Double> termMap = new LinkedHashMap<String, Double>();

        //将文档编号、相似度记录在List<DocSort>中
        List<DocSort> docSortList = new ArrayList<DocSort>();

        //将queryMap中的词插入termMap中，出现次数计为0
        for (Map.Entry<String, Double> entry : queryMap.entrySet()) {
            boolean flag = termMap.containsKey(entry.getKey());
            if (flag == false) {
                termMap.put(entry.getKey(), 0.0);
            }
        }

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

        //对30个文档依次进行检索
        for (int doc = 1; doc <= 30; doc++) {
            //将termMap复制一份，以存入本次检索的文档切出的词及词的出现次数
            Map<String, Double> nowMap = new LinkedHashMap<String, Double>();
            for (Map.Entry<String, Double> entry : termMap.entrySet()) {
                nowMap.put(entry.getKey(), entry.getValue());
            }

            for (TF tf : tfList) {
                //将本次检索的文档切出的词及词的出现次数，存入nowMap
                if (tf.getDocNumber() == doc) {
                    nowMap.put(tf.getTerm(), tf.getTermCount());
                }
            }

            //余弦法的三个参数
            Double Q2 = 0.0;
            Double D2 = 0.0;
            Double DQ = 0.0;

            //计算Q^2
            for (Map.Entry<String, Double> entry : queryMap.entrySet()) {
                Q2 += entry.getValue() * entry.getValue();
            }

            //计算D^2、D*Q
            for (Map.Entry<String, Double> entry : nowMap.entrySet()) {
                D2 += entry.getValue() * entry.getValue();
                DQ += entry.getValue() * queryMap.get(entry.getKey());
            }

            //使用余弦法计算相似度
            Double similarity = DQ / Math.sqrt(Q2 * D2);

            //将文档编号、相似度记录在List<DocSort>中
            DocSort docSort = new DocSort();
            docSort.setDocNumber(doc);
            docSort.setSimilarity(similarity);
            docSortList.add(docSort);
        }

        //实现Comparator，对docSortList按相似度进行排序
        Collections.sort(docSortList, new Comparator<DocSort>() {
            @Override
            public int compare(DocSort docSort1, DocSort docSort2) {
                return docSort2.getSimilarity().compareTo(docSort1.getSimilarity());
            }
        });

        return docSortList;
    }
}
