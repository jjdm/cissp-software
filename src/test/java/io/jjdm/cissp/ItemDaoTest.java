package io.jjdm.cissp;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test the ItemDao.
 *
 * @author Josh Martin (josh.martin@gmail.com)
 */
public class ItemDaoTest {

	@Test
	public void testInitialize() {
		ItemDao dao = ItemDao.getInstance();
		dao.dropTable();
		dao.initialize();
		List<Item> items = dao.getItems();
		Item first = items.get(0);
		Item second = items.get(1);
		items.forEach(i -> log(i));
		Assert.assertEquals(2, items.size());
		Assert.assertEquals(0, first.getId());
		Assert.assertEquals("DDOS Attack", first.getName());
		Assert.assertEquals("Just a little clue here.", second.getDescription());
	}

	private void log(Object object) {
		String result = ToStringBuilder.reflectionToString(object);
		System.out.println(result);
	}

}
