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

        //获取resources文件夹的路径
        String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";
        String filePath = resourcesPath + "Doc/Doc1.doc";
        String outputPath = resourcesPath + "output/";
        String outputFileName = "1.txt";
        String outputContext = null;

        String indexStr = ReadFile.readWord(filePath);
        StringReader reader = new StringReader(indexStr);
        TokenStream ts = analyzer.tokenStream(indexStr, reader);
        Token t = ts.next();
        while (t != null) {
            if (outputContext == null){
                outputContext = t.termText() + "  ";
            } else{
                outputContext += t.termText() + "  ";
            }

            System.out.print(t.termText() + "  ");
            t = ts.next();
        }

        WriteFile.contentToTxt(outputPath, outputFileName, outputContext);
    }
}
