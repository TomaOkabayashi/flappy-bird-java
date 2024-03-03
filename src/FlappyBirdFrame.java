import javax.swing.*; //GUI game window

public class FlappyBirdFrame {

  public static void main(String[] args) throws Exception {
    int boardWidth = 360;
    int boardHeight = 640;

    JFrame frame = new JFrame("Flappy Bird");
    // frame.setVisible(true);
    frame.setSize(boardWidth, boardHeight);
    frame.setLocationRelativeTo(null); //places window at the center of screen
    frame.setResizable(false); //user cannot resize window
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when user clicks 'x', terminates program

    FlappyBird flappyBird = new FlappyBird();
    frame.add(flappyBird);
    frame.pack(); //used to size the JFrame automatically to the size of the widgets within the page.
    // without .pack the dimensions will take into account the dimensions of the title bar. .pack discludes it
    flappyBird.requestFocus();
    frame.setVisible(true); //Set window visible after adding all settings, JPanel to frame then visibility=true
  }
}
