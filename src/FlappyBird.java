//Abstract Windowing Toolkit. It contains classes for graphics, including the Java 2D graphics capabilities,
//also defines the basic graphical user interface (GUI) framework for Java.
import java.awt.*;
import java.awt.event.*; //Provides interfaces and classes for dealing with different types of events fired by AWT components
import java.util.ArrayList; //Stores all the pipes
import java.util.Random.*; //Places pipes in random positions
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener{ //flappybird class inherits JPanel class
  
  int boardWidth = 360;
  int boardHeight = 640;

  //Image variables to store img objects
  Image backgroundImg;
  Image birdImg;
  Image topPipeImg;
  Image bottomPipeImg;

  //Bird
  int birdX = boardWidth / 8; //x position, one eighth from the left side of the screen
  int birdY = boardHeight / 2; //y position, half way down the screen
  int birdWidth = 34;
  int birdHeight = 24;

  class Bird { //class ot hold Bird values

    int x = birdX;
    int y = birdY;
    int width = birdWidth;
    int height = birdHeight;
    Image img;

    Bird(Image img) {
      this.img = img;
    }
  }

  //game logic
  Bird bird;
  int velocityY = 0;
  int gravity = 1;

  Timer gameLoop;
  


  FlappyBird() { //constructor
    setPreferredSize(new Dimension(boardWidth, boardHeight));
    // setBackground(Color.blue);
    setFocusable(true); //makes sure the flappybird class/jpanel is the one that takes in the key events
    addKeyListener(this); //checks the three key listner functions

    //initialise images onto each variable
    backgroundImg =
      new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
    birdImg =
      new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
    topPipeImg =
      new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
    bottomPipeImg =
      new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
    

    //bird
    bird = new Bird(birdImg);

    //game timer
    gameLoop = new Timer(1000/60, this); //60 times per second/60frames, 'this' refers to the current instance/object of the flappy bird class
    gameLoop.start();
      
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g); //JPanel function, super refers to the parent class, so JPanel
    draw(g);
  }

  public void draw(Graphics g) {
    //debug statement
    // System.out.println("draw");
    //background
    g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null); //image object, cords for top left corner 0,0, bottom right corner 360, 640

    //bird
    g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
  }


  //move function
  public void move() {
    //bird
    velocityY += gravity;
    bird.y += velocityY;
    bird.y = Math.max(bird.y, 0);
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    // This actio is going to be performed 60 times a second
    move();
    repaint();
  }

  

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      velocityY = -10;
    }
  }
  @Override
  public void keyTyped(KeyEvent e) {}
  @Override
  public void keyReleased(KeyEvent e) {}
}
