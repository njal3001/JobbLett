package jobblett.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;;

public class GroupListTest {
    
    private GroupList groupList;
    private Group testGroup;

    @BeforeEach
    public void setUp() {
        groupList = new GroupList();
        testGroup = new Group("test1", 1);
    }

    @Test
	public void testAddGroup() {
        groupList.addGroup(testGroup);
        assertEquals(testGroup, groupList.getGroup(testGroup.getGroupID()));

		try {
			Group group1 = new Group("g1", 1234);
			Group group2 = new Group("g2", 1234);
			groupList.addGroup(group1);
			groupList.addGroup(group2);
			fail("Should not be able to create two groups with the same ID!");
		}
		catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testRemoveGroup(){
        groupList.addGroup(testGroup);
        groupList.removeGroup(testGroup);
        assertEquals(null, groupList.getGroup(testGroup.getGroupID()));
    }

    @Test
    public void testGetGroupSize(){
        assertEquals(0, groupList.getGroupSize());
        groupList.addGroup(testGroup);
        assertEquals(1, groupList.getGroupSize());
    }

    @Test
    public void testNewGroup_notSameID(){
        Group group1 = groupList.newGroup("test1");
        Group group2 = groupList.newGroup("test2");
        assertFalse(group1.getGroupID() == group2.getGroupID());
    }
        

}
