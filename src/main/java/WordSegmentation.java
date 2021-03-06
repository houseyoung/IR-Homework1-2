import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 切词
 *
 * @author: yangch
 * @time: 2015/11/19 19:40
 */
public class WordSegmentation {
    /**
     * 对String进行切词，将切出的词、词的出现次数存储在Map中
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Map wordSegmentation(String str) throws Exception {
        Analyzer analyzer = new PaodingAnalyzer();

        StringReader reader = new StringReader(str);
        TokenStream ts = analyzer.tokenStream(str, reader);

        //将Term、出现次数记录在Map中，用LinkedHashMap可以实现按顺序输出
        Map<String, Double> termMap = new LinkedHashMap<String, Double>();

        CharTermAttribute offAtt = (CharTermAttribute) ts.addAttribute(CharTermAttribute.class);
        while (ts.incrementToken()) {
            if (termMap.get(offAtt.toString()) != null) {
                //若termMap中已存在此Term，则value自增1
                Double count = (double) termMap.get(offAtt.toString()).intValue() + 1;
                termMap.put(offAtt.toString(), count);
            }
            //否则value = 1
            else {
                termMap.put(offAtt.toString(), 1.00000);
            }
        }
        return termMap;
    }
}
