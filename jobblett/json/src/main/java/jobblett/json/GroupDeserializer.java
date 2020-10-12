package jobblett.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jobblett.core.*;

import java.io.IOException;

public class GroupDeserializer extends StdDeserializer<Group> {

    protected GroupDeserializer() {
        super(Group.class);
    }

    public Group deserialize(JsonNode node, Main main) throws IOException, JsonProcessingException {
        String groupName = node.get("groupName").asText();
        Integer groupID = node.get("groupID").asInt();

        ArrayNode usersArrayNode = (ArrayNode) node.get("groupMembers");
        Group group = new Group(groupName, groupID);
        for (JsonNode userNode : usersArrayNode) {
            User user = main.getUserList().getUser(userNode.asText());
            group.addUser(user);
        }
        JobShiftList jobShiftList = new JobShiftListDeserializer().deserialize(node.get("jobShifts"),main);
        for (JobShift jobShift : jobShiftList) {
            group.addJobShift(jobShift,group.getAdmin());
        }

        return group;
    }

    @Override
    public Group deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        return deserialize((JsonNode) treeNode, new Main());
    }
}
