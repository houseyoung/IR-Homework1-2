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
     * 从文档中按行读取问题，并存入List
     * @param filePath
     * @return
     * @throws Exception
     */
    public static List<String> getQuestion(String filePath) throws Exception{
        //获取问题，记录在List中
        List<String> questionList = new ArrayList<String>();

        //按行读取问题，存入String数组中
        String questions[] = ReadFile.readWordByLine(filePath);

        for(String question : questions){
            //不为换行符，才执行操作
            if (!question.equals("\r\n")) {
                //去除换行符
                question = question.replaceAll("\r|\n", "");
                //存入questionList
                questionList.add(question);
            }
        }
        return questionList;
    }
}
