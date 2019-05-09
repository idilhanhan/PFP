package rawdata.pfp.ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by idilhanhan on 9.05.2019.
 */
public class UIManager extends Application{ //TODO: fill according to the stages you have written!

    public static void main(String[] args){
        launch(args); //this is gonna set up our program as javafx application
    }

    @Override
    public void start(Stage primaryStage) throws Exception { //whenever you start launch is gonna start app. & then will call start
        primaryStage.setTitle("Project Formation Platform");

    }
}
