package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

	@RequestMapping("/teams/{id}")
	public Team getTeam() {
		return new Team("Lions", "Detroit");
	}
}
