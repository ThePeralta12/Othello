package Assignment_4_CS434;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.awt.Panel;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Othello{

    private static final int WIN_WIDTH = 600;
    private static final int WIN_HEIGHT = 500;
    private static Window window;   

    public static void main(String[] args) {
        window = new Window(WIN_WIDTH, WIN_HEIGHT, "Phil's Othello");
        
    }
}

class Window{
    public JFrame windowFrame;
    public Scoreboard s1;
    public Scoreboard s2;
    public Board b;
    public Options[] Options=new Options[2];
    public boolean running=false;
    public boolean flashing=false;
    public Window main=this;
    public Computer computer;
    public Brain1 brain1=new Brain1();
    public Brain2 brain2=new Brain2();
    public boolean password=true;

    Window(int width, int height, String title) {        
        windowFrame = new JFrame();
        windowFrame.setBounds(0, 0, width, height);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));
        windowFrame.setTitle(title);
        windowFrame.setResizable(false);
        windowFrame.setLayout(null);

        windowFrame.setVisible(true);
        windowFrame.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
        windowFrame.setSize(width,height);
        b = new Board(this);
        s1 = new Scoreboard(this,true);
        s2 = new Scoreboard(this,false);
        Options[0] = new Options(this);
        Options[1] = new Options(this);
        Options[0].skill.select(1);
        Options[0].level=1;
        computer = new Computer(this);
        Button restart = new Button("Restart Game");
        restart.setFont(new Font( "TimesRoman", Font.BOLD, 14));
        restart.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                if(!flashing){
                    if((Options[0].level==10&&Options[1].level==0)||(Options[1].level==10&&Options[0].level==0)){
                        password=true;
                    }
                    for(int i=0;i<8;i++){
                        for(int j=0;j<8;j++){
                            b.array[i][j]=0;
                        }
                    }
                    b.array[3][3]=1;
                    b.array[4][4]=1;
                    b.array[3][4]=2;
                    b.array[4][3]=2;
                    b.player=1;
                    b.won=false;
                    b.winner=0;
                    b.findavailable();
                    s1.repaint();
                    s2.repaint();
                    if(Options[b.player-1].level==0){
                        b.hints=true;
                    }
                    b.repaint();
                    if(Options[b.player-1].level>0&&!running){
                        Thread newthread= new Thread(main.computer);
                        newthread.start();
                        b.hints=false;
                    }
                }
            }
        });
        windowFrame.add(Options[1]);
        windowFrame.add(b);
        windowFrame.add(Options[0]);
        windowFrame.add(s1);
        windowFrame.add(restart);
        windowFrame.add(s2);
        windowFrame.getContentPane().setBackground(new Color(222, 184, 135));
        windowFrame.setVisible(true);
    }
}

class Board extends Canvas{
    public int[][] array = new int[8][8];
    public boolean[][] available = new boolean[8][8];
    public int player=1;
    private Window main;
    public boolean won=false;
    public int winner=0;
    public boolean hints=true;
    
