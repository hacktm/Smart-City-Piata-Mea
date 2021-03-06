package com.mymarket.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mymarket.core.Saying;
import com.mymarket.core.Template;

import io.dropwizard.jersey.caching.CacheControl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class MyMarketResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyMarketResource.class);

    private final Template template;
    private final AtomicLong counter;

    public MyMarketResource(Template template) {
        this.template = template;	
        this.counter = new AtomicLong();
    }

    @GET
    @Timed(name = "get-requests")
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        return new Saying(counter.incrementAndGet(), template.render(name));
    }

    @POST
    public void receiveHello(@Valid Saying saying) {
        LOGGER.info("Received a saying: {}", saying);
    }
}
