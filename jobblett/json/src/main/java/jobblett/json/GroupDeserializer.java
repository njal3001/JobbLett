package jobblett.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.JobShiftList;
import jobblett.core.User;

public class GroupDeserializer extends StdDeserializer<Group> {

  protected GroupDeserializer() {
    super(Group.class);
  }

  /**
   * TODO.
   *
   * @param node TODO
   * @return TODO
   * @throws IOException TODO
   */
  public Group deserialize(JsonNode node) throws IOException {
    String groupName = node.get("groupName").asText();
    Integer groupId = node.get("groupId").asInt();

    ArrayNode usersArrayNode = (ArrayNode) node.get("groupMembers");
    Group group = new Group(groupName, groupId);
    for (JsonNode userNode : usersArrayNode) {
      User user = new UserDeserializer().deserialize(userNode);
      group.addUser(user);
    }

    ArrayNode adminsArrayNode = (ArrayNode) node.get("admins");
    for (JsonNode adminNode : adminsArrayNode) {
      String userName = adminNode.get("username").textValue();
      group.addAdmin(group.getUser(userName));
    }


    JobShiftList jobShiftList = new JobShiftListDeserializer().deserialize(node.get("jobShifts"));
    for (JobShift jobShift : jobShiftList) {
      group.addJobShift(jobShift, group.getAdmins().iterator().next());
    }

    return group;
  }

  @Override
  public Group deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
    return deserialize((JsonNode) treeNode);
  }
}
