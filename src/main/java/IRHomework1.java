import Entity.TF;
import Entity.TFIDF;
import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;

import java.util.List;

/**
 * IRHomework1
 *
 * @author: yangch
 * @time: 2015/11/19 10:48
 */
public class IRHomework1 {
    public static void main(String[] args) throws Exception {
        //获取resources文件夹的路径
        String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";
        //设置输入文件夹的路径
        String docPath = resourcesPath + "Doc/";
        //设置输出文件夹的路径
        String outputPath = resourcesPath + "output/";

        //配置输出文件名
        String outputDocIndex = "docIndex.txt";
        String outputDocIndexTF = "docIndex(tf).txt";
        String outputDocIndexTFIDF = "docIndex(tfidf).txt";
        String outputDocInvertTF = "docInvert(tf).txt";
        String outputDocInvertTFIDF = "docInvert(tfidf).txt";

        //对输入文件夹下所有文件进行切词，并获取每一个词的TF
        List<TF> tfList = WordSegmentation.wordSegmentation(docPath);
        //获取每一个词的TF*IDF
        List<TFIDF> tfidfList = CalcTFIDF.calcTFIDF(tfList, docPath);

        //输出DocIndex的内容
        Output.outputDocIndex(tfList, outputPath, outputDocIndex);
        //输出DocIndexTF的内容
        Output.outputDocIndexTF(tfList, outputPath, outputDocIndexTF);
        //输出DocIndexTFIDF的内容
        Output.outputDocIndexTFIDF(tfidfList, outputPath, outputDocIndexTFIDF);
        //输出DocInvertTF的内容
        Output.outputDocInvertTF(tfList, outputPath, outputDocInvertTF);
        //输出DocInvertTFIDF的内容
        Output.outputDocInvertTFIDF(tfidfList, outputPath, outputDocInvertTFIDF);
    }
}