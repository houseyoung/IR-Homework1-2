import Entity.DocSort;
import Entity.TF;

import java.util.Date;
import java.util.List;

/**
 * IRHomework2
 *
 * @author: yangch
 * @time: 2015/11/21 10:13
 */
public class IRHomework2 {
    /**
     * 作业2
     *
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
        //设置结果文件夹的路径
        String resultPath = resourcesPath + "output/result/";

        //获取问题List
        List<String> questionList = GetQuestion.getQuestion(questionFilePath);

        //获取答案List
        List<Integer> answerList = GetAnswer.getAnswer();

        //获取问题数
        int questionNumber = questionList.size();

        //对输入文件夹下所有文件进行切词，将文档编号、切出的词、词的出现次数、TF存储在List<TF>中
        List<TF> tfList = CalcTF.calcTF(docPath);

        //Rank倒数的总和
        Double rankReciprocalCount = 0.0;

        //对问题依次进行检索
        for (int i = 0; i < questionNumber; i++) {
            //第i个问题
            String question = questionList.get(i);

            //根据问题进行检索，使用余弦法计算相似度，将文档编号、相似度存储至List<DocSort>
            List<DocSort> docSortList = Retrieval.getRetrieval(question, tfList);

            //获得当前问题的答案
            Integer answer = answerList.get(i);

            //获取Rank值
            int rank = CalcRank.calcRank(docSortList, answer);

            //计算Rank倒数的总和
            rankReciprocalCount += 1 / (double) rank;

            //输出Result
            Output.outputResult(docSortList, i + 1, question, resultPath, "result(vectoral)" + (i + 1) + ".txt");
        }

        //计算MRR值
        Double MRR = rankReciprocalCount / questionNumber;
        System.out.println(MRR);

        //计时器结束
        Date endTime = new Date();
        //输出消耗的时间
        System.out.println((endTime.getTime() - startTime.getTime()) / 1000F + "秒");
    }
}
