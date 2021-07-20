package sample;

import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.web.WebHistory;

import java.util.ArrayList;
import java.util.List;

public class Historiques extends Button {
    private static ArrayList<WebHistory.Entry> history = new ArrayList<WebHistory.Entry>();


    public static ArrayList<WebHistory.Entry> getHistory(){
        return  history;
    }
    public static void addLastHistory(WebHistory.Entry h){
        history.add(h);
    }
    public  ArrayList<MenuItem> showHistory(){
        ArrayList<MenuItem>mes;
        mes = new ArrayList<MenuItem>();
        if(history != null && !history.isEmpty()){
        for (WebHistory.Entry h: history) {
            MenuItem i =new MenuItem(h.getTitle()+"\n"+h.getUrl()+"\nA "+h.getLastVisitedDate());
            i.setId(h.getUrl());
            mes.add(i);
        }
        }else {
            MenuItem i =new MenuItem("Pas d'historique recent");
            i.setId("None");
            mes.add(i);
        }
        return mes;
    }
    public static void InsertAtPosition(int pos,WebHistory.Entry f){
        history.set(pos,f);
    }

}
