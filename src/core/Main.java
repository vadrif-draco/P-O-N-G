package core;

import core.welcome.WelcomeScreen;
import javafx.application.Application;
import javafx.stage.Stage;

//class LoadingScreen extends Preloader {
//
//    private Stage loadingDisplay;
//
//    private ProgressBar pb = new ProgressBar();
//    private BorderPane p = new BorderPane();
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        p.setCenter(pb);
//        loadingDisplay.setScene(new Scene(p, 300, 100));
//        stage = loadingDisplay;
//        stage.show();
//    }
//
//    @Override
//    public void handleProgressNotification(ProgressNotification pn) {
//        pb.setProgress(pn.getProgress());
//    }
//
//    @Override
//    public void handleStateChangeNotification(StateChangeNotification evt) {
//        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
//            loadingDisplay.hide();
//        }
//    }
//}

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage = new WelcomeScreen(false); // Launches welcome screen
        // The debugging variable is global here, it affects everything that is debuggable.
        // Can be toggled individually later per each class in its own way.

        //stage = new GamePlayStage();
    }
    // Such a clean empty class, heh.
}