package JW_Tank.tankgame_demo04;

import java.util.Vector;

/**
 * @author JieWu
 * @version 1.0
 */
public class MyTank extends JW_Tank.tankgame_demo04.Tank {
    //定义Shot子弹对象
    private int mylive = 0;
    JW_Tank.tankgame_demo04.Shot shot = null;
    Vector<JW_Tank.tankgame_demo04.Shot> shots = new Vector<>();

    public MyTank(int x, int y) {
        super(x, y, 1);
    }

    public void remake(){
        setX(450);
        setY(550);
        setDirect(0);
        if (mylive>0) {
            mylive--;
        }
        setLive(true);
    }

    //射击
    public void shotTank() {

        if(shots.size() ==  getShotNum()){
            return;
        }
        //根据方向创建Shot子弹
        switch (this.getDirect()) { //获取坦克方向
            case 0:
                shot = new JW_Tank.tankgame_demo04.Shot(this.getX() + 20, this.getY(), 0);
                break;
            case 1:
                shot = new JW_Tank.tankgame_demo04.Shot(this.getX() + 60, this.getY() + 20, 1);
                break;
            case 2:
                shot = new JW_Tank.tankgame_demo04.Shot(this.getX() + 20, this.getY() + 60, 2);
                break;
            case 3:
                shot = new JW_Tank.tankgame_demo04.Shot(this.getX(), this.getY() + 20, 3);
                break;
        }

        //把 shot放入vector集合
        shots.add(shot);
        //启动Shot子弹线程
        new Thread(shot).start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 判断我方坦克是否被消灭
    public boolean isDestroyed() {
        return mylive <= 0;
    }

    public int getMylive() {
        return mylive;
    }

    public void setMylive(int mylive) {
        this.mylive = mylive;
    }
}
