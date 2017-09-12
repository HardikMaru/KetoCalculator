package YOUR_PACKAGE_NAME_HERE;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Created by Hardik_Maru on 8/9/17.
 */

public class KetoCalculator {

    //region PRIVATE_DECLARATIONS

    private final int FEMALE = 0;
    private final int MALE = 1;

    private final int WARNING_LOW_BODYFAT = 1;          //  Essential bodyfat is too low
    private final int WARNING_LOW_FATGRAMS = 2;         //  Fat intake (in grams) required to meet desirable level is too low (less then 30g)
    private final int WARNING_LOW_CALORIES = 4;         //  Calories required to meet desirable level are way too low (less than 1200 kcal)
    private final int WARNING_HIGH_CARBS = 8;           //  Net Carbs limit set too high making it impossible to meet desirable targets

    private final int MIN_AGE = 0;                                          // Min Value of Age
    private final int MAX_AGE = 150;                                        // Max Value of Age
    private final float MIN_WEIGHT = 0F;                                    // Min Value of Weight
    private final float MAX_WEIGHT = 350F;                                  // Max Value of Weight
    private final float MIN_HEIGHT = 0F;                                    // Min Value of Height
    private final float MAX_HEIGHT = 250F;                                  // Max Value of Height
    private final float MIN_ACTIVITY_LEVEL = 0F;                            // Min Value of Activity Level
    private final float MAX_ACTIVITY_LEVEL = 1F;                            // Max Value of Activity Level
    private final int MIN_BODYFAT = 0;                                      // Min Value of Body Fat
    private final int MAX_BODYFAT = 100;                                    // Max Value of Body Fat
    private final int MIN_NETCARBS = 0;                                     // Min Value of Net Carb
    private final int MAX_NETCARBS = 1000;                                  // Max Value of Net Carb
    private final float MIN_ACTIVITYPROTEIN = 1.3F;                         // Min Value of Activity Protein
    private final float MAX_ACTIVITYPROTEIN = 2.5F;                         // Max Value of Activity Protein
    private final float MAINTENANCE_CALORIE_INTAKE_MULTIPLIER = 1.1F;       // Multiplier for Maintainance Calorie Intake

    private int gender;
    private int age;
    private float weight;
    private float height;
    private float activityLevel;
    private float bodyfat;
    private float netCarbs;
    private double bmr;
    private double longTermProteinIntake;
    private double maintenanceCalorieIntake;
    private int essentialBodyFat;
    private boolean bodyFatTooLow;

    //endregion



    //region CONSTRUCTORS

    public KetoCalculator(int gender, int age, float weight, float height, float activityLevel, float bodyfat, float netCarbs) {
        this.gender = gender;
        this.age = getValueInRange(age, MIN_AGE, MAX_AGE);
        this.weight = getValueInRange(weight, MIN_WEIGHT, MAX_WEIGHT);
        this.height = getValueInRange(height, MIN_HEIGHT, MAX_HEIGHT);
        this.activityLevel = getValueInRange(activityLevel, MIN_ACTIVITY_LEVEL, MAX_ACTIVITY_LEVEL);
        this.bodyfat = getValueInRange(bodyfat, MIN_BODYFAT, MAX_BODYFAT);
        this.netCarbs = getValueInRange(netCarbs, MIN_NETCARBS, MAX_NETCARBS);
        this.bmr = calculateBMR(gender);
        this.longTermProteinIntake = calculateLongTermProteinIntake();
        this.maintenanceCalorieIntake = calculateMaintenanceCalorieIntake();
        this.essentialBodyFat = getEssentialBodyFat(gender);
        this.bodyFatTooLow = getBodyFatTooLowValue();
    }

    //endregion



    //region PRIVATE_METHODS

    private int getValueInRange(int value, int min, int max) {
        return Math.min(Math.max(min, value), max);
    }

    private float getValueInRange(float value, float min, float max) {
        return Math.min(Math.max(min, value), max);
    }

    //  calculate Basal BMR
    private double calculateBMR(int gender) {
        int lastValue = 0;

        if(gender == FEMALE) {
            lastValue = -161;
        } else if(gender == MALE) {
            lastValue = 5;
        } else {
            Log.e("HarM", "ERROR: This line never gonna execute in KetoCalculator.java file", null);
        }

        // male:    9.99 x weight (kg) + 6.25 x height (cm) - 4.92 x age (y) + 5
        // female:    9.99 x weight (kg) + 6.25 x height (cm) - 4.92 x age (y) - 161
        return ((9.99 * weight) + (6.25 * this.height) - (4.92 * this.age) + lastValue);
    }


