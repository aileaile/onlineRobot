package ll.pojo;

public class PosPojo {
    int x;
    int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public PosPojo(){
    }

    public PosPojo(int x , int y ){
        this.x = x;
        this.y = y;
    }

    public PosPojo(String[] pos){
        this.x = Integer.valueOf(pos[0]);
        this.y = Integer.valueOf(pos[1]);
    }


}
