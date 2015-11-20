import Entity.TF;
import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;

import java.io.StringReader;
import java.util.*;

/**
 * 切词类
 *
 * @author: yangch
 * @time: 2015/11/19 19:40
 */
public class WordSegmentation {
    /**
     * 切词，并将文档编号、切出的词、词的出现次数、TF存储在List<TF>中
     *
     * @param docPath
     * @return
     * @throws Exception
     */
    public static List<TF> wordSegmentation(String docPath) throws Exception {
        Analyzer analyzer = new PaodingAnalyzer();

        //读出文档列表
        List<String> fileNameList = ReadFile.listFileName(docPath);

        //记录文档编号
        int docNumber = 1;

        //将文档编号、Term、出现次数、TF记录在tfList中
        List<TF> tfList = new ArrayList<TF>();

        for (String fileName : fileNameList) {
            //获得文件地址
            String filePath = docPath + fileName;

            String indexStr = ReadFile.readWord(filePath);
            StringReader reader = new StringReader(indexStr);
            TokenStream ts = analyzer.tokenStream(indexStr, reader);

            //将Term、出现次数记录在Map中，用LinkedHashMap可以实现按顺序输出
            Map<String, Double> termMap = new LinkedHashMap<String, Double>();

            //Max(TF)
            Double maxTF = 1.00000;

            Token t = ts.next();
            while (t != null) {
                if (termMap.get(t.termText()) != null) {
                    //若termMap中已存在此Term，则value自增1
                    Double count = (double) termMap.get(t.termText()).intValue() + 1;

                    //更新MaxTF
                    if (count > maxTF) {
                        maxTF = count;
                    }

                    termMap.put(t.termText(), count);
                }
                //否则value = 1
                else {
                    termMap.put(t.termText(), 1.00000);
                }

                //读下一个Token
                t = ts.next();
            }

            //从termMap中取出Term、出现次数，然后根据Max(TF)算出TF，插入tfList中
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
