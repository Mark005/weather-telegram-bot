
```mermaid
flowchart LR
   registerUser --> mainMenu 
   
   mainMenu -- currentWeatherEvent --> currentWeather --> mainMenu
   mainMenu -- todaysWeatherEvent --> todaysWeather --> mainMenu
   mainMenu -- sevenDaysWeatherEvent --> sevenDaysWeather --> mainMenu
   
   mainMenu -- locationMenuEvent --> locationMenu
   locationMenu -- locationConsumedEvent --> locationSet --> locationMenu
   locationMenu -- dropLocationEvent --> dropLocation --> locationMenu
   locationMenu -- backEvent --> mainMenu
   
   locationMenu -- countrySelectEvent --> countryInputWaiting
   
   mainMenu -- subscribeEvent --> subscriptionMenu
   subscriptionMenu --> mainMenu
```