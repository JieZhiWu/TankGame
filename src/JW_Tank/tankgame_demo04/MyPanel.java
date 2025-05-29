package JW_Tank.tankgame_demo04;

import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.InputStream;
import java.util.Vector;

//增加监听键盘那时间: 实现KeyListener
//为了让Panel不停重绘子弹,需要将Panel 实现Runnable,做一个线程
public class MyPanel extends JPanel implements KeyListener, Runnable {
    String key;

    //定义自己的坦克
    MyTank myTank = null;

    //定义敌人坦克,放入到Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTankSize = (int) (Math.random() * 4) + 7;
    //定义Node对象, 存放敌方坦克信息, 用于重启恢复
    Vector<Node> nodes = new Vector<>();

//    int enemyTankSize =1;
    //定义Vector存放炸弹
    //当子弹击中坦克,加入爆炸效果
    Vector<Bomb> bombs = new Vector<>();

    //定义三张图片,作为爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    private boolean running;
    private static int endChoice;

    public MyPanel(String key) {

//        //记录坦克信息,用于重启恢复
//         nodes = Recorder.getNodesAndEnemyTankRec();
        //检查记录文件是否存在
        File recordFile = new File(".\\myRecord.txt");
        if(recordFile.exists()&& key=="1") {
            nodes = Recorder.getNodesAndEnemyTankRec();
        } else {
//            System.out.println("记录文件不存在，将创建新的游戏记录");
            key ="0";
            nodes = new Vector<>();
        }

        //初始换自己的坦克
        myTank = new MyTank(450, 550);
        myTank.setSpeed(myTank.getSpeed());

        //将MyPanel中的enemyTanks 设置给 Recorder 的 enemyTanks
        Recorder.setEnemyTanks(enemyTanks);

        switch (key){
            case "0":
                Recorder.setMyTankLive(myTank.getMylive());

                //初始化敌方的坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank((int) (Math.random() * 700) + 100, (int) (Math.random() * 200) + 50); //创建坦克
                    //判断定义时是否重叠
                    while(enemyTank.isTouchEnemyTank()){
                        enemyTank = new EnemyTank((int) (Math.random() * 700) + 100, (int) (Math.random() * 300) + 50); //创建坦克
                    }
                    //获取enemyTanks的坦克对象
                    enemyTank.setEnemyTanks(enemyTanks);
                    //启动坦克线程,随机移动
                    new Thread(enemyTank).start();
                    enemyTanks.add(enemyTank); //加入集合(Vector)
                }
                break;
            case "1":
//                int mytankLive = nodes.get(2);f

                //初始化敌方的坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node nodeTank = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(nodeTank.getX(),nodeTank.getY()); //创建坦克
                    //获取enemyTanks的坦克对象 到enemyTanks集合中
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(nodeTank.getDirect()); //设置方向
                    //启动坦克线程,随机移动
                    new Thread(enemyTank).start();
                    //给敌人坦克加入子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    //启动:
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank); //加入集合(Vector)
                }
                break;
        }


        //初始化爆炸图片
        try {
//            image1 = ImageIO.read(MyPanel.class.getResource("./images/tankbomb1.png"));
//            image2 = ImageIO.read(MyPanel.class.getResource("./images/tankbomb2.png"));
//            image3 = ImageIO.read(MyPanel.class.getResource("./images/tankbomb3.png"));

            image1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/tankbomb1.png"));
            image2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/tankbomb2.png"));
            image3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/tankbomb3.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

//    //打印坦克信息
//    public void showInfo(Graphics g){
//        //打印玩家总成绩
//        g.setColor(Color.BLACK);
//        Font font = new Font("行楷", Font.BOLD, 25);
//        g.setFont(font);
//
//        g.drawString("您已击毁敌方坦克", 1020,30);
//    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 获取当前面板的宽度和高度
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // 假设右侧新增区域的宽度为200像素
        int infoPanelWidth = 200;

        // 调整原始填充矩形的宽度为面板宽度减去infoPanelWidth
        int rectWidth = (int) ((panelWidth - infoPanelWidth) * 0.96);  // 矩形宽度为剩余宽度的96%
        int rectHeight = (int) (panelHeight * 0.94); // 矩形高度为面板高度的94%

        // 计算矩形的位置，居中绘制，注意这里x轴位置不变
        int x = (panelWidth - rectWidth - infoPanelWidth) / 2;
        int y = (panelHeight - rectHeight) / 2;

        // 绘制填充矩形，颜色默认是黑色
        g.fillRect(x, y, rectWidth, rectHeight);

        // 设置字体和颜色并打印玩家总成绩到右侧新增区域
        g.setColor(Color.BLACK);
        Font font = new Font("黑体", Font.BOLD, 20);
        g.setFont(font);

        // 绘制信息显示区域背景，可选
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(panelWidth - infoPanelWidth, 0, infoPanelWidth, panelHeight);

        // 在指定位置绘制文本
        g.setColor(Color.BLACK);
        g.drawString("您已击毁敌方坦克", panelWidth - infoPanelWidth + 20, 30);
        g.drawString("我方剩余生命", panelWidth - infoPanelWidth + 20, 550);
        //绘制坦克图标
        drawTank(panelWidth - infoPanelWidth + 20, 60, g, 1, 1);
        g.setColor(Color.RED);
        g.drawString(Recorder.getKOEnemyTankNum() + "", panelWidth - infoPanelWidth + 180, 85);
        g.drawString(Recorder.getMyTankLive() + "", panelWidth - infoPanelWidth + 160, 550);
        drawTank(panelWidth - infoPanelWidth + 130 , 50, g, 0, 0);

        g.setColor(Color.BLACK);
        g.drawString("我方坦克设置:", panelWidth - infoPanelWidth + 20, 150);
        g.drawString("方向键移动", panelWidth - infoPanelWidth + 20, 175);
        g.drawString("A  减 子弹数量", panelWidth - infoPanelWidth + 20, 200);
        g.drawString("D  加 子弹数量", panelWidth - infoPanelWidth + 20, 225);
        g.drawString("W  加 坦克速度", panelWidth - infoPanelWidth + 20, 250);
        g.drawString("S  减 坦克速度", panelWidth - infoPanelWidth + 20, 275);
        g.drawString("Z  加 坦克生命", panelWidth - infoPanelWidth + 20, 300);
        g.drawString("X  加 敌人坦克数", panelWidth - infoPanelWidth + 20, 350);
        g.setColor(Color.red);
        g.drawString("!!! 按键用英文 !!!", panelWidth - infoPanelWidth + 5, 400);

        //画出自己坦克--封装方法
        if (myTank != null && myTank.isLive()) {
            drawTank(myTank.getX() /*+ 200*/, myTank.getY(), g, myTank.getDirect(), 1);
        } else if (myTank.getMylive() >= 1) {
            myTank.remake();
            drawTank(myTank.getX() /*+ 200*/, myTank.getY(), g, myTank.getDirect(), 1);
        } /*else if(myTank.getMylive() == 1){
            myTank.setMylive(0);
        }*/

        //画自己坦克射出的子弹
        for (int i = 0; i < myTank.shots.size(); i++) {
            Shot shot = myTank.shots.get(i);
            if (shot != null && shot.isLive() == true) {
                g.fill3DRect(shot.getX(), shot.getY() - 4, 8, 8, false);
            } else {
                myTank.shots.remove(shot);
            }
        }

        //画出敌人坦克,遍历Vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i); //获取每个坦克
            if (enemyTank.isLive()) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //画坦克的子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制子弹
                    if (shot.isLive()) { //如果子弹还存在
                        g.fill3DRect(shot.getX(), shot.getY() - 4, 8, 8, false);
                    } else {
                        //从Vector 移除
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }

        //如果有bombs炸弹,画爆炸效果
        for (int i = 0; i < bombs.size(); i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //取出炸弹对象
            Bomb bomb = bombs.get(i);
            if (bomb.getLife() > 6) {
                g.drawImage(image1, bomb.getX(), bomb.getY(), 60, 60, this);
            } else if (bomb.getLife() > 3) {
                g.drawImage(image1, bomb.getX(), bomb.getY(), 60, 60, this);
            } else {
                g.drawImage(image1, bomb.getX(), bomb.getY(), 60, 60, this);
            }
            //炸弹生命值--
            bomb.lifeDown();
            if (bomb.getLife() == 0) {
                bombs.remove(bomb);
            }
        }

    }

    //编写方法:绘制坦克

    /**
     * @param x      坦克左上角x坐标
     * @param y      坦克左上角y坐标
     * @param g      画笔
     * @param direct 坦克方向(上右下左)
     * @param type   坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {

        //根据(type)控制坦克类型
        switch (type) {
            case 0: //敌方坦克
                g.setColor(Color.YELLOW);
                break;
            case 1: //我方坦克
                g.setColor(Color.PINK);
                break;
        }

        //根据(direct)坦克方向,绘制对应形状坦克
        switch (direct) {
            case 0: //向上
                g.fill3DRect(x, y, 10, 60, true); //坦克左轮子
                g.fill3DRect(x + 30, y, 10, 60, true); //坦克右轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, true); //坦克盖子
                g.fill3DRect(x + 18, y - 10, 4, 32, true); //坦克枪管
                g.setColor(Color.darkGray);
                g.drawOval(x + 10, y + 20, 19, 20); //圆形盖子
                break;
            case 1: //向右
                g.fill3DRect(x, y, 60, 10, true); //坦克左轮子
                g.fill3DRect(x, y + 30, 60, 10, true); //坦克右轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, true); //坦克盖子
                g.fill3DRect(x + 40, y + 18, 32, 4, true); //坦克枪管
                g.setColor(Color.darkGray);
                g.drawOval(x + 20, y + 10, 20, 19); //圆形盖子
                break;
            case 2: //向下
                g.fill3DRect(x, y, 10, 60, true); //坦克左轮子
                g.fill3DRect(x + 30, y, 10, 60, true); //坦克右轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, true); //坦克盖子
                g.fill3DRect(x + 18, y + 40, 4, 32, true); //坦克枪管
                g.setColor(Color.darkGray);
                g.drawOval(x + 10, y + 20, 19, 20); //圆形盖子
                break;
            case 3: //向左
                g.fill3DRect(x, y, 60, 10, true); //坦克左轮子
                g.fill3DRect(x, y + 30, 60, 10, true); //坦克右轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, true); //坦克盖子
                g.fill3DRect(x - 11, y + 18, 32, 4, true); //坦克枪管
                g.setColor(Color.darkGray);
                g.drawOval(x + 20, y + 10, 20, 19); //圆形盖子
                break;
        }

        //根据(type)控制坦克类型
        switch (type) {
            case 0: //敌方坦克
                g.setColor(Color.YELLOW);
                break;
            case 1: //我方坦克
                g.setColor(Color.PINK);
                break;
        }

    }

    /*因为可以发射多颗子弹
      在判断子弹是否击中敌人是,需遍历集合中所有子弹
    * */
    public void hitEnemyTank() {

        for (int j = 0; j < myTank.shots.size(); j++) {
            Shot shot = myTank.shots.get(j);
            //判断子弹是否击中坦克
            if (shot != null && shot.isLive()) {
                //遍历所有坦克
                for (int i = 0; i < enemyTanks.size(); i++) {

                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }

    //判断是否敌方击中我方坦克
    public void hitMyTank() {
        //遍历敌人坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出敌人坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历enemtTank坦克的子弹
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                //判断是否击中我的坦克
                if (myTank.isLive() && shot.isLive()) {
                    hitTank(shot, myTank);
                    Recorder.setMyTankLive(myTank.getMylive());
                }
            }
        }
    }


    //判断是否子弹击中坦克
    public void hitTank(Shot shot, Tank tank) {
        //判断 s子弹集中坦克
        switch (tank.getDirect()) {
            case 0: //上、下
            case 2:
                if (shot.getX() > tank.getX() && shot.getX() < tank.getX() + 40
                        && shot.getY() > tank.getY() && shot.getY() < tank.getY() + 60) {
                    shot.setLive(false);
                    tank.setLive(false);
                    //击中后删除坦克对象
                    if (tank.getType()==0) {
                        int rand = (int) (Math.random() * 3);
                        switch (rand) {
                            case 0:
                                playSound("/sounds/baga.mp3");
                                break;
                            case 1:
                                playSound("/sounds/hentai.mp3");
                                break;
                            case 2:
                                playSound("/sounds/wulusai.mp3");
                                break;
                        }
                        enemyTanks.remove(tank);
                        Recorder.addKOEnemyTankNum();
                    }else { // 我方坦克被击中
                        playSound("/sounds/你干嘛.mp3");
/*                        int rand = (int) (Math.random() * 2);
                        switch (rand) {
                            case 0:
                                playSound("./sounds/yamietie.mp3");
                                break;
                            case 1:
                                playSound("./sounds/damie.mp3");
                                break;
                        }*/
                    }
                    //击中后添加爆炸效果
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1: //左右
            case 3:
                if (shot.getX() > tank.getX() && shot.getX() < tank.getX() + 60
                        && shot.getY() > tank.getY() && shot.getY() < tank.getY() + 40) {
                    shot.setLive(false);
                    tank.setLive(false);
                    //击中后删除坦克对象
                    if (tank.getType()==0) {
                        int rand = (int) (Math.random() * 3);
                        switch (rand) {
                            case 0:
                                playSound("/sounds/baga.mp3");
                                break;
                            case 1:
                                playSound("/sounds/hentai.mp3");
                                break;
                            case 2:
                                playSound("/sounds/wulusai.mp3");
                                break;
                        }
                        enemyTanks.remove(tank);
                        Recorder.addKOEnemyTankNum();
                    }else { // 我方坦克被击中
                        playSound("/sounds/你干嘛.mp3");
/*                        int rand = (int) (Math.random() * 2);
                        switch (rand) {
                            case 0:
                                playSound("./sounds/yametie.mp3");
                                break;
                            case 1:
                                playSound("./sounds/damie.mp3");
                                break;
                        }*/
                    }
                    //击中后添加爆炸效果
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    int gua = 1;
    //处理WSAD按键按下
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                myTank.setDirect(0);
                for (int i = myTank.getSpeed(); i > 0; i--) {
                    myTank.moveUp();
                    this.repaint();
                }
                break;
            case KeyEvent.VK_RIGHT:
                myTank.setDirect(1);
                for (int i = myTank.getSpeed(); i > 0; i--) {
                    myTank.moveRight();
                    this.repaint();
                }
                break;
            case KeyEvent.VK_DOWN:
                myTank.setDirect(2);
                for (int i = myTank.getSpeed(); i > 0; i--) {
                    myTank.moveDown();
                    this.repaint();
                }
                break;
            case KeyEvent.VK_LEFT:
                myTank.setDirect(3);
                for (int i = myTank.getSpeed(); i > 0; i--) {
                    myTank.moveLeft();
                    this.repaint();
                }
                break;
            case KeyEvent.VK_SPACE://如果按空格,发射子弹
//                //判断 自己坦克是否消亡
                if (myTank == null || myTank.isLive()) {
                    myTank.shotTank();
                    playSound("/sounds/鸡.mp3");
                }
                break;
            case KeyEvent.VK_W:
                if (myTank.getSpeed()<30) {
                    myTank.setSpeed(myTank.getSpeed()+3);
                    gua++;
                }
                break;
            case KeyEvent.VK_S:
                if (myTank.getSpeed()>5) {
                    myTank.setSpeed(myTank.getSpeed()-3);
                    gua--;
                }
                break;
            case KeyEvent.VK_A:
                if (myTank.getShotNum()<10) {
                    myTank.setShotNum(myTank.getShotNum()+1);
                    gua++;
                }
                break;
            case KeyEvent.VK_D:
                if (myTank.getShotNum()>1) {
                    myTank.setShotNum(myTank.getShotNum()-1);
                    gua--;
                }
                break;
            case KeyEvent.VK_Z:
//                if (myTank.getMylive() < 0) {
//                    myTank.remake();
//                }
                endChoice = 1;
                myTank.setMylive(myTank.getMylive() + 1);
                Recorder.setMyTankLive(myTank.getMylive());
                gua++;
                break;
            case KeyEvent.VK_Q:
                endChoice = 0;
                break;
            case KeyEvent.VK_X:

                if (enemyTanks.size() < 15) {
                    enemyTankSize++;
                    EnemyTank enemyTank = new EnemyTank((int) (Math.random() * 700) + 100, (int) (Math.random() * 200) + 50); //创建坦克
                    //判断定义时是否重叠
                    while(enemyTank.isTouchEnemyTank()){
                        enemyTank = new EnemyTank((int) (Math.random() * 700) + 100, (int) (Math.random() * 300) + 50); //创建坦克
                    }
                    //获取enemyTanks的坦克对象
                    enemyTank.setEnemyTanks(enemyTanks);
                    //启动坦克线程,随机移动
                    new Thread(enemyTank).start();
                    enemyTanks.add(enemyTank); //加入集合(Vector)
                }
                break;

        }
        if(gua%5==0){
            playSound("/sounds/没有开挂.mp3");
            gua++;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    // 停止游戏并清空画布
    private void stopGame() {
        // 清空画布
        this.removeAll();
        this.repaint();
        // 停止游戏线程
        this.running = false;
        System.exit(0);
    }

    // 处理游戏结束逻辑
    private void handleGameOver(int choice) {
        choice = JOptionPane.showOptionDialog(this, "您的坦克已被摧毁，选择投降认输或重生", "游戏结束",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
            new String[]{"投降认输", "重生"}, "投降认输");

        if (choice == 0) { // 投降认输
            int rand = (int) (Math.random() * 2);
            switch (rand) {
                case 0:
                    playSound("/sounds/SB.mp3");
                    break;
                case 1:
                    playSound("/sounds/Sab.mp3");
                    break;
            }
            JOptionPane.showMessageDialog(this, "敌方胜利！！！");

            stopGame();
        } else if (choice == 1) { // 重生
            playSound("/sounds/没有开挂.mp3");
            myTank.remake();
            myTank.setLive(true);
            Recorder.setMyTankLive(myTank.getMylive());
        }
    }

    // 处理胜利逻辑
    private void handleWin() {
        int rand = (int) (Math.random() * 2);
        switch (rand) {
            case 0:
                playSound("/sounds/NB.mp3");
                break;
            case 1:
                playSound("/sounds/全体起立.mp3");
                break;
        }
        JOptionPane.showMessageDialog(this, "我方胜利！！！");
        stopGame();
    }

    @Override
    public void run() { //重绘画板

        while (true) {
            try {
                Thread.sleep(18);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            hitEnemyTank();
            hitMyTank();
            this.repaint();

            // 检查游戏结束条件
            if (myTank == null || !myTank.isLive() && myTank.getMylive() <= 0) {
                handleGameOver(endChoice);
            }

            // 检查胜利条件
            if (enemyTanks.isEmpty()) {
                handleWin();
            }
        }
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    // 新增播放声音的方法


    public void playSound(String soundFile) {
        new Thread(()->{
            try {
                InputStream inputStream = this.getClass().getResourceAsStream(soundFile);
                if (inputStream == null) {
                    System.out.println("Sound file not found: " + soundFile);
                    return;
                }
                Player player = new Player(inputStream);
                player.play();
            } catch (Exception e) {
                System.out.println("播放音频时出错: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

}
