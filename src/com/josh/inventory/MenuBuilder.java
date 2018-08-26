package com.josh.inventory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MenuBuilder {
	private static String filePath;

	public static void generateMenu(Stage stage, BorderPane pane) {
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		MenuItem openInvItem = new MenuItem("Open");
		MenuItem saveInvItem = new MenuItem("Save");
		MenuItem mergeInvItem = new MenuItem("Merge");

		openInvItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCodeCombination.CONTROL_DOWN));
		saveInvItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN));
		mergeInvItem.setAccelerator(new KeyCodeCombination(KeyCode.M, KeyCodeCombination.CONTROL_DOWN));

		menuBar.getMenus().addAll(fileMenu);
		fileMenu.getItems().addAll(openInvItem, saveInvItem, mergeInvItem);

		pane.setTop(menuBar);

		if (System.getProperty("os.name").startsWith("Mac")) {
			menuBar.useSystemMenuBarProperty().set(true);
		}

		mergeInvItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (InventoryTableBuilder.data.isEmpty()) {
					Stage promptStage = new Stage();
					BorderPane root = new BorderPane();
					Button openButton = new Button("Open Files");
					Button cancelButton = new Button("Cancel");
					Text error = new Text(
							"As there is no data currently loaded, you must selected the files in your file system you want to merge.");
					HBox options = new HBox(10);

					options.getChildren().addAll(cancelButton, openButton);
					root.setBottom(options);
					root.setCenter(error);
					error.setTextAlignment(TextAlignment.CENTER);
					options.setAlignment(Pos.CENTER);
					options.setPadding(new Insets(10, 10, 10, 10));

					promptStage.setScene(new Scene(root, 700, 100));
					promptStage.show();

					promptStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) {
							promptStage.close();
						}
					});

					cancelButton.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							promptStage.close();
						}
					});

					openButton.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							promptStage.close();
							openMultipleJSONFiles(stage);
						}
					});
				} else {
					openJSONFile(stage);
				}
			}
		});

		openInvItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (InventoryTableBuilder.data.isEmpty()) {
					openJSONFile(stage);
				} else {
					OpenErrorAlert alert = new OpenErrorAlert();
					alert.create("You Must Save the Current Data Before Opening a New File!");
				}
			}
		});

		saveInvItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveJSONFile(stage);
			}
		});
	}

	private static void openMultipleJSONFiles(Stage stage) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Select Files to Merge");
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
		List<File> files = chooser.showOpenMultipleDialog(stage);

		if (files != null) {
			for (File file : files) {
				if (file != null) {
					StringBuilder content = new StringBuilder();

					try {
						BufferedReader reader = new BufferedReader(new FileReader(file));
						String line;

						while ((line = reader.readLine()) != null) {
							content.append(line);
						}

						reader.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}

					String json = content.toString();

					Type itemListType = new TypeToken<ArrayList<InventoryItem>>() {
					}.getType();

					List<InventoryItem> items = new Gson().fromJson(json, itemListType);

					for (InventoryItem item : items) {
						if ((item.getItemName().equals("") || item.getItemDescription().equals("")
								|| item.getItemStorageLocation().equals("") || item.getIsCurrentlyInUse().equals("")
								|| item.getDateEntered().toString().equals(""))) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setContentText(
									"Sorry, the data could not be opened because the JSON was invalid, please try again!");
							alert.setTitle("Error Loading Data");
							alert.setOnCloseRequest(e -> alert.close());
							alert.show();
						} else {
							InventoryTableBuilder.data.add(item);
						}
					}
				}
			}
		}
	}

	private static void saveJSONFile(Stage stage) {
		if (filePath == null || filePath.equals("")) {
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
		} else {
			File file = new File(filePath);

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
	}

	private static void openJSONFile(Stage stage) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open Inventory File");
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
		File file = chooser.showOpenDialog(stage);

		if (file != null) {
			StringBuilder content = new StringBuilder();
			filePath = file.getAbsolutePath();

			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;

				while ((line = reader.readLine()) != null) {
					content.append(line);
				}

				reader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			String json = content.toString();

			Type itemListType = new TypeToken<ArrayList<InventoryItem>>() {
			}.getType();

			List<InventoryItem> items = new Gson().fromJson(json, itemListType);

			for (InventoryItem item : items) {
				if ((item.getItemName().equals("") || item.getItemDescription().equals("")
						|| item.getItemStorageLocation().equals("") || item.getIsCurrentlyInUse().equals("")
						|| item.getDateEntered().toString().equals(""))) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText(
							"Sorry, the data could not be opened because the JSON was invalid, please try again!");
					alert.setTitle("Error Loading Data");
					alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
						@Override
						public void handle(DialogEvent event) {
							alert.close();
						}
					});
					alert.show();
					break;
				} else {
					InventoryTableBuilder.data.add(item);
				}
			}
		}
	}
}