package jobblett.json;

import jobblett.core.HashedPassword;

public class HashedPasswordPersistenceTest extends AbstractPersistenceTest<HashedPassword> {

  public HashedPasswordPersistenceTest() {
    super(HashedPassword.class);
  }

  @Override
  public HashedPassword getObject() {
    return new HashedPassword("TestPassword12345");
  }

  @Override
  public boolean isEquals(HashedPassword o1, HashedPassword o2) {
    return o1.toString().equals(o2.toString());
  }
}