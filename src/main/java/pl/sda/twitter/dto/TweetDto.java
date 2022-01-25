package pl.sda.twitter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TweetDto {
    private String content;
}
