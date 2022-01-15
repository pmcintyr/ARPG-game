package ch.epfl.cs107.play.game.arpg.actor;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.cs107.play.Play;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class LogMonster extends Monstre {

	//private int ANIMATION_DURATION = 10;
	private boolean hasOcc = false, isOk = true, move = false, wantSleep = false ;
	private float inaction = 5f, change , max = 10f, min = 5f, MIN_SLEEP_DURATION = 5f, MAX_SLEEP_DURATION = 10f, GoesSleep;
	private LogMode currentMode ;
	private Animation[] LogAtt;
	private Animation dort,isWaking;
	private Sprite[][] att;
	private final Orientation  orientate [] = {Orientation.UP,Orientation.DOWN,Orientation.LEFT,Orientation.RIGHT};
	private LogMonsterHandler handler = new LogMonsterHandler();


	private enum LogMode{
		ATTACK,
		SLEEP ,
		WAKE,
		IDLE,
		INAC,
		DESTROYED;


	}
	

	public LogMonster(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		// TODO Auto-generated constructor stub
		
		currentMode = LogMode.IDLE;
		change = getRandom();
		GoesSleep = getRandomSleep();
		
		MonsterVuln feu = MonsterVuln.FEU;
		MonsterVuln ph = MonsterVuln.PHYSIQUE;
		MonsterVuln [] Log = {feu,ph};

		att = RPGSprite.extractSprites("zelda/logMonster", 4, 2.f, 2.f, this, 32, 32,orientate);
		LogAtt = RPGSprite.createAnimations(ANIMATION_DURATION / 2, att);

		RPGSprite [] endormi = new RPGSprite [4];
		for (int i = 0; i < 4; ++i) {
			endormi [i] = new RPGSprite("zelda/logMonster.sleeping", 2, 2, this, new RegionOfInterest(0, 32*i, 32, 32));
		}
		 dort = new Animation(5, endormi,true);



		RPGSprite [] reveil = new RPGSprite [3];
		for (int i = 0; i < 3; ++i) {
			reveil [i] = new RPGSprite("zelda/logMonster.wakingUp", 2, 2, this, new RegionOfInterest(0, 32*i, 32, 32));
		}
		 isWaking = new Animation(5, reveil,false);

	}


	public void update (float deltaTime) {
		
		//ajouter des valeurs randoms pour changer de state

		change-=deltaTime;
		GoesSleep -= deltaTime;
		inaction -= deltaTime;
		
		if (currentMode == LogMode.IDLE && change > 0f) {
			
			
			moveOrientate(orientate [RandomGenerator.getInstance().nextInt(3)]);
			
			LogAtt[getOrientation().ordinal()].update(deltaTime);
			
			
		} else if (change <= 0 && !move) {
			
			isOk = true ;
			move = true ;
			inaction = 10f;
			currentMode = LogMode.INAC;
			
		} else if  (GoesSleep <= 0 && wantSleep) {
			
			currentMode = LogMode.SLEEP;
			wantSleep = false ;
		}
		
		if (currentMode == LogMode.INAC && inaction > 0) {
			isWaking.update(deltaTime);
				
			} else if (inaction <= 0 && isOk) {
				
				currentMode = LogMode.IDLE;
				change = getRandom();
				isOk = false ;
				move = false;
				
				
			} 
		
		if (currentMode == LogMode.ATTACK) {
			
			LogAtt[getOrientation().ordinal()].update(deltaTime);
			
			moveOrientate(getOrientation());
			
			if (!isDisplacementOccurs()) {
				
				GoesSleep = getRandomSleep();
				currentMode = LogMode.INAC;
				wantSleep = true ;
				
			}
		}
		
		if (currentMode == LogMode.SLEEP && inaction > 0) {
			
			dort.update(deltaTime);
			
			if (inaction <= 0) {
				
				currentMode = LogMode.WAKE;
				
			}
		}
		
		if (currentMode == LogMode.WAKE) {
			
			isWaking.update(deltaTime);
			
			if (isWaking.isCompleted()) {
				
				currentMode = LogMode.INAC;
				
				
			}
		}
		

		if (!hasOcc && currentMode == LogMode.DESTROYED) {
			animation.reset();
			hasOcc = true ;

		}
		
		if (animation.isCompleted()&& getPv() <= 0) {
			getOwnerArea().unregisterActor(this);
			getOwnerArea().registerActor(new Coin(getOwnerArea(), getOrientation(),
					getCurrentMainCellCoordinates().up()));
		}

		super.update(deltaTime);
		
	}
	
	
	public boolean wantsViewInteraction() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		// TODO Auto-generated method stub
		List<DiscreteCoordinates> fieldOfView = new LinkedList<DiscreteCoordinates>();
		DiscreteCoordinates current =getCurrentMainCellCoordinates();
		for(int i=0; i<8; i++) {
			current=current.jump(getOrientation().toVector());
			fieldOfView.add(current);
		}
		
		return fieldOfView; // faire un rayon de 8
	
	}
	
	@Override
	public boolean isCellInteractable() {
		// TODO Auto-generated method stub
		if (currentMode == LogMode.INAC || currentMode == LogMode.ATTACK) {
			
			return true;
			
			
		}
		
		 return false;
	}
	
	public void interactWith(Interactable other) {
		// TODO Auto-generated method stub
		other.acceptInteraction(handler);   
		
	}
	
	public void acceptInteraction(AreaInteractionVisitor v) {
		// TODO Auto-generated method stub
		((ARPGInteractionVisitor)v).interactWith(this);
	}

	
	private float getRandom () {
		return RandomGenerator.getInstance().nextFloat() * (max-min) + min ; // pour avoir un float compris entre max et min
	}
	
	private float getRandomSleep () {
		return RandomGenerator.getInstance().nextFloat() * (MAX_SLEEP_DURATION-MIN_SLEEP_DURATION) + MIN_SLEEP_DURATION ;
	}



	public void draw (Canvas canvas) {
		
		if ( getPv() > 0 ) {
			if (currentMode == LogMode.IDLE) {
				LogAtt[getOrientation().ordinal()].draw(canvas);
				ANIMATION_DURATION = 10;
			}
			
			if (currentMode == LogMode.WAKE) {
				isWaking.draw(canvas);
			}
			if (currentMode == LogMode.SLEEP) {
				dort.draw(canvas);
			}
			if (currentMode == LogMode.ATTACK) {
				LogAtt[getOrientation().ordinal()].draw(canvas);
				ANIMATION_DURATION = 3;

			}
			if (currentMode == LogMode.INAC) {
				LogAtt[getOrientation().ordinal()].draw(canvas);

			}

			
		} 
		
		 if ( getPv() <=0 && !animation.isCompleted()) {
			animation.draw(canvas);
			
		}
	}
	
	public void Destroy() {
		
			
			currentMode = LogMode.DESTROYED;
			takePv(getPv());

	}
	
	private class LogMonsterHandler implements ARPGInteractionVisitor {
		
		public void interactWith (ARPGPlayer player) {
			
				
				currentMode = LogMode.ATTACK;
				
			
		}
		
	}
}

