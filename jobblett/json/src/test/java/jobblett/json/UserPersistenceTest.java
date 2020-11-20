package jobblett.json;

import jobblett.core.HashedPassword;
import jobblett.core.User;

public class UserPersistenceTest extends AbstractPersistenceTest<User> {

  private User user =
      new User("Olavh123", new HashedPassword("Heisann123456"), "Olav", "Hermansen");

  public UserPersistenceTest() {
    super(User.class);
  }

  @Override public User getObject() {
    return user;
  }

  @Override
  public boolean isEquals(User o1, User o2) {
      if (!o1.getUsername().equals(o2.getUsername())) {
        return false;
      }
      HashedPasswordPersistenceTest hashedPasswordPersistenceTest = new HashedPasswordPersistenceTest();
      if (!hashedPasswordPersistenceTest.isEquals(o1.getPassword(), o2.getPassword())) {
        return false;
      }
      if (!o1.getGivenName().equals(o2.getGivenName())) {
        return false;
      }
      if (!o1.getFamilyName().equals(o2.getFamilyName())) {
        return false;
      }
      return true;
  }
}
