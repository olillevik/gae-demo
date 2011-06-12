package no.steria.demo.appengine;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppEngineDemoServlet extends HttpServlet {

	private static final long serialVersionUID = 8646991715906369248L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter writer = resp.getWriter();

		writer.append("<html><body>");
		writer.append("<form action='appenginedemo' method='get'>")//
				.append("<textarea rows='4' cols='40' name=textArea>Write something clever here</textarea>")//
				.append("<br />").append("<input type='submit' name='submit' value='Submit text'/>")//
				.append("</form>")//
				.append("<br/>");

		if (req.getParameter("textArea") != null && req.getParameter("textArea").trim() != "") {
			save(new Entry(req.getParameter("textArea")));
		}

		String resetParam = req.getParameter("reset");
		if (resetParam != null && resetParam.equalsIgnoreCase("true")) {
			deleteAllEntries();
		} else {
			writer.append("<table cellpadding='10'>")//
					.append("<tr>");
			for (Entry entry : getAllEntries()) {
				writer.append("<tr>")//
						.append("<td>")//
						.append(entry.getFormattedDate("yyyy-MM-dd HH:mm:ss"))//
						.append("</td><td>")//
						.append(entry.getText())//
						.append("</td>")//
						.append("</tr>");
			}

			writer.append("</table>");
			writer.append("</body></html>");
		}
	}

	private void save(Entry entry) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(entry);
		} finally {
			pm.close();
		}
	}

	private SortedSet<Entry> getAllEntries() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		SortedSet<Entry> entries = new TreeSet<Entry>();
		Iterator<Entry> entriesIterator = null;
		try {
			entriesIterator = pm.getExtent(Entry.class).iterator();
			while (entriesIterator.hasNext()) {
				entries.add(entriesIterator.next());
			}
		} finally {
			pm.close();
		}
		return entries;
	}

	private void deleteAllEntries() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Iterator<Entry> entryIterator = pm.getExtent(Entry.class).iterator();
			while (entryIterator.hasNext()) {
				pm.deletePersistent(entryIterator.next());
			}
		} finally {
			pm.close();
		}
	}
}
