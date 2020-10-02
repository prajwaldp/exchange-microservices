package contentservice;

import com.datastax.oss.driver.api.core.CqlSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AppConfig {

	/*
	 * Use the standard Cassandra driver API to create a
	 * com.datastax.oss.driver.api.core.CqlSession instance.
	 */
	public @Bean CqlSession session() {
		return CqlSession.builder().withKeyspace("mykeyspace").build();
	}
}

@EnableDiscoveryClient
@SpringBootApplication
public class ContentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentServiceApplication.class, args);
	}

}

// @RestController
// class ServiceInstancesRestController {

// @Autowired
// private DiscoveryClient discoveryClient;

// @RequestMapping("/service-instances/{applicationName}")
// public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable
// String applicationName) {
// return this.discoveryClient.getInstances(applicationName);
// }
// }
