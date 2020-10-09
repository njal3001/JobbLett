package jobblett.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jobblett.core.JobShift;
import jobblett.core.Main;
import jobblett.core.User;
import jobblett.core.UserList;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class JobShiftDeserializer extends JsonDeserializer<JobShift> {

    public JobShift deserialize(JsonNode node, Main main) throws IOException, JsonProcessingException {
        String username = node.get("user").asText();
        User user = main.getUserList().getUser(username);
        LocalDateTime startingTime = LocalDateTime.parse(node.get("startingTime").asText());
        Duration duration = Duration.ofSeconds(node.get("duration").asLong());
        //LocalDateTime startingTime = new ObjectMapper().treeToValue(node.get("startingTime"), LocalDateTime.class);
        //Duration duration = new ObjectMapper().treeToValue(node.get("duration"), Duration.class);
        String info = node.get("info").asText();
        return new JobShift(user,startingTime,duration,info);
    }

    @Override
    public JobShift deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        TreeNode node = jsonParser.getCodec().readTree(jsonParser);
        return deserialize((JsonNode) node, new Main());
    }
}
