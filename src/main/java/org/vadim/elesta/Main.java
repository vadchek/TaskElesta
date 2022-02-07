package org.vadim.elesta;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;

public class Main extends Application {

    TextArea textArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        textArea = new TextArea();// создание многострочного текстового поля

        MenuBar mb = new MenuBar();//создание строки меню с кнопками  open, save
        Menu fileMenu = new Menu("File");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        fileMenu.getItems().addAll(open, save, new SeparatorMenuItem());
        mb.getMenus().add(fileMenu);

        open.setOnAction(event -> showOpenWindow());//обработка нажатия на кнопку open
        save.setOnAction(event -> showSaveWindow());//обработка нажатия на кнопку save

        BorderPane rootNode = new BorderPane();//создание корневого узла
        StackPane stackPane = new StackPane();
        rootNode.setTop(mb);
        stackPane.getChildren().add(textArea);
        rootNode.setCenter(stackPane);

        Scene myScene = new Scene(rootNode, 600, 400);//создание сцены
        primaryStage.setScene(myScene);
        primaryStage.setTitle("MyNote");
        primaryStage.show();//отображение окна
    }

    public void showOpenWindow() {
        Stage openStage = new Stage();//создание контейнера для окна

        Label label = new Label("Enter Filename:");
        TextField textField = new TextField();//поле ввода названия файла
        RadioButton rbTxt = new RadioButton(".txt");//создание кнопок выбора расширения файла
        RadioButton rbIni = new RadioButton(".ini");
        ToggleGroup tg = new ToggleGroup();
        rbTxt.setToggleGroup(tg);
        rbIni.setToggleGroup(tg);
        rbTxt.setSelected(true);
        Button button = new Button("select");//кнопка подтверждения действия
        Label error = new Label("");//сообщение об ошибке

        //обработчик нажатия на кнопку
        button.setOnAction(event -> {
            error.setText("");
            RadioButton rb = (RadioButton) tg.getSelectedToggle();
            try {
                textArea.setText(FileReadWrite.readFromFile(textField.getText() + rb.getText()));//вызов метода чтения из файла
            } catch (IOException e) {
                error.setText(e.getMessage());
            }
            if (error.getText().equals("")) openStage.close();//закрытие окна при завершении действия без ошибок
        });

        FlowPane rootNode = new FlowPane(10, 20);//создание корневого узла
        rootNode.setOrientation(Orientation.VERTICAL);
        FlowPane filePane = new FlowPane(10, 10);//создание дочерних узлов
        FlowPane tgPane = new FlowPane(10, 10);
        tgPane.getChildren().addAll(rbTxt, rbIni);//добавление элементов в дочерние узлы
        filePane.getChildren().addAll(label, textField);
        rootNode.getChildren().addAll(filePane, tgPane, button, error);//добавление элементов в корневой узел

        Scene openScene = new Scene(rootNode, 300, 200);//создание сцены
        openStage.setScene(openScene);
        openStage.setResizable(false);
        openStage.setTitle("open file");
        openStage.show();//отображение окна
    }

    public void showSaveWindow() {
        Stage saveStage = new Stage();//создание контейнера для окна

        Label label = new Label("Save to File:");
        TextField textField = new TextField();//поле ввода названия файла
        RadioButton rbTxt = new RadioButton(".txt");//создание кнопок выбора расширения файла
        RadioButton rbIni = new RadioButton(".ini");
        ToggleGroup tg = new ToggleGroup();
        rbTxt.setToggleGroup(tg);
        rbIni.setToggleGroup(tg);
        rbTxt.setSelected(true);
        Button button = new Button("select");//кнопка подтверждения действия
        Label error = new Label("");//сообщение об ошибке

        //обработчик нажатия на кнопку
        button.setOnAction(event -> {
            error.setText("");
            RadioButton rb = (RadioButton) tg.getSelectedToggle();
            try {
                FileReadWrite.writeToFile(textField.getText() + rb.getText(), textArea.getText());//вызов метода записи в файл
            } catch (IOException e) {
                error.setText(e.getMessage());
            }
            if (error.getText().equals("")) saveStage.close();//закрытие окна при завершении действия без ошибок
        });

        FlowPane rootNode = new FlowPane(10, 20);//создание корневого узла
        rootNode.setOrientation(Orientation.VERTICAL);
        FlowPane filePane = new FlowPane(10, 10);//создание дочерних узлов
        FlowPane tgPane = new FlowPane(10, 10);
        tgPane.getChildren().addAll(rbTxt, rbIni);//добавление элементов в дочерние узлы
        filePane.getChildren().addAll(label, textField);
        rootNode.getChildren().addAll(filePane, tgPane, button, error);//добавление элементов в корневой узел

        Scene saveScene = new Scene(rootNode, 300, 200);//создание сцены
        saveStage.setScene(saveScene);
        saveStage.setTitle("Save");
        saveStage.setResizable(false);
        saveStage.show();//отображение окна
    }

}
