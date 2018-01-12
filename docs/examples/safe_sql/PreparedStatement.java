/*
 * Original code - any time you see text concatenation with SQL: watch out!
 */
 
Item i = new Item();
i.setName(name);
i.setDescription(description);
Integer id = insert("INSERT INTO ITEMS (NAME, DESCRIPTION) VALUES ("
		+ "'" + i.getName() + "',"
		+ "'" + i.getDescription() + "'"
		+ ")");
i.setId(id);
return i;

/*
 * Prepared statements prevent SQL injection by binding data.
 */
 
stmt = conn.prepareStatement( "INSERT INTO items VALUES(?, ?)" );
stmt.setString( 1, item.getName() );
stmt.setString( 2, item.getDescription() );
stmt.executeUpdate();
stmt.close();


/*
 * Even better - use an ORM like Hibernate
 */
 
@Entity
public class Item {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	private String description;
	
	// getter/setter here
	
}

Session session = openSession();
session.getTransaction().begin();
session.persist(item);
session.getTransaction().commit();