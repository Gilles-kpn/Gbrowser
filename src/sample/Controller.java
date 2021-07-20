package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.io.File;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    @FXML private Button  gotourl ;
    @FXML private Button  settings ;
    @FXML private WebView webcontent;
    @FXML private  TextField url;
    @FXML private WebTab default_page1;
    @FXML private Label loading;
    @FXML private TabPane Tpane;
    @FXML private ContextMenu sub_setting;
    @FXML private BorderPane pane;
    @FXML public  Historiques historique;
    @FXML private ContextMenu sub_history;
    public static String text = "text";

    @FXML protected void goBack(){
        if(historique.getHistory().size() >2){
            ((WebTab)Tpane.getSelectionModel().getSelectedItem()).LoadUrl(historique.getHistory().get(historique.getHistory().size()-2).getUrl());
        }
    }
    public Controller(){
        historique = new Historiques();
        //url = new TextField();
    }

    @FXML protected  void goNext(){

    }
    @FXML protected  void reload(){

        if(historique.getHistory().size() != 0){
            ((WebTab)Tpane.getSelectionModel().getSelectedItem()).reload();
        }
    }
    //show settings sub-menu
    @FXML protected  void showSousMenu(MouseEvent e){
        sub_setting.show(pane,e.getScreenX(),e.getScreenY());
    }

    @FXML protected void showAllHistory(MouseEvent e){

            sub_history.getItems().remove(0, sub_history.getItems().size());

            for (MenuItem f : historique.showHistory()) {
                sub_history.getItems().add(f);
            }
            sub_history.show(pane, e.getScreenX(), e.getScreenY());

    }
    @FXML private void launchSearchFromKey(KeyEvent e){
        if(e.getCode() == KeyCode.ENTER){
            Search();
        }
    }
    //private void Search

    private void Search(){
        ((WebTab)Tpane.getSelectionModel().getSelectedItem()).LoadUrl(url.getText());
        url.setText(((WebTab)Tpane.getSelectionModel().getSelectedItem()).urlretourne());
    }
    //load url on click
    @FXML protected  void LoadURL(MouseEvent e){
        Search();
    }
    //go to hystory url
    @FXML protected void gotoHistoryUrl(ActionEvent e){
        if(!((MenuItem)e.getTarget()).getId().equalsIgnoreCase("None"))
            ((WebTab)Tpane.getSelectionModel().getSelectedItem()).LoadUrl(((MenuItem) e.getTarget()).getId());
    }

    @FXML protected void createNewWindow(){
        //Tpane header

        //
        WebTab w = new WebTab();
        //adding new table with new id
        w.setId("default_page"+Tpane.getTabs().get(Tpane.getTabs().size()-1).getId().substring(Tpane.getTabs().get(Tpane.getTabs().size()-1).getId().length()-1));
        w.setText("New Windows");
        //adding new tab
        Tpane.getTabs().add(w);
        //Setting anchor pane as the layout
    }

    public  void changeUrl(String s){
        url.setText(s);
    }
    public static void texturl(String s){
        text = s;

    }
    @FXML protected  void goHome(){
        url.setText("https://www.google.com");
        ((WebTab)Tpane.getSelectionModel().getSelectedItem()).LoadUrl(url.getText());

    }
}
