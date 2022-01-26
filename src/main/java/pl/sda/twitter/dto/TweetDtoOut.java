package pl.sda.twitter.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetDtoOut {
    private String content;
    private String time;

    public TweetDtoOut(String content, LocalDateTime publishingTime) {
        this.content = content;
        this.time = timeMapper(publishingTime);
    }

    private static String timeMapper(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter); // "1986-04-08 12:30"
    }

}
