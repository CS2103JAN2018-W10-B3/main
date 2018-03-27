package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedFood.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalFoods.BACON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.food.Address;
import seedu.address.model.food.Email;
import seedu.address.model.food.Name;
import seedu.address.model.food.Phone;
import seedu.address.model.food.Price;
import seedu.address.model.food.Rating;
import seedu.address.testutil.Assert;

public class XmlAdaptedFoodTest {
    private static final String INVALID_NAME = "R@nch";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_PRICE = "0a.3";
    private static final String INVALID_RATING = "9";
    private static final String INVALID_TAG = "#fried";
    private static final String INVALID_ALLERGY = "#lactose";

    private static final String VALID_NAME = BACON.getName().toString();
    private static final String VALID_PHONE = BACON.getPhone().toString();
    private static final String VALID_EMAIL = BACON.getEmail().toString();
    private static final String VALID_ADDRESS = BACON.getAddress().toString();
    private static final String VALID_PRICE = BACON.getPrice().toString();
    private static final String VALID_RATING = BACON.getRating().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BACON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<XmlAdaptedAllergy> VALID_ALLERGIES = BACON.getAllergies().stream()
            .map(XmlAdaptedAllergy::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validFoodDetails_returnsFood() throws Exception {
        XmlAdaptedFood food = new XmlAdaptedFood(BACON);
        assertEquals(BACON, food.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedFood food =
                new XmlAdaptedFood(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRICE, VALID_RATING,
                        VALID_TAGS, VALID_ALLERGIES);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedFood food = new XmlAdaptedFood(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_PRICE, VALID_RATING, VALID_TAGS, VALID_ALLERGIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedFood food =
                new XmlAdaptedFood(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRICE, VALID_RATING,
                        VALID_TAGS, VALID_ALLERGIES);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedFood food = new XmlAdaptedFood(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_PRICE,
                VALID_RATING, VALID_TAGS, VALID_ALLERGIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedFood food =
                new XmlAdaptedFood(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_PRICE, VALID_RATING,
                        VALID_TAGS, VALID_ALLERGIES);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedFood food = new XmlAdaptedFood(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_PRICE,
                VALID_RATING, VALID_TAGS, VALID_ALLERGIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedFood food =
                new XmlAdaptedFood(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_PRICE, VALID_RATING,
                        VALID_TAGS, VALID_ALLERGIES);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedFood food = new XmlAdaptedFood(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_PRICE,
                VALID_RATING, VALID_TAGS, VALID_ALLERGIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        XmlAdaptedFood food =
                new XmlAdaptedFood(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_PRICE, VALID_RATING,
                        VALID_TAGS, VALID_ALLERGIES);
        String expectedMessage = Price.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        XmlAdaptedFood food = new XmlAdaptedFood(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null,
                VALID_RATING, VALID_TAGS, VALID_ALLERGIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidRating_throwsIllegalValueException() {
        XmlAdaptedFood food =
                new XmlAdaptedFood(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRICE, INVALID_RATING,
                        VALID_TAGS, VALID_ALLERGIES);
        String expectedMessage = Rating.MESSAGE_RATING_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_nullRating_throwsIllegalValueException() {
        XmlAdaptedFood food = new XmlAdaptedFood(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRICE,
                null, VALID_TAGS, VALID_ALLERGIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, food::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedFood food =
                new XmlAdaptedFood(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRICE, VALID_RATING,
                        invalidTags, VALID_ALLERGIES);
        Assert.assertThrows(IllegalValueException.class, food::toModelType);
    }

    @Test
    public void toModelType_invalidAllergies_throwsIllegalValueException() {
        List<XmlAdaptedAllergy> invalidAllergies = new ArrayList<>(VALID_ALLERGIES);
        invalidAllergies.add(new XmlAdaptedAllergy(INVALID_ALLERGY));
        XmlAdaptedFood food =
                new XmlAdaptedFood(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRICE, VALID_RATING,
                        VALID_TAGS, invalidAllergies);
        Assert.assertThrows(IllegalValueException.class, food::toModelType);
    }

}
