package ch.epfl.cs107.play.game.arpg.actor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

public class DarkLord extends Monstre{
	
	private DarkMode currentMode ;
	private Sprite [][] walk , invoc;
	private Animation [] DarkWalk , DarkInvoc;
	private final Orientation  orientate [] = {Orientation.UP,Orientation.DOWN,Orientation.LEFT,Orientation.RIGHT};
	private final int animationDuration = 50 ;
	private final float MIN_SPELL_WAIT_DURATION = 10f,MAX_SPELL_WAIT_DURATION = 20f, max = 20f, min = 5f, seuil = 10f;
	private float cycle , choixLord, inac, inaction;
	private boolean isOk = true , change = true, hasOcc = false;
	private DarkLordHandler handler = new DarkLordHandler();
	
	private enum DarkMode {
		
		IDLE,
		ATTACK,
		INVOC,
		INVOCTELEPORTATION,
		TELEPORT,
		CONDITIONNE,
		INAC,
		DESTROYED;
	}
	
	public DarkLord(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		// TODO Auto-generated constructor stub
		
	
		currentMode = DarkMode.IDLE;
		
		walk = RPGSprite.extractSprites("zelda/darkLord", 3, 3.f, 3.f, this, 32, 32, orientate);
		DarkWalk = RPGSprite.createAnimations(animationDuration, walk);
		
		invoc = RPGSprite.extractSprites("zelda/darkLord.spell", 3, 3.f, 3.f, this, 32, 32, orientate);
		DarkInvoc = RPGSprite.createAnimations(animationDuration, invoc);
		
		cycle = getRandomSpell();
		
	
		
		inac = getRandom();
		
		MonsterVuln magic = MonsterVuln.MAGIE;
		MonsterVuln [] Dark = {magic};
		
	}
	
	private float getRandomSpell () {
		return  RandomGenerator.getInstance().nextFloat() * (MAX_SPELL_WAIT_DURATION-MIN_SPELL_WAIT_DURATION) + MIN_SPELL_WAIT_DURATION ;

	}
	private float getRandom () {
		return RandomGenerator.getInstance().nextFloat() * (max-min) + min ;
	}
	
	public boolean isCellInteractable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean wantsViewInteraction() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isViewInteractable() {
		// TODO Auto-generated method stub
		return true;
	}

	
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		// TODO Auto-generated method stub
		List<DiscreteCoordinates> fieldOfView = new LinkedList<DiscreteCoordinates>();
		DiscreteCoordinates current =getCurrentMainCellCoordinates();
		for(int i=0; i<8; i++) {
			current=current.jump(Orientation.UP.toVector());
			fieldOfView.add(current);
		}
		
		for(int i=0; i<8; i++) {
			current=current.jump(Orientation.DOWN.toVector());
			fieldOfView.add(current);
		}
		
		for(int i=0; i<8; i++) {
			current=current.jump(Orientation.LEFT.toVector());
			fieldOfView.add(current);
		}
		
		for(int i=0; i<8; i++) {
			current=current.jump(Orientation.RIGHT.toVector());
			fieldOfView.add(current);
		}
		
