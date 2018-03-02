package ru.drom.android.blogger.v2;

import android.support.annotation.IntDef;
import android.support.annotation.StringRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LocationEvent implements Event {

	public static final int ACTIVITY = 0;
	public static final int FRAGMENT = 1;
	public static final int DIALOG = 2;

	@IntDef({ACTIVITY, FRAGMENT, DIALOG})
	@Retention(RetentionPolicy.SOURCE)
	public @interface Type {
	}

	@Type
	private final int type;
	private final String name;
	private final Integer nameRes;

	public LocationEvent(@Type int type, String name) {
		this.type = type;
		this.name = name;
		this.nameRes = null;
	}

	public LocationEvent(@Type int type, @StringRes int nameRes) {
		this.type = type;
		this.name = null;
		this.nameRes = nameRes;
	}

	@Override
	public String toString() {
		return "LocationEvent{" +
				"type=" + type +
				", name='" + name + '\'' +
				", nameRes=" + nameRes +
				'}';
	}
}