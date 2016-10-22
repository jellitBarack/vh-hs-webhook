package hootsuit.webhook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebhookConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class WebhookConfigurationTest {
	
	@LocalServerPort
	private int port;
	
	@Value("${local.management.port}")
	private int managementPort;
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldReturn200WhenSendingRequestToController() throws Exception {
		ResponseEntity<Object> entity = restTemplate.getForEntity("http://localhost:" + port + "/destinations", 
															      Object.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
		ResponseEntity<Object> entity = restTemplate.getForEntity("http://localhost:" + managementPort + "/info", 
																  Object.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}