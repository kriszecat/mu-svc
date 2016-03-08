package com.hq.poc;

import com.hq.service.client.ServiceClientApplication;
import com.hq.service.client.dto.Activity;
import com.hq.service.client.stub.ActivityClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URISyntaxException;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServiceClientApplication.class)
public class ServiceClientApplicationTests {

	@Test
	public void getActivities() throws URISyntaxException {
		Collection<Resource<Activity>> resourceActivities = ActivityClient.getInstance().getActivities();
		assertFalse(resourceActivities.isEmpty());
		resourceActivities.forEach(resource -> {
			assertEquals(resource.getContent().getEventType().name(), "IMPUTATION");
			System.out.println(resource.getContent().getName());
			System.out.println(resource.getContent().getProject().getName());
		});
	}

}
