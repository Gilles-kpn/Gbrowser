package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.io.File;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebTab extends Tab {
    private WebView webcontent = new WebView();
    private AnchorPane content = new AnchorPane();
    private ProgressBar progressBar = new ProgressBar();
    public WebTab(){
        progressBar.setPrefWidth(120);
        progressBar.setPrefHeight(10);
        progressBar.progressProperty().bind(webcontent.getEngine().getLoadWorker().progressProperty());
        content.autosize();
        webcontent.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {

                setTabTitle(webcontent.getEngine().getLocation());
                if(t1 == Worker.State.SUCCEEDED ){
                    try {
                        setTabTitle(webcontent.getEngine().getTitle() == null ? webcontent.getEngine().getLocation() : webcontent.getEngine().getTitle());
                        Controller.texturl(urlretourne());
                        boolean exist = false; int pos = 0;
                        for (WebHistory.Entry f:Historiques.getHistory()) {
                            if(f.getUrl().equalsIgnoreCase(getlastHistory().getUrl())){
                                exist = true;
                                Historiques.InsertAtPosition(pos,getlastHistory());
                                break;
                            }
                            pos++;
                        }
                        if(!exist)
                        Historiques.addLastHistory(getlastHistory());

                        //System.out.println(getlastHistory());
                    }catch (NullPointerException e){
                        setTabTitle("No title");
                    }
                    setTabTitle(!webcontent.getEngine().getLocation().equalsIgnoreCase("file:///home/gilles/IdeaProjects/Web%20Browser/src/404/index.html") ? webcontent.getEngine().getTitle() : "Error when loading");
                }else if(t1 ==Worker.State.FAILED){
                    webcontent.getEngine().load("file:///home/gilles/IdeaProjects/Web Browser/src/404/index.html");
                    setTabTitle("Error when loading page");
                }
            }
        });


        content.autosize();
        //fix webcontent to anchor pane size
        webcontent.prefWidthProperty().bind(content.widthProperty());
        webcontent.prefHeightProperty().bind(content.heightProperty());
        //add webcontent to anchor pane
        content.getChildren().add(webcontent);
        AnchorPane.setRightAnchor(progressBar,2.0);
        AnchorPane.setBottomAnchor(progressBar,2.0);
        content.getChildren().add(progressBar);
        content.setId("ajouter");
        this.setContent(content);

        /*
        content.getChildren().addAll(webcontent);
        this.setContent(content);*/


    }
    private  void setTabTitle(String s){
        this.setText(s);
    }
    public void LoadUrl(String s){
        if(!s.trim().isEmpty()){
            Pattern p = Pattern.compile("^(http://|https://)");
            Matcher m = p.matcher(s);
            if(!m.find()){
                //Si http ou https n'est pas trouvé  et www est trouv
                p =Pattern.compile("^(www)");//dans le cas ou l'url commence par www
                m = p.matcher(s);
                if(m.find()){
                    s = "https://"+s;
                }else{//si www n'est pas trouvé soit une simple recherche on utilise le moteur google //ou des fichiers local
                    p =Pattern.compile("^(file://)");
                    m = p.matcher(s);
                    if(m.find()){

                    }else if(!m.find() && s.charAt(0) == '/'){
                        s = "file://"+s;
                    } else if(!m.find() && s.charAt(0) != '/'){
                        s = s.replace(" ","+");
                        s = "https://www.google.com/search?q="+s;
                    }

                }

            }
            webcontent.getEngine().load(s);
        }
    }

    public String urlretourne(){
        return  webcontent.getEngine().getLocation();
    }


    public WebHistory.Entry getlastHistory(){
        return webcontent.getEngine().getHistory().getEntries().get(webcontent.getEngine().getHistory().getEntries().size()-1);
    }
    public void reload(){
        webcontent.getEngine().reload();
    }

}
