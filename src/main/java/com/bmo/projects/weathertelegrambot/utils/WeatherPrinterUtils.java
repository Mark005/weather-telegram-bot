package com.bmo.projects.weathertelegrambot.utils;

import com.bmo.projects.weathertelegrambot.model.DayForecast;
import com.bmo.projects.weathertelegrambot.model.WeatherPoint;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.util.List;

@UtilityClass
public class WeatherPrinterUtils {

    public static String formNearestForecast(List<WeatherPoint> todayDetailedForecast) {
        int wight = 10;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("⌚          ️\uD83C\uDF21️       \uD83D\uDCA7️        ⛅️   ️     \uD83C\uDF2B");
        todayDetailedForecast.forEach(weatherPoint ->
                stringBuilder.append("\n")
                        .append(weatherPoint.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                        .append(" | ")
                        .append(weatherPoint.getTemperature())
                        .append(getAdditionalSpaces(wight, weatherPoint.getTemperature()))
                        .append(" | ")
                        .append(weatherPoint.getPrecipitations())
                        .append(getAdditionalSpaces(wight, weatherPoint.getPrecipitations()))
                        .append(" | ")
                        .append(weatherPoint.getCloudCoverPercent())
                        .append(getAdditionalSpaces(wight, weatherPoint.getCloudCoverPercent()))
                        .append(" | ")
                        .append(weatherPoint.getHumidity())
                        .append(getAdditionalSpaces(wight, weatherPoint.getHumidity())));
        return stringBuilder.toString();
    }

    public static String formSevenDaysForecast(List<DayForecast> todayDetailedForecast) {
        int wight = 10;
        String decimalFormat = "%,.2f";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83D\uDCC5       max️\uD83C\uDF21️  min\uD83C\uDF21    \uD83D\uDCA7️     ⏱\uD83D\uDCA7️  \uD83D\uDCA8");
        todayDetailedForecast.forEach(dayForecast ->
                stringBuilder.append("\n")
                        .append(dayForecast.getDate().format(DateTimeFormatter.ofPattern("dd.MM")))
                        .append(" | ")
                        .append(dayForecast.getMaxDailyTemperature())
                        .append(getAdditionalSpaces(wight, dayForecast.getMaxDailyTemperature()))
                        .append(" | ")
                        .append(dayForecast.getMinDailyTemperature())
                        .append(getAdditionalSpaces(wight, dayForecast.getMinDailyTemperature()))
                        .append(" | ")
                        .append(dayForecast.getPrecipitations())
                        .append(getAdditionalSpaces(wight, dayForecast.getPrecipitations()))
                        .append(" | ")
                        .append(dayForecast.getPrecipitationHours())
                        .append(getAdditionalSpaces(8, dayForecast.getPrecipitationHours()))
                        .append(" | ")
                        .append(dayForecast.getMaxWindSpeed())
        );
        return stringBuilder.toString();
    }
//07.06 | 26.5     | 17.8     | 0.39     | 6     | 6
//08.06 | 22.8     | 17.7     | 4.28     | 16   | 16
//09.06 | 26.3     | 17.2     | 5.61     | 11   | 11
//10.06 | 27.9     | 18.3     | 0.36     | 6     | 6
//11.06 | 23.6     | 16.0     | 0.0     | 0     | 0
//12.06 | 25.9     | 15.0     | 0.0     | 0     | 0
//13.06 | 29.6     | 16.4     | 0.06     | 6     | 6
    private static String getAdditionalSpaces(int i, Object object) {
        int length = String.valueOf(object).length();
        if (i - (length * 2) < 0) {
            return "";
        }
        return " ".repeat(i - (length * 2));
    }
}
