package ch.epfl.cs107.play.game.rpg.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.arpg.actor.ARPGInventory;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public abstract class CollectableAreaEntity extends AreaEntity {
	private Sprite sprite;
	public CollectableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		// TODO Auto-generated constructor stub
	}

	public boolean isCollectable() {
		if (!getOwnerArea().leaveAreaCells(this, this.getCurrentCells())) {
			return false;
		}
		return true;
	}
	
	public void Collect() {
		if (isCollectable()) {
			getOwnerArea().unregisterActor(this);
		}
	}
}


	