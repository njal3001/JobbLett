package bolett.core;

import bolett.json.JSONDeserialize;
import bolett.json.JSONSerialize;

public class Main {
    private UserList userList = new UserList();
    private GroupList groupList = new GroupList();


    public Main() {
        JSONDeserialize importer = new JSONDeserialize();
        importer.updateMain(this);
    }

    public void closeMain() {
        JSONSerialize serializer = new JSONSerialize(this, "src/main/resources/bolett/json/main.json");

    }

    public GroupList getGroupList() {
        return groupList;
    }

    public UserList getUserList() {
        return userList;
    }

    @Override
    public String toString() {
        return "Main{" +
                "userList=" + userList +
                ", groupList=" + groupList +
                '}';
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main);

    }
}
