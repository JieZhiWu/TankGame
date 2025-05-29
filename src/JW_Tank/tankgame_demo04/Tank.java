package JW_Tank.tankgame_demo04;

/**
 * @author JieWu
 * @version 1.0
 */
public class Tank {
    private int x; //坦克位置横坐标
    private int y; //坦克位置纵坐标
    private int direct; //坦克方向  0上 1右 2下 3左
    private int speed = 5;
    private boolean isLive = true;
    private int[] domain = new int[4];
    private int type;
    private int shotNum = 3;

    //编写坦克移动方法(上下左右)
    public void moveUp(){
        if (y> 20 +25) {
            y-=1;
        }
    }
    public void moveRight(){
        if (x<975-60 -25) {
            x+=1;
        }
    }
    public void moveDown(){
        if (y < 740-60 -20) {
            y+=1;
        }
    }
    public void moveLeft(){
        if (x>25 +10) {
            x-=1;
        }
    }



    public Tank(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getShotNum() {
        return shotNum;
    }

    public void setShotNum(int shotNum) {
        this.shotNum = shotNum;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
