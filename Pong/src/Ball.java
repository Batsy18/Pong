public class Ball {
    public Rect rect;
    public Rect leftPaddle, rightPaddle;
    public Text leftScoreText, rightScoreText;

    private double vy = 100.0;
    private double vx = -150.0;

    public Ball(Rect rect, Rect leftPaddle, Rect rightPaddle, Text leftScoreText, Text rightScoreText) {
        this.rect = rect;
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
        this.leftScoreText = leftScoreText;
        this.rightScoreText = rightScoreText;
    }

    public void update(double dt) {

        if (vx < 0) {
            if (this.rect.x <= leftPaddle.x + leftPaddle.width &&
                    this.rect.x + this.rect.width >= leftPaddle.x &&
                    this.rect.y >= leftPaddle.y &&
                    this.rect.y <= leftPaddle.y + leftPaddle.height) {

                handlePaddleCollision(leftPaddle, true);
                increaseSpeed(Constants.PADDLE_SPEED_INCREASE);
            } else if (this.rect.x + this.rect.width < leftPaddle.x) {
                System.out.println("Player has lost a point");
            }
        }

        else if (vx > 0) {
            if (this.rect.x + this.rect.width >= rightPaddle.x &&
                    this.rect.x <= rightPaddle.x + rightPaddle.width &&
                    this.rect.y >= rightPaddle.y &&
                    this.rect.y <= rightPaddle.y + rightPaddle.height) {

                handlePaddleCollision(rightPaddle, false);
                increaseSpeed(Constants.PADDLE_SPEED_INCREASE);
            } else if (this.rect.x + this.rect.width > rightPaddle.x + rightPaddle.width) {
                System.out.println("AI has lost a point");
            }
        }


        if (vy > 0 && this.rect.y + this.rect.height > Constants.SCREEN_HEIGHT) {
            this.vy *= -1;
            increaseSpeed(Constants.WALL_SPEED_INCREASE);
        } else if (vy < 0 && this.rect.y < Constants.TOOLBAR_HEIGHT) {
            this.vy *= -1;
            increaseSpeed(Constants.WALL_SPEED_INCREASE);
        }


        this.rect.x += vx * dt;
        this.rect.y += vy * dt;

        if (this.rect.x + this.rect.width < leftPaddle.x ){
            int rightScore = Integer.parseInt(rightScoreText.text);
            rightScore++;
            rightScoreText.text = "" + rightScore;
            resetBall();
            if (rightScore > Constants.WIN_SCORE){
                Main.changeState(0);
            }
        } else if (this.rect.x > rightPaddle.x + rightPaddle.width) {
            int leftScore = Integer.parseInt(leftScoreText.text);
            leftScore++;
            leftScoreText.text = "" + leftScore;
            resetBall();
            if (leftScore > Constants.WIN_SCORE){
                Main.changeState(2);
            }
        }
    }

    private void resetBall(){
        this.rect.x = Constants.SCREEN_WIDTH/2.0;
        this.rect.y = Constants.SCREEN_HEIGHT/2.0;

        double initialSpeed = 150.0;
        double randomAngle = Math.random() * Math.PI / 4 - Math.PI / 8;

        double direction = Math.random() > 0.5 ? 1.0 : -1.0;

        this.vx = Math.cos(randomAngle) * initialSpeed * direction;
        this.vy = Math.sin(randomAngle) * initialSpeed * (Math.random() > 0.5 ? 1.0 : -1.0);
    }

    private void increaseSpeed(double speedIncrease){
        double currentSpeed = Math.sqrt(vx * vx + vy * vy);

        if (currentSpeed < Constants.MAX_SPEED){
            double newSpeed = Math.min(currentSpeed + speedIncrease, Constants.MAX_SPEED);

            double scale = newSpeed / currentSpeed;
            vx *= scale;
            vy *= scale;

            System.out.println("Speed increased to " + newSpeed);
        }
    }

    private void handlePaddleCollision(Rect paddle, boolean isLeftPaddle) {

        double relativeIntersectY = (paddle.y + (paddle.height / 2.0)) - (this.rect.y + (this.rect.height / 2.0));
        double normalizedIntersectY = relativeIntersectY / (paddle.height / 2.0);


        double bounceAngle = normalizedIntersectY * (Constants.MAX_ANGLE * Math.PI / 180.0);


        double speed = Math.sqrt(vx * vx + vy * vy);


        vx = Math.cos(bounceAngle) * speed * (isLeftPaddle ? 1 : -1);
        vy = -Math.sin(bounceAngle) * speed;


        if (isLeftPaddle) {
            this.rect.x = paddle.x + paddle.width + 1;
        } else {
            this.rect.x = paddle.x - this.rect.width - 1;
        }
    }
}