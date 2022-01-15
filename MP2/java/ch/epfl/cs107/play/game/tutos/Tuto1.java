package ch.epfl.cs107.play.game.tutos;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.tutos.actor.SimpleGhost;
import ch.epfl.cs107.play.game.tutos.area.tuto1.Ferme;
import ch.epfl.cs107.play.game.tutos.area.tuto1.Village;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class Tuto1 extends AreaGame {
	private SimpleGhost player ;
	public final static float Step = 0.05f;

	private void createAreas () {
		Village village = new Village ();
		addArea(village);

		Ferme ferme = new Ferme ();
		addArea(ferme);


	}
	@Override
	public void end() {
		// TODO Auto-generated method stub
		super.end();
	}
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		player = new SimpleGhost (new Vector (18,7),"ghost.1");
		if(super.begin(window , fileSystem)) {
			createAreas ();
			Area area = setCurrentArea("zelda/village", true);
			area.registerActor(player); // iul faut une aire pour registeractor 
			area.setViewCandidate(player);
			return true;
		}else return false;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Tuto1";
	}
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		super.update(deltaTime);
		
		if (player.isWeak()) {
			switchArea();
		}
		Keyboard keyboard = getWindow().getKeyboard() ;
		Button key = keyboard.get(Keyboard.UP) ;
		if (key.isDown()) {
			player.moveUp(Step);
		}
		key = keyboard.get(Keyboard.DOWN);
		if (key.isDown()) {
			player.moveDown(Step);
		}
		key = keyboard.get(Keyboard.RIGHT);
		if (key.isDown()) {
			player.moveRight(Step);
		}
		key = keyboard.get(Keyboard.LEFT);
		if (key.isDown()) {
			player.moveLeft(Step);
		}
	}

	private void switchArea () {
		Area area = getCurrentArea();

		area.unregisterActor(player);
		
		if (area.getTitle().equals("zelda/village")){
			area = setCurrentArea("zelda/ferme", false);
		}
		else area = setCurrentArea("zelda/village", false);
		
		area.registerActor(player);
		player.strengthen();
		area.setViewCandidate(player);
	}
	
}
