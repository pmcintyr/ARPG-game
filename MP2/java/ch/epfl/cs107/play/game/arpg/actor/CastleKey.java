package ch.epfl.cs107.play.game.arpg.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.InventoryItem;
import ch.epfl.cs107.play.game.rpg.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class CastleKey extends CollectableAreaEntity implements Interactor{
	private Sprite castleKey;
	private boolean isCollected = false;

	private CastleKeyHandler handler = new CastleKeyHandler();
	public CastleKey(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		castleKey = new RPGSprite ("zelda/key",1,1,this,new RegionOfInterest(0, 0, 16, 16));
		// TODO Auto-generated constructor stub
	}
	
	public void Collect() {
		if (isCollectable()) {
			getOwnerArea().unregisterActor(this);
			isCollected = true;
		}
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
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		// TODO Auto-generated method stub
		((ARPGInteractionVisitor)v).interactWith(this);

	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		castleKey.draw(canvas);
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		// TODO Auto-generated method stub
		return Collections.singletonList(getCurrentMainCellCoordinates());

	}

	
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (isCollected) {
			getOwnerArea().unregisterActor(this);
		}
	}



	
	private class CastleKeyHandler implements ARPGInteractionVisitor{
		public void interactWith(CastleDoor castledoor) {
			// TODO Auto-generated method stub
			castledoor.open();
		}
	}




	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		// TODO Auto-generated method stub
		return Collections.singletonList
				(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));	
	}

	@Override
	public boolean wantsCellInteraction() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean wantsViewInteraction() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void interactWith(Interactable other) {
		// TODO Auto-generated method stub
		other.acceptInteraction(handler);

	}
}