		return fieldOfView; 
	
	}
	
	public void interactWith(Interactable other) {
		// TODO Auto-generated method stub
		other.acceptInteraction(handler);   
		
	}
	
	
	
	public void update (float deltaTime) {
		

		cycle -= deltaTime;
		inac -= deltaTime;
		inaction -= deltaTime;

		if (cycle <= 0 && isOk) {

			currentMode = DarkMode.CONDITIONNE;
			isOk = false;

		}
		
		
		if (currentMode == DarkMode.INAC && inaction > 0) {
			
			//System.out.println(inaction);
			
			DarkWalk[getOrientation().ordinal()].update(deltaTime);
			
		} else if (currentMode == DarkMode.INAC && inaction <= 0) {
			
			inac = getRandom();
			currentMode = DarkMode.IDLE;
			cycle = getRandomSpell();
		}
		
		
		if (currentMode == DarkMode.IDLE && cycle > 0 && inac > 0) {
			
			hasOcc = false;
			
			DarkWalk[getOrientation().ordinal()].update(deltaTime);
			moveOrientate(orientate [RandomGenerator.getInstance().nextInt(orientate.length)]);
		//	System.out.println(cycle);
			hasOcc = false;
		} else if (currentMode == DarkMode.IDLE && inac <= 0) {
			
			currentMode = DarkMode.INAC;
			inaction = 5f;
		}
		
		if (currentMode == DarkMode.INVOC && cycle > 0 && !hasOcc) {
			
			DarkInvoc[getOrientation().ordinal()].update(deltaTime);
			change = true;
			//System.out.println("invoc");
			List<DiscreteCoordinates> flame = null;
			do {
				flame = Collections.singletonList(
						
						new DiscreteCoordinates(RandInt(getOwnerArea().getWidth()), RandInt(getOwnerArea().getHeight())));
				
			}while(!this.getOwnerArea().canEnterAreaCells(this, flame));
			
			//System.out.println("feu");
			FlameSkull skull = new FlameSkull(getOwnerArea(), Orientation.DOWN, flame.get(0));
			getOwnerArea().registerActor(skull);
			hasOcc = true;
			if (hasOcc) {
				
			currentMode = DarkMode.IDLE;
			cycle = getRandomSpell();
			inac = getRandom();
			
			}
		}
		
		
		if (currentMode == DarkMode.CONDITIONNE && !isOk) {
			
			choixLord = getRandom();
		//	System.out.println("conditionne");
			
			if (choixLord > seuil && change) {
				
				currentMode = DarkMode.ATTACK;
			//	System.out.println("attack");
				change = false;
				cycle = getRandomSpell();
				
			} else if (change) {
				
				change = false;
				cycle = getRandomSpell();
				currentMode = DarkMode.INVOC;
				//System.out.println("invoc");
			}
		}
		
		if (currentMode == DarkMode.ATTACK) {
			
		//	System.out.println("attack");
			DarkInvoc[getOrientation().ordinal()].update(deltaTime);
				
				List<DiscreteCoordinates> fire = null;
				do {
					fire = Collections.singletonList(
							
							new DiscreteCoordinates(RandInt(getOwnerArea().getWidth()), RandInt(getOwnerArea().getHeight())));
					
				}while(!this.getOwnerArea().canEnterAreaCells(this, fire));
				
				//System.out.println("feu");
				FireSpell spell = new FireSpell(getOwnerArea(), Orientation.DOWN, fire.get(0));
				getOwnerArea().registerActor(spell);
			currentMode = DarkMode.TELEPORT;
			change = true;
			
		}
		
		if (currentMode == DarkMode.INVOCTELEPORTATION) {
			
			DarkInvoc[getOrientation().ordinal()].update(deltaTime);
			
			if (DarkInvoc[getOrientation().ordinal()].isCompleted()) {
				
				currentMode = DarkMode.TELEPORT;
			}
			
		}
		
		if (currentMode == DarkMode.TELEPORT) {
			
			List<DiscreteCoordinates> next = null;
			do {
				next = Collections.singletonList(
						new DiscreteCoordinates(RandInt(getOwnerArea().getWidth()), RandInt(getOwnerArea().getHeight())));
				
			}while(!this.getOwnerArea().canEnterAreaCells(this, next));
			//System.out.println(this.getOwnerArea().canEnterAreaCells(this, next)+" "+next);
			this.getOwnerArea().unregisterActor(this);
			creatLord(next.get(0));
		}
		
		
		if (getPv() <= 0 && animation.isCompleted()) {
			
			getOwnerArea().registerActor(new CastleKey(getOwnerArea(), getOrientation(),
					getCurrentMainCellCoordinates().up()));
			
			getOwnerArea().unregisterActor(this);
			
		}
		
		super.update(deltaTime);
		
	}
	
	
	private int RandInt(int max) {
		return RandomGenerator.getInstance().nextInt(getOwnerArea().getWidth());
	}
	
	private void creatLord (DiscreteCoordinates nextPos) {
		
		DarkLord lord = new DarkLord(getOwnerArea(), orientate [RandomGenerator.getInstance().nextInt(orientate.length-1)], nextPos);
		
		getOwnerArea().registerActor(lord);
	}
	
	public void draw (Canvas canvas) {
		
		if (currentMode == DarkMode.IDLE || currentMode == DarkMode.INAC) {
			
			DarkWalk[getOrientation().ordinal()].draw(canvas);
		}
		
		if (currentMode == DarkMode.INVOC || currentMode == DarkMode.ATTACK || currentMode == DarkMode.CONDITIONNE || currentMode == DarkMode.INVOCTELEPORTATION) {
			
			DarkInvoc[getOrientation().ordinal()].draw(canvas);

		}
		
		if (getPv() <= 0 && ! animation.isCompleted()) {
			animation.draw(canvas);
		}
		
	}
	
	private class DarkLordHandler implements ARPGInteractionVisitor {

		public void interactWith (ARPGPlayer player) {


			currentMode = DarkMode.ATTACK;

		}
		

	}

}
