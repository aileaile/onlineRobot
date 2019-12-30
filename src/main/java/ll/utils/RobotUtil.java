package ll.utils;

import ll.config.AppConfig;
import ll.pojo.SeriRGBPojo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;

public class RobotUtil {

    private static Robot robot;

    //4张：人物选择，排队，断线，服务器选择
    public static String[] picName  = {"character" ,"queue" ,"error" ,"server"};
    public static String[] dataName = {"crt"       ,"qe"    ,"eo"    ,"sv"    };

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单击
     */
    public static void click(){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(RondomUtil.getClickInt());
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * 双击
     */
    public static void doubleClick(){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(RondomUtil.getClickInt());
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(RondomUtil.getClickInt());
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(RondomUtil.getClickInt());
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * alt+F4
     */
    public static void altF4(){
        robot.keyPress(KeyEvent.VK_ALT);
        robot.delay(RondomUtil.getShortMiddleInt());
        robot.keyPress(KeyEvent.VK_F4);
        robot.delay(RondomUtil.getClickInt());
        robot.keyRelease(KeyEvent.VK_F4);
        robot.delay(RondomUtil.getClickInt());
        robot.keyRelease(KeyEvent.VK_ALT);
    }

    /**
     * win+D
     */
    public static void winD(){
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.delay(RondomUtil.getShortInt());
        robot.keyPress(KeyEvent.VK_D);
        robot.delay(RondomUtil.getClickInt());
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        robot.delay(RondomUtil.getClickInt());
        robot.keyRelease(KeyEvent.VK_D);
    }

    /**
     * win+D
     */
    public static void enter(){
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(RondomUtil.getKeyPressInt());
        robot.keyRelease(KeyEvent.VK_ENTER);
    }


    /**
     * 初始化图像信息，存入磁盘
     * @return 0-失败  1-成功
     * */
    public static int initImageInfo(){

        for(int order = 0; order < picName.length; order++){

            File characterFile = new File("./"+picName[order]+".jpg");

            BufferedImage bi = null;

            try {
                bi = ImageIO.read(characterFile);
                int width = bi.getWidth();
                int height = bi.getHeight();
                int[][] rgbs = new int[width][height];
                for(int i = 0; i < width ; i++){
                    for(int j = 0; j < height ; j++){
                        rgbs[i][j] = bi.getRGB(i,j);
                    }
                }
                SeriRGBPojo sp = new SeriRGBPojo();
                sp.setRgbs(rgbs);
                FileOutputStream fos = new FileOutputStream("./data/" + dataName[order] + ".dat");
                ObjectOutput oos = new ObjectOutputStream(fos);
                oos.writeObject(sp);
                oos.flush();
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        return 1;


    }


    /**
     * 从已经储存的dat文件中读取预留的图像信息
     */
    public static int[][] getImageInfo(String dataName){
        try {
            FileInputStream fis = new FileInputStream("./data/" + dataName + ".dat");
            ObjectInput ois = new ObjectInputStream(fis);
            SeriRGBPojo sRgbPojo = (SeriRGBPojo)ois.readObject();
            int[][] rgbData = sRgbPojo.getRgbs();
            return rgbData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断data文件是否存在
     * */
    public static boolean findData(){
        boolean res = true;
        for (int i = 0; i < dataName.length; i++) {
            File file = new File("./data/" + dataName[i] + ".dat");
            if (!file.exists()) {
                res = false;
                break;
            }
        }
        return res;
    }






    /**
     * 识别当前界面
     * @return 1 人物选择
     * @return 2 排队
     * @return 3 断线
     * @return 4 服务器选择
     * @return -1 判断不了
     * */
    public static int imageRecognize(){
        //画个长方形
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Rectangle screenRect = new Rectangle(0,0,screenSize.width,screenSize.height);


        //如果开启超级安全模式，这段不跑
        //只有关闭超级安全模式，这段才跑
        if(AppConfig.getInt("superSafeMode")==0){
            //鼠标移动到左上角，单击
            robot.mouseMove(0,0);
            click();
            //鼠标移动到右上角，防止影响截图
            robot.delay(RondomUtil.getShortInt());
        }


        //截图
        BufferedImage bufferedImage = robot.createScreenCapture(screenRect);

        //以下是精髓 - 判断当前画面是什么
        //tips:尽量避开右下角
        //先判断（0,0）到（485,227）范围内是否有【魔兽世界图标】
        boolean isWowLog = compareImg("wowLogoPosStart", "wowLogoPosEnd",
                /*DataInfo 取的是排队界面的历史信息*/dataName[1], bufferedImage);

        if(isWowLog){//判定成功，有魔兽世界图标

            //如果有【魔兽世界图标】，那么当前可能是断线页面或者是人物选择页面
            //如果  (77,1342)到(310,1422) 有[插件][菜单] 那么确定当前是人物选择界面
            if(compareImg("characterButtonStart", "characterButtonEnd",
                    /*DataInfo 取的是人物选择界面的历史信息*/dataName[0], bufferedImage)){
                return 1;
            }
            //如果 2.中间有排队字样 那么确定当前是排队界面
            if(compareImg("queueSelectServerButtonStart", "queueSelectServerButtonEnd",
                    /*DataInfo 取的是排队界面的历史信息*/dataName[1], bufferedImage)){
                return 2;
            }
            //如果  2.左边有系统公告 3.下边有若干按   那么确定当前是断线页面
            if(compareImg("errorSignUpButtonStart", "errorSignUpButtonEnd",
                    /*DataInfo 取的是断线界面的历史信息*/dataName[2], bufferedImage)){
                return 3;
            }
        }else{
            //如果没有【魔兽世界图标】，那么当前可能是服务器选择界面
            //通过  屏幕中上部（1184,231）到（1420,281）有服务器选择字样，确定当前是服务器选择界面

            if(compareImg("serverStrStart", "serverStrEnd",
                    /*DataInfo 取的是服务器选择界面的历史信息*/dataName[3], bufferedImage)) {
                return 4;
            }

        }
        //判断不了
        return -1;
    }




    /**
     * 判断图像
     * @param dataName : 数据文件名字
     * @param bufferedImage : 当前屏幕截图
     * @param start ： 起始坐标
     * @param end ： 终止坐标
     * */
    private static boolean compareImg(String start,String end,String dataName,BufferedImage bufferedImage){
        int[][] dataInfo = getImageInfo(dataName);
        //count : 计数。
        int count = 0 ;
        //比较
        for(int x = AppConfig.getPos(start).getX();
            x < AppConfig.getPos(end).getX() ; x++){
            for(int y = AppConfig.getPos(start).getY();
                 y < AppConfig.getPos(end).getY() ; y++){

                if(ColorUtil.compareColor(dataInfo[x][y],bufferedImage.getRGB(x,y))){
                    count++;
                }

            }
        }
        //总像素数
        int total = (AppConfig.getPos(end).getX()-AppConfig.getPos(start).getX()) *
                (AppConfig.getPos(end).getY()-AppConfig.getPos(start).getY());

        //结果(保留两位小数)
        double result = new BigDecimal((float)count/total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();



        return result > AppConfig.getDouble("imageRecognitionPercentage");

    }

    public static void createUnrecognizedImage() {

        //画个长方形
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Rectangle screenRect = new Rectangle(0,0,screenSize.width,screenSize.height);

        //鼠标移动到左上角，单击
        robot.mouseMove(0,0);
        click();
        //鼠标移动到右上角，防止影响截图
        robot.delay(RondomUtil.getShortInt());

        //截图
        BufferedImage bufferedImage = robot.createScreenCapture(screenRect);
        //保存截图
        File file = new File("./ThisScreenIsUnrecognizedForTheProgram.jpg");
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
