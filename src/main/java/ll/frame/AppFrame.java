package ll.frame;

import ll.config.AppConfig;
import ll.thread.MainTask;
import ll.utils.FileUtil;
import ll.utils.RobotUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AppFrame extends JFrame implements KeyListener {

    private JLabel label;

    private JLabel resetImageLabel;

    private JButton runBtn;

    private JTextField textField;

    /**
     * 自动运行状态 0-停止 1-运行
     * */
    public static int AutoRunState = 1;

    public AppFrame(){
        //读取配置文件
        FileUtil.initConfig();

        //设置框体
        this.setTitle("MineCraft v0.0.3");
        this.setVisible(true);
        this.setSize(500,600);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.addKeyListener(this);
        Font font = new Font("宋体",Font.BOLD,16);
        label = new JLabel("程序将在"+ AppConfig.getInt("firstWaitTime")+"秒后自动运行,Ctrl+C停止运行。");
        label.setFont(font);
        label.setBounds(18,12,500,50);
        this.add(label);

        //设置按钮
        Container container = getContentPane();
        container.setLayout(null);
        container.add(new AppButton("停止",this,new int[]{30,80,100,50},"stop"));
        runBtn = new AppButton("运行",this,new int[]{180,80,100,50},"run");
        container.add(runBtn);
        resetImageLabel = new JLabel("重置图像文件后单击按钮重新生成初始数据。");
        resetImageLabel.setBounds(18,170,500,50);
        resetImageLabel.setFont(font);
        this.add(resetImageLabel);
        container.add(new AppButton("重置图像",this,new int[]{30,230,100,50},"resetImage"));
        container.add(new AppButton("重置参数",this,new int[]{180,230,100,50},"resetConfig"));


        JLabel label3 = new JLabel("输入坐标后点击下方按钮可以测试鼠标移动情况。");
        label3.setFont(font);
        label3.setBounds(18,300,500,50);
        this.add(label3);
        textField = new JTextField(15);
        textField.setBounds(30,350,100,50);
        textField.setText("500,500");
        container.add(textField);
        container.add(new AppButton("测试坐标",this,new int[]{180,350,100,50},"testPos"));

        this.repaint();
        this.setVisible(true);

        if(RobotUtil.findData()){
            runBtn.setEnabled(false);

            //开始自动运行
            for(int i = 0;i<AppConfig.getInt("firstWaitTime");i++){
                if(AutoRunState==1){
                    try {
                        label.setText("程序将在"+ (AppConfig.getInt("firstWaitTime")-i)+"秒后自动运行,Ctrl+C停止运行。");
                        Thread.sleep(1 * 1000);
                    } catch (InterruptedException e) {
                    }
                }else{
                    break;
                }
            }
            if(AutoRunState==1){
                label.setText("程序运行中,Ctrl+C停止运行。");
                new Thread(new MainTask()).start();
            }else{
                runBtn.setEnabled(true);
            }

        }else {
            label.setText("<html>软件可能是第一次运行，尚未生成初始图像文件，请根据说<br>明将截图重命名并放置到正确位置后点击[重置图像]按钮</html>");
        }



    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.isControlDown()&&e.getKeyCode()==KeyEvent.VK_C){
            label.setText("程序已停止。");
            AutoRunState = 0;
            MainTask.runState = 0 ;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public JLabel getResetImageLabel() {
        return resetImageLabel;
    }

    public JLabel getLabel() {
        return label;
    }

    public JButton getRunBtn() {
        return runBtn;
    }

    public JTextField getTextField() {
        return textField;
    }
}
