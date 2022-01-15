package ch.epfl.cs107.play.game.arpg.area;


import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.Bomb;
import ch.epfl.cs107.play.game.arpg.actor.CastleDoor;
import ch.epfl.cs107.play.game.arpg.actor.DarkLord;
import ch.epfl.cs107.play.game.arpg.actor.FireSpell;
import ch.epfl.cs107.play.game.arpg.actor.FlameSkull;
import ch.epfl.cs107.play.game.arpg.actor.Grass;
import ch.epfl.cs107.play.game.arpg.actor.LogMonster;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.signal.logic.LogicGate;
import ch.epfl.cs107.play.window.Keyboard;

public class RouteChateau extends ARPGArea{
	int Max = 4;
	Orientation orientation [] = {Orientation.UP,Orientation.DOWN,Orientation.LEFT,Orientation.RIGHT};

	@Override
	protected void createArea() {
		// TODO Auto-generated method stub
		Background background = new Background(this);
		registerActor(background);
		
		registerActor(new Door("zelda/Route", new DiscreteCoordinates(9, 18), LogicGate.TRUE,
				this, Orientation.DOWN, new DiscreteCoordinates(9, 0),new DiscreteCoordinates(10, 0)));
		
		CastleDoor castleDoor = new CastleDoor("zelda/Chateau", new DiscreteCoordinates(7,1) , LogicGate.TRUE,
				this, Orientation.UP,new DiscreteCoordinates (9,13),new DiscreteCoordinates(10,13));
		
		castleDoor.close();
		
		registerActor(castleDoor);
		
		
		LogMonster log = new LogMonster(this, orientation[RandomGenerator.getInstance().nextInt(Max)],
				new DiscreteCoordinates(9,9));
		registerActor(log);
		
		
		registerActor(new DarkLord(this, orientation[RandomGenerator.getInstance().nextInt(orientation.length-1)], 
				new DiscreteCoordinates(7, 9)));
		
		for (int i = 5; i < 8; ++i) {
			for (int j = 6; j < 12; ++j) {
				Grass grass = new Grass(this, Orientation.UP, new DiscreteCoordinates(i, j));
				registerActor(grass);
			}
		}
	}
	public String getTitle() {
		// TODO Auto-generated method stub
		return "zelda/RouteChateau";
	}
	
	public void createFlame () {
		FlameSkull flame = new FlameSkull(this, orientation[RandomGenerator.getInstance().nextInt(Max)],
				new DiscreteCoordinates(RandomGenerator.getInstance().nextInt(this.getHeight()), RandomGenerator.getInstance().nextInt(this.getWidth())));
		registerActor(flame);
		
	}
	
	public void createBomb () {
		Bomb bomb = new Bomb(this, Orientation.DOWN, getRelativeMouseCoordinates());
		registerActor(bomb);
	}
	
	public void update (float deltaTime) {
		super.update(deltaTime);
		Keyboard keyboard = this.getKeyboard();
		if (keyboard.get(Keyboard.S).isDown()) {
			createFlame();
		}
		if (keyboard.get(Keyboard.B).isDown()) {
			createBomb();
		}
	}

}
