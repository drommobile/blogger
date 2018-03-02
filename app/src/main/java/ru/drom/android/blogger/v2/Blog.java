package ru.drom.android.blogger.v2;

public abstract class Blog<T extends Event> {
	public abstract void handle(T event);
}