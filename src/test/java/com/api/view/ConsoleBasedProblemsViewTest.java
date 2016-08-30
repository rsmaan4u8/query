package com.api.view;

import com.google.common.collect.Lists;
import org.junit.Test;

import static com.api.view.ConsoleBasedProblemsView.MESSAGES_SHOULD_NOT_BE_NULL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConsoleBasedProblemsViewTest {

    public static final String MESSAGE_1 = "MESSAGE1";
    public static final String MESSAGE_2 = "MESSAGE2";

    @Test
    public void testConstructor_shouldDoNullCheckOnMessages() {
        try {
            new ConsoleBasedProblemsView(null);
            assertTrue("Should not reach here", false);
        } catch (AssertionError ae) {
            assertEquals(MESSAGES_SHOULD_NOT_BE_NULL, ae.getMessage());
        }
    }

    @Test
    public void testRender_shouldReturnAsString() throws Exception {
        String actual = new ConsoleBasedProblemsView(Lists.newArrayList(MESSAGE_1, MESSAGE_2)).render();

        assertEquals(MESSAGE_1 + System.lineSeparator() + MESSAGE_2, actual);
    }

    @Test
    public void testRender_whenEmpty_shouldReturnAsString() throws Exception {
        String actual = new ConsoleBasedProblemsView(Lists.newArrayList()).render();

        assertEquals("", actual);
    }

}