package pl.sda.twitter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.twitter.model.Tweet;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetCommentsPage {
    private TweetDtoOut originalTweet;
    private List<Tweet> tweets;

}
