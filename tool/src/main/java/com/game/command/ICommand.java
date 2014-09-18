package com.game.command;

public interface ICommand extends Cloneable {

	public abstract void action();

	public abstract Object clone() throws CloneNotSupportedException;
}
