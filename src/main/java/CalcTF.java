import Entity.TF;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CalcTF
 *
 * @author: yangch
 * @time: 2015/11/21 13:51
 */
public class CalcTF {
    /**
     * 从一组Word文档中切词，然后计算TF，将文档编号、切出的词、词的出现次数、TF存储在List<TF>中
     *
     * @param docPath
     * @return
     * @throws Exception
     */
    public static List<TF> calcTF(String docPath) throws Exception {
        //获取文档列表
        List<String> fileNameList = ReadFile.listFileName(docPath);

        //记录文档编号
        int docNumber = 1;

        //将文档编号、Term、出现次数、TF存储在tfList中
        List<TF> tfList = new ArrayList<TF>();

        for (String fileName : fileNameList) {
            //获得文档地址
            String filePath = docPath + fileName;

            //获取文档内容
            String str = ReadFile.readWord(filePath);

            //切词
            Map<String, Double> termMap = WordSegmentation.wordSegmentation(str);

            //记录最多出现次数
            Double maxTF = 1.00;

            //从termMap中依次取出出现次数，找出最多出现次数
            for (Map.Entry<String, Double> entry : termMap.entrySet()) {
                if (entry.getValue() > maxTF) {
                    maxTF = entry.getValue();
                }
            }

            //从termMap中取出Term、出现次数，然后根据最多出现次数算出TF，存入tfList
            for (Map.Entry<String, Double> entry : termMap.entrySet()) {
                TF tf = new TF();
                tf.setDocNumber(docNumber);
                tf.setTerm(entry.getKey());
                tf.setTermCount(entry.getValue());
                tf.setTF(entry.getValue() / maxTF);

                tfList.add(tf);
            }

            //文档编号自增
            docNumber++;
        }

        return tfList;
    }

    /**
     * 对已经切好的Map计算TF，将切出的词、词的出现次数、TF存储在List<TF>中
     *
     * @param map
     * @return
     * @throws Exception
     */
    public static List<TF> calcTF(Map<String, Double> map) throws Exception {
        //记录最多出现次数
        Double maxTF = 1.00;

        //将文档编号、Term、出现次数、TF存储在tfList中
        List<TF> tfList = new ArrayList<TF>();

        //从map中依次取出出现次数，找出最多出现次数
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (entry.getValue() > maxTF) {
                maxTF = entry.getValue();
            }
        }

        //从termMap中取出Term、出现次数，然后根据最多出现次数算出TF，存入tfList
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            TF tf = new TF();
            //文档编号设为0
            tf.setDocNumber(0);
            tf.setTerm(entry.getKey());
            tf.setTermCount(entry.getValue());
            tf.setTF(entry.getValue() / maxTF);

            tfList.add(tf);
        }

        return tfList;
    }
}