    public Board(Window mainin){
        main=mainin;
        setSize(321,321);
        setBackground(new Color(65,142,42));
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                array[i][j]=0;
            }
        }
        array[3][3]=1;
        array[4][4]=1;
        array[3][4]=2;
        array[4][3]=2;
        findavailable();
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                if(main.Options[player-1].level<=0&&!won){
                    int x=e.getX()/40;
                    int y=e.getY()/40;
                    if(available[x][y]){
                        makemove(x,y);
                        findavailable();
                        main.s1.repaint();
                        main.s2.repaint();
                        repaint();
                        if(main.Options[player-1].level>0&&won==false&&!main.running){
                            hints=false;
                            Thread newthread= new Thread(main.computer);
                            newthread.start();
                        }
                    }       
                }       
            }
        });
    }   
            
    public void makemove(int x,int y){
        array[x][y]=player;
        boolean move=false;
        int i=x;
        int j=y;
        boolean cont=true;
        do{
            if(array[i][y]!=player%2+1&&i-x>0){
                cont=false;
            }
            i++;
        }while(i<8&&cont);
        if(array[i-1][y]==player&&i-x>2){
            for(int z=i-1;z>x;z--){
                array[z][y]=player;
            }
        }
        i=x;
        cont=true;
        do{
            if(array[i][y]!=player%2+1&&x-i>0){
                cont=false;
            }
            i--;
        }while(i>=0&&cont);
        if(array[i+1][y]==player&&x-i>2){
            for(int z=i+1;z<x;z++){
                array[z][y]=player;
            }
        }
        i=x;
        cont=true;
        do{
            if(array[x][j]!=player%2+1&&j-y>0){
                cont=false;
            }
            j++;
        }while(j<8&&cont);
        if(array[x][j-1]==player&&j-y>2){
            for(int z=j-1;z>y;z--){
                array[x][z]=player;
            }
        }
        j=y;
        cont=true;
        do{
            if(array[x][j]!=player%2+1&&y-j>0){
                cont=false;
            }
            j--;
        }while(j>=0&&cont);
        if(array[x][j+1]==player&&y-j>2){
            for(int z=j+1;z<y;z++){
                array[x][z]=player;
            }
        }
        j=y;
        cont=true;
        do{
            if(array[i][j]!=player%2+1&&i-x>0){
                cont=false;
            }
            i++;
            j++;
        }while(i<8&&j<8&&cont);
        if(array[i-1][j-1]==player&&i-x>2){
            for(int z=1;z<i-x-1;z++){
                array[x+z][y+z]=player;
            }
        }
        i=x;
        j=y;
        cont=true;
        do{
            if(array[i][j]!=player%2+1&&x-i>0){
                cont=false;
            }
            i--;
            j++;
        }while(i>=0&&j<8&&cont);
        if(array[i+1][j-1]==player&&x-i>2){
            for(int z=1;z<x-i-1;z++){
                array[x-z][y+z]=player;
            }
        }
        i=x;
        j=y;
        cont=true;
        do{
            if(array[i][j]!=player%2+1&&y-j>0){
                cont=false;
            }
            j--;
            i++;
        }while(j>=0&&i<8&&cont);
        if(array[i-1][j+1]==player&&y-j>2){
            for(int z=1;z<y-j-1;z++){
                array[x+z][y-z]=player;
            }
        }
        i=x;
        j=y;
        cont=true;
        do{
            if(array[i][j]!=player%2+1&&y-j>0){
                cont=false;
            }
            j--;
            i--;
        }while(j>=0&&i>=0&&cont);
        if(array[i+1][j+1]==player&&y-j>2){
            for(int z=1;z<y-j-1;z++){
                array[x-z][y-z]=player;
            }
        }
    }
    
    public void update(Graphics g){
        paint(g);
    }
    
    public void findavailable() {
        int times=0;
        int amountavailable;
        do{
            player=player%2+1;
            boolean move=false;
            boolean cont=true;
            int i;
            int j;
            amountavailable=0;
            for(int x=0;x<8;x++){
                for(int y=0;y<8;y++){
                    available[x][y]=false;
                    move=false;
                    cont=true;
                    i=x;
                    j=y;
                    do{
                        if(array[i][y]!=player%2+1&&i-x>0){
                            cont=false;
                        }
                        i++;
                    }while(i<8&&cont);
                    if(array[x][y]==0&&array[i-1][y]==player&&i-x>2){
                        move=true;
                    }
                    i=x;
                    cont=true;
                    do{
                        if(array[i][y]!=player%2+1&&x-i>0){
                            cont=false;
                        }
                        i--;
                    }while(i>=0&&cont);
                    if(array[x][y]==0&&array[i+1][y]==player&&x-i>2){
                        move=true;
                    }
                    i=x;
                    cont=true;
                    do{
                        if(array[x][j]!=player%2+1&&j-y>0){
                            cont=false;
                        }
                        j++;
                    }while(j<8&&cont);
                    if(array[x][y]==0&&array[x][j-1]==player&&j-y>2){
                        move=true;
                    }
                    j=y;
                    cont=true;
                    do{
                        if(array[x][j]!=player%2+1&&y-j>0){
                            cont=false;
                        }
                        j--;
                    }while(j>=0&&cont);
                    if(array[x][y]==0&&array[x][j+1]==player&&y-j>2){
                        move=true;
                    }
                    j=y;
                    cont=true;
                    do{
                        if(array[i][j]!=player%2+1&&i-x>0){
                            cont=false;
                        }
                        i++;
                        j++;
                    }while(i<8&&j<8&&cont);
                    if(array[x][y]==0&&array[i-1][j-1]==player&&i-x>2){
                        move=true;
                    }
                    i=x;
                    j=y;
                    cont=true;
                    do{
                        if(array[i][j]!=player%2+1&&x-i>0){
                            cont=false;
                        }
                        i--;
                        j++;
                    }while(i>=0&&j<8&&cont);
                    if(array[x][y]==0&&array[i+1][j-1]==player&&x-i>2){
                        move=true;
                    }
                    i=x;
                    j=y;
                    cont=true;
                    do{
                        if(array[i][j]!=player%2+1&&y-j>0){
                            cont=false;
                        }
                        j--;
                        i++;
                    }while(j>=0&&i<8&&cont);
                    if(array[x][y]==0&&array[i-1][j+1]==player&&y-j>2){
                        move=true;
                    }
                    i=x;
                    j=y;
                    cont=true;
                    do{
                        if(array[i][j]!=player%2+1&&y-j>0){
                            cont=false;
                        }
                        j--;
                        i--;
                    }while(j>=0&&i>=0&&cont);
                    if(array[x][y]==0&&array[i+1][j+1]==player&&y-j>2){
                        move=true;
                    }
                    if(move){
                        available[x][y]=true;
                        amountavailable++;
                    }
                }
            }
            times++;
        }while(times<3&&amountavailable==0);
        if(times==3){
            won=true;
            int b=0;
            int w=0;
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(array[i][j]==2){
                        b++;
                    }else if(array[i][j]==1){
                        w++;
                    }
                }
            }
            if(b>w){
                winner=2;
            }else if(b<w){
                winner=1;
            }
            main.b.repaint();
        }
    }
    
    public void paint(Graphics g){      
        Graphics offscreengraphics;
        Image offscreenimage;
        offscreenimage=createImage(321,321);
        offscreengraphics=offscreenimage.getGraphics();     
        offscreengraphics.setColor(Color.black);       
        for(int l=0;l<9;l++){
            offscreengraphics.drawLine(l*40,0,l*40,320);
            offscreengraphics.drawLine(0,l*40,320,l*40);
        }
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                if(array[x][y]==1){
                    offscreengraphics.setColor(Color.white);
                    offscreengraphics.fillOval(x*40+3,y*40+3,34,34);
                }else if(array[x][y]==2){
                    offscreengraphics.setColor(Color.black);
                    offscreengraphics.fillOval(x*40+3,y*40+3,34,34);
                }
            }
        }
        if(hints){
            for(int x=0;x<8;x++){
                for(int y=0;y<8;y++){
                    if(available[x][y]){
                        offscreengraphics.setColor(new Color(65,162,42));
                        offscreengraphics.fillRect(x*40+1,y*40+1,39,39);
                    }
                }
            }
        }
        g.drawImage(offscreenimage,0,0,this);
    }
}
    

