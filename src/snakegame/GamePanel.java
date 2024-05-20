/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakegame;

import java.awt.Color;
import static java.awt.Color.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.*;



public class GamePanel extends JPanel implements ActionListener , KeyListener {
  
    
private final int[] snakexlength=new int[750];
private final int[] snakeylength=new int[750];
private int lengthOfSnake=3;
private int level=0;

int [] xPos={25,50,75,100,125,150,175,200,225,275,300,325,350,375,400,425,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};

int[] yPos={75,100,125,150,175,200,225,275,300,325,350,375,400,425,475,500,525,550,575,600,625};

private Random random=new Random();
private int enemyX, enemyY;

private boolean left=false;
private boolean right=true;
private boolean up=false;
private boolean down=false;
    
private int moves=0;
private int score=0;
private boolean gameOver=false;



    private ImageIcon snaketitle=new ImageIcon ( getClass().getResource("snaketitle3_1.png"));
private ImageIcon leftmouth=new ImageIcon ( getClass().getResource("leftmouth2.png"));
private ImageIcon rightmouth=new ImageIcon ( getClass().getResource("rightmouth2.png"));
private ImageIcon upmouth=new ImageIcon ( getClass().getResource("upmouth2.png"));
private ImageIcon downmouth=new ImageIcon ( getClass().getResource("downmouth2.png"));
private ImageIcon snakeimage=new ImageIcon ( getClass().getResource("snakeimage25.png"));
private ImageIcon enemy=new ImageIcon ( getClass().getResource("enemy2.png"));
private final String GameOverPath = "C:\\Aachal\\Web Projects\\2D Blind Snake\\gameover.wav";
private final String GameOverPath2 = "C:\\Aachal\\Web Projects\\2D Blind Snake\\gameover2.wav";
private String eatSound = "C:\\Aachal\\Web Projects\\2D Blind Snake\\eatenemy.wav";    
private String startGame = "C:\\Aachal\\Web Projects\\2D Blind Snake\\startgame.wav";    

private Timer timer;
 private int delay=100;
 
 

 
    GamePanel () {
        playSound(startGame);
        addKeyListener((KeyListener) this);
        setFocusable(true);
        setFocusTraversalKeyEnabled(true);


   timer=new Timer(delay, (ActionListener) this);
   timer.start();


        newEnemy();
    }
    
@Override
public void paint(Graphics g) {
    super.paint (g);
    
    g.setColor(Color.WHITE);
    g.drawRect(24, 10, 851, 55);
    g.drawRect(24, 74, 851, 576);
snaketitle.paintIcon(this , g, 25, 11);
 g.setColor(Color.black);
g.fillRect(25, 75, 850, 575);
       


if(moves==0) {
  snakexlength [0]=100;
snakexlength [1]=75;
snakexlength [2]=50;

snakexlength [0]=100;
snakexlength [1]=100;
snakexlength [2]=100;

}
if(left){
leftmouth.paintIcon(this, g, snakexlength [0],  snakeylength [0] );
}
if(right){
rightmouth.paintIcon(this, g, snakexlength [0],  snakeylength [0] );
}
if(up){
upmouth.paintIcon(this, g, snakexlength [0],  snakeylength [0] );
}
if(down){
downmouth.paintIcon(this, g, snakexlength [0],  snakeylength [0] );
}
for(int i=1;  i<lengthOfSnake; i++) {
     snakeimage.paintIcon(this, g, snakexlength [i] , snakeylength [i]);

}
   
   enemy.paintIcon(this, g, enemyX, enemyY);
   
if(gameOver) {
    
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial",Font.BOLD, 30));
    g.drawString(" OPPS..!!! GAME OVER", 288, 300);
    g.setFont(new Font("Arial",Font.PLAIN, 27));
    g.drawString(" Press SPACE To Start New Game ", 240, 340);
}
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial",Font.PLAIN, 14));
    g.drawString(" Score : "+score, 750, 30);
    g.drawString("Length : "+lengthOfSnake, 750, 50);
    g.drawString("Level : "+level, 80, 30);
    g.drawString("Moves : "+moves, 80, 50);

    g.dispose();
}
@Override
  public void actionPerformed(ActionEvent e){
     
     for(int i=lengthOfSnake-1;i>0;i--){
         
        snakexlength[i]=snakexlength[i-1]; 
        snakeylength[i]=snakeylength[i-1];
     }
     
     if (left){
         
       snakexlength[0]=snakexlength[0]-25;
     }
     if (right){
         snakexlength[0]=snakexlength[0]+25;
     }
      
      if(up){
          snakeylength[0]=snakeylength[0]-25;
      }
      
      if(down){
          snakeylength[0]=snakeylength[0]+25;
      }
      
      
     if(snakexlength[0]>850)snakexlength[0]=25;
     if(snakexlength[0]<25)snakexlength[0]=850;
     
     if(snakeylength[0]>625)snakeylength[0]=75;
     if(snakeylength[0]<75)snakeylength[0]=625;
     
     collidesWithEnemy();
     collidesWithBody();
     
      repaint();
  }

@Override
  public void keyPressed(KeyEvent e){
  
   if(e.getKeyCode()==KeyEvent.VK_SPACE){
       
       restart();
   }
      
      if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right)){
          
          left=true;
          right=false;
          up=false;
          down=false;
          moves++;
      }
       if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left)){
          
          left=false;
          right=true;
          up=false;
          down=false;
          moves++;
  }
   if(e.getKeyCode()==KeyEvent.VK_UP && (!down)){
          
          left=false;
          right=false;
          up=true;
          down=false;
          moves++;
          }
    if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up)){
          
          left=false;
          right=false;
          up=false;
          down=true;
          moves++;
          }
          }
          

    @Override
   public void keyReleased(KeyEvent e){
       
   }
   
