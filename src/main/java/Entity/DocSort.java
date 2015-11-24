package Entity;

/**
 * Created by houseyoung on 15/11/23 23:33.
 */
public class DocSort {
    //文档编号
    private int docNumber;

    //相似度
    private Double similarity;

    public int getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(int docNumber) {
        this.docNumber = docNumber;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }
}
