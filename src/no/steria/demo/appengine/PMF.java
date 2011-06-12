package no.steria.demo.appengine;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	private PMF() {
		// Must be private. Only a single instance may exist.
	}

	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}
}
