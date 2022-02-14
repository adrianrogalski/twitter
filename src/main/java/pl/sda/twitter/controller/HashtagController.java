package pl.sda.twitter.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.twitter.model.Hashtag;
import pl.sda.twitter.service.HashtagService;

import java.util.List;
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
@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin(origins = "http://localhost:4200")
public class HashtagController {
    private HashtagService hashtagService;

    public HashtagController(HashtagService hashtagService) {
        this.hashtagService = hashtagService;
    }

    @GetMapping("/hashtags/popular")
    public List<String> getPopularHashtags() {
        return hashtagService.getPopularHashtags();
    }
}
