import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * IRHomework2
 *
 * @author: yangch
 * @time: 2015/11/21 10:13
 */
public class IRHomework2 {
    public static void main(String[] args) throws Exception {
        //获取resources文件夹的路径
        String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";
        //设置输入文件的地址
        String questionFilePath = resourcesPath + "question.doc";
        //设置输出文件的路径
        String outputPath = resourcesPath + "output/";
        //设置输出文件名
        String outputFile = "answer.txt";

        //获取问题List
        List<String> questionList = GetQuestion.getQuestion(questionFilePath);
    }
}
