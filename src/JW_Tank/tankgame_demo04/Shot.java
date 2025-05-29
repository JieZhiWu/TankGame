package JW_Tank.tankgame_demo04;

public class Shot implements Runnable{
    private int x;
    private int y;
    private int direct = 0; //方向 0123 上右下左
    private int speed = 1;
    private boolean isLive = true;

    public Shot(int x, int y, int direct) {
        this.x = x-5;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(6);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ///根据方向改变子弹(x,y)位置
            switch (direct) {
                case 0: //上
                    y -= 2;
                    break;
                case 1: //右
                    x += 2;
                    break;
                case 2: //下
                    y += 2;
                    break;
                case 3: //左
                    x -= 2;
                    break;
                default:
                    ;
            }
            //测试
//            System.out.println("子弹x=" + x + " y=" + y);
        if (!(x >= 25 && x <= 975 && y >= 20  && y <= 740 && isLive)){
                isLive= false;
                break;
            }
        }
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

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


}
