package gov.dvla.osl.EventSource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class EventSourceApplication extends Application<EventSourceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new EventSourceApplication().run(args);
    }

    @Override
    public String getName() {
        return "EventSource";
    }

    @Override
    public void initialize(final Bootstrap<EventSourceConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final EventSourceConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
