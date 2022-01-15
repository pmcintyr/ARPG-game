package ch.epfl.cs107.play.game.arpg.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer.ARPGMode;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Arrow extends Projectile implements Interactor{
	private Keyboard keyboard = getOwnerArea().getKeyboard();
	private Arrowmode currentMode;
	private Sprite arrow;
	private float change = 0.05f;
	private final Orientation  orientate [] = {Orientation.UP,Orientation.DOWN,Orientation.LEFT,Orientation.RIGHT};


	ArrowHandler handler = new ArrowHandler();

	private enum Arrowmode {
		DRAW;
	}
	public Arrow(Area area, Orientation orientation, DiscreteCoordinates position, int remainingFramesForCurrentMove,
			int FramesForCurrentMove) {
		super(area, orientation, position, remainingFramesForCurrentMove, FramesForCurrentMove);
		// TODO Auto-generated constructor stub


		if (orientation == Orientation.DOWN) {
			arrow = new RPGSprite ("zelda/arrow",1,1,this,new RegionOfInterest(64, 0, 32, 32));
		}

		else if (orientation == Orientation.UP) {
			arrow = new RPGSprite ("zelda/arrow",1,1,this,new RegionOfInterest(0, 0, 32, 32));
		}

		else if (orientation == Orientation.RIGHT) {
			arrow = new RPGSprite ("zelda/arrow",1,1,this,new RegionOfInterest(32, 0, 32, 32));
		}

		else if (orientation == Orientation.LEFT) {
			arrow = new RPGSprite ("zelda/arrow",1,1,this,new RegionOfInterest(96, 0, 32, 32));
		}
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
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		// TODO Auto-generated method stub
		return Collections.singletonList
				(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
	}
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub

		arrow.draw(canvas);
	}
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		change -= deltaTime;

		if (change <= 0) {
			getOwnerArea().unregisterActor(this);


			Arrow arrow = new Arrow(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().left(), 4, 1);
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
	}
	

	
	@Override
	public boolean isViewInteractable() {
		return true;
	}
	@Override
	public boolean isCellInteractable() {
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
		other.acceptInteraction(handler); 
		

	}



	private class ArrowHandler implements ARPGInteractionVisitor{
		public void interactWith (LogMonster logMonstre) {
		
			logMonstre.takePv(0.5f);
		}
		//		}

		public void interactWith(Grass grass) {
			grass.cut();
		}

		public void interactWith(Bomb bomb) {
			bomb.explose();
			//			arrow1.abortCurrentMove();
		}

		public void interactWith(ARPGPlayer arpgPlayer) {
			if (arpgPlayer.currentMode == ARPGMode.BOW && keyboard.get(Keyboard.SPACE).isDown()){
				currentMode = Arrowmode.DRAW;
			}
			
		}
		
		public void interactWith (FireSpell spell) {
			spell.DestroySpell();
		}
		
	}
}
	


