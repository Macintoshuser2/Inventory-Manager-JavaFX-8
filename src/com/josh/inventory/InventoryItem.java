package com.josh.inventory;

import java.time.LocalDate;

public class InventoryItem {

	private String itemName;
	private int quantity;
	private String itemDescription;
	private LocalDate dateReceived;
	private String itemStorageLocation;
	private LocalDate dateEntered;
	private String isCurrentlyInUse;
	private String individualUsingItem;

	public InventoryItem(String itemName, int quantity, String itemDescription, LocalDate dateReceived,
			String itemStorageLocation, LocalDate dateEntered, String isCurrentlyInUse, String individualUsingItem) {
		this.itemName = itemName;
		this.quantity = quantity;
		this.itemDescription = itemDescription;
		this.dateReceived = dateReceived;
		this.itemStorageLocation = itemStorageLocation;
		this.dateEntered = dateEntered;
		this.isCurrentlyInUse = isCurrentlyInUse;
		this.individualUsingItem = individualUsingItem;
	}

	public InventoryItem() {
		this.itemName = "";
		this.quantity = 0;
		this.itemDescription = "";
		this.dateReceived = LocalDate.now();
		this.itemStorageLocation = "";
		this.dateEntered = LocalDate.now();
		this.isCurrentlyInUse = "No";
		this.individualUsingItem = "";
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public LocalDate getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(LocalDate dateReceived) {
		this.dateReceived = dateReceived;
	}

	public String getItemStorageLocation() {
		return itemStorageLocation;
	}

	public void setItemStorageLocation(String itemStorageLocation) {
		this.itemStorageLocation = itemStorageLocation;
	}

	public LocalDate getDateEntered() {
		return dateEntered;
	}

	public void setDateEntered(LocalDate dateEntered) {
		this.dateEntered = dateEntered;
	}

	public String getIsCurrentlyInUse() {
		return isCurrentlyInUse;
	}

	public void setIsCurrentlyInUse(String isCurrentlyInUse) {
		this.isCurrentlyInUse = isCurrentlyInUse;
	}

	public String getIndividualUsingItem() {
		return individualUsingItem;
	}

	public void setIndividualUsingItem(String individualUsingItem) {
		this.individualUsingItem = individualUsingItem;
	}

	@Override
	public String toString() {
		return itemName + ", " + quantity + ", " + itemDescription;
	}
}
