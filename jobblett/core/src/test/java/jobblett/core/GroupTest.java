
package jobblett.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;

public class GroupTest {
    
    private Group group;
    User user1, user2;
    JobShift jobshift1;

    @BeforeEach
    public void setUp(){
        group = new Group("test", 1);
        user1 = new User("test1", HashedPassword.hashPassword("Passord123"), "Kari", "Testermann");
        user2 = new User("test2", HashedPassword.hashPassword("Passord123"), "Kari", "Testermann");
        jobshift1 = new JobShift(user1, LocalDateTime.now().plusHours(5), Duration.ofHours(5), "Info");
    }
	
	@Test
	public void testAddUser() {
        group.addUser(user1);
        assertEquals(user1, group.getUser("test1"));
		//Testing if it's possible to add an user twice to the same group
		try {
			group.addUser(user1);
			fail("Should not be able to add an user twice to the same group!");
		}
		catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
        }
	}
	
	@Test
	public void testRemoveUser() {
        group.addUser(user1);
		//testing if and user is really removed when your try to remove the use
		group.removeUser(user1);
        assertNull(group.getUser("test1"));
    }
    
    @Test
	public void testConstructor_invalidGroupName() {
        try{
        Group group = new Group("a ", 1);
        } catch(Exception e){
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    
    @Test
	public void testGetGroupSize() {
        assertEquals(0, group.getGroupSize());
        group.addUser(user1);
        assertEquals(1, group.getGroupSize());
    }

    @Test
    public void testToString() {
        group.addUser(user1);
        group.addUser(user2);
        assertEquals("test: Kari Testermann (@test1), Kari Testermann (@test2)", group.toString());
    }
    
    @Test
    public void testIterator(){
      group.addUser(user1);
      group.addUser(user2);
      Iterator<User> iter = group.iterator();
      assertTrue(iter.hasNext());
      assertEquals(user1, iter.next());
      assertTrue(iter.hasNext());
      assertEquals(user2, iter.next());
      assertFalse(iter.hasNext());
    }

    @Test
    public void testAddAdmin_userInGroup(){
      group.addUser(user1);
      assertFalse(group.isAdmin(user1));
      group.addAdmin(user1);
      assertTrue(group.isAdmin(user1));
    }

    @Test
    public void testAddAdmin_userNotInGroup(){
      try{
        group.addAdmin(user1);
        fail("Exception should be thrown");
      } catch(Exception e){
        assertTrue(e instanceof IllegalArgumentException);
      }
    }

    @Test
    public void testRemoveAdmin(){
      group.addUser(user1);
      group.addAdmin(user1);
      group.removeAdmin(user1);
      assertFalse(group.isAdmin(user1));
    }

    @Test
    public void testAddJobShift_jobshiftUserInGroup(){
      group.addUser(user1);
      group.addAdmin(user1);
      group.addJobShift(jobshift1, user1);
      assertTrue(group.getJobShiftList().contains(jobshift1));
    }

    @Test
    public void testAddJobShift_jobshiftUserNotInGroup(){
      group.addUser(user2);
      group.addAdmin(user2);
      try{
        group.addJobShift(jobshift1, user2);
        fail("Exception should be thrown");
      } catch(Exception e){
        assertTrue(e instanceof IllegalArgumentException);
      }
    }

    @Test
    public void testAddJobShift_userNotAdmin(){
      group.addUser(user1);
      try{
        group.addJobShift(jobshift1, user1);
        fail("Exception should be thrown");
      } catch(Exception e){
        assertTrue(e instanceof IllegalArgumentException);
      }
    }
}

