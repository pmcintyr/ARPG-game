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
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class FireSpell extends AreaEntity implements Interactor{
	
	private Animation animFire ;
	private float lifeTime, MIN_SPELL_DURATION = 5f, MAX_SPELL_DURATION = 10f;
	private SpellHandler handler = new SpellHandler();
	private boolean canOcc = true ;
	private Orientation  orientate [] = {Orientation.UP,Orientation.DOWN,Orientation.LEFT,Orientation.RIGHT};



	public FireSpell(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		// TODO Auto-generated constructor stub
		
		RPGSprite [] sprites = new RPGSprite [7];
		for (int i = 0; i < 7; ++i) {
			sprites [i] = new RPGSprite("zelda/fire", 1.f, 1.f, this, new RegionOfInterest(16*i, 0, 16, 16));
		}
		 animFire = new Animation(7, sprites,true);
		 
		 lifeTime =  RandomGenerator.getInstance().nextFloat() * (MAX_SPELL_DURATION-MIN_SPELL_DURATION) + MIN_SPELL_DURATION ;

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
		
		animFire.draw(canvas);
		
	}
	
	public void update (float deltaTime) {
		
		lifeTime -= deltaTime;
		//System.out.println(lifeTime);
		
		
		if (lifeTime <= 0) {
			
			getOwnerArea().unregisterActor(this);
			
		}
		
		super.update(deltaTime);
		animFire.update(deltaTime);
		
		if (canOcc) {
		createFireSpell(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
		canOcc = false;
		}
		
	
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		// TODO Auto-generated method stub
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean wantsCellInteraction() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean wantsViewInteraction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void interactWith(Interactable other) {
		// TODO Auto-generated method stub
		
		other.acceptInteraction(handler);
	}
	
	private void createFireSpell (DiscreteCoordinates newFire) {
		
		
		// seulement si y a rien
		FireSpell fire = new FireSpell(getOwnerArea(), Orientation.DOWN, newFire);
		getOwnerArea().registerActor(fire);
		
	}
	
	public void DestroySpell() {
		getOwnerArea().unregisterActor(this);
	}
	
	private class SpellHandler implements ARPGInteractionVisitor{
		
		public void interactWith (Bomb bomb) {
			
			bomb.explose();
		}
		
		public void interactWith (LogMonster log) {
			
			log.Destroy();
		}
		
		public void interactWith (Grass grass) {
			grass.cut();
		}
	}

}
