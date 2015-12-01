import java.util.ArrayList;
import java.util.List;

/**
 * GetQuestion
 *
 * @author: yangch
 * @time: 2015/11/21 10:40
 */
public class GetQuestion {
    /**
     * 从文档中按行读取问题，并存储在List中
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static List<String> getQuestion(String filePath) throws Exception {
        //获取问题，存储在List中
        List<String> questionList = new ArrayList<String>();

        //按行读取问题，存入String数组
        String questions[] = ReadFile.readWordByLine(filePath);

        for (String question : questions) {
            //若该行不为换行符，存入问题List
            if (!question.equals("\r\n")) {
                //去除换行符
                question = question.replaceAll("\r|\n", "");
                //存入问题List
                questionList.add(question);
            }
        }
        return questionList;
    }
}
