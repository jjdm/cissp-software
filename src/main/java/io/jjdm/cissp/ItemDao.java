package io.jjdm.cissp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controls access to the item database.
 *
 * @author Josh Martin (josh.martin@gmail.com)
 */
public class ItemDao {

	public final static String URL = "jdbc:hsqldb:file:db/cissp";
	private final static ItemDao INSTANCE = new ItemDao();

	/**
	 * Singleton access.
	 *
	 * @return The data access object.
	 */
	public static ItemDao getInstance() {
		return INSTANCE;
	}

	private ItemDao() {
		super();
		initialize();
	}

	/**
	 * Add a new item to the database.
	 *
	 * @param name The name of the item.
	 * @param description The description of the item.
	 */
	public Item addItem(String name, String description) {
		Item i = new Item();
		i.setName(name);
		i.setDescription(description);
		Integer id = insert("INSERT INTO ITEMS (NAME, DESCRIPTION) VALUES ("
				+ "'" + i.getName() + "',"
				+ "'" + i.getDescription() + "'"
				+ ")");
		i.setId(id);
		return i;
	}

	/**
	 * Delete an item based on the ID.
	 *
	 * @param id The ID of the item.
	 */
	public void deleteItem(Integer id) {
		update("DELETE FROM ITEMS WHERE ID=" + id);
	}

	/**
	 * Get an item based on the ID.
	 *
	 * @param id The ID of the item.
	 * @return The item.
	 */
	public Item getItem(Integer id) {
		List<Item> items = getItems();
		return items.stream().filter(i -> id.equals(i.getId())).findFirst().get();
	}

	/**
	 * Get a list of the items in the database.
	 *
	 * @return The items.
	 */
	public List<Item> getItems() {
		List<Item> items = new ArrayList<>();
		List<Map<String, Object>> results = query("SELECT ID, NAME, DESCRIPTION FROM ITEMS");
		for (Map<String, Object> result : results) {
			Item item = new Item();
			item.setId((Integer) result.get("ID"));
			item.setName((String) result.get("NAME"));
			item.setDescription((String) result.get("DESCRIPTION"));
			items.add(item);
		}
		return items;
	}

	/**
	 * Setup the table, and insert some test data if not found.
	 */
	public void initialize() {
		boolean tableCreated = getTables().contains("ITEMS");
		if (!tableCreated) {
			createTable();
			addItem("DDOS Attack", "Information about the attach here.");
			addItem("General Information", "Just a little clue here.");
		}
	}

	/**
	 * Shutdown the database.
	 */
	public void shutdown() {
		execute("SHUTDOWN");
	}

	/**
	 * Get an item based on the ID.
	 *
	 * @param item The updated item.
	 */
	public void updateItem(Item item) {
		update("UPDATE ITEMS SET"
				+ " NAME='" + item.getName() + "',"
				+ " DESCRIPTION='" + item.getDescription() + "'"
				+ " WHERE ID=" + item.getId()
				+ "");
	}

	/**
	 * Executes a statement.
	 *
	 * @param sql The query to run.
	 * @throws SQLException
	 */
	private void execute(String sql) {
		Connection c = null;
		Statement s = null;
		try {
			c = DriverManager.getConnection(URL, "SA", "");
			s = c.createStatement();
			s.execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (s != null && !s.isClosed()) {
					s.close();
				}
				if (s != null && !s.isClosed()) {
					s.close();
				}
				if (c != null && !c.isClosed()) {
					c.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Get a list of public tables in the schema.
	 *
	 * @return The table list.
	 */
	private List<String> getTables() {
		List<String> tables = new ArrayList<>();
		List<Map<String, Object>> results = query("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'");
		for (Map<String, Object> result : results) {
			tables.add((String) result.get("TABLE_NAME"));
		}
		return tables;
	}

	/**
	 * Run an insert and return identity.
	 *
	 * @param sql The query to run.
	 * @throws SQLException
	 */
	private Integer insert(String sql) {

		Connection c = null;
		Statement s = null;
		ResultSet rs = null;

		try {

			c = DriverManager.getConnection(URL, "SA", "");
			s = c.createStatement();
			s.executeUpdate(sql);

			rs = s.executeQuery("CALL IDENTITY()");
			rs.next();
			return rs.getInt(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (s != null && !s.isClosed()) {
					s.close();
				}
				if (c != null && !c.isClosed()) {
					c.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Run a query.
	 *
	 * @param sql The query to run.
	 * @return
	 * @throws SQLException
	 */
	private List<Map<String, Object>> query(String sql) {

		List<Map<String, Object>> results = new ArrayList<>();
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;

		try {
			c = DriverManager.getConnection(URL, "SA", "");
			s = c.createStatement();
			rs = s.executeQuery(sql);
			ResultSetMetaData meta = rs.getMetaData();
			int columns = meta.getColumnCount();

			while (rs.next()) {
				Map<String, Object> result = new HashMap<>();
				for (int i = 1; i <= columns; i++) {
					String name = meta.getColumnLabel(i);
					Object value = rs.getObject(i);
					result.put(name, value);
				}
				results.add(result);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (s != null && !s.isClosed()) {
					s.close();
				}
				if (c != null && !c.isClosed()) {
					c.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

		return results;
	}

	/**
	 * Run an update.
	 *
	 * @param sql The query to run.
	 * @throws SQLException
	 */
	private void update(String sql) {
		Connection c = null;
		Statement s = null;
		try {
			c = DriverManager.getConnection(URL, "SA", "");
			s = c.createStatement();
			s.executeUpdate(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (s != null && !s.isClosed()) {
					s.close();
				}
				if (c != null && !c.isClosed()) {
					c.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Create the ITEMS table.
	 */
	void createTable() {
		update("CREATE TABLE ITEMS ("
				+ "  ID INTEGER IDENTITY PRIMARY KEY,"
				+ "  NAME VARCHAR(100),"
				+ "  DESCRIPTION VARCHAR(500)"
				+ ")");
	}

	/**
	 * Drop the ITEMS table.
	 */
	void dropTable() {
		update("DROP TABLE ITEMS");
	}

}
