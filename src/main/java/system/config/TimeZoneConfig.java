package system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {
    @Bean
    public TimeZone timeZone(){
        TimeZone defaultTimeZone = TimeZone.getTimeZone("Europe/Moscow");
        TimeZone.setDefault(defaultTimeZone);
        return defaultTimeZone;
    }
}