    //  Calculate Protein Level for given activity level
    private double calculateLongTermProteinIntake() {
        double activityProteinFactor = (MIN_ACTIVITYPROTEIN + ((MAX_ACTIVITYPROTEIN - MIN_ACTIVITYPROTEIN) * activityLevel));
        double leanMass = (((100 - bodyfat) * weight) / 100);
        return (leanMass * activityProteinFactor);
    }


    //  Calculate calorie intake for maintenance level,     Note: Using polynomial curve fitting function
    private double calculateMaintenanceCalorieIntake() {
        double activityBmrFactor = (1.0999999999999945e+000
                + (-2.3333333333231288e-001 * activityLevel)
                + (3.7999999999943399e+000 * Math.pow(activityLevel, 2))
                + (-5.8666666666573466e+000 * Math.pow(activityLevel, 3))
                + (3.1999999999953190e+000 * Math.pow(activityLevel, 4)));
        return (bmr * activityBmrFactor * MAINTENANCE_CALORIE_INTAKE_MULTIPLIER);
    }


    //  Calculate essential and non-essential bodyfat
    private int getEssentialBodyFat(int gender) {
        if(gender == FEMALE) {
            return (8);                     // Source: wikipedia: 8% - 12%
        } else if(gender == MALE) {
            return (3);                     // Source: wikipedia: 3% - 5%
        } else {
            Log.e("HarM", "ERROR: This line never gonna execute in KetoCalculator.java file", null);
            return (0);
        }
    }


    private boolean getBodyFatTooLowValue() {
        float nonEssentialBodyFat = this.bodyfat - this.essentialBodyFat;
        if (nonEssentialBodyFat < 0) {
//            nonEssentialBodyFat = 0;
            return (true);
        } else {
            return (false);
        }
    }


    // HELPER METHOD
    // Calculate calories based on fat, protein & carbs
    private float calculateCalorieIntakeFromMacronutrients(float fatGrams, float proteinInGrams, float netCarbsInGrams) {
        return ((fatGrams * 9) + (proteinInGrams * 4) + (netCarbsInGrams * 4));
    }


    // HELPER METHOD
    // Calculate fat intake in grams to meet calorie requirements for a given protein and carbs intake
    private float calculateFatIntakeInGrams(double calorieIntake, double proteinInGrams, float netCarbsInGrams) {
        float proteinKCals = (float) (proteinInGrams * 4);
        float carbsKCals = netCarbsInGrams * 4;
        float fatKCals = (float) (calorieIntake - (proteinKCals + carbsKCals));
        float fatGrams = (fatKCals / 9);
        return fatGrams;
    }


    // HELPER METHOD
    // Calculate macronutrient ratios in grams, calories and percentages based on fat, protein & carbs
    private HashMap<String, Float> calculateMacronutrientRatio(double fatGrams, double proteinGrams, float netCarbGrams) {

        float kcalFat = (float) (fatGrams * 9);
        float kcalProtein = (float) (proteinGrams * 4);
        float kcalNetCarbs = netCarbGrams * 4;
        float kcalTotal = kcalNetCarbs + kcalProtein + kcalFat;
        HashMap<String, Float> result = new HashMap<>();

        if (kcalTotal <= 0) {
            return result;
        }

        result.put("energy", roundMacroEnergy(kcalTotal));
        result.put("gramsFat", roundMacroGrams((float) fatGrams));
        result.put("gramsProtein", roundMacroGrams((float) proteinGrams));
        result.put("gramsNetCarbs", roundMacroGrams(netCarbGrams));
        result.put("energyFat", roundMacroEnergy(kcalFat));
        result.put("energyProtein", roundMacroEnergy(kcalProtein));
        result.put("energyNetCarbs", roundMacroEnergy(kcalNetCarbs));
        result.put("percEnergyNetCarbs", roundMacroPerc((100 * kcalNetCarbs) / kcalTotal));
        result.put("percEnergyProtein", roundMacroPerc((100 * kcalProtein) / kcalTotal));
        result.put("percEnergyFat", roundMacroPerc(100 - (result.get("percEnergyNetCarbs") + result.get("percEnergyProtein"))));
        return result;
    }


