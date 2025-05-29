package JW_Tank.tankgame_demo04;

import java.io.*;
import java.util.Vector;

/**
 * 记录对战信息
 */
public class Recorder {

    //定义我方击毁敌人坦克数
    private static int KOEnemyTankNum = 0;
    private static int myTankLive;

    //定义IO变量
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = ".\\myRecord.txt";

    //定义一个Vector, 指向MyPanel中的敌人坦克
    private static Vector<EnemyTank> enemyTanks = null;

    //定义Node的集合(Vector),保存敌人的信息
    private static Vector<Node> nodes = new Vector<>();

    /**
     * 重置记录数据
     */
    public static void reset() {
        KOEnemyTankNum = 0; // 重置击毁敌方坦克数
        myTankLive = 2;
        nodes.clear(); // 清空敌方坦克信息
    }

    //恢复相关信息
    public static Vector<Node> getNodesAndEnemyTankRec(){
        try {
            br = new BufferedReader(new FileReader(recordFile));
            KOEnemyTankNum = Integer.parseInt(br.readLine());
            myTankLive = Integer.parseInt(br.readLine());
            //循环读取文件,生成nodes集合
            String line = "";
            while ((line=br.readLine())!= null){
                String[] x_y_d = line.split(" ");
                //用Integer将 String对象 转为 Int对象
                Node node = new Node(Integer.parseInt(x_y_d[0]), Integer.parseInt(x_y_d[1]),
                        Integer.parseInt(x_y_d[2]));
                nodes.add(node); //将坦克信息放入nodes集合
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return nodes;
    }

    //当游戏退出时,将[战绩]保存至文件中
    //并保存敌人坦克的坐标
    public static void keepRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(KOEnemyTankNum + "\r\n");
            bw.write(myTankLive + "\r\n");

            //遍历敌人坦克的Vector
            for(int i =0; i<enemyTanks.size();i++){
                EnemyTank enemyTank = enemyTanks.get(i);
                if(enemyTank.isLive()){
                    //保存坦克的信息
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    bw.write(record + "\r\n");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(bw!=null){
                    bw.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static int getMyTankLive() {
        return myTankLive;
    }

    public static void setMyTankLive(int myTankLive) {
        Recorder.myTankLive = myTankLive;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static int getKOEnemyTankNum() {
        return KOEnemyTankNum;
    }

    public static void setKOEnemyTankNum(int KOEnemyTankNum) {
        Recorder.KOEnemyTankNum = KOEnemyTankNum;
    }

    //击毁坦克计数
    public static void addKOEnemyTankNum() {
        Recorder.KOEnemyTankNum++;
    }
}