package ll.frame;

import ll.thread.MainTask;
import ll.utils.FileUtil;
import ll.utils.RobotUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppButton extends JButton {

    private AppFrame af;

    public AppButton(String btnName,AppFrame af,int[] bonds,String buttonType){
        super(btnName);
        this.af = af;
        super.setBackground(new Color(255,255,255));
        super.setBounds(bonds[0],bonds[1],bonds[2],bonds[3]);
        super.setFocusPainted(false);
        if("resetImage".equals(buttonType)){
            super.addActionListener(new resetImageListener());
        }else if("stop".equals(buttonType)){
            super.addActionListener(new stopListener());
        }else if("run".equals(buttonType)){
            super.addActionListener(new runListener());
        }else if("resetConfig".equals(buttonType)){
            super.addActionListener(new resetConfigListener());
        }else if("testPos".equals(buttonType)){
            super.addActionListener(new testPosListener());
        }
    }

    private class testPosListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                Robot robot = new Robot();
                String[] pos = af.getTextField().getText().split(",");
                robot.mouseMove(Integer.valueOf(pos[0]),Integer.valueOf(pos[1]));
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
            af.requestFocus();

        }
    }

    private class resetImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            af.getResetImageLabel().setText("正在初始化参照图像数据。。。");

            if(RobotUtil.initImageInfo()==1){
                af.getResetImageLabel().setText("初始化参照图像数据成功。");
            }else{
                af.getResetImageLabel().setText("初始化参照图像数据失败。");
            }

            af.requestFocus();

        }
    }

    private class resetConfigListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            af.getResetImageLabel().setText("正在初始化配置参数。。。");
            if(MainTask.runState==0){
                FileUtil.initConfig();
                af.getResetImageLabel().setText("初始化配置参数成功。");
            }else{
                af.getResetImageLabel().setText("初始化配置参数失败！！！请先停止程序");
            }

            af.requestFocus();

        }
    }

    private class runListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(MainTask.runState==1){
                af.getLabel().setText("程序运行中，勿重复运行!");
            }else{
                af.getLabel().setText("程序运行中,Ctrl+C停止运行。");
                af.getRunBtn().setEnabled(false);
                new Thread(new MainTask()).start();
            }
            af.requestFocus();
        }

    }

    private class stopListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            af.getLabel().setText("程序已停止。");
            AppFrame.AutoRunState = 0;
            MainTask.runState = 0 ;
            af.requestFocus();
        }
    }







}
