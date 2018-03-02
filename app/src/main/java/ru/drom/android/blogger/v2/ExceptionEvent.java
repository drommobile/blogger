package ru.drom.android.blogger.v2;

public class ExceptionEvent implements Event {
	public final Throwable throwable;

	public ExceptionEvent(Throwable throwable) {
		this.throwable = throwable;
	}
}