import javax.swing.*;
import java.awt.*;

public class CountDownScreen extends JFrame implements Runnable {
    private Graphics2D g2;
    private Text countDownText;
    private int currentCount = 3;
    private long lastCountChange;
    private boolean isRunning = true;

    public CountDownScreen(){
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setTitle(Constants.TITLE);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        g2 = (Graphics2D)this.getGraphics();
        countDownText = new Text("3", new Font("Times New Roman", Font.BOLD, Constants.COUNTDOWN_FONT_SIZE),
                                 Constants.SCREEN_WIDTH/2.0 - Constants.COUNTDOWN_FONT_SIZE/2.0,
                                 Constants.SCREEN_HEIGHT/2.0 + Constants.COUNTDOWN_FONT_SIZE/4.0,
                                    Color.WHITE);

        lastCountChange = System.currentTimeMillis();
    }

    public void update(){
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);

        long currentTime = System.currentTimeMillis();
        if(currentTime - lastCountChange >= 1000){
            currentCount--;
            countDownText.text = currentCount > 0 ? String.valueOf(currentCount) : "GO!";

            if(currentCount == 0){
                countDownText.x = Constants.SCREEN_WIDTH/2.0 - Constants.COUNTDOWN_FONT_SIZE;
            }

            lastCountChange = currentTime;

            if (currentCount < 0){
                isRunning = false;
                Main.startGameAfterCountdown();
            }
        }
    }

    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        countDownText.draw(g2);
    }

    public void stop(){
        isRunning = false;
    }

    @Override
    public void run() {
        while(isRunning){
            update();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.dispose();
    }
}
