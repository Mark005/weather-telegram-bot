package com.bmo.projects.weathertelegrambot.weather.api.openmeteo;

import com.bmo.projects.weathertelegrambot.weather.api.openmeteo.model.OpenMeteoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Api docs
 * https://open-meteo.com/en/docs
 */
@FeignClient(name = "forecast-service", url = "${client.open-meteo.base-url}")
public interface OpenMeteoClient {

    @RequestMapping(value = "/v1/forecast", method = RequestMethod.GET)
    OpenMeteoResponse getForecast(@RequestParam("latitude") Double latitude,
                                  @RequestParam("longitude") Double longitude,
                                  @RequestParam("hourly") List<String> hourly,
                                  @RequestParam("daily") List<String> daily,
                                  @RequestParam("timezone") String timezone);


}
