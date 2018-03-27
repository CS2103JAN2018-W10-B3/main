package seedu.address.model.food.allergy;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AllergyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Allergy(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidAllergyName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Allergy(invalidAllergyName));
    }

    @Test
    public void isValidAllergyName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Allergy.isValidAllergyName(null));
    }
}
