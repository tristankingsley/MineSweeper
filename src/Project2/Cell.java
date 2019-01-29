package Project2;


public class Cell {
    private boolean isExposed;
    private boolean isMine;
    private boolean test;

    int boundup = 0;
    int bounddown = 0;
    int boundleft = 0;
    int boundright = 0;

    public String getProx() {
        return prox;
    }

    public void setProx(String prox) {
        this.prox = prox;
    }

    private String prox;

    public Cell(boolean exposed, boolean mine) {
        isExposed = exposed;
        isMine = mine;
    }

    public boolean isExposed() {
        return isExposed;
    }

    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }


}
