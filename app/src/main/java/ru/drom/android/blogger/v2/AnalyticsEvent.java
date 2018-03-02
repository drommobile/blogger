package ru.drom.android.blogger.v2;

public class AnalyticsEvent implements Event {

	public final String category;
	public final String event;
	public final String label;

	public AnalyticsEvent(String category, String event, String label) {
		this.category = category;
		this.event = event;
		this.label = label;
	}

	@Override
	public String toString() {
		return "AnalyticsEvent{" +
				"category='" + category + '\'' +
				", event='" + event + '\'' +
				", label='" + label + '\'' +
				'}';
	}
}