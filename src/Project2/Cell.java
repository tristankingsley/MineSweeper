package Project2;

/***********************************************************************
 This class creates a cell as well as methods to interact with and
 describe a cell to be used with JButtons in our MineSweeperPanel class

 @author Tristan Kingsley and Trevor Spitzley
 @Version February 2019
 **********************************************************************/

public class Cell {

    /** Three booleans to represent being flagged, a mine, or exposed */
    private boolean isExposed, isMine, isFlagged;

    /** Presets the upper bound of a clicked cell */
    int boundup = 1;

    /** Presets the lower bound of a clicked cell */
    int bounddown = 1;

    /** Preset the left bound of a clicked cell */
    int boundleft = 1;

    /** Preset the right bound of a clicked cell */
    int boundright = 1;

    /*******************************************************************
     * Public constructor for a cell, setting boolean values to
     * respective passed values
     * @param exposed Boolean value of exposure
     * @param mine Boolean value if cell is a mine
     ******************************************************************/
    public Cell(boolean exposed, boolean mine) {
        isExposed = exposed;
        isMine = mine;
    }

    /******************************************************************
     * Public method that returns a boolean whether the cell is
     * exposed or not
     * @return A boolean value representing exposure
     *****************************************************************/
    public boolean isExposed() {
        return isExposed;
    }

    /******************************************************************
     * Public method that sets a boolean value whether the cell is
     * being exposed or not
     * @param exposed Boolean value of exposure
     *****************************************************************/
    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }

    /******************************************************************
     * Public method that returns a boolean whether the cell is
     * a mine or not
     * @return A boolean value representing mine status
     *****************************************************************/
    public boolean isMine() {
        return isMine;
    }

    /******************************************************************
     * Public method that sets a boolean value whether the cell is
     * a mine
     * @param mine  A boolean value representing being a mine or not
     *****************************************************************/
    public void setMine(boolean mine) {
        isMine = mine;
    }

    /******************************************************************
     * Public method that returns a boolean whether the cell is
     * flagged or not
     * @return A boolean value representing being flagged or not
     *****************************************************************/
    public boolean isFlagged(){
        return isFlagged;
    }

    /******************************************************************
     * Public method that sets a boolean value whether the cell is
     * flagged or not
     * @param flagged  A boolean value representing being flagged
     *****************************************************************/
    public void setFlagged(boolean flagged){
        isFlagged = flagged;
    }

}
