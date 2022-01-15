package ch.epfl.cs107.play.game.arpg.actor;

import java.util.List;

import ch.epfl.cs107.play.game.actor.Actor;
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
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class MagicWaterProjectile extends Projectile implements Interactor{
	private Keyboard keyboard = getOwnerArea().getKeyboard();
	private Watermode currentMode ;
	private Sprite water;
	private float change = 0.05f;
	MagicWaterHandler handler = new MagicWaterHandler();
	private enum Watermode {
		DRAW;
	}
	public MagicWaterProjectile(Area area, Orientation orientation, DiscreteCoordinates position,
			int remainingFramesForCurrentMove, int FramesForCurrentMove) {
		super(area, orientation, position, remainingFramesForCurrentMove, FramesForCurrentMove);
		// TODO Auto-generated constructor stub
		if (orientation == Orientation.DOWN) {
			water = new RPGSprite ("zelda/magicWaterProjectile",1,1,this,new RegionOfInterest(64, 0, 32, 32));
		}

		else if (orientation == Orientation.UP) {
			water = new RPGSprite ("zelda/magicWaterProjectile",1,1,this,new RegionOfInterest(0, 0, 32, 32));
		}

		else if (orientation == Orientation.RIGHT) {
			water = new RPGSprite ("zelda/magicWaterProjectile",1,1,this,new RegionOfInterest(32, 0, 32, 32));
		}

		else if (orientation == Orientation.LEFT) {
			water = new RPGSprite ("zelda/magicWaterProjectile",1,1,this,new RegionOfInterest(96, 0, 32, 32));
		}
	}
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub

		water.draw(canvas);
	}
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		change -= deltaTime;

		if (change <= 0) {
			getOwnerArea().unregisterActor(this);


			MagicWaterProjectile water = new MagicWaterProjectile(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().left(), 4, 1);
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
	public boolean isCellInteractable() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean wantsViewInteraction() {
		return true;
	}
	
	@Override
	public boolean isViewInteractable() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		// TODO Auto-generated method stub
		return (getCurrentMainCellCoordinates().getNeighbours());
	}


	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		// TODO Auto-generated method stub
		((ARPGInteractionVisitor)v).interactWith(this);

	}
	
	@Override
	public boolean takeCellSpace() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

	@Override
	public void interactWith(Interactable other) {
		// TODO Auto-generated method stub
		other.acceptInteraction(handler); 		
	}
	
	private class MagicWaterHandler implements ARPGInteractionVisitor{
		public void interactWith (Monstre monstre) {
			//si monstre vulnerable a magie
			//alors 
			//	if (MonsterVuln.MAGIE) {
			monstre.takePv(0.5f);
			//		}
		}
		public void interactWith(ARPGPlayer arpgPlayer) {
			if (arpgPlayer.currentMode == ARPGMode.STAFF && keyboard.get(Keyboard.SPACE).isDown()){
				currentMode = Watermode.DRAW;
			}
		
		}
		
		public void interactWith (FireSpell spell) {
			spell.DestroySpell();
		}
		
		public void interactWith (Grass grass) {
			grass.setGrow();
			grass.setSize(grass.getSize()+0.05f);
		}
		
	}

	@Override
	public boolean wantsCellInteraction() {
		// TODO Auto-generated method stub
		return false;
	}

}
