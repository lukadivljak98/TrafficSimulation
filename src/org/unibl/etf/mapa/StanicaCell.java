package org.unibl.etf.mapa;

public class StanicaCell extends Polje{

    public StanicaCell(int x, int y){
        super(x, y);
        setX(x);
        setY(y);
    }

    public static String getStanica(int x, int y){
        String s = "";
        if((x == 2 && y == 27) || (x == 2 && y == 28))
            s =  "A";
        else if((x == 6 && y == 6) || (x == 7 && y == 6)){
            s = "B";
        }else if((x == 19 && y == 12) || (x == 20 && y == 12) || (x == 20 && y == 13)){
            s = "C";
        }else if((x == 26 && y == 1) || (x == 27 && y == 1)){
            s = "D";
        }else if(x == 26 && y == 25){
            s = "E";
        }
        return s;
    }
}
