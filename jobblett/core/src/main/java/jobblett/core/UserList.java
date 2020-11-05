package jobblett.core;

public class UserList extends JobblettList<String, User> implements Iterable<User> {

  /**
   * Lets the user log into their account.
   * Checks whether the username and password matches an existing user, before logging in.
   *
   * @param username username used to check
   * @param password password used to check
   * @return the user if logged in, else null
   */
  public User checkUserNameAndPassword(String username, HashedPassword password) {
    User user = get(username);
    if (user == null || !user.getPassword().equals(password)) {
      return null;
    } else {
      return user;
    }
  }

  @Override protected String identifier(User type) {
    return type.getUserName();
  }

  @Override protected void optionalAlreadyExists() {
    throw new IllegalArgumentException("User with the same username already exists");
  }
}
