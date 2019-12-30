package ll.thread;

import ll.App;
import ll.config.AppConfig;
import ll.utils.RobotUtil;
import ll.utils.RondomUtil;

import java.awt.*;
import java.io.IOException;

public class MainTask implements Runnable{

    /**
     * 运行状态，1跑 0不跑
     * */
    public static int runState = 0;

    public void run() {
        try {
            //初始化恢复
            this.runState = 1;
            Robot robot = new Robot();

            //打开战网并进入游戏
            switchToDesktopAndOpenTheGame(robot);

            //开始循环
            while(runState==1){
                // 1 人物选择,2 排队, 3 断线, 4 服务器选择, -1 判断不了
                int nowState = RobotUtil.imageRecognize();
                //如果是选择服务器页面，则选择服务器
                try {
                    switch (nowState) {
                        case 1:
                            //等待60-90秒
                            Thread.sleep(RondomUtil.getMiddleLongInt());
                            break;
                        case 2:
                            //等待60-90秒
                            Thread.sleep(RondomUtil.getMiddleLongInt());
                            break;
                        case 3:
                            //等待5-10秒
                            Thread.sleep(RondomUtil.getMiddleInt());
                            //在判断一次，如果结果不变，alt+F4
                            if(3==RobotUtil.imageRecognize()){
                                //先关闭。
                                RobotUtil.altF4();
                                //等待
                                robot.delay(AppConfig.getInt("waitAfterExitGame") * 1000);
                                //打开战网并进入游戏
                                switchToDesktopAndOpenTheGame(robot);
                            }
                            break;
                        case 4:
                            RobotUtil.enter();
                            Thread.sleep(20*1000);
                            break;
                        default:
                            int count = 0 ;
                            for(int i = 0;i<10;i++){
                                Thread.sleep(60*1000);
                                if(RobotUtil.imageRecognize()>0){
                                    break;
                                }else{
                                    count++;
                                }
                            }
                            if(count>=9){
                                RobotUtil.createUnrecognizedImage();
                                runState = 0;
                            }
                            break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //程序停止。
            App.af.getLabel().setText("程序已【停止】！！！");
            resetRunBtn();

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private boolean keepRunning(){
        return runState==1?true:false;
    }

    private void switchToDesktopAndOpenTheGame(Robot robot){

        //切桌面
        RobotUtil.winD();

        //鼠标移动到战网图标位置，双击打开战网
        if(!keepRunning())return;
        openBattleNet(robot);

        //鼠标移动到开始游戏按钮位置
        if(!keepRunning())return;
        robot.mouseMove(AppConfig.getPos("enterGamePos").getX(),
                AppConfig.getPos("enterGamePos").getY());

        //等待启动战网
        if(!keepRunning())return;
        robot.delay(AppConfig.getInt("waitAfterOpenBattleNet") * 1000);

        //进入游戏。
        if(!keepRunning())return;
        enterGame();

        //单击后等待时间
        robot.delay((AppConfig.getInt("waitAfterClickButton") * 1000));

    }

    private void resetRunBtn(){
        if (runState == 0) {
            App.af.getRunBtn().setEnabled(true);
        }
    }

    /**
     * 进入游戏
     * 1-鼠标单击进入，2或其他-enter键进入
     * */
    private void enterGame(){
        if(1==AppConfig.getInt("enterGameMode")){
            RobotUtil.click();
        }else{
            RobotUtil.enter();
        }
    }

    /**
     * 进入战网模式
     * 1-鼠标移动并双击进入，2或其他-用cmd命令打开
     * */
    private void openBattleNet(Robot robot){
        if (1 == AppConfig.getInt("openBattleNetMode")) {
            robot.mouseMove(AppConfig.getPos("battleNetOnYourDeskPos").getX(),
                    AppConfig.getPos("battleNetOnYourDeskPos").getY());
            RobotUtil.doubleClick();
        } else {
            try {
                Runtime.getRuntime().exec("cmd /c start "+AppConfig.getStr("BattleNetPath"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
