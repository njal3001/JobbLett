package jobblett.core;

public class GroupMemberList extends AbstractList<String, User> {

  public static final String ALREADY_EXIST_ERROR_TEXT = "You are already a member of the group";

  @Override
  protected String identifier(User type) {
    return type.getUsername();
  }

  @Override
  public void optionalAlreadyExists() {
    throw new IllegalArgumentException(ALREADY_EXIST_ERROR_TEXT);
  }

}
