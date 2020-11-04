package jobblett.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GroupListTest {

  private GroupList groupList;
  private Group testGroup;

  @BeforeEach public void setUp() {
    groupList = new GroupList();
    testGroup = new Group("test1", 1);
  }

  @Test public void testAddGroup() {
    groupList.add(testGroup);
    assertEquals(testGroup, groupList.get(testGroup.getGroupId()));

    try {
      Group group1 = new Group("g1", 1234);
      Group group2 = new Group("g2", 1234);
      groupList.add(group1);
      groupList.add(group2);
      fail("Should not be able to create two groups with the same ID!");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
    }
  }

  @Test public void testRemoveGroup() {
    groupList.add(testGroup);
    groupList.remove(testGroup);
    assertEquals(null, groupList.get(testGroup.getGroupId()));
  }

  @Test public void testGetGroupSize() {
    assertEquals(0, groupList.size());
    groupList.add(testGroup);
    assertEquals(1, groupList.size());
  }

  @Test public void testNewGroup_notSameID() {
    Group group1 = groupList.newGroup("test1");
    Group group2 = groupList.newGroup("test2");
    assertFalse(group1.getGroupId() == group2.getGroupId());
  }


}
