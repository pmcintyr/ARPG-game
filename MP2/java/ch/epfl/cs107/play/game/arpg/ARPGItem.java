package ch.epfl.cs107.play.game.arpg;


import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.InventoryItem;

public enum ARPGItem implements InventoryItem{
	Arrow("Arrow",0.f,10,"zelda/arrow.icon"), Sword("Sword",0.f,50,"zelda/sword.icon"), Staff("Staff",0.f,50,"zelda/staff_water.icon"), 
	Bow("Bow",0.f,30,"zelda/bow.icon"), Bomb("Bomb",0.f,25,"zelda/bomb"), CastleKey("CastleKey",2.f,40,"zelda/key"),;

	final String itemName;
	final String spriteName;
	final float weight;
	final int price;

	ARPGItem(String itemName, float weight, int price,String spriteName) {
		this.itemName = itemName;
		this.weight = weight;
		this.price = price;
		this.spriteName = spriteName;
	}

	@Override
	public float getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPrice() {
		// TODO Auto-generated method stub
		return price;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return itemName;
	}
	public String getSpriteName() {
		return spriteName;
	}

	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);
	}
}
