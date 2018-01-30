package defensetrial;

import java.awt.Rectangle;
import java.util.Random;

public class Enemy {

    private int xCoord;
    private int yCoord;
    private int direction; //0 = Spawning on the left
    //1 = Spawning on the top
    //2 = Spawning on the right
    //3 = Spawning on the bottom
    private boolean collided;
    private Rectangle rectEnemy;
    private int health;
    private int originalHealth;
    private int velocity;
    Random randy = new Random();

    public Enemy(int myDirection) {
        direction = myDirection;
        velocity = randy.nextInt(3) + 1;
        if (DefenseTrial.level > 1) {
            health = randy.nextInt(DefenseTrial.level) + 1;
        }
        else {
            health = 1;
        }
        originalHealth = health;

        if (direction == 0) {
            this.xCoord = 0;
            this.yCoord = DefenseTrial.height / 2;
        }

        if (direction == 1) {
            this.xCoord = DefenseTrial.width / 2;
            this.yCoord = 0;
        }

        if (direction == 2) {
            this.xCoord = DefenseTrial.width;
            this.yCoord = DefenseTrial.height / 2;
        }

        if (direction == 3) {
            this.xCoord = DefenseTrial.width / 2;
            this.yCoord = DefenseTrial.height;
        }

    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean getCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    public Rectangle getRectEnemy() {
        return rectEnemy;
    }

    public void setRectEnemy(Rectangle rectEnemy) {
        this.rectEnemy = rectEnemy;
    }

    public void setHealth(int myHealth) {
        health = myHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setOriginalHealth(int myOriginalHealth) {
        originalHealth = myOriginalHealth;
    }

    public int getOriginalHealth() {
        return originalHealth;
    }

    public void setVelocity(int myVelocity) {
        velocity = myVelocity;
    }

    public int getVelocity() {
        return velocity;
    }

}
