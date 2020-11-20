package jobblett.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.JobShiftList;
import jobblett.core.User;

class GroupDeserializer extends JsonDeserializer<Group> {

  @Override
  public Group deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);

    String groupName = node.get("groupName").asText();
    Integer groupId = node.get("groupId").asInt();

    ArrayNode usersArrayNode = (ArrayNode) node.get("groupMembers");
    Group group = new Group(groupName, groupId);
    for (JsonNode userNode : usersArrayNode) {
      User user = new JobblettPersistence().readValue(User.class, userNode);
      group.addUser(user);
    }

    ArrayNode adminsArrayNode = (ArrayNode) node.get("admins");
    for (JsonNode adminNode : adminsArrayNode) {
      String userName = adminNode.get("username").textValue();
      group.addAdmin(group.getUser(userName));
    }

    JsonNode jobShiftNode = node.get("jobShifts");
    JobblettPersistence persistence = new JobblettPersistence();
    JobShiftList jobShiftList = persistence.readValue(JobShiftList.class, jobShiftNode);
    for (JobShift jobShift : jobShiftList) {
      User duplicateUser = jobShift.getUser();
      User realUser = group.getUser(duplicateUser.getUsername());
      jobShift.setUser(realUser);
      group.addJobShift(jobShift, group.getAdmins().iterator().next());
    }

    return group;
  }
}
