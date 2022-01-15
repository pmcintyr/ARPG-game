package ch.epfl.cs107.play.game.rpg;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.cs107.play.game.arpg.actor.ARPGInventory;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;

public class Inventory {
	private final static float INVENTORY_MAX_WEIGHT = 200;
	protected Map <String , Integer > quantités;
	protected Map <String , InventoryItem > objets;
	protected InventoryItem currentObject;

	protected Inventory(InventoryItem key, int quantity) {
		quantités = new HashMap <>();
		//quantités.containsKey(key.getTitle());
	}

	protected int getCurrentQuantity(InventoryItem inventoryItemName) {
		if (quantités.get(inventoryItemName.getTitle()) == null) {
			return 0;
		}
		else {
			return quantités.get(inventoryItemName.getTitle());
		}
	}

	protected boolean addInventoryItem(InventoryItem inventoryItemName, int addQuantity) {
		if ((getCurrentQuantity(inventoryItemName)>0)) {	//ne pas soustraire dans methode additionner
			if ((getCurrentQuantity(inventoryItemName)+addQuantity)*inventoryItemName.getWeight()<INVENTORY_MAX_WEIGHT) {
				if(!quantités.containsKey(inventoryItemName.getTitle())) {
					quantités.put(inventoryItemName.getTitle(), addQuantity);
				}
				quantités.put(inventoryItemName.getTitle(), (getCurrentQuantity(inventoryItemName)+addQuantity));
				return true;
			}
		}
		return false;
	}

	protected boolean removeInventoryItem(InventoryItem inventoryItemName, int substractQuantity) {
		if ((getCurrentQuantity(inventoryItemName)>0)) {	//ne pas additionner dans methode soustraire
			quantités.put(inventoryItemName.getTitle(), (getCurrentQuantity(inventoryItemName)-substractQuantity));			
			return true;
		}
		return false;
	}

	public boolean isItemOwned(InventoryItem inventoryItemName) {
		if ((getCurrentQuantity(inventoryItemName)>0)) {
			return true;
		}
		return false;
	}
}