@Override
   public void keyTyped(KeyEvent e){
       
   }
   
   private void newEnemy(){
    enemyX=xPos[random.nextInt(32)];
    enemyY=yPos[random.nextInt(21)];    
    for(int i=lengthOfSnake-1;i>=0;i--){
        
   if(snakexlength[i]==enemyX && snakeylength[i]==enemyY) {
          
          newEnemy();
      }
    }
   }
   
   private void collidesWithEnemy(){
    if(snakexlength[0]==enemyX && snakeylength[0]==enemyY){
    
    playSound(eatSound);
    
    newEnemy();
    lengthOfSnake++;
    
    score=score+5;
    if(score%25==0){
        level++;
        
    }    
        switch(score){
        case 25 -> setBackground(Color.WHITE);
        case 50 -> setBackground(Color.YELLOW);
        case 75 -> setBackground(Color.orange);
        case 100 -> setBackground(Color.red);
        case 125 -> setBackground(Color.MAGENTA);
        case 150 -> setBackground(Color.green);
        case 175 -> setBackground(Color.blue);
        case 200 -> setBackground(Color.CYAN);
        case 225 -> setBackground(Color.PINK);
        case 250 -> setBackground(Color.green);
        case 275 -> setBackground(Color.yellow);
        case 300 -> setBackground(Color.orange);
        case 325 -> setBackground(Color.red);
        case 350 -> setBackground(Color.magenta);
        case 375 -> setBackground(Color.green);
        case 400 -> setBackground(Color.blue);
        case 425 -> setBackground(Color.CYAN);
        case 450 -> setBackground(Color.pink);
        case 475 -> setBackground(Color.yellow);
        case 500 -> setBackground(Color.orange);
        case 525 -> setBackground(Color.red);
        case 550 -> setBackground(Color.magenta);
        case 575 -> setBackground(Color.pink);
        case 600 -> setBackground(Color.green);
        case 625 -> setBackground(Color.blue);
        case 650 -> setBackground(Color.pink);
        }
    
    }
   }
  
  private void collidesWithBody(){
    int i;
     for(i=lengthOfSnake-1;i>0;i--) {
         
       if(snakexlength[i]==snakexlength[0] && snakeylength[i]==snakeylength[0]){
        timer.stop();
        gameOver=true;
        playSound(GameOverPath);
        playSound(GameOverPath2);

       }
     }
    }
    
    public void restart(){
        
      gameOver=false;  
      playSound(startGame);
      moves=0;
      score=0;
    lengthOfSnake = 3;
    level=0;
    setBackground(Color.DARK_GRAY);
      left=false;
      right=true;
      up=false;
      down=false;
      timer.start();
      repaint();
    }
    

    private void setFocusTraversalKeyEnabled(boolean b) {
//        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public void playSound(String musicLoc){
                 try {
                         File musicPath = new File(musicLoc);
                          if(musicPath.exists()){ 
                                  AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                                  Clip clip = AudioSystem.getClip();
                                  clip.open(audioInput);
                                  clip.start();
                                 // JOptionPane.showMessageDialog(null,"Press ok to stop playing");

                           }
                          else{
                                   System.out.println("Couldn't find Music file");
                                }
                }
                catch (Exception ex){
                     }
           }



}