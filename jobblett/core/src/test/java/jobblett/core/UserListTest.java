package jobblett.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserListTest{

private UserList userList;
private User user1;
private User user2;

@BeforeEach
public void setUp(){
  userList = new UserList();
  user1 = new User("User1", "Test12345", "Ole", "Dole");
  user2 = new User("User2", "Test12345", "Kristoff", "Arntsen");
}

@Test
public void testGetUser(){
  assertEquals(null, userList.getUser("User1"));
  userList.addUser(user1);
  assertEquals(user1, userList.getUser(user1.getUserName()));
}

@Test
public void testAddUser_TwoUsersWithSameUsername(){
  userList.addUser(user1);
  try{
    userList.addUser(user1);
    fail("Exception should be thrown");
  } catch(Exception e){
    assertTrue(e instanceof IllegalArgumentException);
  }
}

@Test
public void testLogin_CorrectUsernameAndPassword(){
  userList.addUser(user1);
  assertEquals(user1, userList.login(user1.getUserName(), "Test12345"));
}

@Test
public void testLogin_WrongPassword(){
  userList.addUser(user1);
  assertEquals(null, userList.login(user1.getUserName(), ""));
}

@Test
public void testIterator(){
  userList.addUser(user1);
  userList.addUser(user2);
  Iterator<User> it = userList.iterator();
  assertTrue(it.hasNext());
  assertEquals(user1, it.next());
  assertTrue(it.hasNext());
  assertEquals(user2, it.next());
  assertFalse(it.hasNext());
}

}