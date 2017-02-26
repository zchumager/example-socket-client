package net.mazatlab.dev;

import java.awt.Point;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Socket Client
 *
 */
public class App extends Application {
	private String title;
	private int clientMessagesCoodrsX;
	private int clientMessagesCoodrsY;
	private Point clientMessagesCoords;
	private TextField clientMessagesTxt;
	private int sendMessageBtnX;
	private int sendMessageBtnY;
	private Point sendMessageBtnCords;
	private String sendMessageBtnTxt;
	private Button sendMessageBtn;
	private GridPane grid;
	private Pane root;
	
	private Socket socket;
	
	public App() {
		this.title = "Socket Client";
		
		this.clientMessagesCoodrsX = 10;
		this.clientMessagesCoodrsY = 10;
		this.clientMessagesCoords = new Point(
				this.clientMessagesCoodrsX
				, this.clientMessagesCoodrsY);
		this.clientMessagesTxt = new TextField();
		
		this.sendMessageBtnX = 10;
		this.sendMessageBtnY = 50;
		this.sendMessageBtnCords = new Point(
				this.sendMessageBtnX
				, this.sendMessageBtnY);
		this.sendMessageBtnTxt = "Send Message";
		this.sendMessageBtn = new Button(
				this.sendMessageBtnTxt);
		
		this.grid = new GridPane();
		this.root = new Pane();
	}
	
	@Override
	public void start(Stage primaryStage) 
			throws Exception {
		primaryStage.setTitle(this.title);
		primaryStage.setScene(new Scene(this.appWidgets()));;
		primaryStage.show();
		
		this.appEvents();
	}
	
	public Pane appWidgets() {
		this.grid.add(this.clientMessagesTxt
				, (int) this.clientMessagesCoords.getX()
				, (int) this.clientMessagesCoords.getY());
		this.grid.add(this.sendMessageBtn
				, (int) this.sendMessageBtnCords.getX()
				, (int) this.sendMessageBtnCords.getY());
		this.root.getChildren().addAll(this.grid);
		return this.root;
	}
	
	public void sentClientMessage(){
		try {
			this.socket = new Socket("192.168.0.13", 5050);
			DataOutputStream dos = new DataOutputStream(this.socket.getOutputStream());
			dos.writeUTF(this.clientMessagesTxt.getText());
			dos.close();
			this.socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void appEvents() {
		this.sendMessageBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				App.this.sentClientMessage();
			}});
	}
	
	 public static void main( String[] args ) {
	 	launch(args);
	 }
}
