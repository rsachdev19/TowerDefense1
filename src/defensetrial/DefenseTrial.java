package defensetrial;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DefenseTrial extends JPanel implements KeyListener, MouseListener, MouseMotionListener, ActionListener {

    public static int tick;
    public static final int TICK = 20;
    public static final int FPS = 1000 / TICK;

    public boolean gameOver2 = false;
    public static int width = Toolkit.getDefaultToolkit().getScreenSize().width - 100;
    public static int normalWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int height = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
    public static int normalHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    public Rectangle rectBase = new Rectangle(width / 2, height / 2, 100, 100);
    public Rectangle rectEnemy /*= new Rectangle()*/;
    public Rectangle rectBullet /*= new Rectangle()*/;
    public JFrame frame;
    public Random randy = new Random();
    public BufferedImage base;
    public int location;
    public int xEnemy;
    public int yEnemy;
    public int cash;
    public int highScore;
    int healthBought = 0;
    int strengthBulletBought = 0;
    public int health = 50 + (healthBought * 10);
    public boolean penetratingBullets = false;
    public int optionPane;
    public int xCharacter = 500;
    public int yCharacter = 500;
    public boolean up;
    public boolean down;
    public boolean right;
    public boolean left;
    public boolean enemyBulletCollided = false;
    public boolean enemyBaseCollided = false;
    public boolean shotBullet = false;
    public boolean gameOver = false;
    public int xBullet = 0;
    public int yBullet = 0;
    Timer timer = new Timer(20/*change to vary frequency*/, this);
    public static Font font = new Font("Arial", Font.BOLD, 50);
    public static Font fontStart = new Font("Arial", Font.BOLD, 20);
    public static int level = 1;
    public static ArrayList<Integer> enemyList = new ArrayList<>();
    public int currentEnemy;
    public int enemyListLength;
    public int enemyKilled = 0;
    public boolean levelCompleted = false;
    public boolean startScreen = true;
    public boolean gameScreen = false;
    public boolean upCharacter;
    public boolean downCharacter;
    public boolean leftCharacter;
    public boolean rightCharacter;
    public boolean downCharacterMove;
    public boolean upCharacterMove;
    public boolean rightCharacterMove;
    public boolean leftCharacterMove;
    public boolean upBulletStart;
    public boolean rightBulletStart;
    public boolean leftBulletStart;
    public boolean downBulletStart;
    public int yBulletStart;
    public int xBulletStart;
    public boolean shotBulletStart;
    public ArrayList<Enemy> enemyObjectList = new ArrayList();
    public ArrayList<Bullet> bulletObjectList = new ArrayList();
    public static int rectWidth = 25;

    public static void main(String[] args) {
        DefenseTrial project = new DefenseTrial("Ravjot's Tower Defense Game");
        enemyList.add(5);
        enemyList.add(7);
        enemyList.add(9); //Add number of enemies for 1st three levels

    }

    public DefenseTrial(String title) {
        try {
            base = ImageIO.read(getClass().getResource("base.png"));
        } catch (IOException ex) {
            Logger.getLogger(DefenseTrial.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame = new JFrame(title);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(this);
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
        timer.start();
        currentEnemy = 5; //Set the number of enemies in the first level = 5

    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(font);
        if (!startScreen) {
            background(g); //Constantly displays cash and level once you start the game
        }
        if (startScreen) { //Will only work during the start screen. Start screen becomes false when you press enter
            character(g, xCharacter, yCharacter);
            startScreenBackground(g);
            if (shotBulletStart) { //Controls movement of the bullet
                g.setColor(Color.red);
                g.fillRect(xBulletStart, yBulletStart, rectWidth, rectWidth);
                if (rightBulletStart) {
                    xBulletStart += 3;
                }
                if (leftBulletStart) {
                    xBulletStart -= 3;
                }
                if (downBulletStart) {
                    yBulletStart += 3;
                }
                if (upBulletStart) {
                    yBulletStart -= 3;
                }
                if (xBulletStart < 0 || xBulletStart > normalWidth || yBulletStart < 0 || yBulletStart > normalHeight) {
                    shotBulletStart = false;
                }
            }
        }
        if (levelCompleted) { //Goes to upgrade screen after destroying all enemies in a level
            upgradeScreen(g);
        }
        int x = 0;
        int y = 0;
        int velocity;
        int enemyDirection;
        int bulletDirection; //5 temporary variables to control enemy and bullet
        if (gameScreen & !gameOver2) {
            gameBackground(g);
            g.setColor(Color.blue);
            for (int i = 0; i < enemyObjectList.size(); i++) { //An arrayList of enemy objects that each have their own x & y variables, direction, and velocities
                x = enemyObjectList.get(i).getxCoord();
                y = enemyObjectList.get(i).getyCoord();
                velocity = enemyObjectList.get(i).getVelocity(); //To change x or y position of the enemy depending on the randomly generated speed
                switch (enemyObjectList.get(i).getHealth()) {
                    case 1:
                        g.setColor(Color.blue);
                        break;
                    case 2:
                        g.setColor(Color.CYAN);
                        break;
                    case 3:
                        g.setColor(Color.BLACK);
                        break;
                    case 4:
                        g.setColor(Color.DARK_GRAY);
                        break;
                    case 5:
                        g.setColor(Color.GREEN);
                        break;
                    case 6:
                        g.setColor(Color.LIGHT_GRAY);
                        break;
                    case 7:
                        g.setColor(Color.MAGENTA);
                        break;
                    case 8:
                        g.setColor(Color.ORANGE);
                        break;
                    case 9:
                        g.setColor(Color.PINK);
                        break;
                    case 10:
                        g.setColor(Color.YELLOW);
                } //Changes color of the enemy depending on the health
                g.fillRect(x, y, rectWidth, rectWidth);
                rectEnemy = new Rectangle(x, y, rectWidth, rectWidth);
                enemyObjectList.get(i).setRectEnemy(rectEnemy); //To be used to detect collision
                enemyDirection = enemyObjectList.get(i).getDirection(); //Changes direction of an enemy according to the direction assigned
                if (enemyDirection == 0) { //Spawning on the left
                    enemyObjectList.get(i).setxCoord(x + velocity);
                }
                if (enemyDirection == 1) { //Spawning on the top
                    enemyObjectList.get(i).setyCoord(y + velocity);
                }
                if (enemyDirection == 2) { //Spawning on the right
                    enemyObjectList.get(i).setxCoord(x - velocity);
                }
                if (enemyDirection == 3) { //Spawning on the bottom
                    enemyObjectList.get(i).setyCoord(y - velocity);
                }
            }
            g.setColor(Color.red);
            for (int i = 0; i < bulletObjectList.size(); i++) {
                x = bulletObjectList.get(i).getxCoord();
                y = bulletObjectList.get(i).getyCoord();
                g.fillRect(x, y, rectWidth, rectWidth);
                rectBullet = new Rectangle(x, y, rectWidth, rectWidth);
                bulletObjectList.get(i).setRectBullet(rectBullet);
                bulletDirection = bulletObjectList.get(i).getDirection();
                if (bulletDirection == 0) {
                    bulletObjectList.get(i).setxCoord(x + 3);
                }
                if (bulletDirection == 1) {
                    bulletObjectList.get(i).setyCoord(y + 3);
                }
                if (bulletDirection == 2) {
                    bulletObjectList.get(i).setxCoord(x - 3);
                }
                if (bulletDirection == 3) {
                    bulletObjectList.get(i).setyCoord(y - 3);
                }
                if (x < 0 || x > normalWidth || y < 0 || y > normalWidth) {
                    bulletObjectList.remove(i); //Gets rid of an bullet from the array list if it goes off screen
                }
            }
            try {
                for (int i = enemyObjectList.size() - 1; i >= 0; i--) {
                    for (int j = bulletObjectList.size() - 1; j >= 0; j--) {
                        if (enemyObjectList.get(i).getRectEnemy().intersects(bulletObjectList.get(j).getRectBullet())) {
                            enemyObjectList.get(i).setHealth(enemyObjectList.get(i).getHealth() - (strengthBulletBought + 1));
                            if (enemyObjectList.get(i).getHealth() <= 0) {
                                cash += 10 * (enemyObjectList.get(i).getOriginalHealth()); //Increases cash according to the original health of the enemy
                                enemyObjectList.remove(i); //Removes enemy if it collides with a bullet
                                checkEnemyKillCount(); //See if enemy count of level goes less than 0
                            }
                            if (!penetratingBullets) {
                                bulletObjectList.remove(j); //Will only remove enemy from the array list if player has not bought the last upgrade for bullets
                            }
                        }
                    }

                }
                for (int i = enemyObjectList.size() - 1; i >= 0; i--) {
                    if (enemyObjectList.get(i).getRectEnemy().intersects(rectBase)) {
                        health -= 10 * enemyObjectList.get(i).getHealth();
                        enemyObjectList.remove(i);
                        checkEnemyKillCount();
                    }
                }
            } catch (Exception e) {
                //System.out.println("Error.");
            }
        }
        if (health <= 0) {
            health = 0;
            gameOver = true;
        }
        if (gameOver && !gameOver2) { //What to do if they lose
            gameOver = false;
            gameOver2 = true;//Game Over2 prevents an error with JOptionPane and repaint()

            optionPane = JOptionPane.showConfirmDialog(frame, "Would you like to try again?", "Game over.", JOptionPane.YES_NO_OPTION);
            if (optionPane == 0) { //0 = Yes
                restart(); //Restart method resets variables
            }
            if (optionPane == 1) { //1 = no
                System.exit(73);
            }
        }
        repaint();
    }

    public void startScreenBackground(Graphics g) {
        g.setFont(fontStart);
        g.drawString("Use WASD to shoot in 4 directions.", 300, 20);
        g.drawString("Press enter to continue to the game.", 300, 50);
        g.drawString("In the actual game, you will not be able to move your base. It will be located at the center of the screen.", 300, 80);
        g.drawString("Defend your base from incoming enemies and survive as long as possible.", 300, 110); //Drawn at the start screen
    }

    public void character(Graphics g, int x, int y) {
        g.drawOval(x - 12, y - 25, 25, 25);
        g.drawLine(x, y, x, y + 50);
        g.drawLine(x, y + 50, x - 10, y + 75);
        g.drawLine(x, y + 50, x + 10, y + 75); //Draw stick figure at the start screen

    }

    public void background(Graphics g) {
        g.setFont(font);
        g.drawString("Cash:" + cash, 0, 50);
        g.drawString("Level:" + level, width - 100, 50);

    }

    public void gameBackground(Graphics g) {
        g.drawImage(base, width / 2, height / 2, 100, 100, this);
        g.drawString("Health:" + health, width - 200, height);
        g.drawString("High Score:" + highScore, 0, 100);
        g.drawString("Enemies left:" + (currentEnemy - enemyKilled), 0, height); //Drawn while the user is in the game
    }

    public void restart() { //Used to reset variables
        health = 50;
        level = 1;
        enemyObjectList.clear();
        bulletObjectList.clear();
        healthBought = 0;
        strengthBulletBought = 0;
        penetratingBullets = false;
        levelCompleted = false;
        gameScreen = true;
        gameOver2 = false;
        gameOver = false;
        if (highScore < cash) {
            highScore = cash;
        } else {
            System.out.println("You did not beat your high score");
        }
        cash = 0;
        timer.start();
        currentEnemy = enemyList.get(0);
        enemyKilled = 0;
    }

    public void newLevel() { //Resets health at every new level. Adds 2 more enemies to each level
        level++;
        health = 50 + (healthBought * 10); //Use a boolean. If difficultyHard = true, health does not reset. If difficultyEasy,health resets every level.
        enemyListLength = enemyList.size();
        enemyList.add(enemyList.get(enemyListLength - 1) + 2); //Adds 2 enemies to each new level
        currentEnemy = enemyList.get(level - 1); //Changes the number of enemies according to the level
        enemyKilled = 0;
        enemyObjectList.clear();
        bulletObjectList.clear();

    }

    public void checkEnemyKillCount() {
        enemyKilled++;
        if (enemyKilled == currentEnemy) {
            levelCompleted = true;
            gameScreen = false;
        }
    }

    public void upgradeScreen(Graphics g) { //Draw upgrade boxes seen in the upgrade screen
        g.drawString("Continue", width - 200, height);
        g.setColor(Color.white);
        g.fillRect(100, 100, 100, 100); //Health upgrade
        g.fillRect(250, 100, 100, 100); //Bullet upgrade
        g.setFont(fontStart);
        g.setColor(Color.red);
        g.fillRect(135, 100, 30, 100);
        g.fillRect(100, 135, 100, 30);
        g.setColor(Color.black);
        g.drawString("Health+10", 100, 165);
        g.drawString("Price:" + (healthBought + 1) * 10, 100, 215); //Health upgrade
        g.drawString("Bullets", 250, 165);
        if (!penetratingBullets) {
            g.drawString("Price:" + (strengthBulletBought + 1) * 75, 250, 215);
            g.setColor(Color.black);
            g.drawLine(300, 200, 300, 600);
            g.drawLine(300, 200, 250, 250);
            g.drawLine(300, 200, 350, 250);
            g.drawString("If you buy this upgrade enough,", 250, 625);
            g.drawString("your bullets will go through enemies!", 250, 650);

        }

    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) { //Exit system
            System.exit(73);
        }
        if (gameScreen) { //Control shooting in the actual game
            if (ke.getKeyCode() == KeyEvent.VK_W && !shotBullet) {
                shotBullet = true;
                bulletObjectList.add(new Bullet(3));
            }
            if (ke.getKeyCode() == KeyEvent.VK_A && !shotBullet) {
                shotBullet = true;
                bulletObjectList.add(new Bullet(2));
            }
            if (ke.getKeyCode() == KeyEvent.VK_S && !shotBullet) {
                shotBullet = true;
                bulletObjectList.add(new Bullet(1));
            }
            if (ke.getKeyCode() == KeyEvent.VK_D && !shotBullet) {
                shotBullet = true;
                bulletObjectList.add(new Bullet(0));
            }
        }

        if (startScreen) { //Control shooting when the stick figure is present in the start screen
            if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                downCharacterMove = true;
            }
            if (ke.getKeyCode() == KeyEvent.VK_UP) {
                upCharacterMove = true;
            }
            if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                leftCharacterMove = true;
            }
            if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightCharacterMove = true;
            }
            if (!shotBulletStart) {
                if (ke.getKeyCode() == KeyEvent.VK_W) {
                    upBulletStart = true;
                    downBulletStart = false;
                    leftBulletStart = false;
                    rightBulletStart = false;
                    xBulletStart = xCharacter;
                    yBulletStart = yCharacter;
                    shotBulletStart = true;
                }
                if (ke.getKeyCode() == KeyEvent.VK_A) {
                    leftBulletStart = true;
                    downBulletStart = false;
                    upBulletStart = false;
                    rightBulletStart = false;
                    xBulletStart = xCharacter;
                    yBulletStart = yCharacter;
                    shotBulletStart = true;
                }
                if (ke.getKeyCode() == KeyEvent.VK_S) {
                    downBulletStart = true;
                    upBulletStart = false;
                    leftBulletStart = false;
                    rightBulletStart = false;
                    xBulletStart = xCharacter;
                    yBulletStart = yCharacter;
                    shotBulletStart = true;
                }
                if (ke.getKeyCode() == KeyEvent.VK_D) {
                    rightBulletStart = true;
                    downBulletStart = false;
                    leftBulletStart = false;
                    upBulletStart = false;
                    xBulletStart = xCharacter;
                    yBulletStart = yCharacter;
                    shotBulletStart = true;
                }
            }
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                startScreen = false;
                gameScreen = true;
            }
        } //End of if (startScreen)
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (startScreen) { //Control movement of stick figure
            if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                downCharacterMove = false;
            }
            if (ke.getKeyCode() == KeyEvent.VK_UP) {
                upCharacterMove = false;
            }
            if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightCharacterMove = false;
            }
            if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                leftCharacterMove = false;
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (levelCompleted) { //Only works during the upgrade screen, after a level is compelted to control whether or not the player can buy upgrades
            if (me.getX() < normalWidth && me.getX() > width - 200 && me.getY() > height - 100) { //This where "Continue" is located
                levelCompleted = false;
                gameScreen = true;
                newLevel();
            }
            if (me.getX() > 100 && me.getX() < 200 && me.getY() > 100 && me.getY() < 200 && cash >= (10 * (healthBought + 1))) { //This is where the health upgrade is located
                healthBought++;
                cash -= 10 * (healthBought);
            }
            if (me.getX() > 250 && me.getX() < 350 && me.getY() > 100 && me.getY() < 200 && cash >= (75 * (strengthBulletBought + 1)) && strengthBulletBought < 5) {
                cash -= (75 * (strengthBulletBought + 1));
                strengthBulletBought++; //Increases damage of each bullet
            }
            if (me.getX() > 250 && me.getX() < 350 && me.getY() > 100 && me.getY() < 200 && cash >= (75 * (strengthBulletBought + 1)) && strengthBulletBought == 5) {
                penetratingBullets = true;
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //runs whenever the mouse is moved across the screen, same methods as mouseClicked
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        tick++;
        if (tick % (FPS * .25) == 0) {
            shotBullet = false;
        }
        if (gameScreen) {
            if (tick % (FPS * 1) == 0) {
                location = randy.nextInt(4);
                enemyObjectList.add(new Enemy(location));
            }
        } //Control enemy spawn with random variables
        if (rightCharacterMove) {
            xCharacter += 5;
        }
        if (leftCharacterMove) {
            xCharacter -= 5;
        }
        if (upCharacterMove) {
            yCharacter -= 5;
        }
        if (downCharacterMove) {
            yCharacter += 5;
        }
    }

}
