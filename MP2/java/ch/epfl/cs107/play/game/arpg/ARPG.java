package ch.epfl.cs107.play.game.arpg;
import ch.epfl.cs107.play.game.areagame.Area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.area.Chateau;
import ch.epfl.cs107.play.game.arpg.area.Ferme;
import ch.epfl.cs107.play.game.arpg.area.GrotteMew;
import ch.epfl.cs107.play.game.arpg.area.Hopital;
import ch.epfl.cs107.play.game.arpg.area.Route;
import ch.epfl.cs107.play.game.arpg.area.RouteChateau;
import ch.epfl.cs107.play.game.arpg.area.Village;
import ch.epfl.cs107.play.game.rpg.RPG;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class ARPG extends RPG{


		public final static float CAMERA_SCALE_FACTOR = 13.f;
		//public final static float STEP = 0.05f;


		private ARPGPlayer ARPGplayer;
		private  Area area ;
		private final DiscreteCoordinates[] startingPositions = {new DiscreteCoordinates(8,7)};
		private int areaIndex;

		/**
		 * Add all the areas
		 */
		private void createAreas(){

			addArea(new Ferme());
			addArea(new Village());
			addArea(new Route());
			addArea(new RouteChateau());
			addArea(new Chateau());
			addArea (new GrotteMew());
			addArea (new Hopital());

		}
	 

		@Override
		public boolean begin(Window window, FileSystem fileSystem) {


			if (super.begin(window, fileSystem)) {

				createAreas();
				area = setCurrentArea("zelda/Ferme", true);
				areaIndex = 0;
				ARPGplayer = new ARPGPlayer (area, Orientation.DOWN, startingPositions[areaIndex]);
				initPlayer(ARPGplayer);
				return true;
			}
			 return false;
		}
		

		@Override
		public void update(float deltaTime) {
			super.update(deltaTime);
		
		}

		@Override
		public void end() {
		}

		@Override
		public String getTitle() {
			return "ZeldIC";
		}
}
