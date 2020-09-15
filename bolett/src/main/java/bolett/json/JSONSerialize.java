/*
package bolett.json;

import java.io.File;

import bolett.core.Group;
import bolett.core.Main;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JSONSerialize {
    Main main;

    public JSONSerialize(Main main) {
        this.main = main;
    }

    public void exportJSON() {
        try {
            // create object mapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            // convert map file to JSON
            objectMapper.writeValue(new File("src/main/java/bolett/main.json"), main);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        Group gruppe7 = main.getGroup(main.newGroup("Gruppe7"));

        main.newUser("haryp", "bestePassord123", "Hary", "Pi");
        main.newUser("sanketb", "bestePassord123", "Sanket", "Be");
        main.newUser("kavus", "bestePassord123", "Lol", "Si");
        main.newUser("lol", "bestePassord123", "Njaal", "Te");

        gruppe7.addUser(main.getUser("haryp"));
        gruppe7.addUser(main.getUser("sanketb"));
        gruppe7.addUser(main.getUser("kavus"));
        gruppe7.addUser(main.getUser("lol"));

        JSONSerialize exporter = new JSONSerialize(main);
        exporter.exportJSON();
    }
}
*/
