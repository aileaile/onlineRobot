package ll.utils;

import java.util.Random;

public class RondomUtil {

    /**
     * 获取双击动作的三次间隔所需毫秒的int （43-56ms）
     * */
    public static int getClickInt(){
        return new Random().nextInt(13)+43;
    }

    /**
     * 获取键盘单击所需毫秒的int （200-300ms）
     * */
    public static int getKeyPressInt(){
        return new Random().nextInt(100)+200;
    }

    /**
     * 获取50-150毫秒的int
     * */
    public static int getShortInt(){
        return new Random().nextInt(100)+50;
    }

    /**
     * 获取1-2秒的int
     * */
    public static int getShortMiddleInt(){
        return new Random().nextInt(1*1000)+1*1000;
    }


    /**
     * 获取5-10秒的int
     * */
    public static int getMiddleInt(){
        return new Random().nextInt(5*1000)+5*1000;
    }

    /**
     * 获取60-90秒的int
     * */
    public static int getMiddleLongInt(){
        return new Random().nextInt(30*1000)+1*60*1000;
    }



    /**
     * 获取3-5分钟的秒int
     * */
    public static int getLongInt(){
        return new Random().nextInt(2*60*1000)+3*60*1000;
    }
}
