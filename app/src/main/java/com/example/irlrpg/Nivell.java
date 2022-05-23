package com.example.irlrpg;

public class Nivell {
    public int currentXP = 0;
    public int lastLevel = 0;
    public int nextLevel = 0;
    int xpBetweenLevels = nextLevel - lastLevel;
    int xpSinceLevelUp = currentXP - lastLevel;
    float percentageOfXP = xpSinceLevelUp / (float) xpBetweenLevels;
    //int barWidth = (int) (percentageOfXP * maxWidth);


}
