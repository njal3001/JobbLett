package jobblett.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jobblett.core.Group;
import jobblett.core.JobShift;
import jobblett.core.JobShiftList;
import jobblett.core.User;

import java.io.IOException;
import java.util.Collection;

public class GroupDeserializer extends StdDeserializer<Group> {

    protected GroupDeserializer() {
        super(Group.class);
    }

    public Group deserialize(JsonNode node) throws IOException {
        String groupName = node.get("groupName").asText();
        Integer groupID = node.get("groupID").asInt();

        ArrayNode usersArrayNode = (ArrayNode) node.get("groupMembers");
        Group group = new Group(groupName, groupID);
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
            group.addJobShift(jobShift,group.getAdmins().iterator().next());
        }

        return group;
    }

    @Override
    public Group deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        return deserialize((JsonNode) treeNode);
    }
}
