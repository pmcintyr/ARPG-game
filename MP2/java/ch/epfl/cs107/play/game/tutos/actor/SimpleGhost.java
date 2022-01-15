package ch.epfl.cs107.play.game.tutos.actor;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;;
public class SimpleGhost extends Entity {

	private float energy ;
	TextGraphics hpText ;
	private Sprite sprite ;
	
	public SimpleGhost (Vector position,String spriteName) {
		super(position);
		sprite = new Sprite (spriteName, 1.f,1.f,this);
		hpText = new TextGraphics(Integer.toString((int)energy), 0.4f, Color.BLUE);
		hpText.setParent(this);
		this.hpText.setAnchor(new Vector(-0.3f, 0.1f)); // point d'ancrage du texte
		this.energy = 10 ;
	}
	
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		hpText.draw(canvas);
		sprite.draw(canvas);
		
	}
	public void update (float deltaTime) {
		super.update(deltaTime);
		energy = energy - deltaTime ;
		
		if (isWeak())
			energy = 0;
			
		hpText.setText(Integer.toString((int)energy));
	}
	
	
	public boolean isWeak () {
		boolean verite = false;
		if (energy <= 0)
			verite = true ;
		return verite ;
	}
	public void strengthen () {
		energy = 10 ;
	}
	public void moveUp (float delta) {
		setCurrentPosition(getPosition().add(0.f, delta));
	}
	 public void moveDown(float delta) {
		 setCurrentPosition(getPosition().add(0.f,- delta));
	 }
	 public void moveLeft(float delta) {
		 setCurrentPosition(getPosition().add(-delta, 0.f));
	 }
	 public void moveRight(float delta) {
		 setCurrentPosition(getPosition().add(delta, 0.f));
	 }
}
