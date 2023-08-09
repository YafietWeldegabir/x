//package com.x.discoveryservice.config;
//
//import com.netflix.discovery.AbstractDiscoveryClientOptionalArgs;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class MyEurekaConfig {
//
//    @Bean
//    public AbstractDiscoveryClientOptionalArgs discoveryClientOptionalArgs() {
//        return new MyDiscoveryClientOptionalArgs(); // Replace with your custom implementation if needed
//    }
//}
package com.x.discoveryservice.config;

import com.netflix.discovery.AbstractDiscoveryClientOptionalArgs;
import org.springframework.stereotype.Component;

@Component
public class MyDiscoveryClientOptionalArgs extends AbstractDiscoveryClientOptionalArgs {
    // Customize your implementation if needed
}
