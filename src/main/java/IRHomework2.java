import java.util.Date;
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
        //计时器开始
        Date startTime = new Date();

        //获取resources文件夹的路径
        String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";
        //设置问题文件的地址
        String questionFilePath = resourcesPath + "question.doc";
        //设置输出文件的路径
        String outputPath = resourcesPath + "output/";
        //设置输出Query文件的路径
        String queryPath = outputPath + "query/";
        //设置输出答案的文件名
        String outputAnswerFile = "answer.txt";

        //获取问题List
        List<String> questionList = GetQuestion.getQuestion(questionFilePath);

        //问题计数
        int countQuestion = 1;

        for (String question : questionList){
            //从问题中得到Query
            Map<String, Double> queryMap = GetQuery.getQuery(question);

            //Query中的词计数
            int countQueryWord = 1;

            //将得到的Query存入文本文档
            for (Map.Entry<String, Double> entry : queryMap.entrySet()){
                if (countQueryWord == 1) {
                    WriteFile.writeToTxt(queryPath, "Query" + countQuestion + ".txt", entry.getKey());
                } else {
                    WriteFile.contentToTxt(queryPath, "Query" + countQuestion + ".txt", entry.getKey());
                }
                //Query中的词计数自增
                countQueryWord++;
            }

            //问题计数自增
            countQuestion++;
        }

        //计时器结束
        Date endTime = new Date();
        //输出消耗的时间
        System.out.println((endTime.getTime() - startTime.getTime()) / 1000F + "秒");
    }
}
