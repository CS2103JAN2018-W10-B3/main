package seedu.address.model.session;

import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.food.Address;
import seedu.address.model.food.Email;
import seedu.address.model.food.Food;
import seedu.address.model.food.Name;
import seedu.address.model.food.Phone;
import seedu.address.model.food.Price;
import seedu.address.model.food.Rating;
import seedu.address.model.tag.Tag;

/**
 *
 */
public class SessionAddCommand extends Session {

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Price price;
    private Rating rating;
    private Set<Tag> tagSet;

    public SessionAddCommand(Command command, EventsCenter eventsCenter) {
        super(command, eventsCenter);
    }

    @Override
    protected void parseInputForMultivaluedField(Class field) throws IllegalValueException, IllegalArgumentException {
        switch (field.getSimpleName()) {
        case "Tag":
            tagSet = ParserUtil.parseTags(temporaryStrings);
            break;
        default:
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected void finishCommand() {
        AddCommand addCommand = (AddCommand) command;
        Food food = new Food(name, phone, email, address, price, rating, tagSet);
        addCommand.setFood(food);
    }

    /**
     *
     */
    public void parseInputForField(Class field, String userInput)
            throws IllegalValueException, IllegalArgumentException {
        switch (field.getSimpleName()) {
        case "Name":
            name = ParserUtil.parseName(Optional.of(userInput)).get();
            break;
        case "Phone":
            phone = ParserUtil.parsePhone(Optional.of(userInput)).get();
            break;
        case "Email":
            email = ParserUtil.parseEmail(Optional.of(userInput))
                    .orElse(new Email(Email.DEFAULT_EMAIL));;
            break;
        case "Address":
            address = ParserUtil.parseAddress(Optional.of(userInput))
                      .orElse(new Address(Address.DEFAULT_ADDRESS));
            break;
        case "Price":
            price = ParserUtil.parsePrice(Optional.of(userInput))
                    .orElse(new Price(Price.DEFAULT_PRICE));
            break;
        case "Rating":
            rating = ParserUtil.parseRating(Optional.of(userInput))
                     .orElse(new Rating(Rating.DEFAULT_RATING));
            break;
        default:
            throw new IllegalArgumentException();
        }
    }
}
