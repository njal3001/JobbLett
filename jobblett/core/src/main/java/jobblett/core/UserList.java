package jobblett.core;

public class UserList extends AbstractList<String, User> {

  @Override
  protected String identifier(User type) {
    return type.getUsername();
  }

  @Override
  public void optionalAlreadyExists() {
    throw new IllegalArgumentException("User with the same username already exists");
  }
}
