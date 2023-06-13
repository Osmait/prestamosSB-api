package com.prestamossb.prestamossbapi.infraestruture.utils;

import org.testcontainers.containers.PostgreSQLContainer;

public class TestContainerInitializer {

    private static PostgreSQLContainer<?> postgreSQLContainer;

    public  static PostgreSQLContainer<?>getPostgreSQLContainer(){
        if (postgreSQLContainer == null){
            postgreSQLContainer = new PostgreSQLContainer<>("postgres:9.6.12");
        }
        return postgreSQLContainer;
    }

    public static void stopAndRemoveContainer() {
        if (postgreSQLContainer != null) {
            postgreSQLContainer.stop();
            postgreSQLContainer = null;
        }
    }
}
