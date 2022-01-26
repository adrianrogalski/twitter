package pl.sda.twitter.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetDtoOut {
    private String content;
    private LocalDateTime publishingTime;

    private String time;

    public TweetDtoOut(String content, LocalDateTime publishingTime) {
        this.content = content;
        this.publishingTime = publishingTime;
        this.time = timeMapper(publishingTime);
    }

    private static String timeMapper(LocalDateTime localDateTime){
        String Year = String.valueOf(LocalDateTime.now().getYear());
        String Month = String.valueOf(LocalDateTime.now().getMonthValue());
        String Day = String.valueOf(LocalDateTime.now().getDayOfMonth());
        String Hour = String.valueOf(LocalDateTime.now().getHour());
        String Minute = String.valueOf(LocalDateTime.now().getMinute());
        String Se = String.valueOf(LocalDateTime.now().getSecond());
        String formatedDateTime = String.format("%s-%s-%s %s:%s:%s", Year, Month, Day, Hour, Minute, Se);
        return formatedDateTime;
    }

}
