import javax.swing.*;
import java.awt.*;

public class Mainmenu extends JFrame implements Runnable {

    public Graphics2D g2;
    public KL keyListener = new KL();
    public ML mouseListener = new ML();
    public Text startGame, exitGame, pong;
    public boolean isRunning = true;


    public Mainmenu(){
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setTitle(Constants.TITLE);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.startGame = new Text("Start Game", new Font("Times New Roman", Font.PLAIN, Constants.TEXT_SIZE), Constants.SCREEN_WIDTH / 2.0 - 140.0, Constants.SCREEN_HEIGHT / 2.0, Color.WHITE);
        this.exitGame = new Text("Exit Game", new Font("Times New Roman", Font.PLAIN, Constants.TEXT_SIZE), Constants.SCREEN_WIDTH / 2.0 - 80.0, Constants.SCREEN_HEIGHT / 2.0 + 60.0, Color.WHITE);
        this.pong = new Text("Pong", new Font("Times New Roman", Font.PLAIN, 100), Constants.SCREEN_WIDTH / 2.0 - 155.0, 200.0, Color.WHITE);


        g2 = (Graphics2D)this.getGraphics();
    }

    public void update(double dt){
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);

        if (mouseListener.getMouseX() > startGame.x && mouseListener.getMouseX() < startGame.x + startGame.width &&
                mouseListener.getMouseY() > startGame.y - startGame.height / 2.0 && mouseListener.getMouseY() < startGame.y + startGame.height / 2.0){
                startGame.color = new Color(230,230,230, 114);

            if (mouseListener.isMousePressed()){
                Main.changeState(Constants.STATE_COUNTDOWN);
            }
        } else {
            startGame.color = Color.WHITE;
        }

        if (mouseListener.getMouseX() > exitGame.x && mouseListener.getMouseX() < exitGame.x + exitGame.width &&
                mouseListener.getMouseY() > exitGame.y - exitGame.height / 2.0 && mouseListener.getMouseY() < exitGame.y + exitGame.height / 2.0){
            exitGame.color = new Color(230,230,230, 114);
            if (mouseListener.isMousePressed()){
                Main.changeState(2);
            }
        } else {
            exitGame.color = Color.WHITE;
        }
    }

    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);



        pong.draw(g2);
        startGame.draw(g2);
        exitGame.draw(g2);
    }

    public void stop(){
        isRunning = false;
    }

    public void run(){
        double lastFrameTime = 0.0;
        while (isRunning){
            double time = Time.getTime();
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;

            update(deltaTime);


        }
        this.dispose();
        return;
    }
}