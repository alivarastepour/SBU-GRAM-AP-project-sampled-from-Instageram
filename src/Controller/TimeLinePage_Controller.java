package Controller;

import Model.PageLoader;
import Model.TimeLineServer;
import Model.post;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 <h1>TimeLinePage_Controller </h1>
 <p>this controller page is a connector between TimeLinePage FXML(ths stage) and TimeLinePageServer class </p>
 @author Ali Varaste Pour
 @version 1.0
 @since 5/25/2021
 @since 4/3/1400
 */
public class TimeLinePage_Controller {
    public static String LoggedInUsername ;
    public ListView<post> TimeLine;
    public Label greet;
    public Label greet1;
    private List<post> posts = new ArrayList<>();
    public void initialize() throws IOException, ClassNotFoundException {
        LoggedInUsername = logInPage_Controller.Username;
        posts = TimeLineServer.TimeLineHandler(LoggedInUsername);
        TimeLine.setItems(FXCollections.observableArrayList(posts));
        TimeLine.setCellFactory(TimeLine -> new PostItem());
        greet.setVisible(posts.size() == 0);
        greet1.setVisible(posts.size() == 0);
    }
    
    public void Profile(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("selfProfilePage");
    }

    public void SignOut(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("loginPage");
    }
    
    public void newPost(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("newPostPage");
    }
    
    public void refresh(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("TimeLinePage");
    }

    public void search(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("searchPage");
    }

    public void directs(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("allDirectsPage");
    }
}
