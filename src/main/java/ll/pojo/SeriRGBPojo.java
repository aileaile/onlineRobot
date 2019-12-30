package ll.pojo;

import java.io.Serializable;

public class SeriRGBPojo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int[][] rgbs;

    public int[][] getRgbs() {
        return rgbs;
    }

    public void setRgbs(int[][] rgbs) {
        this.rgbs = rgbs;
    }
}
