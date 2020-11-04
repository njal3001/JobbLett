package jobblett.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import jobblett.core.JobShift;
import jobblett.core.User;

public class JobShiftDeserializer extends JsonDeserializer<JobShift> {

  /**
   * TODO.
   *
   * @param node TODO
   * @return TODO
   * @throws IOException TODO
   */
  public JobShift deserialize(JsonNode node) throws IOException {
    User user = new UserDeserializer().deserialize(node.get("user"));
    LocalDateTime startingTime = LocalDateTime.parse(node.get("startingTime").asText());
    Duration duration = Duration.ofSeconds(node.get("duration").asLong());
    String info = node.get("info").asText();
    JobShift jobShift = new JobShift(user, startingTime, duration, info, true);
    return jobShift;
  }

  @Override
  public JobShift deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    TreeNode node = jsonParser.getCodec().readTree(jsonParser);
    return deserialize((JsonNode) node);
  }
}
