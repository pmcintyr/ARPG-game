package ch.epfl.cs107.play.game.arpg.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class Monstre extends MovableAreaEntity implements Interactor{
	private float PvMax = 10;
	private float Pv;
	protected Animation animation ;
	protected int ANIMATION_DURATION = 10;

	protected enum MonsterVuln {

		MAGIE,
		PHYSIQUE,
		FEU;

	}

	public Monstre(Area area, Orientation orientation, DiscreteCoordinates position) { // que donner en argument pour ajouter les defauts du personnage a vuln ?
		super(area, orientation, position);
		// TODO Auto-generated constructor stub
		Sprite [] sprites = new Sprite [4];
		for (int i = 0; i < 4; ++i) {
			sprites [i] = new Sprite("zelda/vanish", 2, 2, this,new RegionOfInterest(32*i, 0, 32, 32)); 
		}
		animation = new Animation(4, sprites,false);
		Pv = PvMax;
	}
	
	public void takePv(float f) {
		// TODO Auto-generated method stub
		Pv -= f;
	}
	
	public float getPv() {
		return Pv;
	}

	public void update (float deltaTime) {
		super.update(deltaTime);
		animation.update(deltaTime);
	}

	protected void moveOrientate (Orientation orientation) {
		if(getOrientation() == orientation) {
			move(ANIMATION_DURATION);
		}
		else orientate(orientation);
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean wantsCellInteraction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wantsViewInteraction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		// TODO Auto-generated method stub
		return  Collections.singletonList(getCurrentMainCellCoordinates());
	}

	public List<DiscreteCoordinates> getFieldOfViewCells() {
		// TODO Auto-generated method stub
		return null ; // trop spÃ©cifique pour le dÃ©terminer mtn.
	}

	@Override
	public boolean takeCellSpace() {
		// TODO Auto-generated method stub
		boolean verite = true;
		if (Pv <= 0)
			verite = false;

		return verite ;
	}

	@Override
	public boolean isCellInteractable() {
		// TODO Auto-generated method stub
		return false;
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
	public void interactWith(Interactable other) {
		// TODO Auto-generated method stub
	}

}
