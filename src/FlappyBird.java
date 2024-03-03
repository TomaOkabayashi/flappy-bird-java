//Abstract Windowing Toolkit. It contains classes for graphics, including the Java 2D graphics capabilities,
//also defines the basic graphical user interface (GUI) framework for Java.
import java.awt.*;
import java.awt.event.*; //Provides interfaces and classes for dealing with different types of events fired by AWT components
import java.util.ArrayList; //Stores all the pipes
import java.util.Random;
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

  //Pipes
  int pipeX = boardWidth;
  int pipeY = 0;
  int pipeWidth = 64; //scaled by 1/6
  int pipeHeight = 512;


  class Pipe {
    int x = pipeX;
    int y = pipeY;
    int width = pipeWidth;
    int height = pipeHeight;
    Image img;
    boolean passed = false;

    Pipe(Image img) {
      this.img = img;
    }
  }

  //game logic
  Bird bird;
  int velocityX = -4; //for pipes
  int velocityY = 0; //for bird
  int gravity = 1;

  ArrayList<Pipe> pipes; 
  Random random = new Random();


  Timer gameLoop;
  Timer placePipesTimer;

  boolean gameOver = false;

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
    pipes = new ArrayList<Pipe>();

    //place pipes timer
    placePipesTimer = new Timer(1500, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        placePipes();
      }
    }); //Every 1.5secs, calls action

    placePipesTimer.start();

    //game timer
    gameLoop = new Timer(1000/60, this); //60 times per second/60frames, 'this' refers to the current instance/object of the flappy bird class
    gameLoop.start();
      
  }

  public void placePipes() {
    //(0-1) * pipeHeight/2.
    // 0 -> -128 (pipeHeight/4)
    // 1 -> -128 - 256 (pipeHeight/4 - pipeHeight/2) = -3/4 pipeHeight
    int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
    int openingSpace = boardHeight/4;

    Pipe topPipe = new Pipe(topPipeImg);
    topPipe.y = randomPipeY;
    pipes.add(topPipe);

    Pipe bottomPipe = new Pipe(bottomPipeImg);
    bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
    pipes.add(bottomPipe); //add bottom pipe to array list
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

    //pipes
    for (int i = 0; i < pipes.size(); i++) {
      Pipe pipe = pipes.get(i);
      g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
    }
  }


  //move function
  public void move() {
    //bird
    velocityY += gravity;
    bird.y += velocityY;
    bird.y = Math.max(bird.y, 0);

    //pipes
    for (int i = 0; i < pipes.size(); i++) {
      Pipe pipe = pipes.get(i);
      pipe.x += velocityX;

      if (collision(bird, pipe)) {
        gameOver = true;
      }
    }

    if (bird.y > boardHeight) {
      gameOver = true;
    }
  }

  public boolean collision(Bird a, Pipe b) {
    return  a.x < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
            a.x + a.width > b.x &&   //a's top right corner passes b's top left corner
            a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
            a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    // This actio is going to be performed 60 times a second
    move();
    repaint();
    if (gameOver) {
      placePipesTimer.stop();
      gameLoop.stop();
    }
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
