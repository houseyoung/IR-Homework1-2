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
        int rank = 1;

        for (DocSort docSort : docSortList) {
            if (docSort.getDocNumber() == answer) {
                //若文档编号与答案相等，则终止循环
                break;
            }

            //rank自增
            rank++;
        }

        return rank;
    }
}
