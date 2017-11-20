package io.jjdm.cissp;

/**
 * Example data holder.
 *
 * @author Josh Martin (josh.martin@gmail.com)
 */
public class Item {

	private int id;
	private String name;
	private String description;

	public Item() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Item other = (Item) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 11 * hash + this.id;
		return hash;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Item{" + "id=" + id + ", name=" + name + ", description=" + description + '}';
	}

}
