package jobblett.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class UserListTest {

  private UserList userList;
  private User user1;
  private User user2;

  @BeforeEach public void setUp() {
    userList = new UserList();
    user1 = new User("User1", new HashedPassword("Test12345"), "Ole", "Dole");
    user2 = new User("User2", new HashedPassword("Test12345"), "Kristoff", "Arntsen");
  }

  @Test public void testGetUser() {
    assertEquals(null, userList.get("User1"));
    userList.add(user1);
    assertEquals(user1, userList.get(user1.getUsername()));
  }

  @Test public void testAddUser_TwoUsersWithSameUsername() {
    userList.add(user1);
    try {
      userList.add(user1);
      fail("Exception should be thrown");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
    }
  }

  @Test public void testIterator() {
    userList.add(user1);
    userList.add(user2);
    Iterator<User> it = userList.iterator();
    assertTrue(it.hasNext());
    assertEquals(user1, it.next());
    assertTrue(it.hasNext());
    assertEquals(user2, it.next());
    assertFalse(it.hasNext());
  }

}
