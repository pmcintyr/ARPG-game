package ch.epfl.cs107.play.game.arpg.area;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.tutos.area.Tuto2Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.Signal;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.signal.logic.LogicGate;

public class Ferme extends ARPGArea{
	private Door door1,door2,door3;
	private Area area = this ;
	
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "zelda/Ferme";
	}

		public void createArea () {
			Background background = new Background (this);
			registerActor(background);
			
					
			DiscreteCoordinates [] arrivingCoord = {new DiscreteCoordinates (1,15),new DiscreteCoordinates(4,18),new DiscreteCoordinates(14,18)};
			DiscreteCoordinates [] otherCoord = {new DiscreteCoordinates (19,16),new DiscreteCoordinates(5,0),new DiscreteCoordinates(14,0)};
			DiscreteCoordinates [] mainCell = {new DiscreteCoordinates (19,15),new DiscreteCoordinates(4,0),new DiscreteCoordinates(13,0)};
			
			
			
			door1 = new Door("zelda/Route", arrivingCoord[0], (LogicGate.TRUE),area , Orientation.RIGHT, mainCell[0], otherCoord [0]);
			registerActor(door1);
			door2 = new Door("zelda/Village", arrivingCoord[1], LogicGate.TRUE, area, Orientation.DOWN, mainCell[1],otherCoord[1]);
			registerActor(door2);
			door3 = new Door("zelda/Village", arrivingCoord[2], LogicGate.TRUE, area, Orientation.DOWN, mainCell[2], otherCoord[2]);
			registerActor(door3);
			
			registerActor(new Door("PetalBurgCenter", new DiscreteCoordinates (6,2), 
					(LogicGate.TRUE),this, Orientation.DOWN, new DiscreteCoordinates(6,11)));
			
		Foreground foreground = new Foreground (this);
		registerActor(foreground);
		}

	}

