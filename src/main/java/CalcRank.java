import Entity.DocSort;

import java.util.List;

/**
 * CalcRank
 *
 * @author: yangch
 * @time: 2015/11/24 19:32
 */
public class CalcRank {
    /**
     * 计算Rank值
     *
     * @param docSortList
     * @param answer
     * @return
     * @throws Exception
     */
    public static int calcRank(List<DocSort> docSortList, int answer) throws Exception {
        //记录rank
        int rank = 0;

        //记录第几个文档获得正确答案
        int number = 1;

        for (DocSort docSort : docSortList) {
            if (docSort.getDocNumber() == answer) {
                rank = number;
            }
            //文档数自增
            number++;
        }

        return rank;
    }
}
