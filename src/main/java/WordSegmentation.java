import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;

import java.io.StringReader;
import java.util.*;

/**
 * WordSegmentation
 * 切词类
 *
 * @author: yangch
 * @time: 2015/11/19 19:40
 */
public class WordSegmentation {
    public static String wordSegmentation () throws Exception {
        Analyzer analyzer = new PaodingAnalyzer();

        String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";
        String filePath = resourcesPath + "Doc/Doc1.doc";
        String outputContext = null;

        String indexStr = ReadFile.readWord(filePath);
        StringReader reader = new StringReader(indexStr);
        TokenStream ts = analyzer.tokenStream(indexStr, reader);
        //将Token、出现次数记录在Map中
        //LinkedHashMap可以实现按顺序输出
        Map<String, Integer> tokenMap = new LinkedHashMap<String, Integer>();

        Token t = ts.next();
        while (t != null) {
            //若TokenMap中已存在此Token，则value自增1
            if (tokenMap.get(t.termText()) != null) {
                int count = tokenMap.get(t.termText()).intValue() + 1;
                tokenMap.put(t.termText(), count);
            } else {
                tokenMap.put(t.termText(), 1);
            }

            //读下一个Token
            t = ts.next();
        }

        //输出到outputContext
        for (Map.Entry<String, Integer> entry : tokenMap.entrySet()) {
            if (outputContext == null){
                outputContext = entry.getValue() + "  " + entry.getKey() + "\n";
            } else{
                outputContext += entry.getValue() + "  " + entry.getKey() + "\n";
            }

        }

        return outputContext;
    }
}
