import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;

import java.io.StringReader;

/**
 * Test
 *
 * @author: yangch
 * @time: 2015/11/19 10:48
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Analyzer analyzer = new PaodingAnalyzer();

        String filePath = "E:\\1.doc";

        String indexStr = ReadFile.readWord(filePath);
        StringReader reader = new StringReader(indexStr);
        TokenStream ts = analyzer.tokenStream(indexStr, reader);
        Token t = ts.next();
        while (t != null) {
            System.out.print(t.termText()+"  ");
            t = ts.next();
        }
    }
}
