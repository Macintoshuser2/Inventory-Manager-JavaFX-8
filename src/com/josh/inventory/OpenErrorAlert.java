package com.josh.inventory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class OpenErrorAlert {
	public void create(String errorText) {
		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		Button saveButton = new Button("Save");
		Button cancelButton = new Button("Cancel");
		Text error = new Text(errorText);
		HBox options = new HBox(10);

		options.getChildren().addAll(cancelButton, saveButton);
		root.setBottom(options);
		root.setCenter(error);
		error.setTextAlignment(TextAlignment.CENTER);
		options.setAlignment(Pos.CENTER);
		options.setPadding(new Insets(10, 10, 10, 10));
		stage.setScene(new Scene(root, 400, 100));
		stage.show();

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				stage.close();
			}
		});

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});

		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser chooser = new FileChooser();
				chooser.setTitle("Save Inventory File");
				chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
				File file = chooser.showSaveDialog(stage);

				if (file != null) {
					String json = new Gson().toJson(InventoryTableBuilder.data);

					try {
						BufferedWriter writer = new BufferedWriter(new FileWriter(file));

						writer.write(json);
						writer.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
	}
}
