package com.api;

import com.api.config.PropertiesProvider;
import com.api.controller.APISearchController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppRunner {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.api");
        PropertiesProvider configuration = context.getBean(PropertiesProvider.class);
        APISearchController controller = context.getBean(APISearchController.class);
        String renderedView = controller.performSearch(configuration.getMovieTitle(), configuration.getMusicTitle(), configuration.getAPIProviders()).render();
        System.out.println(renderedView);
    }
}
