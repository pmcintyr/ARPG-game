package ch.epfl.cs107.play.game.arpg;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class ARPGPlayerStatusGUI implements Graphics{
	final float DEPTH = 1.f;
	private final static float FULL_HP = 5.f;
	private ARPGPlayer arpgPlayer;

	public ARPGPlayerStatusGUI(ARPGPlayer arpgPlayer) {
		this.arpgPlayer=arpgPlayer;
	}

	@Override
	public void draw(Canvas canvas) {
		//		currentObjectSprite = new Sprite(arpgPlayer.getCurrentObject().getSpriteName(), 1, 1, currentObjectSprite);
		//		currentObjectSprite.draw(canvas);

		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new
				Vector(width/2, height/2));

		ImageGraphics gearDisplay = new
				ImageGraphics(ResourcePath.getSprite("zelda/gearDisplay"),
						1.5f, 1.5f, new RegionOfInterest(0, 0, 32, 32),
						anchor.add(new Vector(0, height - 1.75f)), 1, DEPTH);
		gearDisplay.draw(canvas);
		//System.out.println(arpgPlayer.getCurrentObject().getSpriteName());
		ImageGraphics currentObjectSprite = new
				ImageGraphics(ResourcePath.getSprite(arpgPlayer.getCurrentObject().getSpriteName()),
						1.f, 1.f, new RegionOfInterest(0, 0, 16, 32),
						anchor.add(new Vector(0.25f, height - 1.5f)), 1, DEPTH);
		currentObjectSprite.draw(canvas);

		for (int i = 0; i < (int)arpgPlayer.getHp(); ++i) {
			ImageGraphics heartDisplay = new
					ImageGraphics(ResourcePath.getSprite("zelda/heartDisplay"),
							1f, 1.2f, new RegionOfInterest(32, 0, 16, 48),
							anchor.add(new Vector(2+1*i, height - 1.75f)), 1, DEPTH);

			heartDisplay.draw(canvas);
		}

		if ((arpgPlayer.getHp()%1>0)){
			ImageGraphics heartDisplay1 = new
					ImageGraphics(ResourcePath.getSprite("zelda/heartDisplay"),
							1f, 1.2f, new RegionOfInterest(16, 0, 16, 16),
							anchor.add(new Vector(2+1*(int)arpgPlayer.getHp(), height - 1.75f)), 1, DEPTH);
			heartDisplay1.draw(canvas);
		}

		ImageGraphics coinsDisplay = new
				ImageGraphics(ResourcePath.getSprite("zelda/coinsDisplay"),
						5.f, 2.f, new RegionOfInterest(0, 0, 64, 64),
						anchor.add(new Vector(0,0)), 1, DEPTH);
		coinsDisplay.draw(canvas);

		String coinsNumber = String.valueOf(arpgPlayer.getInventory().getMoney());


		for(int i = 0; i < coinsNumber.length(); i++) {
			int j = Character.digit(coinsNumber.charAt(i), 10);

			if ((j < 5)&&(j>0)){
				//				    		System.out.println(coinsNumber);
				ImageGraphics digitsDisplay4 = new
						ImageGraphics(ResourcePath.getSprite("zelda/digits"),
								0.75f, 0.75f, new RegionOfInterest((j-1)*16, 0, 16, 16),
								anchor.add(new Vector(1.75f+0.5f*i, 0.70f)), 1, DEPTH);
				digitsDisplay4.draw(canvas);
			}
			else if ((j>4)&&(j<9)) {
				ImageGraphics digitsDisplay5 = new
						ImageGraphics(ResourcePath.getSprite("zelda/digits"),
								0.75f, 0.75f, new RegionOfInterest((j-5)*16, 16, 16, 16),
								anchor.add(new Vector(1.75f+0.5f*i, 0.70f)), 1, DEPTH);
				digitsDisplay5.draw(canvas);
			}
			else if (j==9){
				ImageGraphics digitsDisplay9 = new
						ImageGraphics(ResourcePath.getSprite("zelda/digits"),
								0.75f, 0.75f, new RegionOfInterest(0*16, 32, 16, 16),
								anchor.add(new Vector(1.75f+0.5f*i, 0.70f)), 1, DEPTH);
				digitsDisplay9.draw(canvas);
			}
			else {
				ImageGraphics digitsDisplay0 = new
						ImageGraphics(ResourcePath.getSprite("zelda/digits"),
								0.75f, 0.75f, new RegionOfInterest(1*16, 32, 16, 16),
								anchor.add(new Vector(1.75f+0.5f*i, 0.70f)), 1, DEPTH);
				digitsDisplay0.draw(canvas);
			}
		}
	}
}
