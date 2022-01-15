package ch.epfl.cs107.play.game.arpg.actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.ARPGItem;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.handler.RPGInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class CastleDoor extends Door{
	private Sprite castleDoorOpen;
	private Sprite castleDoorClose;

	public CastleDoor(String destination, DiscreteCoordinates otherSideCoordinates, Logic signal, Area area, Orientation orientation, DiscreteCoordinates position, DiscreteCoordinates... otherCells) {
		super(destination, otherSideCoordinates, signal, area, orientation, position, otherCells);

		castleDoorOpen = new Sprite("zelda/castleDoor.open", 2.f, 2.f, this);
		//		castleDoorOpen.setDepth(-100);
		castleDoorClose = new Sprite("zelda/castleDoor.close" , 2.f, 2.f, this);
	}
	public void open() {
		setSignal(Logic.TRUE);
	}
	public void close() {
		setSignal(Logic.FALSE);
	}
	@Override
	public boolean takeCellSpace() {
		if (isOpen()) {
			return false;
		}
		return true;
	}
	@Override
	public boolean isViewInteractable(){
		if (isOpen()) {
			return false;
		}
		return true;
	}
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if (isOpen()) {
			castleDoorOpen.setDepth(-100);
			castleDoorOpen.draw(canvas);
		}
		else {
			castleDoorClose.setDepth(-100);
			castleDoorClose.draw(canvas);
		}
	}
}
