import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ReadFile
 *
 * @author: yangch
 * @time: 2015/11/19 16:10
 */
public class ReadFile {
    /**
     * 从Word文档中读取全部内容
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String readWord(String filePath) throws Exception {
        InputStream is = new FileInputStream(filePath);
        WordExtractor extractor = new WordExtractor(is);

        //获取Word文档中的全部文本
        String str = extractor.getText();

        //关闭输入流
        is.close();

        return str;
    }

    /**
     * 从Word文档中按行读取内容
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String[] readWordByLine(String filePath) throws Exception {
        InputStream is = new FileInputStream(filePath);
        WordExtractor extractor = new WordExtractor(is);

        //获取Word文档中的全部文本
        String str[] = extractor.getParagraphText();

        //关闭输入流
        is.close();

        return str;
    }

    /**
     * 列出目录中所有文件的文件名
     * @param path
     * @return
     * @throws Exception
     */
    public static List<String> listFileName(String path) throws Exception {
        File docPath = new File(path);
        //存储文件名
        List<String> fileNameList = new ArrayList<String>();

        if (!docPath.exists()) {
            System.out.println(path + "目录不存在");
            return null;
        }

        int filesLength = docPath.listFiles().length;

        for (int i = 1;i <= filesLength; ++i) {
            File file = new File(docPath + "" + i + ".doc");
            //如果不是目录，则存入fileNameList
            if (!file.isDirectory()) {
                fileNameList.add(file.getName());
            }
        }

        return fileNameList;
    }

    /**
     * 计算目录中的文件数
     * @param path
     * @return
     * @throws Exception
     */
    public static int countFileNumber(String path) throws Exception {
        File docPath = new File(path);

        if (!docPath.exists()) {
            System.out.println(path + "目录不存在");
            return 0;
        }

        int count = docPath.listFiles().length;

        return count;
    }
}
