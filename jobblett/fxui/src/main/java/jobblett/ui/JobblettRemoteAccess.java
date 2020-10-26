package jobblett.ui;

import jobblett.core.Group;
import jobblett.core.GroupList;
import jobblett.core.User;
import jobblett.core.UserList;
import jobblett.json.JobblettDeserializer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;

public class JobblettRemoteAccess implements JobblettAccess{

    private final URI endpointBaseUri;

    private JobblettDeserializer<UserList> userListDeserializer = new JobblettDeserializer<>(UserList.class);
    private JobblettDeserializer<GroupList> groupListDeserializer = new JobblettDeserializer<>(GroupList.class);

    public JobblettRemoteAccess(URI endpointBaseUri) {
        this.endpointBaseUri = endpointBaseUri;
    }

    private Collection<Object> getUpdatedLists() {
        UserList userList = null;
        GroupList groupList = null;
        HttpRequest requestUserList = null;
        HttpRequest requestGroupList = null;
        try {
            requestUserList = HttpRequest.newBuilder(endpointBaseUri.resolve(new URI("jobblett/userlist"))).header("Accept","application/json").build();
            requestGroupList = HttpRequest.newBuilder(endpointBaseUri.resolve(new URI("jobblett/grouplist"))).header("Accept","application/json").build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            HttpResponse<String> responseUserList = HttpClient.newBuilder().build().send(requestUserList,HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseGroupList = HttpClient.newBuilder().build().send(requestGroupList,HttpResponse.BodyHandlers.ofString());
            String responseBodyUserList = responseUserList.body();
            String responseBodyGroupList = responseGroupList.body();

            userList = userListDeserializer.deserializeString(responseBodyUserList);
            GroupList oldGroupList = groupListDeserializer.deserializeString(responseBodyGroupList);
            groupList = correctGroupList(oldGroupList,userList);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Collection<Object> result = new ArrayList<>();
        result.add(userList);
        result.add(groupList);
        return result;
    }

    //TODO
    public Group newGroup(String groupName) {
        return null;
    }

    //TODO
    public void addUser(User newUser) {

    }

    public Group getGroup(int groupID){
        GroupList groupList = (GroupList) getUpdatedLists().iterator().next();
        return groupList.getGroup(groupID);
    }

    //TODO
    public User login(String userName, String password) {
        return null;
    }

    public Collection<Group> getGroups(User user) {
        GroupList groupList = (GroupList) getUpdatedLists().iterator().next();
        return groupList.getGroups(user);
    }

    //TODO
    public void save() {

    }

    @Override
    public void setUserList(UserList userList) {

    }

    @Override
    public void setGroupList(GroupList groupList) {
        
    }


}
