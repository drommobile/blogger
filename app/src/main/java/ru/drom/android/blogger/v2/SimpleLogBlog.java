package ru.drom.android.blogger.v2;

import android.text.TextUtils;
import android.util.Log;

public class SimpleLogBlog implements Blog<LogEvent> {
	@Override
	public void handle(LogEvent event) {
		switch (event.priority) {
			case LogEvent.DEBUG:
				if (event.throwable == null) {
					Log.d(event.tag, event.message);
				} else {
					Log.d(event.tag, event.message, event.throwable);
				}
				break;
			case LogEvent.WARN:
				if (event.throwable == null) {
					Log.w(event.tag, event.message);
				} else if (TextUtils.isEmpty(event.message)) {
					Log.w(event.tag, event.throwable);
				} else {
					Log.w(event.tag, event.message, event.throwable);
				}
				break;
			case LogEvent.ERROR:
				if (event.throwable == null) {
					Log.e(event.tag, event.message);
				} else {
					Log.e(event.tag, event.message, event.throwable);
				}
				break;
		}
	}
}