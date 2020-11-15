package jobblett.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.HashedPassword;
import jobblett.core.User;
import jobblett.core.UserList;

public class JobblettPersistenceTest {

  JobblettPersistence jobblettPersistence;
  UserPersistenceTest userPersistenceTest;
  UserListPersistenceTest userListPersistenceTest;
  GroupPersistenceTest groupPersistenceTest;

  User user1, user2;
  String user2InString;
  UserList userList;
  Group group;
  GroupList groupList;

  @BeforeEach
  public void setup() {
    jobblettPersistence = new JobblettPersistence();
    userPersistenceTest = new UserPersistenceTest();
    userListPersistenceTest = new UserListPersistenceTest();
    groupPersistenceTest = new GroupPersistenceTest();

    user1 = new User("karl123", new HashedPassword("testPassword123"), "Karl", "Testersen");
    user2 = new User("siri123", new HashedPassword("bestPassword123"), "Siri", "Testersen");
    user2InString = "{" + "\n" + "  \"username\" : \"siri123\"," + "\n" + "  \"hashedPassword\" : " +
        "{" + "\n" + "    \"password\" : \"" + new HashedPassword("bestPassword123").toString() +
        "\"" + "\n  },\n" + "  \"givenName\" : \"Siri\"," + "\n" + "  \"familyName\" : \"Testersen\"" +
        "\n" + "}";
    userList = new UserList();
    userList.add(user1);
    userList.add(user2);

    group = new Group("TestTeam", ThreadLocalRandom.current().nextInt(1000, 10000));
    group.addUser(user1);
    group.addUser(user2);
    group.addAdmin(user1);
    groupList = new GroupList();
    groupList.add(group);
  }

  @Test
  public void testWriteValueOnDefaulLocation() throws IOException {
    try {
      // This code is placed at the top to skip the test in case it doesn't find the file.
      URL fileUrl = getClass().getResource(UserList.class.getSimpleName() + ".json");

      // deleting the folder to make sure that it gets created with this method
      deleteLocalSaves();
      assertFalse(new File(JobblettPersistence.JOBBLETT_DATA_DIRECTORY).exists(),
          "The path: " + JobblettPersistence.JOBBLETT_DATA_DIRECTORY + " should not already exist.");
      jobblettPersistence.writeValueOnDefaultLocation(group);
      // checking if the json save was created.
      assertTrue(new File(JobblettPersistence.JOBBLETT_DATA_DIRECTORY + "/Group.json").exists(),
          "The file: " + JobblettPersistence.JOBBLETT_DATA_DIRECTORY + "/Group.json" + " should now exist.");
      // checking if the data contains the correct save
      assertEquals("TestTeam", jobblettPersistence.readValue(group.getClass()).getGroupName(),
          "The correct data wasn't saved in the savefile");

      // creating a file with same path as the app's local datasave directory
      deleteLocalSaves();

      File sourceFile = new File(fileUrl.getPath());
      File outputFile = new File(JobblettPersistence.JOBBLETT_DATA_DIRECTORY);
      Files.copy(sourceFile.toPath(), outputFile.toPath());
      Runnable runable = () -> jobblettPersistence.writeValueOnDefaultLocation(userList);
      String expected =
          "Could not save. The path " + JobblettPersistence.JOBBLETT_DATA_DIRECTORY + " is not available.\n";
      assertConsolPrintOut(runable, expected);
    } catch (NoSuchFileException e) {
      System.out.println("The test \"testWriteValueOnDefaulLocation()\" could not be ran.");
    }
  }

/*  @Test
  public void testWriteValueAsString() {
    assertEquals(user2InString, jobblettPersistence.writeValueAsString(user2));

  }*/

  @Test
  public void testReadDefaultValue() {
    // deleting any existing saves
    deleteLocalSaves();
    User userInDefaulUserList = new User("olav", new HashedPassword("bestePassord123"), "Olav", "Nordmann");
    // this readValue(ClassType) will use readDefault because there are no savefolders.
    UserList defaultUserList = jobblettPersistence.readValue(userList.getClass());
    assertTrue(userPersistenceTest.isEquals(userInDefaulUserList, defaultUserList.get("olav")),
        "The app didn't use the defaul values.");

    // testing that we get null when we try to read default values for a class without default values in
    // the resources folder
    assertNull(jobblettPersistence.readDefault(Group.class));

  }

  @Test
  public void testReadSavedValue() throws IOException {
    try {
      // This code is placed at the top to skip the test in case it doesn't find the file.
      URL fileUrl = getClass().getResource(UserList.class.getSimpleName() + ".json");

      deleteLocalSaves();
      // Creating the the file and saving our data.
      jobblettPersistence.writeValueOnDefaultLocation(groupList);
      GroupList defaultGroupList = jobblettPersistence.readValue(groupList.getClass());
    assertTrue(groupPersistenceTest.isEquals(group, defaultGroupList.get(group.getGroupId())),
          "The app didn't use the locally saved datas.");

      // Changing saved userlist to our corrupted userlist.
      File sourceFile = new File(fileUrl.getPath());
      File outputFile = new File(JobblettPersistence.JOBBLETT_DATA_DIRECTORY + "/UserList.json");
      Files.copy(sourceFile.toPath(), outputFile.toPath());
      UserList expected = new JobblettPersistence().readDefault(UserList.class);
      // This should read the default values because of our corrupted jsonfile
      UserList actual = new JobblettPersistence().readValue(UserList.class);
      assertTrue(userListPersistenceTest.isEquals(expected, actual));
    } catch (NoSuchFileException e) {
      System.out.println("The test \"testReadSavedValue()\" could not be ran.");
    }
  }

  //TODO
  /*@Test
  public void testReadValuesFromString() {
    assertTrue(userPersistenceTest.isEquals(user2, jobblettPersistence.readValue(user2.getClass(), user2InString)),
        "The string: " + user2InString + "wasn't read deserialized to User2");
  }*/

  // function to test that we get expected message printed out to console
  private void assertConsolPrintOut(Runnable runnable, String expected) {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream orginalConsole = System.out;
    System.setOut(new PrintStream(outContent));
    outContent.reset();
    runnable.run();
    System.setOut(orginalConsole);
    assertEquals(expected, outContent.toString());
  }

  // inspired by:
  // https://stackoverflow.com/questions/20281835/how-to-delete-a-folder-with-files-using-java
  private void deleteLocalSaves() {
    // deleting existing saves
    File dir = new File(JobblettPersistence.JOBBLETT_DATA_DIRECTORY);
    if (dir.isDirectory()) {
      String[] entries = dir.list();
      for (String entry : entries) {
        File currentFile = new File(dir.getPath(), entry);
        currentFile.delete();
      }
    }
    if (dir.exists()) {
      dir.delete();
    }
  }

}
