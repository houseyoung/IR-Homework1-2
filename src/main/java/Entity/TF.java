package Entity;

/**
 * TF
 *
 * @author: yangch
 * @time: 2015/11/20 17:00
 */
public class TF {
    //文档编号
    private int docNumber;

    //词
    private String term;

    //词出现次数
    private Double termCount;

    //词频
    private Double TF;

    public int getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(int docNumber) {
        this.docNumber = docNumber;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Double getTermCount() {
        return termCount;
    }

    public void setTermCount(Double termCount) {
        this.termCount = termCount;
    }

    public Double getTF() {
        return TF;
    }

    public void setTF(Double TF) {
        this.TF = TF;
    }
}
