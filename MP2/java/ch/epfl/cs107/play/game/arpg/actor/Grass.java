package ch.epfl.cs107.play.game.arpg.actor;

import java.util.Collections;

import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class Grass extends AreaEntity {
	private Sprite grass ;
	private boolean isCut, canGrow = false;
	private float size = 0.5f;
	private double random;
	private DiscreteCoordinates coordinates;
	private final static double PROBABILITY_TO_DROP_ITEM = 0.5;
	private final static double PROBABILITY_TO_DROP_HEART = 0.5;

	
	public Grass (Area area,Orientation orientation,DiscreteCoordinates coord) {
		super (area,orientation,coord);
		isCut = false ;
		createSprite(getSize(), getSize());
	}
	public boolean isCut() {
		return isCut ;
	}
	public void update (float deltaTime) {
		super.update(deltaTime);
		
		if (canGrow && getSize()<=1f) {
		createSprite(getSize(), getSize());
		canGrow = false;
		}
		
	}
	
	public void setGrow () {
		canGrow = true;
	}
	
	public void setSize (float taille) {
		size = taille ;
	}
	
	public float getSize () {
		return size;
	}
	
	public void createSprite (float width,float height ) {
		grass = new RPGSprite("zelda/grass", width, height, this,new RegionOfInterest(0, 0,16, 16));
	}
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		// TODO Auto-generated method stub
		return Collections.singletonList(getCurrentMainCellCoordinates());
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
		grass.draw(canvas);
		
	}
	public void cut () {
		coordinates = getCurrentMainCellCoordinates();
		isCut = true;
		getOwnerArea().unregisterActor(this);

		random = RandomGenerator.getInstance().nextDouble();
		if (random<PROBABILITY_TO_DROP_ITEM) {
			random = RandomGenerator.getInstance().nextDouble();
			if (random<PROBABILITY_TO_DROP_HEART) {
				getOwnerArea().registerActor(new Coin(getOwnerArea(), getOrientation(),coordinates));
			}
			else {
				getOwnerArea().registerActor(new Heart (getOwnerArea(), getOrientation(), coordinates));
			}
		}

	}
	
}
