package ll;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TestApp {
    public static void main(String[] args) throws Exception{
        Rectangle screenRect = new Rectangle(0,0,2560,1440);
        Robot robot = new Robot();
        //截图
        BufferedImage bufferedImage = robot.createScreenCapture(screenRect);
        //保存截图
        File file = new File("screenRect2.png");
        ImageIO.write(bufferedImage, "png", file);
    }
}
