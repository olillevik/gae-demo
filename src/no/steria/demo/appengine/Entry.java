package no.steria.demo.appengine;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Entry implements Comparable<Entry> {

	public Entry(String str) {
		text = str;
		date = new Date();
	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent
	private String text;
	@Persistent
	private Date date;

	public String getText() {
		return text;
	}

	private Date getDate() {
		return date;
	}

	public String getFormattedDate(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof Entry)) {
			Entry other = (Entry) obj;
			if (other.key == this.key) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(Entry o) {
		if (o.getDate().after(this.getDate()))
			return 1;
		else if (o.getDate().equals(this.getDate()))
			return 0;
		else
			return -1;
	}
}
