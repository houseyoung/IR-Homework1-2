import Entity.TF;
import Entity.TFIDF;

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
    /**
     * 作业2
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //计时器开始
        Date startTime = new Date();

        //获取resources文件夹的路径
        String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";
        //设置文档文件夹的路径
        String docPath = resourcesPath + "Doc/";
        //设置问题文件的地址
        String questionFilePath = resourcesPath + "question.doc";
        //设置答案文件的地址
        String answerFilePath = resourcesPath + "answer.doc";
        //设置输出文件的路径
        String outputPath = resourcesPath + "output/";
        //设置输出Query文件的路径
        String queryPath = outputPath + "query/";

        //获取问题List
        List<String> questionList = GetQuestion.getQuestion(questionFilePath);

        //从问题List中依次获取Query，并将词、TF*IDF依次输出至TXT
        GetQuery.getQuery(questionList, queryPath);

        //计时器结束
        Date endTime = new Date();
        //输出消耗的时间
        System.out.println((endTime.getTime() - startTime.getTime()) / 1000F + "秒");
    }
}
