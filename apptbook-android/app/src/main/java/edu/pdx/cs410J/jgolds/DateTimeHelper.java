package edu.pdx.cs410J.jgolds;

public class DateTimeHelper {
    public static final String INCORRECT_DATE_FORMATTING = "Incorrect date formatting";
    public static final String INCORRECT_TIME_FORMATTING = "Incorrect time formatting";
    /**
     * Takes in a String that should be beginTime or endTime and makes sure it is
     * the correct format, i.e. 3:33 or 11:45 would both be appropriate
     */
    public boolean parseTimes(String time){
        char []array = new char[time.length()];
        for (int i = 0; i < time.length(); ++i){
            array[i] = time.charAt(i);
        }
        if(time.length() < 4 || time.length() > 5)
            return false;
        if(time.length() == 4){
            for(int i = 0; i < 4; ++i){
                if(i == 0) {
                    if(!checkOneThroughNine(array[i]))
                        return false;
                }
                else if(i == 1 && array[i] != ':')
                    return false;
                else if(i ==2) {
                    if(!checkZeroThroughFive(array[i]))
                        return false;
                }
                else if(i==3) {
                    if(!checkZeroThroughNine(array[i]))
                        return false;
                }
            }
        }
        else{
            for(int i = 0; i < 5; ++i){
                if(i == 0) {
                    if(!checkZeroAndOne(array[i]))
                        return false;
                }
                else if(i == 1) {
                    if(!checkZeroThroughNine(array[i]))
                        return false;
                    if(array[0] == '1') {
                        if(!checkZeroThroughTwo(array[1]))
                            return false;
                    }
                }
                else if(i == 2 && array[i] != ':')
                    return false;
                else if(i == 3) {
                    if(!checkZeroThroughFive(array[i]))
                        return false;
                }
                else if(i == 4) {
                    if(!checkZeroThroughNine(array[i]))
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Takes in a String that should be beginDate or endDate and makes sure it is
     * the correct format, i.e. 12/03/2020 or 05/3/2021 would both be appropriate
     */
    public boolean parseDates(String date) {
        if(date == null)
        {
            return false;
        }
        char[] array = new char[date.length()];
        for (int i = 0; i < date.length(); ++i)
            array[i] = date.charAt(i);
        if (date.length() < 8 || date.length() > 10)
            return false;
        else if (date.length() == 8) {
            if (array[1] != '/' || array[3] != '/')
                return false;
            for (int i = 0; i < 8; ++i) {
                if (i == 1 || i == 3)
                    continue;
                else if(i == 0 || i == 2) {
                    if(!checkOneThroughNine(array[i]))
                        return false;
                }
                else {
                    if(!checkZeroThroughNine(array[i]))
                        return false;
                }
            }
        }
        else if (date.length() == 9){
            if(array[4] != '/')
                return false;
            if(array[1] == '/'){
                for(int i = 0; i < 9; ++i){
                    if(i == 0) {
                        if(!checkOneThroughNine(array[i]))
                            return false;
                    }
                    else if(i == 1 || i == 4)
                        continue;
                    else if(i == 2) {
                        if(!checkZeroThroughThree(array[i]))
                            return false;
                    }
                    else if(i == 3){
                        if(array[2] == '3') {
                            if (array[0] == '4' || array[0] == '6' || array[0] == '9') {
                                if (array[i] != '0')
                                    return false;
                            }
                            else if (array[0] == '2')
                                return false;
                            else {
                                if(!checkZeroAndOne(array[i]))
                                    return false;
                            }
                        }
                    }
                    else {
                        if(!checkZeroThroughNine(array[i]))
                            return false;
                    }
                }
            }
            else if(array[2] == '/'){
                for(int i = 0; i < 9; ++i){
                    if(i == 0) {
                        if(!checkZeroAndOne(array[i]))
                            return false;
                    }
                    else if(i == 1) {
                        if (array[0] == '0') {
                            if(!checkOneThroughNine(array[i]))
                                return false;
                        }
                        else {
                            if(!checkZeroThroughTwo(array[i]))
                                return false;
                        }
                    }
                    else if(i == 2 || i ==4)
                        continue;
                    else if(i == 3){
                        if(!checkOneThroughNine(array[i]))
                            return false;
                    }
                    else {
                        if(!checkZeroThroughNine(array[i]))
                            return false;
                    }
                }
            }
            else
                return false;
        }
        else{
            if(array[2] != '/' || array[5] != '/')
                return false;
            for(int i = 0; i < 10; ++i){
                if(i == 0) {
                    if(!checkZeroAndOne(array[i]))
                        return false;
                }
                else if(i == 1) {
                    if (array[0] == '0') {
                        if(!checkOneThroughNine(array[i]))
                            return false;
                    }
                    else {
                        if(!checkZeroThroughTwo(array[i]))
                            return false;
                    }
                }
                else if(i == 2 || i == 5)
                    continue;
                else if(i == 3) {
                    if(!checkZeroThroughThree(array[i]))
                        return false;
                }
                else if(i == 4){
                    if(array[3] == '3') {
                        if (array[0] == '0' && array[1] == '2') {
                            return false;
                        }
                        else if((array[0] == '0' && (array[1] == '4' || array[1] == '6' || array[1] == '9')) || (array[0] == '1' && array[1] == '1')){
                            if(array[i] != '0')
                                return false;
                        }
                        else {
                            if(!checkZeroAndOne(array[i]))
                                return false;
                        }
                    }
                }
                else {
                    if(!checkZeroThroughNine(array[i]))
                        return false;
                }
            }
            if((array[0] == '0' && array[1] == '0') || array[3] == '0' && array[4] == '0')
                return false;
        }
        return true;
    }

    /**
     * Prints out the constant string indicating incorrect date format and exits
     */
    private static void incorrectDateFormatting(){
        System.err.println(INCORRECT_DATE_FORMATTING);
        System.exit(1);
    }

    /**
     * Prints out the constant string indicating incorrect time format and exits
     */
    private static void incorrectTimeFormatting(){
        System.err.println(INCORRECT_TIME_FORMATTING);
        System.exit(1);
    }

    /**
     * Prints out the error message sent in as an argument and exits program
     */
    private static void errorMessage(String message){
        System.err.println(message);
        System.exit(1);
    }

    /**
     * Checks the character to make sure it is 0 through 5. If it fails, it will print
     * the error message sent in as an argument and exits program
     */
    private static boolean checkZeroThroughFive(char ch){
        if(ch < '0' || ch > '5')
            return false;
        return true;
    }

    /**
     * Checks the character to make sure it is 0 or 1. If it fails, it will print
     * the error message sent in as an argument and exits program
     */
    private static boolean checkZeroAndOne(char ch){
        if(ch < '0' || ch > '1')
            return false;
        return true;
    }

    /**
     * Checks the character to make sure it is 0 through 2. If it fails, it will print
     * the error message sent in as an argument and exits program
     */
    private static boolean checkZeroThroughTwo(char ch){
        if(ch < '0' || ch > '2')
            return false;
        return true;
    }

    /**
     * Checks the character to make sure it is 0 through 3. If it fails, it will print
     * the error message sent in as an argument and exits program
     */
    private static boolean checkZeroThroughThree(char ch){
        if(ch < '0' || ch > '3')
            return false;
        return true;
    }

    /**
     * Checks the character to make sure it is 1 through 9. If it fails, it will print
     * the error message sent in as an argument and exits program
     */
    private static boolean checkOneThroughNine(char ch){
        if(ch < '1' || ch > '9')
            return false;
        return true;
    }

    /**
     * Checks the character to make sure it is 0 through 9. If it fails, it will print
     * the error message sent in as an argument and exits program
     */
    private static boolean checkZeroThroughNine(char ch){
        if(ch < '0' || ch > '9')
            return false;
        return true;
    }

}
