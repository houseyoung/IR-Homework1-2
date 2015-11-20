package Entity;

/**
 * TFIDF
 *
 * @author: yangch
 * @time: 2015/11/20 17:46
 */
public class TFIDF {
    //文档编号
    private int docNumber;

    //词
    private String term;

    //TF*IDF
    private Double tfIDF;

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

    public Double getTfIDF() {
        return tfIDF;
    }

    public void setTfIDF(Double tfIDF) {
        this.tfIDF = tfIDF;
    }
}