class Scoreboard extends Canvas{
    
    private boolean toggle;
    private Window main;
    
    public Scoreboard(Window mainin,boolean togglein){
        main=mainin;
        toggle=togglein;
        setSize(200,60);
    }
    
    public void update(Graphics g){
        paint(g);
    }
        
    public void paint(Graphics g) {
        Graphics offscreengraphics;
        Image offscreenimage;
        offscreenimage=createImage(200,60);
        offscreengraphics=offscreenimage.getGraphics();
        offscreengraphics.setFont(new Font( "TimesRoman", Font.BOLD, 14));
        offscreengraphics.setColor(new Color(128,128,128));
        offscreengraphics.fillRect(0,0,200,60);
        offscreengraphics.setColor(Color.lightGray);
        offscreengraphics.fillRect(5,5,190,15);
        offscreengraphics.setColor(Color.black);
        if(toggle){
            offscreengraphics.drawString("Game Status",9,18);
        }else{
            offscreengraphics.drawString("Game Score",9,18);
        }
        offscreengraphics.drawRect(5,5,190,15);
        offscreengraphics.setFont(new Font( "TimesRoman", Font.BOLD, 20));
        if(toggle){
            if(main.b.won){
                if(main.b.winner==1){
                    offscreengraphics.setColor(Color.white);
                    offscreengraphics.drawString("White wins!!!",9,45);
                }else if(main.b.winner==2){
                    offscreengraphics.setColor(Color.black);
                    offscreengraphics.drawString("Black wins!!!",9,45);
                }else{
                    offscreengraphics.setColor(Color.green);
                    offscreengraphics.drawString("It's a draw!!!",9,45);
                }
            }else{  
                if(main.b.player==1){
                    offscreengraphics.setColor(Color.white);
                    offscreengraphics.drawString("White to play",9,45);
                }else if(main.b.player==2){
                    offscreengraphics.setColor(Color.black);
                    offscreengraphics.drawString("Black to play",9,45);
                }
            }
        }else{
            int b=0;
            int w=0;
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(main.b.array[i][j]==2){
                        b++;
                    }else if(main.b.array[i][j]==1){
                        w++;
                    }
                }
            }
            offscreengraphics.drawString("Black "+b,9,45);
            offscreengraphics.setColor(Color.white);
            offscreengraphics.drawString("White "+w,99,45);
        }
        g.drawImage(offscreenimage,0,0,this);
    }
}

