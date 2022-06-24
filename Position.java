package Assignment_4_CS434;

public class Position{
    private char the_col;
    private int the_row;
    
    public Position(char column, int row){
        the_col = Character.toUpperCase(column);
        the_row = row;
        if ((the_col < 'A') || (the_col > 'H') || 
            (the_row < 0) || (the_row > 7)) {
            throw new Error("Position.Position: Fatal. Position " + the_col + "," + the_row + " is not legal!");
        }
    }
    
    public char getCol(){
        return the_col;
    }
    
    public int getRow(){
        return the_row;
    }
    
    public String toString(){
        return "(" + the_col + "," + the_row + ")";
    }
}