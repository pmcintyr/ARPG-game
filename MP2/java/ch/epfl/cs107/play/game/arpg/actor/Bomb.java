package ch.epfl.cs107.play.game.arpg.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Bomb extends AreaEntity implements Interactor{
	
	BombHandler handler = new BombHandler();
	private Sprite bomb ;
	private  int retardateur = 10;
	private Animation animation ;
	private boolean hasExplode = false;
	private boolean verite;
	private Bombexpl currentMode;
	
	public enum Bombexpl {
		
		EXPL;
	}
	
	public Bomb(Area area, Orientation orientation, DiscreteCoordinates position) {
		
		super(area, orientation, position);
		// TODO Auto-generated constructor stub
		bomb = new RPGSprite ("zelda/bomb",1,1,this,new RegionOfInterest(0, 0, 16, 16));
		RPGSprite [] sprites = new RPGSprite [5];
		for (int i = 0; i < 5; ++i) {
			sprites [i] = new RPGSprite("zelda/explosion", 1, 1, this, new RegionOfInterest(32*i, 0, 32, 32));
		}
		 animation = new Animation(5, sprites,false);
		
	}
	@Override
	public void draw(Canvas canvas) {
		//	 TODO Auto-generated method stub
		if(retardateur >0 && currentMode != Bombexpl.EXPL) {
			bomb.draw(canvas);
		}else if(!animation.isCompleted() && currentMode == Bombexpl.EXPL) {
			animation.draw(canvas);
		}
	}
	
	
	public void update (float deltaTime) {
		retardateur -= deltaTime;
		animation.update(deltaTime);
		
		if(retardateur<=0 && !hasExplode) {
			currentMode = Bombexpl.EXPL;
			animation.reset();
			hasExplode=true;
		}
		
		
		if (animation.isCompleted()&& retardateur <= 0) {
			getOwnerArea().unregisterActor(this);
		}
		
		super.update(deltaTime);
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
	public List<DiscreteCoordinates> getCurrentCells() {
		// TODO Auto-generated method stub
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}


	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		// TODO Auto-generated method stub
		return (getCurrentMainCellCoordinates().getNeighbours());
	}


	@Override
	public boolean wantsCellInteraction() {
		// TODO Auto-generated method stub
		 	if (retardateur <= 0)
		 		setVerite();
		 	
		 	return verite;
	}


	@Override
	public boolean wantsViewInteraction() {
		// TODO Auto-generated method stub
	 	if (retardateur <= 0)
	 		setVerite();
	 	
	 	return verite;
	 		
	}
	private void setVerite () {
		verite = true;
	}


	@Override
	public void interactWith(Interactable other) {
		// TODO Auto-generated method stub
		other.acceptInteraction(handler); 
	}
	public void explose () {
		currentMode = Bombexpl.EXPL;
	}

	private class BombHandler implements ARPGInteractionVisitor{
		public void interactWith (Grass grass) {
			grass.cut();
		}
		
		public void interactWith (LogMonster log) {
			log.Destroy();
		}
		
		public void interactWith (ARPGPlayer player) {
			player.setHp(-1.f);
		}

	}

}
