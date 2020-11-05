package jobblett.core;

public class GroupMemberList extends JobblettList<String, User> implements Iterable<User> {

  public static final String ALREADY_EXIST_ERROR_TEXT = "You are already a member of the group";

  @Override protected String identifier(User type) {
    return type.getUserName();
  }

  @Override protected void optionalAlreadyExists() {
    throw new IllegalArgumentException(ALREADY_EXIST_ERROR_TEXT);
  }

}
