package center.myfit;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract class BaseWebIntegrationTest extends BaseIntegrationTest {

  protected MockMvc mockMvc;

  public BaseWebIntegrationTest(WebApplicationContext context) {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }
}
