package ru.drom.android.blogger.v2;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

public class Blogger {

	private static Blogger blogger;

	public static void setDefaultInstance(Blogger blogger) {
		Blogger.blogger = blogger;
	}

	public static Blogger get() {
		if (blogger == null) {
			blogger = new Blogger();
		}
		return blogger;
	}

	private Set<Blog> blogs = new HashSet<>();

	public void registerAll(Blogger blogger) {
		blogs.addAll(blogger.blogs);
	}

	public <T extends Event> void register(Blog<T> blog) {
		blogs.add(blog);
	}

	public <T extends Event> void unregister(Blog<T> blog) {
		blogs.remove(blog);
	}

	@SuppressWarnings("unchecked")
	public <T extends Event> void post(T event) {
		for (Blog blog : blogs) {
			Class<T> type = (Class<T>) ((ParameterizedType) blog.getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
			if (event.getClass().equals(type)) {
				blog.handle(event);
			}
		}
	}

	public void hook(Throwable throwable) {
		post(new ExceptionEvent(throwable));
	}

	public void logActivity(@StringRes int name) {
		post(new LocationEvent(LocationEvent.ACTIVITY, name));
	}

	public void logFragment(@StringRes int name) {
		post(new LocationEvent(LocationEvent.FRAGMENT, name));
	}

	public void logDialog(@StringRes int name) {
		post(new LocationEvent(LocationEvent.DIALOG, name));
	}

	public void d(String message) {
		d(null, message);
	}

	public void d(@Nullable String tag, String message) {
		post(new LogEvent(LogEvent.DEBUG, tag, message, null));
	}

	public void df(@NonNull String format, Object... args) {
		df(null, format, args);
	}

	public void df(@Nullable String tag, @NonNull String format, Object... args) {
		post(new LogEvent(LogEvent.DEBUG, tag, String.format(format, args), null));
	}

	public void auto(Object... args) {
		StringBuilder sb = new StringBuilder();
		for (Object obj : args) {
			if (sb.length() > 0) {
				sb.append("; ");
			}
			sb.append(obj);
		}
		post(new LogEvent(LogEvent.DEBUG, null, sb.toString(), null));
	}

	/**
	 * Логирует в стиле slf4j.
	 *
	 * @param format Формат строки
	 * @param args   Аргументы, для замены {} в формате строки
	 */
	public void debug(@NonNull String format, Object... args) {
		debug(null, format, args);
	}

	public void debug(@Nullable String tag, @NonNull String format, Object... args) {
		String result = format;
		for (Object arg : args) {
			result = result.replaceFirst("\\{\\}", String.valueOf(arg));
		}
		post(new LogEvent(LogEvent.DEBUG, tag, result, null));
	}
}