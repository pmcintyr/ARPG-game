	package ch.epfl.cs107.play.game.arpg.area;
	import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Background;
	import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.Bomb;
import ch.epfl.cs107.play.game.arpg.actor.Grass;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.tutos.area.Tuto2Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.LogicGate;
import ch.epfl.cs107.play.window.Keyboard;
	
public class Route extends ARPGArea{
			Door door1,door2,door3;


			public void createArea () {
				Background background = new Background (this);
				registerActor(background);
				
//				Foreground foreground = new Foreground (this);
//				registerActor(foreground);
				
				DiscreteCoordinates [] arrivingCoord = {new DiscreteCoordinates (18,15),new DiscreteCoordinates(29,18)};
				DiscreteCoordinates [] otherCoord = {new DiscreteCoordinates (0,16),new DiscreteCoordinates(10,0)};
				DiscreteCoordinates [] mainCell = {new DiscreteCoordinates (0,15),new DiscreteCoordinates(9,0)};
				
				
				
				door1 = new Door("zelda/Ferme", arrivingCoord[0], (LogicGate.TRUE),this , Orientation.RIGHT, mainCell[0], otherCoord [0]);
				registerActor(door1);
				door2 = new Door("zelda/Village", arrivingCoord[1], LogicGate.TRUE, this, Orientation.DOWN, mainCell[1],otherCoord[1]);
				registerActor(door2);
				door3 = new Door("zelda/RouteChateau", new DiscreteCoordinates(9, 1), LogicGate.TRUE, this, Orientation.UP, new DiscreteCoordinates(9, 19),
						new DiscreteCoordinates(10, 19));
				registerActor(door3);
				
				for (int i = 5; i < 8; ++i) {
					for (int j = 6; j < 12; ++j) {
						Grass grass = new Grass(this, Orientation.UP, new DiscreteCoordinates(i, j));
						registerActor(grass);
					}
				}
				Bomb bomb = new Bomb (this,Orientation.RIGHT,new DiscreteCoordinates(5, 6));
				registerActor(bomb);
			}

			@Override
			public String getTitle() {
				// TODO Auto-generated method stub
				return "zelda/Route";
			}
			public void createBomb () {
				Bomb bomb = new Bomb(this, Orientation.DOWN, getRelativeMouseCoordinates());
				registerActor(bomb);
			}
			public void update (float deltaTime) {
				super.update(deltaTime);
				Keyboard keyb = this.getKeyboard();
				if (keyb.get(Keyboard.B).isDown()) {
					createBomb();
				}
			}

		}

