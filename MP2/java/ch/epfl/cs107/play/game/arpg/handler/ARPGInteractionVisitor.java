package ch.epfl.cs107.play.game.arpg.handler;

import ch.epfl.cs107.play.game.arpg.ARPGItem;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.actor.Arrow;
import ch.epfl.cs107.play.game.arpg.actor.Bomb;
import ch.epfl.cs107.play.game.arpg.actor.CastleDoor;
import ch.epfl.cs107.play.game.arpg.actor.CastleKey;
import ch.epfl.cs107.play.game.arpg.actor.Coin;
import ch.epfl.cs107.play.game.arpg.actor.DarkLord;
import ch.epfl.cs107.play.game.arpg.actor.FireSpell;
import ch.epfl.cs107.play.game.arpg.actor.FlameSkull;
import ch.epfl.cs107.play.game.arpg.actor.Grass;
import ch.epfl.cs107.play.game.arpg.actor.Heart;
import ch.epfl.cs107.play.game.arpg.actor.LogMonster;
import ch.epfl.cs107.play.game.arpg.actor.MagicWaterProjectile;
import ch.epfl.cs107.play.game.arpg.actor.Monstre;
import ch.epfl.cs107.play.game.arpg.actor.Potion;
import ch.epfl.cs107.play.game.rpg.handler.RPGInteractionVisitor;

public interface ARPGInteractionVisitor extends RPGInteractionVisitor{
	
	public default void interactWith (ARPGPlayer player) {}
	
	public default void interactWith (Grass grass) {}
	
	public default void interactWith(Bomb bomb) {}
	
	public default void interactWith(Heart heart) {}
	
	public default void interactWith(Coin coin) {}

	public default void interactWith(CastleDoor castleDoor) {}
	
	public default void interactWith(ARPGItem arpgItem) {}
	
	public default void interactWith(Arrow arrow) {}
	
	public default void interactWith(FireSpell fireSpell) {}
	
	public default void interactWith (LogMonster logMonster) {}
	
	public default void interactWith (Monstre monstre) {}
	
	public default void interactWith(Potion potion) {}
	
	public default void interactWith (MagicWaterProjectile water ) {}
	
	public default void interactWith (DarkLord Lord) {}
	
	public default void interactWith (FlameSkull skull) {}
	
	public default void interactWith (CastleKey key) {}

}
