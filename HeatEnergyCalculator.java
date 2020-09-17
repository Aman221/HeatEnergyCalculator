import java.util.Scanner;

public class HeatEnergyCalculator {
    
    public static void main(String[] args) {
        
    Scanner reader = new Scanner(System.in);
    System.out.println("Enter in the mass of the water, in grams.");
    double mass = reader.nextDouble();
    if (mass < 0)
        mass = Math.abs(mass);
        System.out.println("The value given below zero. The mass was set to " + mass + ".");
    System.out.println("Enter the initial temperature of the water, in degrees Celsius.");
    double temp1 = reader.nextDouble();
    if (temp1 < -273.14) {
        temp1 = -273.14;
        System.out.println("The value given was too low. The value was set to -273.14.");
    }
    System.out.println("Enter the final temperature of the water, in degrees Celsius.");
    double temp2 = reader.nextDouble();
    if (temp2 < -273.14) {
        temp2 = -273.14;
        System.out.println("The value given was too low. The value was set to -273.14.");
    }
    
    String phase1 = "Liquid";
    String phase2 = "Liquid";

    if (temp1 > 100)
        phase1 = "Gas";
    else if (temp1 < 0)
        phase1 = "Solid";
    
    if (temp2 > 100)
        phase2 = "Gas";
    else if (temp2 < 0)
        phase2 = "Solid";
    
    System.out.println("The mass is " + mass + "g.");
    System.out.println("The initial temperature is " + temp1 + "C. " + "(" + phase1 + ")");
    System.out.println("The final temperature is " + temp2 + "C. " + "(" + phase2 + ")");

    boolean endothermic = false;
    if (temp2 > temp1)
        endothermic = true;


    double heatEnergy = 0;
    

    if (phase1.equals("Solid")) {
        heatEnergy += tempChangeSolid(mass, temp1, temp2, phase2, endothermic);
    
        if (!phase2.equals("Solid")) {
            heatEnergy += fusion(mass, endothermic);
            heatEnergy += tempChangeLiquid(mass, 0, temp2, phase2, endothermic);
        }

        if (phase2.equals("Gas")) {
            heatEnergy += Vaporization(mass, endothermic);
            heatEnergy += tempChangeGas(mass, 100, temp2, phase2, endothermic);
        }
    
    }

    if (phase1.equals("Liquid")) {
        heatEnergy += tempChangeLiquid(mass, temp1, temp2, phase2, endothermic);
    
        if (phase2.equals("Solid")) {
            heatEnergy += fusion(mass, endothermic);
            heatEnergy += tempChangeSolid(mass, 0, temp2, phase2, endothermic);
        }
    
        if (phase2.equals("Gas")) {
            heatEnergy += Vaporization(mass, endothermic);
            heatEnergy += tempChangeGas(mass, 100, temp2, phase2, endothermic);
        }
    
    }

    if (phase1.equals("Gas")) {
        heatEnergy += tempChangeGas(mass, temp1, temp2, phase2, endothermic);
    
        if (!phase2.equals("Gas")) {
            heatEnergy +=  Vaporization(mass, endothermic);
            heatEnergy += tempChangeLiquid(mass, 100, temp2, phase2, endothermic);
        }
    
        if (phase2.equals("Solid")) {
            heatEnergy += fusion(mass, endothermic);
            heatEnergy += tempChangeSolid(mass, 0, temp2, phase2, endothermic);
        }
    
    }

    System.out.println("Total Heat Energy: " + heatEnergy + "kJ");

    }


    public static double tempChangeSolid(double mass, double temp1, double temp2, String phase2, Boolean endothermic) {
    
    if (!phase2.equals("Solid"))
        temp2 = 0;
    double energyChange = round(mass*0.002108*(temp2 - temp1));
    if (endothermic)
        System.out.println("Heating (Solid): " + energyChange + "kJ.");
    else
        System.out.println("Cooling (Solid): " + energyChange + "kJ.");
    return energyChange;
    
    }

    public static double tempChangeGas(double mass, double temp1, double temp2, String phase2, Boolean endothermic) {
    
    if (!phase2.equals("Gas"))
        temp2 = 100;
    double energyChange = round(mass*0.001996*(temp2 - temp1));
    if (endothermic)
        System.out.println("Heating (Gas): " + energyChange + "kJ.");
    else
        System.out.println("Cooling (Gas): " + energyChange + "kJ.");
    return energyChange;
        
    }

    public static double tempChangeLiquid(double mass, double temp1, double temp2, String phase2, Boolean endothermic) {
    if (phase2.equals("Solid"))
        temp2 = 0;
        if (phase2.equals("Gas"))
        temp2 = 100;
    double energyChange = round(mass*0.004184*(temp2 - temp1));
    if (endothermic)
        System.out.println("Heating (Liquid): " + energyChange + "kJ.");
    else
        System.out.println("Cooling (Liquid): " + energyChange + "kJ.");
    return energyChange;
        
    }

    public static double fusion(double mass,  Boolean endothermic) {
    double energyChange;
    if (endothermic) {
        energyChange = round(0.333*mass);
        System.out.println("Melting: " + energyChange + "kJ.");
    }
    else {
        energyChange = round(-0.333*mass);
        System.out.println("Freezing: " + energyChange + "kJ.");        
    }
    return energyChange;
    }

    public static double Vaporization(double mass,  Boolean endothermic) {
    double energyChange;
    if (endothermic) {
        energyChange = round(2.257*mass);
        System.out.println("Evaporation: " + energyChange + "kJ.");
    }
    else {
        energyChange = round(-2.257*mass);
        System.out.println("Condensation: " + energyChange + "kJ.");        
    }
    return energyChange;
    }

    public static double round(double x) {
    x *= 1000;
    if (x > 0)
        return (int)(x + 0.5)/1000.0;
    else
        return (int)(x - 0.5)/1000.0;
    }
    
}