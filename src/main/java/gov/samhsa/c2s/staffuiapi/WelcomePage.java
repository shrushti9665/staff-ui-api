package gov.samhsa.c2s.staffuiapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomePage {
	
	@GetMapping("/")
	@ResponseBody
	public String welcome() {
		return "<h1>welcome to staff-ui-api</h1>";
	}

}
