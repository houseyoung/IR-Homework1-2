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

        //将Term、出现次数记录在Map中，用LinkedHashMap可以实现按顺序输出
        Map<String, Double> termMap = new LinkedHashMap<String, Double>();

        //将文档编号、相似度记录在List<DocSort>中
        List<DocSort> docSortList = new ArrayList<DocSort>();

        //对30个文档依次进行检索
        for (int doc = 1; doc <= 30; doc++) {
            //读取本次检索的文档切出的词及词的出现次数，存入termMap
            for (TF tf : tfList) {
                if (tf.getDocNumber() == doc) {
                    termMap.put(tf.getTerm(), tf.getTermCount());
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

            //计算D^2
            for (Map.Entry<String, Double> entry : termMap.entrySet()) {
                D2 += entry.getValue() * entry.getValue();
            }

            //计算D*Q
            for (Map.Entry<String, Double> entry1 : termMap.entrySet()) {
                for (Map.Entry<String, Double> entry2 : queryMap.entrySet()) {
                    if (entry1.getKey() == entry2.getKey()) {
                        DQ += entry1.getValue() * entry2.getValue();
                    }
                }
            }

            //使用余弦法计算相似度
            Double similarity = DQ / Math.sqrt(Q2 * D2);

            //将文档编号、相似度记录在List<DocSort>中
            DocSort docSort = new DocSort();
            docSort.setDocNumber(doc);
            docSort.setSimilarity(similarity);
            docSortList.add(docSort);

            //清空termMap
            termMap = new LinkedHashMap<String, Double>();
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
