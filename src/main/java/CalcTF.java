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
     * @param docPath
     * @return
     * @throws Exception
     */
    public static List<TF> calcTF(String docPath) throws Exception {
        //读出文档列表
        List<String> fileNameList = ReadFile.listFileName(docPath);

        //记录文档编号
        int docNumber = 1;

        //将文档编号、Term、出现次数、TF记录在tfList中
        List<TF> tfList = new ArrayList<TF>();

        for (String fileName : fileNameList) {
            //获得文档地址
            String filePath = docPath + fileName;

            //获取文档内容
            String str = ReadFile.readWord(filePath);

            //切词
            Map<String, Double> termMap = WordSegmentation.wordSegmentation(str);

            //最多出现次数
            Double maxTF = 1.00000;

            //从termMap中依次取出出现次数，找出最多出现次数
            for (Map.Entry<String, Double> entry : termMap.entrySet()) {
                if (entry.getValue() > maxTF) {
                    maxTF = entry.getValue();
                }
            }

            //从termMap中取出Term、出现次数，然后根据最多出现次数算出TF，插入tfList中
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
}
