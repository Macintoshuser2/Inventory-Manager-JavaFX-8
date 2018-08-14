package com.josh.inventory;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class InventoryTableBuilder {

	public static ObservableList<InventoryItem> data;

	private static TableView<InventoryItem> inventoryTable = new TableView<InventoryItem>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void generateTable(Stage stage, Scene scene, BorderPane pane, ArrayList<InventoryItem> items) {
		inventoryTable.setPrefWidth(1100);

		TableColumn itemNameColumn = new TableColumn("Name");
		TableColumn itemQuantityColumn = new TableColumn("Quantity");
		TableColumn itemDescriptionColumn = new TableColumn("Description");
		TableColumn dateRecievedColumn = new TableColumn("Date Recieved");
		TableColumn itemStorageLocationColumn = new TableColumn("Storage Location");
		TableColumn dateEnteredColumn = new TableColumn("Date Entered");
		TableColumn isCurrentlyInUseColumn = new TableColumn("Being used?");
		TableColumn individualUsingItemColumn = new TableColumn("Person Using Item");

		itemNameColumn.setPrefWidth(100);
		itemQuantityColumn.setPrefWidth(100);
		itemDescriptionColumn.setPrefWidth(100);
		dateRecievedColumn.setPrefWidth(150);
		dateEnteredColumn.setPrefWidth(150);
		itemStorageLocationColumn.setPrefWidth(200);
		isCurrentlyInUseColumn.setPrefWidth(100);
		individualUsingItemColumn.setPrefWidth(200);

		inventoryTable.getColumns().addAll(itemNameColumn, itemQuantityColumn, itemDescriptionColumn,
				dateRecievedColumn, dateEnteredColumn, itemStorageLocationColumn, isCurrentlyInUseColumn,
				individualUsingItemColumn);

		pane.setCenter(inventoryTable);

		data = FXCollections.observableArrayList();

		for (InventoryItem item : items) {
			data.add(item);
		}

		itemNameColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("itemName"));
		itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("quantity"));
		itemDescriptionColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("itemDescription"));
		dateRecievedColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("dateRecieved"));
		dateEnteredColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("dateEntered"));
		itemStorageLocationColumn
				.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("itemStorageLocation"));
		isCurrentlyInUseColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("isCurrentlyInUse"));
		individualUsingItemColumn
				.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("individualUsingItem"));

		inventoryTable.setItems(data);
	}

	@SuppressWarnings("rawtypes")
	public static TableView getInventoryTable() {
		return inventoryTable;
	}
}
