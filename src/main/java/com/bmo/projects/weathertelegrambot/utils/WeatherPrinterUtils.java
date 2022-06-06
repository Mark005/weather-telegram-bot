package com.bmo.projects.weathertelegrambot.utils;

import com.bmo.projects.weathertelegrambot.model.WeatherPoint;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.util.List;

@UtilityClass
public class WeatherPrinterUtils {

    public static String formNearestForecast(List<WeatherPoint> todayDetailedForecast) {
        int wight = 8;
        String decimalFormat = "%,.2f";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("⌚          ️\uD83C\uDF21️      \uD83D\uDCA7️       ⛅️   ️   \uD83C\uDF2B");
        todayDetailedForecast.forEach(weatherPoint ->
                stringBuilder.append("\n")
                        .append(weatherPoint.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                        .append(" | ")
                        .append(weatherPoint.getTemperature())
                        .append(getAdditionalSpaces(wight, weatherPoint.getTemperature()))
                        .append(" | ")
                        .append(decimalFormat.formatted(weatherPoint.getPrecipitations()))
                        .append(getAdditionalSpaces(wight, decimalFormat.formatted(weatherPoint.getPrecipitations())))
                        .append(" | ")
                        .append(weatherPoint.getCloudCoverPercent())
                        .append(getAdditionalSpaces(wight, weatherPoint.getCloudCoverPercent()))
                        .append(" | ")
                        .append(weatherPoint.getHumidity())
                        .append(getAdditionalSpaces(wight, weatherPoint.getHumidity())));
        return stringBuilder.toString();
    }

    private static String getAdditionalSpaces(int i, Object object) {
        int length = String.valueOf(object).length();
        if (i - (length * 2) < 0) {
            return "";
        }
        return " ".repeat(i - (length * 2));
    }
}