    private float roundMacroGrams(float floatValue) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return (Float.valueOf(decimalFormat.format(floatValue)));
    }

    private float roundMacroPerc(float floatValue) {
        DecimalFormat decimalFormat = new DecimalFormat("#");
        return (Float.valueOf(decimalFormat.format(floatValue)));
    }

    private float roundMacroEnergy(float floatValue) {
        DecimalFormat decimalFormat = new DecimalFormat("#");
        return (Float.valueOf(decimalFormat.format(floatValue)));
    }



    public HashMap<String, HashMap<String, Float>> calculateCalorieIntake(float adjustment) {

        HashMap<String, HashMap<String, Float>> result = new HashMap<>();
        HashMap<String, Float> warningsAndAdjustment = new HashMap<>();
        warningsAndAdjustment.put("adjustment", adjustment);
        warningsAndAdjustment.put("warnings", 0F);

        double nonEssentialFatMass = ((bodyfat - essentialBodyFat) * this.weight) / 100;
        double minimumFoodIntake = maintenanceCalorieIntake - (69.2F * Math.max(0, nonEssentialFatMass));

        double maxFatInGrams = calculateFatIntakeInGrams(maintenanceCalorieIntake, longTermProteinIntake, netCarbs);
        if (maxFatInGrams < 0) {
            maxFatInGrams = 0;
            int warning = Integer.valueOf(String.valueOf(warningsAndAdjustment.get("warnings")));
            warningsAndAdjustment.put("warnings", (float)(warning | WARNING_HIGH_CARBS));
        }

        float minFatInGrams = calculateFatIntakeInGrams(minimumFoodIntake, longTermProteinIntake, netCarbs);
        if (minFatInGrams < 30) {
            minFatInGrams = 30;
            minimumFoodIntake = calculateCalorieIntakeFromMacronutrients(minFatInGrams, (float) longTermProteinIntake, netCarbs);
        }

        if (bodyFatTooLow) {
            int warning = Integer.valueOf(String.valueOf(warningsAndAdjustment.get("warnings")));
            warningsAndAdjustment.put("warnings", (float)(warning | WARNING_LOW_BODYFAT));
        } else if (minimumFoodIntake >= maintenanceCalorieIntake) {
            int warning = Integer.valueOf(String.valueOf(warningsAndAdjustment.get("warnings")));
            warningsAndAdjustment.put("warnings", (float)(warning | WARNING_HIGH_CARBS));
        }

        float desirableFoodIntake = (float) (maintenanceCalorieIntake + ((adjustment * maintenanceCalorieIntake) / 100));
        float desirableFatInGrams = calculateFatIntakeInGrams(desirableFoodIntake, longTermProteinIntake, netCarbs);
        if (desirableFatInGrams < 0) {
            desirableFatInGrams = 0;
            int warning = Integer.valueOf(String.valueOf(warningsAndAdjustment.get("warnings")));
            warningsAndAdjustment.put("warnings", (float)(warning | WARNING_HIGH_CARBS));
        }
        Log.d("HarM", "calculateCalorieIntake: " + maxFatInGrams);
        result.put("maintenance", calculateMacronutrientRatio(maxFatInGrams, longTermProteinIntake, netCarbs));
        result.put("minimum", calculateMacronutrientRatio(minFatInGrams, longTermProteinIntake, netCarbs));
        result.put("desirable", calculateMacronutrientRatio(desirableFatInGrams, longTermProteinIntake, netCarbs));

        if (result.get("desirable").get("gramsFat") < 30) {
            int warning = Integer.valueOf(String.valueOf(warningsAndAdjustment.get("warnings")));
            warningsAndAdjustment.put("warnings", (float)(warning | WARNING_LOW_FATGRAMS));
        }
        if (desirableFoodIntake < 1200) {
            int warning = Integer.valueOf(String.valueOf(warningsAndAdjustment.get("warnings")));
            warningsAndAdjustment.put("warnings", (float)(warning | WARNING_LOW_CALORIES));
        }
        result.put("warningsAndAdjustment", warningsAndAdjustment);
        return result;
    }

    //endregion

}
