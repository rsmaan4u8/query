package com.api.bdd

import com.api.AppRunner
import cucumber.api.DataTable

import static org.hamcrest.core.StringContains.containsString
import static org.junit.Assert.assertThat

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

class CustomWorld {
    def ByteArrayOutputStream result
}

World {
    new CustomWorld()
}

Before() {
    System.clearProperty("api");
    System.clearProperty("movie");
    System.clearProperty("music");
}


When(~/I run application with api="([^"]*)" and movie="([^"]*)"/) { String api, String movie ->
    result = captureOutput()
    System.setProperty("api", api);
    System.setProperty("movie", movie);
    AppRunner.main([] as String[])
}

When(~/I run application with command 'java -jar -Dapi="([^"]*)" -Dmovie="([^"]*)" query.jar'/) { String api, String movie ->
    result = captureOutput()
    System.setProperty("api", api);
    System.setProperty("movie", movie);
    AppRunner.main([] as String[])
}

When(~/I run application with command 'java -jar -Dapi="([^"]*)" -Dmusic="([^"]*)" query.jar'/) { String api, String music ->
    result = captureOutput()
    System.setProperty("api", api);
    System.setProperty("music", music);
    AppRunner.main([] as String[])
}

When(~/I run application with command 'java -jar -Dapi="([^"]*)" -Dmovie="([^"]*)" -Dmusic="([^"]*)" query.jar'/) { String api, String movie, String music ->
    result = captureOutput()
    System.setProperty("api", api);
    System.setProperty("music", music);
    System.setProperty("movie", movie);
    AppRunner.main([] as String[])
}

Then(~/console contains/) { DataTable table ->
    table.asMaps(String, String).eachWithIndex { row, index ->
        assertThat(result.toString(), containsString(row."OUTPUT"))
    }
}

private ByteArrayOutputStream captureOutput() {
    final ByteArrayOutputStream output = new ByteArrayOutputStream()
    System.setOut(new PrintStream(output))
    return output
}
