# Keto Calculator in Java

Keto Calculator source code in Java for Android Projects. Code written in Java, it is coded in Android Studio 2.3.3 and tested in Android 7.0 (Nougat).

It is very easy to use and providing near to accurate results.

## Example

<b>For Maintaining Weight:</b><br/>
KetoCalculator ketoCalculator = new KetoCalculator(1, 23, 54F, 157F, 1F, 25, 25);<br/>
HashMap<String, HashMap<String, Float>> result = ketoCalculator.calculateCalorieIntake(0);<br/><br/>

<b>For Gaining Weight:</b><br/>
KetoCalculator ketoCalculator = new KetoCalculator(1, 23, 54F, 157F, 1F, 25, 25);<br/>
HashMap<String, HashMap<String, Float>> result = ketoCalculator.calculateCalorieIntake(+10);<br/><br/><br/>

<b>For Lossing Weight:</b><br/>
KetoCalculator ketoCalculator = new KetoCalculator(1, 23, 54F, 157F, 1F, 25, 25);<br/>
HashMap<String, HashMap<String, Float>> result = ketoCalculator.calculateCalorieIntake(-10);<br/><br/><br/>


Here in line " KetoCalculator ketoCalculator = new KetoCalculator(1, 23, 54F, 157F, 1F, 25, 25); ", where constructor is being initialized with default values.<br/>
* First parameter is gender as int, pass "1" is for Male and "0" for Female.<br/>
* Second parameter is age as int.<br/>
* Third parameter is weight as float in KG.<br/>
* Fourth parameter is height as float in CM.<br/>
* Fifth parameter is activity level as float between 0 to 1, pass "0.0" for Sedentary, "0.25" for Lightly Active, "0.5" for Moderately Active, "0.75" for very active, "1" for Athelete/BodyBuilder.<br/>
* Sixth parameter is body fat as float.<br/>
* and Seventh parameter is net carbs as float.

## Output

HashMap containing four HashMap will be retured to result HashMap and will look like below (NOTE: NOT EXACTLY SAME).<br/>

minimum: {
  gramsFat: 64.1,
  energyProtein: 211.0,
  gramsNetCarbs: 25.0,
  energyFat: 576.0,
  percEnergyFat: 65.0,
  gramsProtein: 52.6,
  percEnergyProtein: 24.0,
  percEnergyNetCarbs: 11.0,
  energyNetCarbs: 100.0,
  energy: 887.0,
  
},
desirable: {
  gramsFat: 155.4,
  energyProtein: 211.0,
  gramsNetCarbs: 25.0,
  energyFat: 1399.0,
  percEnergyFat: 82.0,
  gramsProtein: 52.6,
  percEnergyProtein: 12.0,
  percEnergyNetCarbs: 6.0,
  energyNetCarbs: 100.0,
  energy: 1709.0,
  
},
maintenance: {
  gramsFat: 155.4,
  energyProtein: 211.0,
  gramsNetCarbs: 25.0,
  energyFat: 1399.0,
  percEnergyFat: 82.0,
  gramsProtein: 52.6,
  percEnergyProtein: 12.0,
  percEnergyNetCarbs: 6.0,
  energyNetCarbs: 100.0,
  energy: 1709.0,
  
}

where values under "desirable" HashMap is what we should display as a result.

It have all three values Grams, Calories and Percentage for all three factors Net Carbs, Protein and Fat. <br/><br/>
<b>Key name and its value representation</b><br/>
* energy - Total Calories<br/>
* gramsFat - Fat in grams<br/>
* energyFat - Fat in Calories<br/>
* percEnergyFat - Fat in Percentage<br/>
* gramsNetCarbs - Net Carbs in grams<br/>
* energyNetCarbs - Net Carbs in Calories<br/>
* percEnergyNetCarbs - Net Carbs in Percentage<br/>
* gramsProtein - Protein in grams<br/>
* energyProtein - Protein in Calories<br/>
* percEnergyProtein - Protein in Percentage<br/><br/>


<b>I hope this will help you.! Happy Coding...!</b>
