package JW_Tank.tankgame_demo04;

import java.util.Vector;

public class EnemyTank extends JW_Tank.tankgame_demo04.Tank implements Runnable{
    //创建坦克子弹,保存多个子弹
    Vector<JW_Tank.tankgame_demo04.Shot> shots = new Vector<>();

    //增加成员othertanks,获取其他坦克对象
    Vector<EnemyTank> enemyTanks = new Vector<>();

    public EnemyTank(int x, int y) {
        super(x, y, 0);
    }

    //获取Enemytanks坦克对象
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //判断enemyTanks是否碰撞
    public boolean isTouchEnemyTank(){

        //依据坦克方向,比较位置
        switch (this.getDirect()) {
            case 0:
                //和其他坦克比
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank otherTank = enemyTanks.get(i);
                    //不和自己比
                    if (otherTank != this) {
                        switch (otherTank.getDirect()) {
                            case 0:
                            case 2:
                                if (this.getX() >= otherTank.getX() &&
                                        this.getX() <= otherTank.getX() + 40 &&
                                        this.getY() >= otherTank.getY() &&
                                        this.getY() <= otherTank.getY() + 60)
                                    return true;
                                if (this.getX() >= otherTank.getX() &&
                                        this.getX() <= otherTank.getX() + 60 &&
                                        this.getY() >= otherTank.getY() &&
                                        this.getY() <= otherTank.getY() + 40)
                                    return true;
                            case 1:
                            case 3:
                                if (this.getX() >= otherTank.getX() &&
                                        this.getX() <= otherTank.getX() + 60 &&
                                        this.getY() >= otherTank.getY() &&
                                        this.getY() <= otherTank.getY() + 40)
                                    return true;
                                if (this.getX() + 40 >= otherTank.getX() &&
                                        this.getX() + 40 <= otherTank.getX() + 60 &&
                                        this.getY() >= otherTank.getY() &&
                                        this.getY() <= otherTank.getY() + 40)
                                    return true;
                        }
                    }
                }
                break;
            case 1:
                //和其他坦克比
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank otherTank = enemyTanks.get(i);
                    //不和自己比
                    if (otherTank != this) {
                        switch (otherTank.getDirect()) {
                            case 0:
                            case 2:
                                if (this.getX() +60 >= otherTank.getX() &&
                                        this.getX() +60 <= otherTank.getX() + 40 &&
                                        this.getY() >= otherTank.getY() &&
                                        this.getY() <= otherTank.getY() + 60)
                                    return true;
                                if (this.getX() +60 >= otherTank.getX() &&
                                        this.getX() +60 <= otherTank.getX() + 40 &&
                                        this.getY()+40 >= otherTank.getY() &&
                                        this.getY()+40 <= otherTank.getY() + 60)
                                    return true;
                            case 1:
                            case 3:
                                if (this.getX() +60 >= otherTank.getX() &&
                                        this.getX()+60 <= otherTank.getX() + 60 &&
                                        this.getY() >= otherTank.getY() &&
                                        this.getY() <= otherTank.getY() + 40)
                                    return true;
                                if (this.getX() + 60 >= otherTank.getX() &&
                                        this.getX() + 60 <= otherTank.getX() + 60 &&
                                        this.getY() +40>= otherTank.getY() &&
                                        this.getY() +40<= otherTank.getY() + 40)
                                    return true;
                        }
                    }
                }
                break;
            case 2:
                //和其他坦克比
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank otherTank = enemyTanks.get(i);
                    //不和自己比
                    if (otherTank != this) {
                        switch (otherTank.getDirect()) {
                            case 0:
                            case 2:
                                if (this.getX() >= otherTank.getX() &&
                                        this.getX() <= otherTank.getX() + 40 &&
                                        this.getY()+60 >= otherTank.getY() &&
                                        this.getY()+60 <= otherTank.getY() + 60)
                                    return true;
                                if (this.getX()+40 >= otherTank.getX() &&
                                        this.getX()+40 <= otherTank.getX() + 40 &&
                                        this.getY() +60>= otherTank.getY() &&
                                        this.getY() +60<= otherTank.getY() + 60)
                                    return true;
                            case 1:
                            case 3:
                                if (this.getX() >= otherTank.getX() &&
                                        this.getX() <= otherTank.getX() + 60 &&
                                        this.getY()+60 >= otherTank.getY() &&
                                        this.getY()+60 <= otherTank.getY() + 40)
                                    return true;
                                if (this.getX() + 40 >= otherTank.getX() &&
                                        this.getX() + 40 <= otherTank.getX() + 60 &&
                                        this.getY() +60>= otherTank.getY() &&
                                        this.getY() +60<= otherTank.getY() + 40)
                                    return true;
                        }
                    }
                }
                break;
            case 3:
                //和其他坦克比
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank otherTank = enemyTanks.get(i);
                    //不和自己比
                    if (otherTank != this) {
                        switch (otherTank.getDirect()) {
                            case 0:
                            case 2:
                                if (this.getX() >= otherTank.getX() &&
                                        this.getX() <= otherTank.getX() + 40 &&
                                        this.getY() >= otherTank.getY() &&
                                        this.getY() <= otherTank.getY() + 60)
                                    return true;
                                if (this.getX()  >= otherTank.getX() &&
                                        this.getX()  <= otherTank.getX() + 40 &&
                                        this.getY() + 40 >= otherTank.getY() &&
                                        this.getY() + 40 <= otherTank.getY() + 60)
                                    return true;
                            case 1:
                            case 3:
                                if (this.getX()  >= otherTank.getX() &&
                                        this.getX()  <= otherTank.getX() + 60 &&
                                        this.getY() >= otherTank.getY() &&
                                        this.getY() <= otherTank.getY() + 40)
                                    return true;
                                if (this.getX()  >= otherTank.getX() &&
                                        this.getX()  <= otherTank.getX() + 60 &&
                                        this.getY() + 40 >= otherTank.getY() &&
                                        this.getY() + 40 <= otherTank.getY() + 40)
                                    return true;
                        }
                    }
                }
                break;

        }
        return false;
    }

    @Override
    public void run() {
        int n = 0;
        int rand = (int)(Math.random()*14)+16;
        setSpeed(rand%3+4);
        setDirect(rand%4);
        while (true){
            //如果没有子弹了, 再次创建子弹
            if(isLive() && shots.size() == 0){
//                try {
//                    Thread.sleep(rand*20);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                //根据方向创建子弹
                JW_Tank.tankgame_demo04.Shot shot = null;
                try {
                    Thread.sleep(rand*20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                switch (this.getDirect()) { //获取坦克方向
                    case 0:
                        shot = new JW_Tank.tankgame_demo04.Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        shot = new JW_Tank.tankgame_demo04.Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2:
                        shot = new JW_Tank.tankgame_demo04.Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3:
                        shot = new JW_Tank.tankgame_demo04.Shot(getX(), getY() + 20, 3);
                        break;
                }
                shots.add(shot);
                new Thread(shot).start();

            }


            //根据方向移动
            switch (getDirect()){
                case 0:
                    for (int i=0; i<getSpeed()&&!isTouchEnemyTank(); i++) {
                        moveUp();
                    }
                    break;
                case 1:
                    for (int i=0; i<getSpeed()&&!isTouchEnemyTank(); i++) {
                        moveRight();
                    }
                    break;
                case 2:
                    for (int i=0; i<getSpeed()&&!isTouchEnemyTank(); i++) {
                        moveDown();
                    }
                    break;
                case 3:
                    for (int i=0; i<getSpeed()&&!isTouchEnemyTank(); i++) {
                        moveLeft();
                    }
                    break;
            }
            n++;

            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            //随机改变方向
            if (n%(rand+5)==0 || getX()<=25 +10 || getX()>=975-60 -25 ||
                    getY()<=20 +25 || getY()>=740-60 -20 || isTouchEnemyTank()) {
                int t_direct = (int)(Math.random() * 4);
                //如果坦克碰到什么了，就停一会, 再改变方向
                if (getX()<=25 +10 || getX()>=975-60 -25 ||
                        getY()<=20 +25 || getY()>=740-60 -20 || isTouchEnemyTank()) {
                    try {
                        while (t_direct == getDirect()){
                            t_direct = (int)(Math.random() * 4);
                            Thread.sleep(300);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                setDirect(t_direct);
            }

            //退出线程
            if(isLive()==false){
                break;
            }
        }
    }
}
