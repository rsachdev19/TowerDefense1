package defensetrial;

import java.awt.Rectangle;

public class Bullet {

    private int xCoord;
    private int yCoord;
    private boolean collided;
    private Rectangle rectBullet;
    private int direction; //0 = going right
    //1 = going down
    //2 = going left
    //3 = going up

    public Bullet(int newDirection) {
        direction = newDirection;
        if (direction == 0) {
            this.xCoord = DefenseTrial.width / 2 + 75;
            this.yCoord = DefenseTrial.height / 2;
        }
        if (direction == 1) {
            this.xCoord = DefenseTrial.width / 2;
            this.yCoord = DefenseTrial.height / 2 + 75;
        }
        if (direction == 2) {
            this.xCoord = DefenseTrial.width / 2;
            this.yCoord = DefenseTrial.height / 2;
        }
        if (direction == 3) {
            this.xCoord = DefenseTrial.width / 2;
            this.yCoord = DefenseTrial.height / 2;
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

    public boolean isCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    public Rectangle getRectBullet() {
        return rectBullet;
    }

    public void setRectBullet(Rectangle rectBullet) {
        this.rectBullet = rectBullet;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

}
