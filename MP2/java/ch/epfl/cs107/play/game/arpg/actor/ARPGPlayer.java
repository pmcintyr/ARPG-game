package ch.epfl.cs107.play.game.arpg.actor;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;

import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.arpg.ARPGItem;
import ch.epfl.cs107.play.game.arpg.ARPGPlayerStatusGUI;
import ch.epfl.cs107.play.game.arpg.actor.Grass;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class ARPGPlayer extends Player{
	private float hp;
	private TextGraphics message;
	//	private TextGraphics messageExplosion;
	private  ARPGPlayerHandler handler = new ARPGPlayerHandler();
	private final static int ANIMATION_DURATION = 4;
	private final static float FULL_HP = 5.f;
	private Keyboard keyboard;
	private ARPGItem currentObject;
	private List<ARPGItem> rotationList;
	private int currObjectID;

	private ARPGInventory inventory;
	private ARPGPlayerStatusGUI arpgPlayerStatusGUI;

	private CastleKey castleKey;
	private Sprite[][] sprites;
	private Animation[] animations;
	private Sprite[][] bowSprites;
	private Animation[] bowAnimations;
	private Sprite[][] swordSprites;
	private Animation[] swordAnimations;
	private Sprite[][] staffSprites;
	private Animation[] staffAnimations;

	private void nextCurrentObject(){
		do {
			currObjectID=(currObjectID+1)%rotationList.size();
			//			System.out.print(currentObject);
			currentObject=rotationList.get(currObjectID);
			//			System.out.println(" becomes " + currentObject);
		}while(inventory.isItemOwned(currentObject));
	}
	public ARPGItem getCurrentObject() {
		return currentObject;
	}
	public ARPGMode currentMode ;

	public enum ARPGMode {

		SWORD,
		BOW,
		STAFF,
		NoBusy;
	}

	public ARPGPlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
		super(area, orientation, coordinates);
		arpgPlayerStatusGUI = new ARPGPlayerStatusGUI(this);

		//personage humain
		sprites = RPGSprite.extractSprites("zelda/player", 4, 1.f, 2.f, this, 16, 32,
				new Orientation[] { Orientation.DOWN, Orientation.RIGHT, Orientation.UP, Orientation.LEFT });

		// crée un tableau de 4 animation
		animations = RPGSprite.createAnimations(ANIMATION_DURATION / 4, sprites);

		//personage humain arc
		bowSprites = RPGSprite.extractSprites("zelda/player.bow", 4, 2.f, 2.f, this,32, 32,
				new Orientation[] { Orientation.DOWN, Orientation.UP, Orientation.RIGHT, Orientation.LEFT });

		// crée un tableau de 4 animation
		bowAnimations = RPGSprite.createAnimations(ANIMATION_DURATION / 4, bowSprites);

		//personage humain avec epee
		swordSprites = RPGSprite.extractSprites("zelda/player.sword", 4, 2.f, 2.f, this, 2*16, 2*16,
				new Orientation[] { Orientation.DOWN, Orientation.UP, Orientation.RIGHT, Orientation.LEFT });

		// crée un tableau de 4 animation pour epee
		swordAnimations = RPGSprite.createAnimations(ANIMATION_DURATION / 4, swordSprites);

		//personage humain avec baton
		staffSprites = RPGSprite.extractSprites("zelda/player.staff_water", 4, 2.f, 2.f, this, 2*16, 2*16,
				new Orientation[] {Orientation.DOWN,Orientation.UP,Orientation.RIGHT , Orientation.LEFT });

		// crée un tableau de 4 animation pour baton
		staffAnimations = RPGSprite.createAnimations(ANIMATION_DURATION / 4, staffSprites);


		currentObject = ARPGItem.Bomb;
		currObjectID=1;
		inventory = new ARPGInventory(ARPGItem.Bomb, 15);
		inventory.addInventoryItem(ARPGItem.Arrow,5);
		inventory.addInventoryItem(ARPGItem.Bomb,5);
		inventory.addMoney(195);

		keyboard = getOwnerArea().getKeyboard();
		rotationList=new ArrayList<ARPGItem>();
		rotationList.add(ARPGItem.Arrow); 
		rotationList.add(ARPGItem.Bomb); 
		rotationList.add(ARPGItem.Bow);
		rotationList.add(ARPGItem.CastleKey);
		rotationList.add(ARPGItem.Staff);
		rotationList.add(ARPGItem.Sword);


		this.hp = FULL_HP;
		message = new TextGraphics(Integer.toString((int)hp), 0.4f, Color.BLUE);
		message.setParent(this);
		message.setAnchor(new Vector(-0.3f, 0.1f));
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		// TODO Auto-generated method stub
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		// TODO Auto-generated method stub
		return Collections.singletonList
				(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));	}

	public float getHp() {
		return this.hp;
	}
	public void setHp(float health) {
		if (((this.hp + health)<=5.f)&&((this.hp + health)>=0.f)) {
			this.hp += health;
		}
	}
	public ARPGInventory getInventory() {
		return inventory;
	}
	public void setInventory(ARPGInventory inventory) {
		this.inventory = inventory;
	}


	public boolean wantsCellInteraction() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean wantsViewInteraction() {
		// s’il demande une interaction distante
		boolean wanted = false;
		Keyboard keyboard = getOwnerArea().getKeyboard();
		Button e = keyboard.get(Keyboard.E);
		if(e.isDown() || (keyboard.get(Keyboard.SPACE).isDown() && (currentMode == ARPGMode.SWORD || currentMode == ARPGMode.BOW))) {
			wanted = true;
		}
		return wanted;
	}


	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}
	public void interactWith(ARPGItem arpgItem) {
		arpgItem.acceptInteraction(handler);
	}

	@Override
	public boolean takeCellSpace() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCellInteractable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);

	}


	private void moveOrientate(Orientation orientation, Button b){

		if(b.isDown()) {
			if(getOrientation() == orientation) move(ANIMATION_DURATION);
			else orientate(orientation);
		}
	}

	public void update(float deltaTime) {
		//System.out.println(getCurrentCells());
		if (hp < 0) hp = 0.f;
		Keyboard keyboard= getOwnerArea().getKeyboard();
		moveOrientate(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
		moveOrientate(Orientation.UP, keyboard.get(Keyboard.UP));
		moveOrientate(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
		moveOrientate(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

		if(keyboard.get(Keyboard.TAB).isDown()){
			nextCurrentObject();
			//			System.out.println("current object is a " + currentObject);
		}

		if(keyboard.get(Keyboard.SPACE).isDown()) {
			interactWith(currentObject);
			//			System.out.println(currentObject);
		}

		if(!isDisplacementOccurs()) {
			animations[getOrientation().ordinal()].reset();
		}

		else {
			animations[getOrientation().ordinal()].update(deltaTime);
		}
		animations[getOrientation().ordinal()].update(deltaTime);
		super.update(deltaTime);
		
		if (currentObject == ARPGItem.CastleKey) {
			currentMode = ARPGMode.NoBusy;
		}
		
		if (currentMode == ARPGMode.NoBusy) {
			animations[getOrientation().ordinal()].update(deltaTime);
		}
		
		if (currentObject == (ARPGItem.Sword)) {	
			currentMode = ARPGMode.SWORD;}
		if ((currentMode == ARPGMode.SWORD) && keyboard.get(Keyboard.SPACE).isDown()) {
			swordAnimations[getOrientation().ordinal()].update(deltaTime);}

		if (currentObject == (ARPGItem.Bow)) {	
			currentMode = ARPGMode.BOW;}
		if (currentMode == ARPGMode.BOW && keyboard.get(Keyboard.SPACE).isDown()) {
			bowAnimations[getOrientation().ordinal()].update(deltaTime);
			Arrow arrow = new Arrow (getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().left(),4,1);
			switch(getOrientation()) {
			case LEFT:
				arrow = new Arrow (getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().left(),4,1);
				break;
			case RIGHT:
				arrow = new Arrow (getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().right(),4,1);
				break;
			case UP:
				arrow = new Arrow (getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().up(),4,1);
				break;
			case DOWN:
				arrow = new Arrow (getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().down(),4,1);
				break;
			}	
			getOwnerArea().registerActor(arrow);
		}

		if (currentObject.equals(ARPGItem.Staff)) {	
			currentMode = ARPGMode.STAFF;}
		if (currentMode == ARPGMode.STAFF && keyboard.get(Keyboard.SPACE).isDown()) {
			staffAnimations[getOrientation().ordinal()].update(deltaTime);
			MagicWaterProjectile water = new MagicWaterProjectile (getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().left(),4,1);
			switch(getOrientation()) {
			case LEFT:
				water = new MagicWaterProjectile (getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().left(),4,1);
				break;
			case RIGHT:
				water = new MagicWaterProjectile (getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().right(),4,1);
				break;
			case UP:
				water = new MagicWaterProjectile (getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().up(),4,1);
				break;
			case DOWN:
				water = new MagicWaterProjectile (getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().down(),4,1);
				break;
			}	
			getOwnerArea().registerActor(water);
		}
	}
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		message.draw(canvas);
		//		spriteBow.draw(canvas);


		if (currentMode == ARPGMode.SWORD) {
			swordAnimations[getOrientation().ordinal()].draw(canvas);
		}
		else if (currentObject.equals(ARPGItem.Staff)) {
			staffAnimations[getOrientation().ordinal()].draw(canvas);
		}
		else if(currentMode == ARPGMode.BOW) {

			bowAnimations[getOrientation().ordinal()].draw(canvas);

		}

		else {
			animations[getOrientation().ordinal()].draw(canvas);
		}



		arpgPlayerStatusGUI.draw(canvas);
	}

	public class ARPGPlayerHandler implements ARPGInteractionVisitor{

		public void interactWith(Bomb bomb) {
			//			if (currentMode == ARPGMode.SWORD) {
			bomb.explose();
			//			}
		}
		public void interactWith(Door door) {
			//			System.out.println("Door interaction");

			// fait en sorte que la porte soit passée par l'acteur
			setIsPassingADoor(door);
		}


		public void interactWith(Grass grass) {
			//fait partir l'herbe
			if (keyboard.get(Keyboard.E).isDown()|| (keyboard.get(Keyboard.SPACE).isDown()) && currentMode == ARPGMode.SWORD) {
				grass.cut();
			}
		}
		public void interactWith(Heart heart) {
			heart.Collect();
			setHp(1.f);
		}
		public void interactWith(Potion potion) {
			potion.Collect();
			setHp(5.f);
		}
		public void interactWith(Coin coin) {
			coin.Collect();
			inventory.addMoney(50);
		}
		
		public void interactWith (CastleKey castleKey) {
			castleKey.Collect();
			inventory.addInventoryItem(ARPGItem.CastleKey, 1);
		}

		public void interactWith(CastleDoor castleDoor) {
			if (castleDoor.isOpen()) {
				setIsPassingADoor(castleDoor);
				castleDoor.close();
				return;
			}
		
		}


		public void interactWith(ARPGItem arpgItem) {

			switch(arpgItem.getTitle()) {
		
			case "Bomb":
				//interaction bombe;
				if (inventory.removeInventoryItem(ARPGItem.Bomb,1)){

					Bomb bomb1 = new Bomb (getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().left());
					switch(getOrientation()) {
					case LEFT:
						bomb1 = new Bomb (getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().left());
						break;
					case RIGHT:
						bomb1 = new Bomb (getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().right());
						break;
					case UP:
						bomb1 = new Bomb (getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().up());
						break;
					case DOWN:
						bomb1 = new Bomb (getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().down());
						break;
					}	
					getOwnerArea().registerActor(bomb1);

				}
				
			case "CastleKey":
				
				CastleKey key = new CastleKey(getOwnerArea(), Orientation.LEFT, getCurrentMainCellCoordinates().up());
				getOwnerArea().registerActor(key);

			}
		}
	}
}