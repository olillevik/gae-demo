package no.steria.demo.appengine;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Entry implements Comparable<Entry> {

	public Entry(String str) {
		text = str;
	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent
	private String text;

	public String getText() {
		return text;
	}

	public long getId() {
		return key.getId();
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
		return (int) (o.getId() - this.getId());
	}
}
