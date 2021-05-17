package model;

import java.util.ArrayList;

public class Dto {
    private ArrayList<String> data;

    public ArrayList<String> getData(){ return this.data; }

    public void clear(){ this.data.clear(); }

    public Dto(){ data = new ArrayList<String>(); }

    public void setData(ArrayList<String> newData){ data = newData; }
}
