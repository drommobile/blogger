package ru.drom.android.blogger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused", "SameParameterValue"})
public class Blogger {

	private static final List<Blog> LIVE_JOURNAL = new ArrayList<>();

	public static void createBlog(Blog blog) {
		LIVE_JOURNAL.add(blog);
	}

	public static void publish(
			Post.Priority priority, String tag,
			@Nullable String message, @Nullable Throwable throwable
	) {
		publish(new Post(priority, tag, message, throwable));
	}

	public static void publish(Post post) {
		for (Blog blog : LIVE_JOURNAL) {
			blog.publish(post);
		}
	}

	public static void d(String message) {
		d(generateTag(), message);
	}

	public static void d(String tag, String message) {
		publish(Post.Priority.DEBUG, tag, message, null);
	}

	public static void df(@NonNull String format, Object... args) {
		df(generateTag(), format, args);
	}

	public static void df(String tag, @NonNull String format, Object... args) {
		publish(Post.Priority.DEBUG, tag, String.format(format, args), null);
	}

	/**
	 * Логирует в стиле slf4j.
	 *
	 * @param format Формат строки
	 * @param args   Аргументы, для замены {} в формате строки
	 */
	public static void debug(@NonNull String format, Object... args) {
		debug(generateTag(), format, args);
	}

	public static void debug(String tag, @NonNull String format, Object... args) {
		String result = format;
		for (Object arg : args) {
			result = result.replaceFirst("\\{\\}", String.valueOf(arg));
		}
		publish(Post.Priority.DEBUG, tag, result, null);
	}

	/**
	 * Логирует в виде "arg1; arg2; arg3"
	 */
	public static void auto(Object... args) {
		StringBuilder sb = new StringBuilder();
		for (Object obj : args) {
			if (sb.length() > 0) {
				sb.append("; ");
			}
			sb.append(obj);
		}
		publish(Post.Priority.DEBUG, generateTag(), sb.toString(), null);
	}

	private static String generateTag() {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		StackTraceElement stackTraceElement = stackTraceElements[4];
		return "(" + stackTraceElement.getFileName() + ":"
				+ stackTraceElement.getLineNumber() + ")";
	}

	public static class SimpleBlog implements Blog {

		@Override
		public void publish(Post post) {
			switch (post.priority) {
				case DEBUG:
					if (post.throwable == null) {
						Log.d(post.tag, post.message);
					} else {
						Log.d(post.tag, post.message, post.throwable);
					}
					break;
				case WARN:
					if (post.throwable == null) {
						Log.w(post.tag, post.message);
					} else if (TextUtils.isEmpty(post.message)) {
						Log.w(post.tag, post.throwable);
					} else {
						Log.w(post.tag, post.message, post.throwable);
					}
					break;
				case ERROR:
					if (post.throwable == null) {
						Log.e(post.tag, post.message);
					} else {
						Log.e(post.tag, post.message, post.throwable);
					}
					break;
			}
		}
	}
}