package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.Potion;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.LogicGate;

public class Hopital extends ARPGArea{
	public String getTitle() {
		return "PetalBurgCenter";
	}

	@Override
	protected void createArea() {
		// TODO Auto-generated method stub
		registerActor(new Background(this));
		registerActor(new Foreground(this));

		registerActor(new Door("zelda/Ferme", new DiscreteCoordinates (6,10), 
				(LogicGate.TRUE), this, Orientation.RIGHT, new DiscreteCoordinates(6,1), new DiscreteCoordinates (7,1)));
		
		registerActor(new Potion(this, Orientation.UP, new DiscreteCoordinates(7, 6)));
		registerActor(new Potion(this, Orientation.UP, new DiscreteCoordinates(12, 1)));
	}
}

