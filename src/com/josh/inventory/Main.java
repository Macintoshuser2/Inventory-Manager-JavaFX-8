package com.josh.inventory;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
	static Scene scene;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		scene = new Scene(root);
		scene.getStylesheets().add("com/josh/inventory/resources/styles.css");
		
		MenuBuilder.generateMenu(primaryStage, root);
		InventoryTableBuilder.generateTable(primaryStage, scene, root, new ArrayList<InventoryItem>());

		ObservableList<String> options = FXCollections.observableArrayList("Yes", "No");

		GridPane inputs = new GridPane();
		Button addButton = new Button("Add Item to Inventory");
		Button removeButton = new Button("Remove Item From Inventory");
		TextField nameField = new TextField();
		TextField quantityField = new TextField();
		TextField descriptionField = new TextField();
		DatePicker dateReceivedPicker = new DatePicker();
		TextField itemStorageLocationField = new TextField();
		ComboBox<String> isCurrentlyInUseComboBox = new ComboBox<String>(options);
		TextField individualUsingItemField = new TextField();

		HBox buttons = new HBox(15);
		buttons.getChildren().add(addButton);
		buttons.getChildren().add(removeButton);
		buttons.setAlignment(Pos.CENTER);

		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					int quantity = Integer.parseInt(quantityField.getText());

					if (!nameField.getText().isEmpty() && !descriptionField.getText().isEmpty()
							&& !dateReceivedPicker.getValue().toString().isEmpty()
							&& !itemStorageLocationField.getText().isEmpty()
							&& !isCurrentlyInUseComboBox.getSelectionModel().getSelectedItem().isEmpty()) {
						InventoryItem item = null;

						if (isCurrentlyInUseComboBox.getSelectionModel().getSelectedItem().equalsIgnoreCase("Yes")) {
							item = new InventoryItem(nameField.getText(), quantity, descriptionField.getText(),
									dateReceivedPicker.getValue(), itemStorageLocationField.getText(), LocalDate.now(),
									isCurrentlyInUseComboBox.getSelectionModel().getSelectedItem(),
									individualUsingItemField.getText());
						} else if (isCurrentlyInUseComboBox.getSelectionModel().getSelectedItem()
								.equalsIgnoreCase("No")) {
							item = new InventoryItem(nameField.getText(), quantity, descriptionField.getText(),
									dateReceivedPicker.getValue(), itemStorageLocationField.getText(), LocalDate.now(),
									isCurrentlyInUseComboBox.getSelectionModel().getSelectedItem(), "N/A");
						}

						InventoryTableBuilder.data.add(item);

						nameField.clear();
						quantityField.clear();
						descriptionField.clear();
						dateReceivedPicker.setValue(null);
						itemStorageLocationField.clear();
						isCurrentlyInUseComboBox.getSelectionModel().clearSelection();
						individualUsingItemField.clear();
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText(
								"Sorry, you entered some invalid data! The name and description of an item is required!");
						alert.setTitle("Invalid Data");
						alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
							@Override
							public void handle(DialogEvent event) {
								alert.close();
							}
						});
						alert.show();
					}
				} catch (NumberFormatException ex) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText(
							"Sorry, you entered an invalid value! Quantity must be an integer value.\nYou entered: "
									+ quantityField.getText());
					alert.setTitle("Invalid Data");
					alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
						@Override
						public void handle(DialogEvent event) {
							alert.close();
						}
					});
					alert.show();
				}
			}
		});

		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				InventoryTableBuilder.data
						.remove(InventoryTableBuilder.getInventoryTable().getSelectionModel().getSelectedIndex());
			}
		});

		inputs.add(new Label("Item Name: "), 0, 0);
		inputs.add(nameField, 1, 0);
		inputs.add(new Label("Item Quantity: "), 0, 1);
		inputs.add(quantityField, 1, 1);
		inputs.add(new Label("Item Description: "), 0, 2);
		inputs.add(descriptionField, 1, 2);
		inputs.add(new Label("Date Received"), 0, 3);
		inputs.add(dateReceivedPicker, 1, 3);
		inputs.add(new Label("Storage Location"), 0, 4);
		inputs.add(itemStorageLocationField, 1, 4);
		inputs.add(new Label("Is Item in Use?"), 0, 5);
		inputs.add(isCurrentlyInUseComboBox, 1, 5);
		inputs.add(new Label("Who is Using it?"), 0, 6);
		inputs.add(individualUsingItemField, 1, 6);
		inputs.add(buttons, 0, 7, 2, 1);

		for (Node n : inputs.getChildren()) {
			n.prefWidth(300);
		}

		dateReceivedPicker.setPrefWidth(300);
		isCurrentlyInUseComboBox.setPrefWidth(300);

		inputs.setAlignment(Pos.CENTER);
		inputs.setPadding(new Insets(15, 15, 15, 15));
		inputs.setVgap(15);

		root.setBottom(inputs);
		
		primaryStage.setScene(scene);
		
		primaryStage.setTitle("Inventory Manager");
		primaryStage.show();
	}
}
