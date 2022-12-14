
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
   
   mainMenu -- subscriptionMenuEvent --> subscriptionMenu
   subscriptionMenu -- unsubscribeEvent --> unsubscribe --> subscriptionMenu
   
   subscriptionMenu -- subscribeEvent --> timeInputWaiting
   timeInputWaiting -- backEvent --> subscriptionMenu
   timeInputWaiting -- inputConsumedEvent --> subscribe
   subscribe -- errorEvent --> timeInputWaiting
   subscribe -- successEvent --> subscriptionMenu
   
   
   subscriptionMenu -- backEvent --> mainMenu
```