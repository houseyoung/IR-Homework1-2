import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.*;

/**
 * ReadFile
 *
 * @author: yangch
 * @time: 2015/11/19 16:10
 */
public class ReadFile {
    /**
     * 从Word文档中读取内容
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
}
