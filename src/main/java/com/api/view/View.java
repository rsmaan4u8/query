package com.api.view;

/**
 * This interface should be implemented by different presentation views of results or problems.
 */
public interface View {
    /**
     * Method to render a view to string.
     * @return rendered String
     */
    String render();
}
