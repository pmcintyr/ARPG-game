package ch.epfl.cs107.play.game.tutos.area.tuto1;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.tutos.area.SimpleArea;

public class Ferme extends SimpleArea{
	
	public void createArea () {
		Background background = new Background (this);
		registerActor(background);
		
//		Foreground foreground = new Foreground (this);
//		registerActor(foreground);
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "zelda/ferme";
	}


}
