package jobblett.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.User;
import jobblett.core.UserList;
import jobblett.core.Workspace;

class WorkspaceDeserializer extends JsonDeserializer<Workspace> {

  @Override
  public Workspace deserialize(JsonParser jsonParser, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
    JobblettPersistence jobblettPersistence = new JobblettPersistence();

    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    JsonNode userListNode = node.get("userList");
    UserList userList = jobblettPersistence.readValue(UserList.class, userListNode);
    JsonNode groupListNode = node.get("groupList");
    GroupList groupList = jobblettPersistence.readValue(GroupList.class, groupListNode);

    Workspace workspace = new Workspace();
 
    for (User user : userList) {
      workspace.getUserList().add(user);
    }
    for (Group group : groupList) {
      workspace.getGroupList().add(group);
    }
    return workspace;
  }
  
}