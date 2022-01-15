package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.LogMonster;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.LogicGate;

public class GrotteMew extends ARPGArea{
	

		private LogMonster log;
		public String getTitle() {
			return "GrotteMew";
		}

		@Override
		protected void createArea() {
			// TODO Auto-generated method stub
			registerActor(new Background(this));
			registerActor (new Foreground (this));

			
			registerActor(new Door("zelda/Village", new DiscreteCoordinates (25,17), 
					(LogicGate.TRUE), this, Orientation.DOWN, new DiscreteCoordinates(8,2)));
			
			log = new LogMonster(this, Orientation.DOWN, new DiscreteCoordinates(8, 8));
			registerActor(log);
			
					

				registerActor(new Door("zelda/Chateau", new DiscreteCoordinates (13,12), 
						(LogicGate.TRUE), this, Orientation.LEFT, new DiscreteCoordinates(8,9)));
				
		}
		
		public void update (float deltaTime) {
			
			if (log.getPv()<=0) {
				System.out.println("dead");
			}
			
		}
	}
