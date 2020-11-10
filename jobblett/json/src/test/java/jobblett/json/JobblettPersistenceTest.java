package jobblett.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.HashedPassword;
import jobblett.core.User;
import jobblett.core.UserList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)//??
public class JobblettPersistenceTest{

  JobblettPersistence jobblettPersistence;
  User user1, user2;
  String user2InString;
  UserList userList;
  Group group;
  GroupList groupList;

  @BeforeAll
  public void setup(){
    jobblettPersistence=new JobblettPersistence();

    user1 = new User("karl123", new HashedPassword("testPassword123"), "Karl", "Testersen");
    user2 = new User("siri123", new HashedPassword("bestPassword123"), "Siri", "Testersen");
    user2InString= "{" + "\n" + "  \"username\" : \"siri123\"," + "\n" +"  \"password\" : \"" + new HashedPassword("bestPassword123").toString() + "\"," +"\n" + "  \"givenName\" : \"Siri\"," + "\n" + "  \"familyName\" : \"Testersen\"" + "\n" +"}";
    userList = new UserList();
    userList.add(user1);
    userList.add(user2);

    group= new Group("TestTeam", ThreadLocalRandom.current().nextInt(1000, 10000));
    group.addUser(user1);
    group.addUser(user2);
    group.addAdmin(user1);
    groupList = new GroupList();
    groupList.add(group);
  }

  @Test
  public void testWriteValueOnDefaulLocation(){
    //deleting the folder to make sure that it gets created with this method
    deleteLocalSaves();
    assertFalse(new File(JobblettPersistence.JOBBLETT_DATA_DIRECTORY).exists(),"The path: " + JobblettPersistence.JOBBLETT_DATA_DIRECTORY +" should not already exist.");
    jobblettPersistence.writeValueOnDefaultLocation(group);
    //checking if the json save was created. 
    assertTrue(new File(JobblettPersistence.JOBBLETT_DATA_DIRECTORY+"/Group.json").exists(), "The file: " + JobblettPersistence.JOBBLETT_DATA_DIRECTORY+"/Group.json" +" should now exist.");
    //checking if the data contains the correct save
    assertEquals("TestTeam",jobblettPersistence.readValue(group.getClass()).getGroupName(), "The correct data wasn't saved in the savefile");
  }
  
  @Test
  public void testWriteValueAsString(){
    assertEquals(user2InString,jobblettPersistence.writeValueAsString(user2));
  }

  @Test
  public void testReadDefaultValue(){
    //deleting any existing saves
    deleteLocalSaves();
    User userInDefaulUserList = new User("olav", new HashedPassword("bestePassord123"), "Olav", "Nordmann");
    //this readValue(ClassType) will use readDefault because there are no savefolders.
    assertTrue(jobblettPersistence.readValue(userList.getClass()).contains(userInDefaulUserList), "The app didn't use the defaul values.");
    
    //testing that an errormessage shall be thrown when we try to read default values for a class without defaulvalues in the resources folder
    try{
      jobblettPersistence.readDefault(group.getClass());
      fail("An error should have been thrown");
    } catch(NullPointerException e){}

  }

  @Test
  public void testReadSavedValue(){
    deleteLocalSaves();
    //creating the the file and saving our data.
    jobblettPersistence.writeValueOnDefaultLocation(groupList);
    assertTrue(jobblettPersistence.readValue(groupList.getClass()).contains(group), "The app didn't use the locally saved datas.");
  }

  @Test
  public void testReadValuesFromString(){
    assertEquals(user2, jobblettPersistence.readValue(user2.getClass(),user2InString), "The string: " + user2InString + "wasn't read deserialized to User2");
  }


  private void deleteLocalSaves(){
    //deleting existing saves
    File dir = new File(JobblettPersistence.JOBBLETT_DATA_DIRECTORY);
    if(dir.exists()){
      String[] entries = dir.list();
      for(String entry: entries){
        File currentFile = new File(dir.getPath(),entry);
        currentFile.delete();
      }
      dir.delete();
      }
    }

  
}