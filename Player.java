package Assignment_4_CS434;

public interface Player{
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    
    void setPlayer(final int p0);
        //whose move is next, WHITE(1) or BLACK(2)?
    
    void updateBoard(final int[][] p0);
        //this method sends in the board
        //0 for blank
        //1 for WHITE piece
        //2 for BLACK piece
        
    Position move();
        //when this method is called, it decides which square to play into
        //A0 to H8   
}