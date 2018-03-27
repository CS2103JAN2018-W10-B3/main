package seedu.address.model.food.allergy;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UniqueAllergyListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueAllergyList uniqueAllergyList = new UniqueAllergyList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueAllergyList.asObservableList().remove(0);
    }
}
