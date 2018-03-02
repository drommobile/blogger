package ru.drom.android.blogger;

import android.support.annotation.Nullable;

@SuppressWarnings("WeakerAccess")
public class Post {

	public enum Priority {
		DEBUG, WARN, ERROR
	}

	public final Priority priority;
	public final String tag;
	public final String message;
	public final Throwable throwable;

	public Post(
			Priority priority, String tag, @Nullable String message, @Nullable Throwable throwable
	) {
		this.priority = priority;
		this.tag = tag;
		this.message = message;
		this.throwable = throwable;
	}
}