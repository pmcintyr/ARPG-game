package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.arpg.actor.Monstre.MonsterVuln;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class FlameSkull extends Monstre implements Flyable{
	private Keyboard keyboard = getOwnerArea().getKeyboard();
	private boolean affiche ;
	private float lifeTime = 10f;
	private boolean hasOcc = false, isOk = true;
	private final int ANIMATION_DURATION = 50;
	private SkullHandler handler = new SkullHandler();
	private Animation[] skullAnim;
	private Sprite[][] sprites;
	private Orientation  orientate [] = {Orientation.UP,Orientation.DOWN,Orientation.LEFT,Orientation.RIGHT};


	

	public FlameSkull(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		// TODO Auto-generated constructor stub

		
		 sprites = RPGSprite.extractSprites("zelda/flameSkull", 4, 2.f, 2.f, this, 32, 32,orientate);
		
			 skullAnim = RPGSprite.createAnimations(ANIMATION_DURATION / 2, sprites);
			
			 
			 MonsterVuln magic = MonsterVuln.MAGIE;
				MonsterVuln ph = MonsterVuln.PHYSIQUE;
				MonsterVuln [] Skull = {magic,ph};
		
	}
	
	public void update (float deltaTime) {
		lifeTime -= deltaTime;
		
		if (lifeTime <= 0 && !hasOcc) {
			animation.reset();
			hasOcc = true ;
			
		}
		
			
		if (animation.isCompleted() && lifeTime<= 0) {
			getOwnerArea().unregisterActor(this);
		}
		
		if (!hasOcc ) {
			
			moveOrientate(orientate [RandomGenerator.getInstance().nextInt(4)]);
			
			   if(!isDisplacementOccurs()) {
					skullAnim[getOrientation().ordinal()].reset();
				}
			   else if (!hasOcc) {
				   
					skullAnim[getOrientation().ordinal()].update(deltaTime);
					
				}


			 skullAnim[getOrientation().ordinal()].update(deltaTime);
		}

		
		super.update(deltaTime);
	}
	
	
	public void draw (Canvas canvas) {
		super.draw(canvas);
		
		if (((keyboard.get(Keyboard.S).isDown() || affiche) || isOk)&&lifeTime > 0 ) {
			
			skullAnim[getOrientation().ordinal()].draw(canvas);
			setaffiche();
			
		} else if (!animation.isCompleted() && lifeTime <= 0) {
			animation.draw(canvas);
		}
	}
	
	
	public void interactWith(Interactable other) {
		// TODO Auto-generated method stub
		other.acceptInteraction(handler); 
		
	}
	
	public boolean wantsCellInteraction() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void setaffiche () {
		affiche = true;
	}
	

	
	public void createFlame () {
		
		FlameSkull skull = new FlameSkull(getOwnerArea(), orientate[RandomGenerator.getInstance().nextInt(3)], 
				new DiscreteCoordinates(RandomGenerator.getInstance().nextInt(getOwnerArea().getHeight()), RandomGenerator.getInstance().nextInt(getOwnerArea().getWidth())));
		getOwnerArea().registerActor(skull);
		
		
	}

	
	private class SkullHandler implements  ARPGInteractionVisitor{
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
