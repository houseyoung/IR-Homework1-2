import Entity.DocSort;
import Entity.TF;
import Entity.TFIDF;

import java.util.*;

/**
 * 输出结果类
 *
 * @author: yangch
 * @time: 2015/11/20 15:05
 */
public class Output {
    /**
     * 输出DocIndex
     * @param tfList
     * @param outputPath
     * @param outputFile
     * @throws Exception
     */
    public static void outputDocIndex(List<TF> tfList, String outputPath, String outputFile) throws Exception {
        //使用StringBuilder，使大量字符串拼接时减少内存及时间占用
        StringBuilder outputContext = new StringBuilder();

        for (TF tf : tfList) {
            //保留5位小数
            String count = String.format("%.5f", tf.getTermCount());

            //输出到outputContext中
            outputContext.append(tf.getDocNumber() + "\t" + count + " " + tf.getTerm() + "\n");
        }

        //将StringBuilder转换为String
        String str = outputContext.toString();

        //写入内容至TXT文件
        WriteFile.writeToTxt(outputPath, outputFile, str);
    }

    /**
     * 输出DocIndexTF
     * @param tfList
     * @param outputPath
     * @param outputFile
     * @throws Exception
     */
    public static void outputDocIndexTF(List<TF> tfList, String outputPath, String outputFile) throws Exception {
        //使用StringBuilder，使大量字符串拼接时减少内存及时间占用
        StringBuilder outputContext = new StringBuilder();

        for (TF tf : tfList) {
            //保留5位小数
            String tfFormat = String.format("%.5f", tf.getTF());

            //输出到outputContext中
            outputContext.append(tf.getDocNumber() + "\t" + tfFormat + " " + tf.getTerm() + "\n");
        }

        //将StringBuilder转换为String
        String str = outputContext.toString();
        //写入内容至TXT文件
        WriteFile.writeToTxt(outputPath, outputFile, str);
    }

    /**
     * 输出DocIndexTFIDF
     * @param tfidfList
     * @param outputPath
     * @param outputFile
     * @throws Exception
     */
    public static void outputDocIndexTFIDF(List<TFIDF> tfidfList, String outputPath, String outputFile) throws Exception {
        //使用StringBuilder，使大量字符串拼接时减少内存及时间占用
        StringBuilder outputContext = new StringBuilder();

        for (TFIDF tfidf : tfidfList) {
            //保留5位小数
            String tfidfFormat = String.format("%.5f", tfidf.getTfIDF());

            //输出到outputContext中
            outputContext.append(tfidf.getDocNumber() + "\t" + tfidfFormat + " " + tfidf.getTerm() + "\n");
        }

        //将StringBuilder转换为String
        String str = outputContext.toString();

        //写入内容至TXT文件
        WriteFile.writeToTxt(outputPath, outputFile, str);
    }

    /**
     * 输出DocInvertTF
     * @param tfList
     * @param outputPath
     * @param outputFile
     * @throws Exception
     */
    public static void outputDocInvertTF(List<TF> tfList, String outputPath, String outputFile) throws Exception {
        //使用StringBuilder，使大量字符串拼接时减少内存及时间占用
        StringBuilder outputContext = new StringBuilder();

        //实现Comparator，对tfList按Term进行排序
        Collections.sort(tfList, new Comparator<TF>() {
            @Override
            public int compare(TF tf1, TF tf2) {
                return tf1.getTerm().compareTo(tf2.getTerm());
            }
        });

        for (TF tf : tfList) {
            //保留5位小数
            String tfFormat = String.format("%.5f", tf.getTF());

            //输出到outputContext中
            outputContext.append(tf.getTerm() + "\t" + tfFormat + " " + tf.getDocNumber() + "\n");
        }

        //将StringBuilder转换为String
        String str = outputContext.toString();

        //写入内容至TXT文件
        WriteFile.writeToTxt(outputPath, outputFile, str);
    }

    /**
     * 输出DocInvertTFIDF
     * @param tfidfList
     * @param outputPath
     * @param outputFile
     * @throws Exception
     */
    public static void outputDocInvertTFIDF(List<TFIDF> tfidfList, String outputPath, String outputFile) throws Exception {
        //使用StringBuilder，使大量字符串拼接时减少内存及时间占用
        StringBuilder outputContext = new StringBuilder();

        //实现Comparator，对tfidfList按Term进行排序
        Collections.sort(tfidfList, new Comparator<TFIDF>() {
            @Override
            public int compare(TFIDF tfidf1, TFIDF tfidf2) {
                return tfidf1.getTerm().compareTo(tfidf2.getTerm());
            }
        });

        for (TFIDF tfidf : tfidfList) {
            //保留5位小数
            String tfidfFormat = String.format("%.5f", tfidf.getTfIDF());

            //输出到outputContext中
            outputContext.append(tfidf.getTerm() + "\t" + tfidfFormat + " " + tfidf.getDocNumber() + "\n");
        }

        //将StringBuilder转换为String
        String str = outputContext.toString();

        //写入内容至TXT文件
        WriteFile.writeToTxt(outputPath, outputFile, str);
    }


    /**
     * 输出Result
     * @param docSortList
     * @param outputPath
     * @param outputFile
     * @throws Exception
     */
    public static void outputResult(List<DocSort> docSortList, int i, String question,
                                    String outputPath, String outputFile) throws Exception {
        //获取resources文件夹的路径
        String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";
        //设置文档文件夹的路径
        String docPath = resourcesPath + "Doc/";

        //设置第一行文字
        String outputContext = "Request " + i + ": " + question + "\n";

        //若为第10个问题或第20个问题，则输出详细答案
        if (i == 10 || i == 20) {
            for (DocSort docSort : docSortList) {
                //获得文档的前20个字符
                String docFirst20 = ReadFile.readWordFirst20(docPath + "Doc" + docSort.getDocNumber() + ".doc");

                //保留5位小数
                String similarity = String.format("%.5f", docSort.getSimilarity());

                //输出到outputContext中
                outputContext += docSort.getDocNumber() + "\t" + similarity + "\t" + docFirst20 + "\n";
            }
        } else {
            for (DocSort docSort : docSortList) {
                //保留5位小数
                String similarity = String.format("%.5f", docSort.getSimilarity());

                //输出到outputContext中
                outputContext += docSort.getDocNumber() + "\t" + similarity + "\n";
            }
        }

        //写入内容至TXT文件
        WriteFile.writeToTxt(outputPath, outputFile, outputContext);
    }

}