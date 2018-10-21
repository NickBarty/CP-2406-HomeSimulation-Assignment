# Home Automation Simulator
This is the CP2406 home automation simulator project codebase.
# Task Plan
1. (ON-GOING) Review project brief 
2. (DONE) Establish initial version of user stories 
3. (DONE) Establish initial version of spikes user stories 
4. (DONE) Establish initial UML Class diagram
5. (DONE) Establish classes in intellij
6. (DONE) Set up classes based on the UML class diagram
7. (DONE) Implement user stories one at a time (17/18 Complete) (1/17 Incomplete / Buggy)
8. (DONE) Refine application once all user stories have been implemented


# How To Use csv File
The following details what is allowed in each field of  the 'config.csv' file
- **ObjectType can be**: Fixture / Appliance / WaterFixture / WaterAppliance
- **Name can be**: Any words (String)
- **ElectricityUsePerMinute can be**: Any Integer
- **LitersUsedPerMin can be**: Any floating point number
- **Room can be**: Any Room *(If this doesn't match any created rooms the object will not be created)*  
**IMPORTANT:** _If any rooms or objects are set incorrectly they will not be made_

#### Creating A Room:
- Room, roomName
- E.g: Room,Living Room
#### Creating Fixtures and Appliances:
- ObjectType, Name, ElectricityUsePerMinute, Room
- E.g: Fixture,Light,1,Living Room
#### Creating WaterFixtures and WaterAppliances:
- ObjectType, Name, ElectricityUsePerMinute, LitersUsedPerMinute, Room
- E.g: <font color="red">WaterFixture,Sprinklers,0,1.5,Garden
## Config Values Information 
- **minTemperature**: Set minimum temperature simulator will naturally get to
- **maxTemperature**: Set maximum temperature simulator will naturally get to
- **startTemperature**: Set starting temperature of simulator
- **startSunlight**: Set starting sunlight percent of simulator (0-100)
- **startMoisture**: Set starting moisture percent of simulator (0-100)
- **tempChangeDelayTime**: Set how often temperature is checked to be changed in simulated minutes 
- **soilChangeDelayTime**: Set how often soil moisture is checked to be changed in simulated minutes 
- **miliSecondsPerMin**: Set how many mili seconds is a simulated minute (Higher is slower)
- **randomEventChance**: Set how often random events occur (Higher is more often)
- **rainMinLength**: Set the minimum number of simulated minutes to rain for if raining occurs
- **rainMaxLength**: Set the maximum number of simulated minutes to rain for if raining occurs
- **centsPerKw**: Set the number of cents per kW to be used to calculate cost of electricity for the day
## Trigger Values Information
- **minSunlightWhenRaining**: Set how low the sunlight percent can get while raining
- **fansOnAboveTemp**: Set what temperature Fans turn on above
- **fansOnBelowTemp**: Set what temperature Fans turn on below
- **airconsOnAboveTemp**: Set what temperature Aircons turn on above
- **lightsOnBelowSunlight**: Below what sunlight percent lights automatically come on
- **updateSoilMoistureRaining**: Set how often in simulated minutes to increase soil moisture if it is raining
- **reduceMoistureAboveSunlight**: Above what sunlight percent soil moisture constantly reduces