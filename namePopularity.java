package JAVAFX;

import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.*;

import org.apache.commons.csv.*;

public class namePopularity extends Application {
    public static void main(String[] args) throws IOException {
        // namePopularity name = new namePopularity();
        // name.testGetRank();
        launch(args);
    }

    public int getRank(int year, String name, String gender) throws IOException {
        int rank = 0;
        int last = 1;

        Reader fr = new FileReader("yob" + year + ".csv");

        for (CSVRecord record : CSVFormat.EXCEL.parse(fr)) {
            String currName = record.get(0);
            // System.out.println(currName);
            String currgender = record.get(1);
            // System.out.println(currgender);
            if (currgender.equals("F") && gender.equals("F")) {
                rank += 1;
                // System.out.println(rank);
                if (currName.equals(name)) {
                    return rank;
                } else {
                    continue;
                }
            } else {
                last = 1;
            }

            if (currgender.equals("M") && gender.equals("M")) {
                rank += 1;
                // System.out.println(rank);
                if (currName.equals(name)) {
                    return rank;
                } else {
                    continue;
                }
            } else {
                last = 1;
            }
        }
        if (last != 1) {
            return rank;
        } else {
            return 0;
        }

    }

    public String getName(int year, int rank, String gender) throws IOException {
        int getRank = 0;
        String currName = "";
        Reader fr = new FileReader("yob" + year + ".csv");
        for (CSVRecord record : CSVFormat.EXCEL.parse(fr)) {
            if (record.get(1).equals(gender)) {
                if (getRank == rank) {
                    return currName;
                }
                currName = record.get(0);
                getRank++;
            }
        }

        return "NO NAME";
    }

    public String whatIsNameInYear(int newYear, int yourRank, String gender) throws IOException {
        // Reader fr = new FileReader("yob" + year + ".csv");
        // int yourRank = getRank(year, name, gender);
        String yourName = getName(newYear, yourRank, gender);
        return yourName;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Your Name Popularity");
        Label title = new Label("Hello! My name is:");
        title.setFont(new Font("Calibri", 20));

        Label nameLabel = new Label("Name: ");
        TextField nameField = new TextField();
        nameField.setMaxWidth(300);

        Label dobLabel = new Label("Born: ");
        TextField dobField = new TextField();
        dobField.setMaxWidth(300);

        Label genderLabel = new Label("Gender: ");
        ToggleGroup gender = new ToggleGroup();
        RadioButton male = new RadioButton("Male  ");
        RadioButton female = new RadioButton("Female");
        male.setToggleGroup(gender);
        female.setToggleGroup(gender);

        Button submitButton = new Button("Submit");
        submitButton.setTextFill(Color.GREEN);

        Label resultLabel = new Label("Here you will get the rank");
        resultLabel.setFont(new Font("Calibri", 13));
        Label resultLabel2 = new Label("Here you will get your name today");
        resultLabel2.setFont(new Font("Calibri", 13));

        Button clearButton = new Button("Clear");
        clearButton.setTextFill(Color.RED);

        // ---------------------------------------------------------------------------------------------

        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                nameField.clear();
                dobField.clear();
                male.setSelected(false);
                female.setSelected(false);
                resultLabel.setText("Here you will get the rank");
                resultLabel.setFont(new Font("Calibri", 13));
                resultLabel2.setText("Here you will get your name today");
                resultLabel2.setFont(new Font("Calibri", 13));
            }
        });

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (nameField.getText().isBlank() || dobField.getText().isBlank() || !male.isSelected()
                        || !female.isScaleShape()) {
                    resultLabel.setText("Please enter correct details");
                }
                resultLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 13));
                resultLabel2.setFont(Font.font("Calibri", FontWeight.BOLD, 13));
                int nameRank = 0;
                String newName = "";
                String g = "";
                String d = dobField.getText();
                int dob = Integer.parseInt(d);
                String name = "";

                if (male.isSelected()) {
                    g = "M";
                } else {
                    g = "F";
                }
                try {
                    name = nameField.getText().substring(0, 1).toUpperCase() + nameField.getText().substring(1);
                    nameRank = getRank(dob, name, g);
                    newName = whatIsNameInYear(2020, nameRank, g);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String rank = String.valueOf(nameRank);
                if (rank.equals("0") && (male.isSelected() || female.isSelected())) {
                    resultLabel.setText("Rank not found");
                    resultLabel2.setText("Name not found");
                } else if (!rank.equals("0")) {
                    resultLabel.setText("\"" + name + "\"" + " was the " + rank + "th most popular name in " + d + ".");
                    resultLabel2.setText("Your name today would be: " + newName);
                }
            }

        });

        // ---------------------------------------------------------------------------------------------
        // adding the layout as vertical using VBox

        HBox getButton2 = new HBox();
        getButton2.getChildren().addAll(clearButton);
        getButton2.setAlignment(Pos.CENTER);

        HBox getResult2 = new HBox();
        getResult2.getChildren().addAll(resultLabel2);
        getResult2.setAlignment(Pos.CENTER);
        getResult2.setPadding(new Insets(0, 0, 20, 0));

        HBox getResult = new HBox();
        getResult.getChildren().addAll(resultLabel);
        getResult.setAlignment(Pos.CENTER);
        getResult.setPadding(new Insets(0, 0, 20, 0));

        HBox getButton = new HBox();
        getButton.getChildren().addAll(submitButton);
        getButton.setAlignment(Pos.CENTER);
        getButton.setPadding(new Insets(20));

        HBox getGender = new HBox();
        getGender.getChildren().addAll(genderLabel, male, female);
        getGender.setAlignment(Pos.CENTER);
        getGender.setPadding(new Insets(20));

        HBox getDOB = new HBox();
        getDOB.getChildren().addAll(dobLabel, dobField);
        getDOB.setAlignment(Pos.CENTER);
        getDOB.setPadding(new Insets(20));

        HBox getName = new HBox();
        getName.getChildren().addAll(nameLabel, nameField);
        getName.setAlignment(Pos.CENTER);
        getName.setPadding(new Insets(20));

        HBox getTitle = new HBox();
        getTitle.getChildren().addAll(title);
        getTitle.setAlignment(Pos.CENTER_LEFT);
        getTitle.setPadding(new Insets(20));

        VBox root = new VBox();
        root.getChildren().addAll(getTitle, getName, getDOB, getGender, getButton, getResult, getResult2, getButton2);

        // adding the layout to a scene and stage

        primaryStage.setScene(new Scene(root, 500, 450));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
