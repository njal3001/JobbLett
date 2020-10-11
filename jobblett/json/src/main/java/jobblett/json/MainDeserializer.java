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

public class MainDeserializer extends StdDeserializer<Main> {
    protected MainDeserializer() {
        super(Main.class);
    }

    public Main deserialize(JsonNode node) throws IOException, JsonProcessingException {
        Main main = new Main();
        UserList userList = new UserListDeserializer().deserialize(node.get("userList"));
        for (User user : userList) {
            main.getUserList().addUser(user);
        }

        GroupList groupList = new GroupListDeserializer().deserialize(node.get("groupList"),main);
        for (Group group : groupList) {
            main.getGroupList().addGroup(group);
        }

        String username = node.get("loggedIn").asText();
        if (!username.equals("null")) {
            User loggedIn = main.getUserList().getUser(username);
            main.logIn(loggedIn);
        }

        String groupIDString = node.get("activeGroup").asText();
        Integer groupID = node.get("activeGroup").asInt();
        if (!groupIDString.equals("null")) {
            Group activeGroup = main.getGroupList().getGroup(groupID);
            main.setActiveGroup(activeGroup);
        }
        return main;
    }

    @Override
    public Main deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        return deserialize((JsonNode) treeNode);
    }
}