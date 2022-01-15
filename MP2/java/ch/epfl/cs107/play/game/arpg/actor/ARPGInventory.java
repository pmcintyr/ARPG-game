package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.arpg.ARPGItem;
import ch.epfl.cs107.play.game.rpg.Inventory;
import ch.epfl.cs107.play.game.rpg.InventoryItem;


public class ARPGInventory extends Inventory{
	private final static int STARTING_VBUCKS = 5;
	private final static float INVENTORY_MAX_WEIGHT = 200;


	protected int getCurrentQuantity(InventoryItem inventoryItemName) {
		if (quantités.get(inventoryItemName.getTitle()) == null) {
			return 0;
		}
		else {
			return quantités.get(inventoryItemName.getTitle());
		}
	}

	int fortune = getCurrentQuantity(ARPGItem.Arrow)*ARPGItem.Arrow.getPrice()+getCurrentQuantity(ARPGItem.Sword)*ARPGItem.Sword.getPrice()+
			getCurrentQuantity(ARPGItem.Bow)*ARPGItem.Bow.getPrice()+getCurrentQuantity(ARPGItem.CastleKey)*ARPGItem.CastleKey.getPrice()+
			getCurrentQuantity(ARPGItem.Staff)*ARPGItem.Staff.getPrice()+getCurrentQuantity(ARPGItem.Bomb)*ARPGItem.Bomb.getPrice();

	protected ARPGInventory(InventoryItem key, int money) {
		super(key, money);
		addInventoryItem(ARPGItem.Bomb,3);


		// TODO Auto-generated constructor stub
	}
	int myVbucks = STARTING_VBUCKS;


	//	ATTENTION: METTRE DIFFERENTES QUANTITES POUR DIFFERENTS D'OBJETS!!!

	protected void addMoney(int money) {
		if (money>0) {//on veut s'enrichir pas perdre de l'argent
			myVbucks += money;
		}
	}
	public int getMoney() {
		return myVbucks;
	}
	public int getFortune() {
		return fortune;
	}


	public boolean isItemOwned(ARPGItem ARPGinventoryItemName) {
		super.isItemOwned(ARPGinventoryItemName);
		if ((getCurrentQuantity(ARPGinventoryItemName)>0)) {
			System.out.println(getCurrentQuantity(ARPGinventoryItemName));

			return true;
		}
		return false;
	}
	protected boolean removeInventoryItem(InventoryItem inventoryItemName, int substractQuantity) {
		if ((getCurrentQuantity(inventoryItemName)>substractQuantity)) {	//ne pas additionner dans methode soustraire
			quantités.put(inventoryItemName.getTitle(), (getCurrentQuantity(inventoryItemName)-substractQuantity));			
			return true;
		}
		return false;
	}
	public boolean addInventoryItem(InventoryItem inventoryItemName, int addQuantity) {
		if (addQuantity>0) {
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

}
