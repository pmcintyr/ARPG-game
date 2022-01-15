package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.tutos.actor.SimpleGhost;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.signal.logic.LogicGate;
import ch.epfl.cs107.play.window.Canvas;

public class Chateau extends ARPGArea{
	
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "zelda/Chateau";
	}

	@Override
	protected void createArea() {
		// TODO Auto-generated method stub
		Background background = new Background(this);
		registerActor(background);
		
		registerActor(new Door("zelda/RouteChateau", new DiscreteCoordinates(9, 12), LogicGate.TRUE,
				this, Orientation.DOWN, new DiscreteCoordinates(7, 0),new DiscreteCoordinates(8, 0)));
		
		Door door1 = new Door("GrotteMew",new DiscreteCoordinates(7, 9), LogicGate.TRUE, this, Orientation.DOWN,
			new DiscreteCoordinates(14, 12));
		registerActor(door1);
		registerActor(new SimpleGhost(new Vector(14, 12), "Shadow"));
	}
	@Override
	public void update (float deltaTime) {
		super.update(deltaTime);
	}
	
	
	
}
