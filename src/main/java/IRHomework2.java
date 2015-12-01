import Entity.DocSort;
import Entity.TF;

import java.util.*;

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

        //获取问题总数
        int questionCount = questionList.size();

        //获取文档总数
        int docCount = ReadFile.countFileNumber(docPath);

        //对输入文件夹下所有文件进行切词，将文档编号、切出的词、词的出现次数、TF存储在List<TF>中
        List<TF> tfList = CalcTF.calcTF(docPath);

        //Rank倒数的总和
        Double rankReciprocalCount = 0.0;

        //对问题依次进行检索
        for (int i = 0; i < questionCount; i++) {
            //获取第i个问题
            String question = questionList.get(i);

            //将文档编号、相似度记录在List<DocSort>中
            List<DocSort> docSortList = new ArrayList<DocSort>();

            //建立查询向量
            Map<String, Double> queryMap = VSM.queryVector(question, tfList);

            for (int docNumber = 1; docNumber <= docCount; docNumber++) {
                //对每一个文档计算文档向量
                Map<String, Double> termMap = VSM.documentVector(queryMap, tfList, docNumber);

                //根据查询向量及文档向量进行检索，使用余弦法计算相似度
                Double similarity = CalcSimilarity.calcSimilarity(queryMap, termMap);

                //将文档编号、相似度记录在DocSort实体中，再存储至docSortList
                DocSort docSort = new DocSort();
                docSort.setDocNumber(docNumber);
                docSort.setSimilarity(similarity);
                docSortList.add(docSort);
            }

            //实现Comparator，对docSortList按相似度进行排序
            Collections.sort(docSortList, new Comparator<DocSort>() {
                @Override
                public int compare(DocSort docSort1, DocSort docSort2) {
                    return docSort2.getSimilarity().compareTo(docSort1.getSimilarity());
                }
            });

            //获得当前问题的答案
            Integer answer = answerList.get(i);

            //获取Rank值
            int rank = CalcRank.calcRank(docSortList, answer);

            System.out.println(rank);
            
            //计算Rank倒数的总和
            rankReciprocalCount += 1 / (double) rank;

            //输出Result
            Output.outputResult(docSortList, i + 1, question, resultPath, "result(vectoral)" + (i + 1) + ".txt");
        }

        //计算MRR值
        Double MRR = rankReciprocalCount / questionCount;
        System.out.println(MRR);

        //计时器结束
        Date endTime = new Date();
        //输出消耗的时间
        System.out.println((endTime.getTime() - startTime.getTime()) / 1000F + "秒");
    }
}
