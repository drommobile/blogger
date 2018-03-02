package ru.drom.android.blogger.v2;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LogEvent implements Event {
	public static final int DEBUG = 0;
	public static final int WARN = 1;
	public static final int ERROR = 2;

	@IntDef({DEBUG, WARN, ERROR})
	@Retention(RetentionPolicy.SOURCE)
	public @interface Priority {
	}

	@Priority
	public final int priority;
	public final String tag;
	public final String message;
	public final Throwable throwable;

	public LogEvent(
			@Priority int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable
	) {
		this.priority = priority;
		this.message = message;
		this.throwable = throwable;
		if (tag == null) {
			this.tag = generateTag();
		} else {
			this.tag = message;
		}
	}

	private String generateTag() {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		StackTraceElement stackTraceElement = stackTraceElements[4];
		return "(" + stackTraceElement.getFileName() + ":"
				+ stackTraceElement.getLineNumber() + ")";
	}
}