package com.hq.service.client.stub;

import com.hq.service.client.dto.Activity;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.mvc.TypeReferences;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import static org.springframework.hateoas.client.Hop.rel;

@Component
public class ActivityClient {
    private  ActivityClient() {}

    private static class ActivityClientHolder {
        private static final ActivityClient INSTANCE = new ActivityClient();
    }

    public static ActivityClient getInstance() {
        return ActivityClientHolder.INSTANCE;
    }

    public Collection<Resource<Activity>> getActivities() throws URISyntaxException {
        Collection<Resource<Activity>> activities = null;

        final Traverson traverson = new Traverson(new URI("http://localhost:8080/"), MediaTypes.HAL_JSON);
        PagedResources<Resource<Activity>> resources = traverson
                .follow(rel("activities").
                        withParameter("size", 5).
                        withParameter("page", 0))
                .toObject(new TypeReferences.PagedResourcesType<Resource<Activity>>(){});

        return resources.getContent();
    }
}
