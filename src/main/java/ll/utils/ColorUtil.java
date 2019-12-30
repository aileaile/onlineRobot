package ll.utils;

import ll.config.AppConfig;

public class ColorUtil {

    public static int getRed(int color) {
        return (color >> 16) & 0xFF;
    }

    public static int getGreen(int color) {
        return (color >> 8) & 0xFF;
    }

    public static int getBlue(int color) {
        return color & 0xFF;
    }

    public static boolean compareColor(int color1, int color2) {
        int tolerance = AppConfig.getInt("tolerance");
        return getRed(color1) - getRed(color2) < tolerance
                && getGreen(color1) - getGreen(color2) < tolerance
                && getBlue(color1) - getBlue(color2) < tolerance;
    }

    public static boolean compareColor(int color, int r, int g, int b) {
        int tolerance = AppConfig.getInt("tolerance");
        return getRed(color) - r < tolerance && getGreen(color) - g < tolerance
                && getBlue(color) - b < tolerance;
    }

}
