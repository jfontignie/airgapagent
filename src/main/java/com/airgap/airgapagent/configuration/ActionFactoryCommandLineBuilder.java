package com.airgap.airgapagent.configuration;

import org.springframework.boot.ApplicationArguments;

import java.util.EnumMap;
import java.util.Map;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
public class ActionFactoryCommandLineBuilder implements CommandLineBuilder<Action> {

    private static final Map<ActionType, CommandLineBuilder<Action>> map = new EnumMap<>(Map.of(
            ActionType.COPY, new FileCopyActionCommandLineBuilder(),
            ActionType.SEARCH, new AbstractSearchActionCommandLineBuilder(),
            ActionType.NULL, new NullCommandLineBuilder()
    )
    );
    private static final String ACTION_PARAMETER = "action";


    @Override
    public Action map(ApplicationArguments arguments) throws CommandLineParseException {

        String actionType = getRequiredArgument(arguments, ACTION_PARAMETER);
        try {
            ActionType action = ActionType.valueOf(actionType);
            return map.get(action).map(arguments);
        } catch (IllegalArgumentException e) {
            throw new CommandLineParseException("Invalid action: " + actionType);
        }
    }
}
