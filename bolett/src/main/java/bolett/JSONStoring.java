package bolett;

import java.nio.file.Paths;
import java.util.Map;

public class JSONStoring {

    public void exportJSON() {
        try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // convert JSON file to map
            Map<?, ?> map = mapper.readValue(Paths.get("book.json").toFile(), Map.class);

            // print map entries
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
