package ch.epfl.cs107.play.game.arpg.actor;

public interface Flyable {
	
	default boolean canFly () {
		return true;
	}

}
