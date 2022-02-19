package mai.flash.Controllers;

/*
Class used to temp est and get the selected deck name and id
Find a way to remove this please?
 */

public class BadValueStorageClass {

    private static String selectedDeckName;
    private static Long selectedDeckId;

    public static String getSelectedDeckName() {
        return selectedDeckName;
    }

    public static void setSelectedDeckName(String selectedDeckName) {
        BadValueStorageClass.selectedDeckName = selectedDeckName;
    }

    public static Long getSelectedDeckId() {
        return selectedDeckId;
    }

    public static void setSelectedDeckId(Long selectedDeckId) {
        BadValueStorageClass.selectedDeckId = selectedDeckId;
    }

}
