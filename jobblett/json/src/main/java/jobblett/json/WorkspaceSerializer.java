package jobblett.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import jobblett.core.GroupList;
import jobblett.core.UserList;
import jobblett.core.Workspace;

class WorkspaceSerializer extends JsonSerializer<Workspace> {

  @Override
  public void serialize(Workspace workspace, JsonGenerator jsonGenerator, 
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();
    UserList userList = workspace.getUserList();
    jsonGenerator.writeObjectField("userList", userList);
    GroupList groupList = workspace.getGroupList();
    jsonGenerator.writeObjectField("groupList", groupList);
    jsonGenerator.writeEndObject();
  }


  
}