package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;

public class AssignCommandParser {

    /**
     * Return an AssignCommand command object that contains user input, contact name and group name
     *
     * @param args The argument contains contact name and group name
     * @return A parsed contact name and group name
     * @throws ParseException If contact name or group name fails
     */
    public AssignCommand parse(String args) throws ParseException {

        requireNonNull(args);

        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GROUP);

        if (!arePrefixesExist(argumentMultimap, PREFIX_NAME, PREFIX_GROUP)
            || !argumentMultimap.getPreamble().isEmpty()) {

            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignCommand.MESSAGE_USAGE));
        }

        Group group = ParserUtil.parseGroup(argumentMultimap.getValue(PREFIX_GROUP).get());
        Name name = ParserUtil.parseName(argumentMultimap.getValue(PREFIX_NAME).get());

        return new AssignCommand(group, name);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesExist(ArgumentMultimap argumentMultimap, Prefix... prefixes) {

        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}