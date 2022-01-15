package ch.epfl.cs107.play.game.arpg.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Projectile extends MovableAreaEntity //implementsFlyable
{
	private int FramesForCurrentMove;//velocité
	private int remainingFramesForCurrentMove;// distance max atteignable depuis pt de depart
	public Projectile(Area area, Orientation orientation, DiscreteCoordinates position, int remainingFramesForCurrentMove,int FramesForCurrentMove) {
		super(area, orientation, position);
		this.remainingFramesForCurrentMove = remainingFramesForCurrentMove;
		this.FramesForCurrentMove = FramesForCurrentMove;
	}

	protected final boolean stop(){
		return abortCurrentMove();
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
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {}

	@Override
	public void draw(Canvas canvas) {

	}
	public void interactWith(Interactable other) {}
}
