package com.api.view;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleBasedProblemsView implements View {
    public static final String MESSAGES_SHOULD_NOT_BE_NULL = "Messages should not be null";
    private List<String> messages;

    public ConsoleBasedProblemsView(List<String> messages) {
        assert messages != null : MESSAGES_SHOULD_NOT_BE_NULL;
        this.messages = messages;
    }

    @Override
    public String render() {
        return messages.stream().collect(Collectors.joining(System.lineSeparator()));
    }

    public List<String> getMessages() {
        return messages;
    }

}
