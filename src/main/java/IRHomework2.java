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
        long startTime = System.currentTimeMillis();

        //获取resources文件夹的路径
        String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";
        //设置文档文件夹的路径
        String docPath = resourcesPath + "Doc/";
        //设置问题文件的地址
        String questionFilePath = resourcesPath + "question.doc";
        //设置查询扩展前检索结果输出文件夹的路径
        String resultPathBeforeQE = resourcesPath + "output/result/beforeQE/";
        //设置查询扩展后检索结果输出文件夹的路径
        String resultPathAfterQE = resourcesPath + "output/result/afterQE/";

        //获取问题列表
        List<String> questionList = GetQuestion.getQuestion(questionFilePath);

        //获取问题总数
        int questionCount = questionList.size();

        //获取文档总数
        int docCount = ReadFile.countFileNumber(docPath);

        //对输入文件夹下所有文件进行切词，将文档编号、切出的词、词的出现次数、TF存储在List<TF>中
        List<TF> tfList = CalcTF.calcTF(docPath);

        //查询扩展前Rank倒数的总和
        Double rankReciprocalCountBeforeQE = 0.0;

        //查询扩展后Rank倒数的总和
        Double rankReciprocalCountAfterQE = 0.0;

        //对问题依次进行检索
        for (int questionNumber = 0; questionNumber < questionCount; questionNumber++) {
            //获取问题内容
            String question = questionList.get(questionNumber);

            //将查询扩展前文档编号、相似度记录在List中
            List<DocSort> docSortListBeforeQE = new ArrayList<DocSort>();

            //将查询扩展后的文档编号、相似度记录在List中
            List<DocSort> docSortListAfterQE = new ArrayList<DocSort>();

            //获取查询扩展前的查询向量
            Map<String, Double> queryMapBeforeQE = VSM.queryVector(question, tfList);

            //将所有文档的文档向量存入List中，查询扩展时使用
            List<Map<String, Double>> termMapList = new ArrayList<Map<String, Double>>();

            for (int docNumber = 1; docNumber <= docCount; docNumber++) {
                //对每一个文档依次计算文档向量
                Map<String, Double> termMap = VSM.documentVector(queryMapBeforeQE, tfList, docNumber);

                //将该文档向量存储至文档向量List中
                termMapList.add(termMap);

                //根据查询扩展前的查询向量及文档向量进行检索，使用余弦法计算相似度
                Double similarity = CalcSimilarity.calcSimilarity(queryMapBeforeQE, termMap);

                //将文档编号、相似度记录在DocSort实体中，再存入docSortListBeforeQE中
                DocSort docSort = new DocSort();
                docSort.setDocNumber(docNumber);
                docSort.setSimilarity(similarity);
                docSortListBeforeQE.add(docSort);
            }

            //获取查询扩展后的查询向量
            Map<String, Double> queryMapAfterQE = QueryExpansion.queryExpansion(termMapList, queryMapBeforeQE, questionNumber);

            for (int docNumber = 1; docNumber <= docCount; docNumber++) {
                //从文档向量List中依次取出文档向量
                Map<String, Double> termMap = termMapList.get(docNumber - 1);

                //根据查询扩展后的查询向量及文档向量进行检索，使用余弦法计算相似度
                Double similarity = CalcSimilarity.calcSimilarity(queryMapAfterQE, termMap);

                //将文档编号、相似度记录在DocSort实体中，再存入docSortListAfterQE
                DocSort docSort = new DocSort();
                docSort.setDocNumber(docNumber);
                docSort.setSimilarity(similarity);
                docSortListAfterQE.add(docSort);
            }

            //实现Comparator，对docSortListBeforeQE按相似度进行排序
            Collections.sort(docSortListBeforeQE, new Comparator<DocSort>() {
                @Override
                public int compare(DocSort docSort1, DocSort docSort2) {
                    return docSort2.getSimilarity().compareTo(docSort1.getSimilarity());
                }
            });

            //实现Comparator，对docSortListAfterQE按相似度进行排序
            Collections.sort(docSortListAfterQE, new Comparator<DocSort>() {
                @Override
                public int compare(DocSort docSort1, DocSort docSort2) {
                    return docSort2.getSimilarity().compareTo(docSort1.getSimilarity());
                }
            });

            //获得当前问题的答案
            int answer = GetAnswer.getAnswerByQuestion(questionNumber);

            //对查询扩展前的结果获取Rank值
            int rankBeforeQE = CalcRank.calcRank(docSortListBeforeQE, answer);

            //对查询扩展后的结果获取Rank值
            int rankAfterQE = CalcRank.calcRank(docSortListAfterQE, answer);

            //打印Rank值
            System.out.println(rankBeforeQE + "\t" + rankAfterQE);

            //计算查询扩展前的结果Rank倒数的总和
            rankReciprocalCountBeforeQE += 1 / (double)rankBeforeQE;

            //计算查询扩展后的结果Rank倒数的总和
            rankReciprocalCountAfterQE += 1 / (double)rankAfterQE;

            //输出查询扩展前的Result
            Output.outputResult(docSortListBeforeQE, questionNumber + 1, question, resultPathBeforeQE, "result(vectoral)" + (questionNumber + 1) + ".txt");

            //输出查询扩展后的Result
            Output.outputResult(docSortListAfterQE, questionNumber + 1, question, resultPathAfterQE, "result(vectoral)" + (questionNumber + 1) + ".txt");
        }

        //计算查询扩展前MRR值
        Double MRRBeforeQE = rankReciprocalCountBeforeQE / questionCount;

        //计算查询扩展后MRR值
        Double MRRAfterQE = rankReciprocalCountAfterQE / questionCount;

        //打印MRR值
        System.out.println("\nBefore QE: " + MRRBeforeQE);
        System.out.println("After QE: " + MRRAfterQE + "\n");

        //计时器结束
        long endTime = System.currentTimeMillis();
        //打印消耗的时间
        System.out.println((endTime - startTime) / 1000F + "秒");
    }
}
