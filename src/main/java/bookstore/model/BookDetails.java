package bookstore.model;

import lombok.Data;
import org.apache.commons.math3.random.RandomDataGenerator;

@Data
public class BookDetails {

    private String uniqueId = "ID" + new RandomDataGenerator().nextInt(100000, 99999999);
    private Book book;
    private int availability;

    public String getID() {
        return uniqueId;
    }
}
