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
        String outputPath = resourcesPath + "output/";
        String outputFileName = "1.txt";

        String outputContext = WordSegmentation.wordSegmentation();

        WriteFile.contentToTxt(outputPath, outputFileName, outputContext);
    }
}
