package com.ittalents.pojos;

public class Table {

	private int tableId;
	private boolean isActive = true;
	private boolean hasOrder = false;
	private boolean hasMessage = false;
	public boolean isHasMessage() {
		return hasMessage;
	}

	public void setHasMessage(boolean hasMessage) {
		this.hasMessage = hasMessage;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTabletId(int tableId) {
		this.tableId = tableId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isHasOrder() {
		return hasOrder;
	}

	public void setHasOrder(boolean isThereOrder) {
		this.hasOrder = isThereOrder;
	}

}
