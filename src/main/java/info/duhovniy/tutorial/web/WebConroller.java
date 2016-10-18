package info.duhovniy.tutorial.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebConroller {

	@RequestMapping("/")
	String home() {
		return "index";
	}
}
