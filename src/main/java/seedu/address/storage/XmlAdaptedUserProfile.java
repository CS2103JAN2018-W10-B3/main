package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.food.Address;
import seedu.address.model.food.Food;
import seedu.address.model.food.Name;
import seedu.address.model.food.Phone;
import seedu.address.model.food.allergy.Allergy;
import seedu.address.model.user.UserProfile;

/**
 * JAXB-friendly version of the User Profile.
 */
public class XmlAdaptedUserProfile {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "User Profile's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedAllergy> allergies = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedFood> recentFoods = new ArrayList<>();


    /**
     * Constructs an XmlAdaptedUserProfile.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUserProfile() {}

    /**
     * Constructs an {@code XmlAdaptedUserProfile} with the given food details.
     */
    public XmlAdaptedUserProfile(String name, String phone, String address, List<XmlAdaptedAllergy> allergies,
                                 List<XmlAdaptedFood> foods) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        if (allergies != null) {
            this.allergies = new ArrayList<>(allergies);
        }

        if (foods != null) {
            this.recentFoods = new ArrayList<>(foods);
        }
    }

    /**
     * Converts a given Profile into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedProfile
     */
    public XmlAdaptedUserProfile(UserProfile source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        address = source.getAddress().value;
        for (Allergy allergy: source.getAllergies()) {
            allergies.add(new XmlAdaptedAllergy(allergy));
        }
        for (Food food: source.getRecentFoods()) {
            recentFoods.add(new XmlAdaptedFood(food));
        }
    }

    /**
     * Converts this jaxb-friendly adapted UserProfile object into the model's UserProfile object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted UserProfile
     */
    public UserProfile toModelType() throws IllegalValueException {
        final List<Allergy> allergiesList = new ArrayList<>();
        final List<Food> recentFoodsList = new ArrayList<>();
        for (XmlAdaptedAllergy allergy : allergies) {
            allergiesList.add(allergy.toModelType());
        }

        for (XmlAdaptedFood food : recentFoods) {
            recentFoodsList.add(food.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(this.phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone phone = new Phone(this.phone);

        if (this.address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(this.address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address address = new Address(this.address);

        final Set<Allergy> allergies = new HashSet<>(allergiesList);
        final Set<Food> recentFoods = new HashSet<>(recentFoodsList);
        return new UserProfile(name, phone, address, allergies, recentFoods);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedUserProfile)) {
            return false;
        }

        XmlAdaptedUserProfile otherProfile = (XmlAdaptedUserProfile) other;
        return Objects.equals(name, otherProfile.name)
                && Objects.equals(phone, otherProfile.phone)
                && Objects.equals(address, otherProfile.address)
                && allergies.equals(otherProfile.allergies)
                && recentFoods.equals(otherProfile.recentFoods);
    }
}
