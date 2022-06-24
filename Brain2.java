package Assignment_4_CS434;
import java.util.*;

public class Brain2 implements Player {
    public static final int WHITE = 1,BLACK = 2;
private int player;
private int[][] board=new int[8][8];
private int moves;
private int Available;
private int Searchdepth;

public void setPlayer(int whichPlayer){

    player=whichPlayer;
}

public void updateBoard(int[][] boardin){
    for(int i=0;i<8;i++){
        for(int j=0;j<8;j++){
            board[i][j]=boardin[i][j];
        }
    }
}

public Position move(){
    String letters="ABCDEFGH";
    boolean[][] available = findavailable(board, player);


    //added another way of creating a random number within the range of 0 to 1
    Random random = new Random();


    //double value = random.nextDouble();
    //or
    //double value = Math.random();


    int xaxis = 0;
    int yaxis = 0;

    while(!available[xaxis][yaxis]){

       //was unsucessful as it did not continue playing.
        
        //xaxis = (int)(8*value);
        xaxis = (int)(8*Math.random());
        //yaxis = (int)(8*value);
        yaxis = (int)(8*Math.random());
    }
    return new Position(letters.charAt(xaxis),yaxis);	
}

     
public boolean[][] findavailable(int[][] Board, int Player) {
    boolean move=false;
    boolean cont=true;
    boolean[][] available = new boolean[8][8];
    Available=0;
    int i;
    int j;
    for(int x=0;x<8;x++){
        for(int y=0;y<8;y++){
            available[x][y]=false;
            move=false;
            cont=true;
            i=x;
            j=y;
            do{
                if(Board[i][y]!=Player%2+1&&i-x>0){
                    cont=false;
                } 
                i++;
            }while(i<8&&cont);
            if(Board[x][y]==0&&Board[i-1][y]==Player&&i-x>2){
                move=true;
            }
            if(!move){
                i=x;
                cont=true;
                do{
                    if(Board[i][y]!=Player%2+1&&x-i>0){
                        cont=false;
                    }
                    i--;
                }while(i>=0&&cont);
                if(Board[x][y]==0&&Board[i+1][y]==Player&&x-i>2){
                    move=true;
                }
            }
            if(!move){
                i=x;
                cont=true;
                do{
                    if(Board[x][j]!=Player%2+1&&j-y>0){
                        cont=false;
                    }
                    j++;
                }while(j<8&&cont);
                if(Board[x][y]==0&&Board[x][j-1]==Player&&j-y>2){
                    move=true;
                }
            }
            if(!move){
                j=y;
                cont=true;
                do{
                    if(Board[x][j]!=Player%2+1&&y-j>0){
                        cont=false;
                    }
                    j--;
                }while(j>=0&&cont);
                if(Board[x][y]==0&&Board[x][j+1]==Player&&y-j>2){
                    move=true;
                }
            }
            if(!move){
                j=y;
                cont=true;
                do{
                    if(Board[i][j]!=Player%2+1&&i-x>0){
                        cont=false;
                    }
                    i++;
                    j++;
                }while(i<8&&j<8&&cont);
                if(Board[x][y]==0&&Board[i-1][j-1]==Player&&i-x>2){
                    move=true;
                }
            }
            if(!move){
                i=x;
                j=y;
                cont=true;
                do{
                    if(Board[i][j]!=Player%2+1&&x-i>0){
                        cont=false;
                    }
                    i--;
                    j++;
                }while(i>=0&&j<8&&cont);
                if(Board[x][y]==0&&Board[i+1][j-1]==Player&&x-i>2){
                    move=true;
                }
            }
            if(!move){
                i=x;
                j=y;
                cont=true;
                do{
                    if(Board[i][j]!=Player%2+1&&y-j>0){
                        cont=false;
                    }
                    j--;
                    i++;
                }while(j>=0&&i<8&&cont);
                if(Board[x][y]==0&&Board[i-1][j+1]==Player&&y-j>2){
                    move=true;
                }
            }
            if(!move){
                i=x;
                j=y;
                cont=true;
                do{
                    if(Board[i][j]!=Player%2+1&&y-j>0){
                        cont=false;
                    }
                    j--;
                    i--;
                }while(j>=0&&i>=0&&cont);
                if(Board[x][y]==0&&Board[i+1][j+1]==Player&&y-j>2){
                    move=true;
                }
            }
            if(move){
                available[x][y]=true;
                Available++;
            }
        }
    }
    return available;
}
    
}
