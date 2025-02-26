public class Main {
    public static int state = Constants.STATE_MENU;
    public static Thread mainThread;
    public static Mainmenu menu;
    public static CountDownScreen countDown;
    public static Window window;

    public static void main(String[] args) {
        menu = new Mainmenu();
        mainThread = new Thread(menu);
        mainThread.start();
    }

    public static void changeState(int newState){
        if (state == Constants.STATE_MENU && menu != null){
            menu.stop();
        }else if(state == Constants.STATE_COUNTDOWN && countDown != null){
            countDown.stop();
        }else if (state == Constants.STATE_GAME && window != null){
            window.stop();
        }

        if (newState == Constants.STATE_MENU){
            menu = new Mainmenu();
            mainThread = new Thread(menu);
            mainThread.start();
        } else if (newState == Constants.STATE_COUNTDOWN) {
            countDown = new CountDownScreen();
            mainThread = new Thread(countDown);
            mainThread.start();
        } else if (newState == Constants.STATE_GAME) {
            window = new Window();
            mainThread = new Thread(window);
            mainThread.start();
        } else if (newState == Constants.STATE_EXIT) {
            System.exit(0);
        }
        state = newState;
    }

    public static void startGameAfterCountdown(){
        changeState(Constants.STATE_GAME);
    }
}
