package ru.drom.android.blogger.v2;

public interface Blog<T extends Event> {
	void handle(T event);
}