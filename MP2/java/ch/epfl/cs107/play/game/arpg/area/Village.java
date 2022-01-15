package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.Potion;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.tutos.actor.SimpleGhost;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.signal.logic.LogicGate;

public class Village extends ARPGArea{
	
	
	public String getTitle() {
		return "zelda/Village";
	}


	@Override
	protected void createArea() {
		// TODO Auto-generated method stub
		registerActor(new Background(this));
		registerActor(new Foreground(this));
		
		
		registerActor(new SimpleGhost(new Vector(20, 10), "ghost.2"));

		registerActor(new Door("zelda/Ferme", new DiscreteCoordinates (4,1), 
				(LogicGate.TRUE), this, Orientation.UP, new DiscreteCoordinates(4,19),new DiscreteCoordinates(5,19)));
		
		registerActor(new Door("zelda/Ferme", new DiscreteCoordinates (14,1) , 
				(LogicGate.TRUE), this, Orientation.UP, new DiscreteCoordinates(13,19), 
				new DiscreteCoordinates(14,19),new DiscreteCoordinates(15,19)));
		
		registerActor(new Door("zelda/Route", new DiscreteCoordinates (9,1), 
				(LogicGate.TRUE), this, Orientation.UP, new DiscreteCoordinates(29,19),new DiscreteCoordinates(30,19)));
		
		registerActor(new Door("GrotteMew", new DiscreteCoordinates (8,3), 
				(LogicGate.TRUE), this, Orientation.UP, new DiscreteCoordinates(25,18)));
		
		registerActor(new Potion(this, Orientation.UP, new DiscreteCoordinates(15, 18)));
	}
}