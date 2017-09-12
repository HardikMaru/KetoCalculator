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