class Options extends Panel{
    
    public int level=0;
    public Choice skill;
    private Window main;
    
    public Options(Window mainin){
        main=mainin;
        setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        skill = new Choice();
        skill.addItem("Human");
        skill.addItem("Brain 1");
        skill.addItem("Brain 2");
        skill.select(0);
        setFont(new Font( "TimesRoman", Font.BOLD, 14));
        skill.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if(e.getStateChange()==ItemEvent.SELECTED){
                    main.password=false;
                    if(e.getItem()!="Human"){
                        if(e.getItem()=="Brain 1"){            
                            level=1;
                        }else if(e.getItem()=="Brain 2"){
                            level=2;
                        }
                        if(main.Options[main.b.player-1].level>0&&main.b.won==false&&!main.running){
                            main.b.hints=false;
                            main.computer = new Computer(main);
                            new Thread(main.computer).start();  
                        }
                    }else{
                        level=0;
                    }
                    main.b.repaint();
                }
            }
        });
        add(skill); 
    }   
}

class Computer implements Runnable{
    
    private Window main;
    
    public Computer(Window mainin){
        main=mainin;
    }
    
    public void run(){
        main.running=true;
        do{
            try{Thread.sleep(500);}catch(InterruptedException e){}
            if(main.Options[main.b.player-1].level>0){
                main.flashing=true;
                int x,y;
                Position move = new Position('A',0);
                if(main.Options[main.b.player-1].level==1){
                    main.brain1.setPlayer(main.b.player);
                    main.brain1.updateBoard(main.b.array);
                    move=main.brain1.move();
                }else if(main.Options[main.b.player-1].level==2){
                    main.brain2.setPlayer(main.b.player);
                    main.brain2.updateBoard(main.b.array);
                    move=main.brain2.move();
                }
                String alphabet="ABCDEFGH";
                for(x=0;x<8&&move.getCol()!=alphabet.charAt(x);x++){}
                y=move.getRow();        
                int oldvalue=main.b.player;
                for(int a=7;a>=0;a--){
                    try{Thread.sleep(50);}catch(InterruptedException e){}
                    main.b.array[x][y]=oldvalue;
                    main.b.repaint();
                    try{Thread.sleep(50);}catch(InterruptedException e){}
                    main.b.array[x][y]=0;
                    main.b.repaint();
                }
                main.b.makemove(x,y);
                main.b.findavailable();
                main.s1.repaint();
                main.s2.repaint();
                main.b.repaint();
                main.flashing=false;
            }
        }while(main.Options[main.b.player-1].level>0&&main.b.won==false);
        main.running=false;
        main.b.hints=true;
        main.b.repaint();
    }
}