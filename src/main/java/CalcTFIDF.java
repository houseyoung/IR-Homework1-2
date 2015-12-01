import Entity.TF;
import Entity.TFIDF;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 计算TF*IDF
 *
 * @author: yangch
 * @time: 2015/11/20 18:34
 */
public class CalcTFIDF {
    /**
     * 计算TF*IDF，并将文档编号、词、TF*IDF存储在List<TFIDF>中
     *
     * @param TFList
     * @param docPath
     * @throws Exception
     */
    public static List<TFIDF> calcTFIDF(List<TF> TFList, String docPath) throws Exception {
        //将Term、DF存储在Map中，用LinkedHashMap可以实现按顺序输出
        Map<String, Double> dfMap = new LinkedHashMap<String, Double>();
        //将Term、IDF存储在Map中，用LinkedHashMap可以实现按顺序输出
        Map<String, Double> idfMap = new LinkedHashMap<String, Double>();

        //计算DF，存入dfMap
        for (TF tf : TFList) {
            //若该词已出现过，则DF值加1
            if (dfMap.containsKey(tf.getTerm())) {
                Double df = dfMap.get(tf.getTerm());
                dfMap.put(tf.getTerm(), df + 1.0);
            }
            //若该词为第一次出现，则DF值设为1
            else {
                dfMap.put(tf.getTerm(), 1.0);
            }
        }

        //读出文档数目
        int count = ReadFile.countFileNumber(docPath);

        //计算IDF，存入idfMap
        for (Map.Entry<String, Double> entry : dfMap.entrySet()) {
            Double idf = Math.log10(count / entry.getValue());
            idfMap.put(entry.getKey(), idf);
        }

        //计算TF*IDF，存入tfidfList
        List<TFIDF> tfidfList = new ArrayList<TFIDF>();
        for (TF tf : TFList) {
            Double tfIDF = tf.getTF() * idfMap.get(tf.getTerm());

            TFIDF tfidf = new TFIDF();
            tfidf.setDocNumber(tf.getDocNumber());
            tfidf.setTerm(tf.getTerm());
            tfidf.setTfIDF(tfIDF);

            tfidfList.add(tfidf);
        }

        return tfidfList;
    }
}
