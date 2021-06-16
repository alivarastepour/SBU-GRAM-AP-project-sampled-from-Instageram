package Controller;

import Model.*;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 <h1>directPage_Controller </h1>
 <p>this class sets primitive data values for a single chat page</p>
 @author Ali Varaste Pour
 @version 1.0
 @since 6/11/2021
 @since 21/3/1400
 */
public class directPage_Controller {
    public JFXTextField messageBox;
    public Circle profilePhoto;
    public Label username;
    public ListView<message> messages ;
    private Map<user, List<message>> receivedMessages = new HashMap<>();
    private Map<user, List<message>> sentMessages = new HashMap<>();
    List<message> messageList = new ArrayList<>();

    /**
     *
     * @throws IOException since "getMessage" method throws CNFException , this method should also handle it
     * @throws ClassNotFoundException since "getMessage" method throws CNFException , this method should also handle it
     */
    public void initialize() throws IOException, ClassNotFoundException {
        username.setText(DirectItemController.user.getUserName());
        profilePhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(DirectItemController.user.getProfilePhoto()))));
        receivedMessages = Model.allDirectsServer.getMessages(logInPage_Controller.Username , "receivedMessages");
        sentMessages = Model.allDirectsServer.getMessages(logInPage_Controller.Username , "sentMessages");
        for (Map.Entry<user, List<message>> v: receivedMessages.entrySet())
            if (v.getKey().getUserName().equals(DirectItemController.user.getUserName()))
                messageList.addAll(v.getValue());
        for (Map.Entry<user, List<message>> v: sentMessages.entrySet())
            if (v.getKey().getUserName().equals(DirectItemController.user.getUserName()))
                messageList.addAll(v.getValue());

        messageList = messageList.stream().sorted((a , b) -> Math.toIntExact(a.getTime() - b.getTime())).collect(Collectors.toList());
        messages.setItems(FXCollections.observableArrayList(messageList));
        messages.setCellFactory(messages -> new MessageItem());
    }

    /**
     * on click , it sends messageBox data to server and then to receiver user
     * @param mouseEvent on mouse click
     * @throws IOException "sendMessageHandler" method is called here and since it may threw IOException current method should handle it
     */
    public void sendMessage(MouseEvent mouseEvent) throws IOException {
        String message = messageBox.getText();
        directsServer.MessageHandler(message , logInPage_Controller.Username , DirectItemController.user.getUserName() , "newMessage");
        messageBox.clear();
        new PageLoader().load("directPage");
    }
    public void backToAllDirectsPage(MouseEvent mouseEvent) throws IOException {
        new PageLoader().load("allDirectsPage");
    }
    public void refresh(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("directPage");
    }
}