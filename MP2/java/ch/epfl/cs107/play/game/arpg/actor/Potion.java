package ch.epfl.cs107.play.game.arpg.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class Potion extends CollectableAreaEntity{
	private Sprite potion;
	public Potion(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		potion = new Sprite("zelda/potion1", 1.f, 1.f, this,new RegionOfInterest(64, 64, 128,128));
	}
	public boolean isHealed = false;

	@Override
	public void Collect() {
		if (isCollectable()) {
			isHealed = true;
		}
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		// TODO Auto-generated method stub
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		// TODO Auto-generated method stub
		((ARPGInteractionVisitor)v).interactWith(this);
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		potion.draw(canvas);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (isHealed) {
			getOwnerArea().unregisterActor(this);
		}
	}

}
