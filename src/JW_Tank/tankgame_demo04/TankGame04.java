package JW_Tank.tankgame_demo04;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankGame04 extends JFrame {
    static MyPanel panel = null;

    public static void main(String[] args) {
        TankGame04 tankGame04 = new TankGame04();
    }

    public TankGame04() {
        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();

        // 新增“新游戏”菜单项
        JMenu newGameMenu = new JMenu("新游戏");
        JMenuItem newGameItem = new JMenuItem("新的对局");
        JMenuItem continueGameItem = new JMenuItem("继续上局");

        // 将“新游戏”菜单项添加到菜单栏
        newGameMenu.add(newGameItem);
//        newGameMenu.add(continueGameItem);
        menuBar.add(newGameMenu);

        // 将菜单栏设置到此窗体（JFrame）
        this.setJMenuBar(menuBar);


        // 启动游戏时弹出选项框
        String[] options = {"新游戏", "继续上局"};
        int choice = JOptionPane.showOptionDialog(this, "请选择游戏模式", "游戏启动", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);


        // 根据用户选择初始化面板
        String key = (choice == 0) ? "0" : "1";
        panel = new MyPanel(key);
        Thread thread = new Thread(panel);
        thread.start();
        this.add(panel);
        panel.playSound("/sounds/tank.mp3");

        // 处理“新的对局”菜单项点击事件
        newGameItem.addActionListener(e -> {
            this.remove(panel); // 移除当前面板
            Recorder.reset(); // 重置记录数据
            panel = new MyPanel("0"); // 创建新的面板，key为0
            new Thread(panel).start();
            this.add(panel); // 添加新的面板
            this.revalidate(); // 刷新界面
        });

        // 处理“继续上局”菜单项点击事件
        continueGameItem.addActionListener(e -> {
            if (!(panel.nodes==null)) {
                this.remove(panel); // 移除当前面板
                panel = new MyPanel("1"); // 创建新的面板，key为1
                new Thread(panel).start();
                this.add(panel); // 添加新的面板
                this.revalidate(); // 刷新界面
            }
        });

        this.setSize(1200, 800);
        this.addKeyListener(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        // 在JFrame中 增加相应的关闭窗口处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}