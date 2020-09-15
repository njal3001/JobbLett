package bolett;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;;

public class GroupListTest {
	private GroupList groupList = new GroupList();
	private Group testGroup = groupList.newGroup("MoholtKollektivet");
	private User testUser1 = new User("test1","Passord123", "Ole", "Testmannsen");
	
	@BeforeAll
	public void setUp(){
		testGroup.addUser(testUser1);
	}
	
	@Test
	public void testGroupName_GroupsWithSameName() {
		//Should be able to create a new group with an allready existing groupname
		try {
			Group group1 = groupList.newGroup("MoholtKollektivet");
			//Group group2 = new Group("Moholtkollektivet");
		}
		catch (IllegalArgumentException e) {
			//No errors should be thrown
			fail("Groups with same name can exist!");
		}
	}
	
	@Test
	public void testGroupID() {
		//testing if it's possible to create two groups with the same ID
		try {
			Group group1 = new Group("gruppe1", 1234);
			Group group2 = new Group("gruppe2", 3234);
			groupList.addGroup(group1);
			groupList.addGroup(group2);
		}
		catch (IllegalStateException e) {
			//An error code should not be thrown
			fail("Should be able to create two groups with unique ID!");		}
		try {
			Group group1 = new Group("gruppe1", 1234);
			Group group2 = new Group("gruppe2", 1234);
			groupList.addGroup(group1);
			groupList.addGroup(group2);
			fail("Should not be able to create two groups with the same ID!");
		}
		catch (IllegalStateException e) {
			//An error code should be thrown
		}
	}

	
	
}
