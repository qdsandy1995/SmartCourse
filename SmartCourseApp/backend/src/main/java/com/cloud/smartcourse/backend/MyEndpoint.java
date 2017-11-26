

package com.cloud.smartcourse.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.smartcourse.cloud.com",
                ownerName = "backend.smartcourse.cloud.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public Course sayHi(@Named("name") String name) {
        Course response = new Course();
        response.setData("This course is " + name);

        return response;
    }

}
