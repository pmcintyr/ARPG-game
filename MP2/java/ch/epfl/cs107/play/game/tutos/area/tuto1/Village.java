package ch.epfl.cs107.play.game.tutos.area.tuto1;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.tutos.actor.SimpleGhost;
import ch.epfl.cs107.play.game.tutos.area.SimpleArea;
import ch.epfl.cs107.play.math.Vector;

public class Village extends SimpleArea{
	
	public void createArea () {
		
		Vector vect = new Vector (18,7);
		SimpleGhost ghost = new SimpleGhost (vect,"ghost.2");
		
		registerActor(ghost);
		
		Background background = new Background (this);
		registerActor(background);
		
//		Foreground foreground = new Foreground (this);
//		registerActor(foreground);
		
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "zelda/village";
	}
	
}
