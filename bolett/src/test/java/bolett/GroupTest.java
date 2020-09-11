package bolett;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;;

public class GroupTest {
	static Group testGroup= new Group("MoholtKollektivet");;
	static User testUser1 = new User("test1","Passord123", "Ole", "Testmannsen");
	
	@BeforeAll
	public static void setUp(){
		testGroup = new Group("MoholtKollektivet");
		testUser1 = new User("test1","Passord123", "Ole", "Testmannsen");
		testGroup.addUser(testUser1);
	}
	
	@Test
	public void testGroupName() {
		//Testing groupname with less than 2 letters
		try {
			Group shortNameGroup = new Group("q"); 
			fail("Should not be able to create groups with names less than two letters!");
		}
		catch (IllegalArgumentException e) {
			//An error code should be thrown
		}
		
		//Should be able to create a new group with an allready existing groupname
		try {
			Group group1 = new Group("MoholtKollektivet");
			//Group group2 = new Group("Moholtkollektivet");
		}
		catch (IllegalArgumentException e) {
			//No errors should be thrown
			fail("Groups with same name can exist!");
		}
	}
	
	//The test below will be closed until this part is implemented in the main class
	/*@Test
	public void testGroupID() {
		//testing if it's possible to create two groups with the same ID
		try {
			Group group1 = new Group("q", 123); 
			Group group2 = new Group("q", 123);
			fail("Should not be able to create two groups with the same ID!");
		}
		catch (IllegalArgumentException e) {
			//An error code should be thrown 
		}
	}*/
	
	@Test
	public void testAddUser() {
		//Testing if it's possible to add an user twice to the same group
		try {
			testGroup.addUser(testUser1);
			fail("Should not be able to add an user twice to the same group!");
		}
		catch (IllegalArgumentException e) {
			//An error code should be thrown
		}
		
		//Testing if the members are added to the group
		User testUser2 = new User("test2", "Passord123", "Kari", "Testermann");
		testGroup.addUser(testUser2);
		assertTrue(testGroup.getGroupmembers().contains(testUser2));
		assertTrue(testGroup.getGroupmembers().contains(testUser1));
	}
	
	@Test
	public void testRemoveUser() {
		//testing if and user is really removed when your try to remove the use
		testGroup.removeUser(testUser1);
		assertFalse(testGroup.getGroupmembers().contains(testUser1));
		
		//Testcode for if only admin is able to remove an user
	}
	
	
}
