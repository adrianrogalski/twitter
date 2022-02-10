package pl.sda.twitter.controller;

import org.springframework.web.bind.annotation.*;
import pl.sda.twitter.dto.HashtagDto;
import pl.sda.twitter.dto.TweetDtoOut;
import pl.sda.twitter.dto.UserDtoOut;
import pl.sda.twitter.repository.JpaTweetRepository;
import pl.sda.twitter.service.TweetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4200")
public class HashtagController {
}
