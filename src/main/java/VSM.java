import Entity.TF;

import java.util.*;

/**
 * VSM
 *
 * @author: yangch
 * @time: 2015/11/23 14:09
 */
public class VSM {
    public static void main(String[] args) throws Exception {
        //计时器开始
        Date startTime = new Date();

        //获取resources文件夹的路径
        String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";
        //设置问题文件的地址
        String questionFilePath = resourcesPath + "question.doc";
        //设置文档文件夹的路径
        String docPath = resourcesPath + "Doc/";

        //获取问题List
        List<String> questionList = GetQuestion.getQuestion(questionFilePath);

        //对36个问题依次进行检索
        for (int i = 0; i < 36; i++) {
            //第i个问题
            String question = questionList.get(i);

            //从问题进行切词，并将切出的词、词的出现次数存入Map中
            Map<String, Double> queryMap = WordSegmentation.wordSegmentation(question);

            //将Term、出现次数记录在Map中，用LinkedHashMap可以实现按顺序输出
            Map<String, Double> termMap = new LinkedHashMap<String, Double>();

            //对输入文件夹下所有文件进行切词，并计算每一个词的TF
            List<TF> tfList = CalcTF.calcTF(docPath);

            //记录最大cos
            Double maxCos = 0.0;

            //记录最大cos对应的文档编号
            int docNumber = 0;

            //对30个文档依次进行检索
            for (int doc = 1; doc <= 30; doc++) {
                //读取本次检索的文档切出的词及词的出现次数，存入termMap
                for (TF tf : tfList) {
                    if (tf.getDocNumber() == doc) {
                        termMap.put(tf.getTerm(), tf.getTermCount());
                    }
                }

                //余弦法的三个参数
                Double Q2 = 0.0;
                Double D2 = 0.0;
                Double DQ = 0.0;

                for (Map.Entry<String, Double> entry : queryMap.entrySet()) {
                    Q2 += entry.getValue() * entry.getValue();
                }

                for (Map.Entry<String, Double> entry : termMap.entrySet()) {
                    D2 += entry.getValue() * entry.getValue();
                }

                for (Map.Entry<String, Double> entry1 : termMap.entrySet()) {
                    for (Map.Entry<String, Double> entry2 : queryMap.entrySet()) {
                        if (entry1.getKey() == entry2.getKey()) {
                            DQ += entry1.getValue() * entry2.getValue();
                        }
                    }
                }

                Double cos = DQ / Math.sqrt(Q2 * D2);

//                Double cos = 2 * DQ / (D2 + Q2);
                if (cos > maxCos) {
                    maxCos = cos;
                    docNumber = doc;
                }

                termMap = new LinkedHashMap<String, Double>();
            }
            System.out.println((i + 1) + "、<Doc" + docNumber + ">\n");
        }

        //计时器结束
        Date endTime = new Date();
        //输出消耗的时间
        System.out.println((endTime.getTime() - startTime.getTime()) / 1000F + "秒");
    }
}
