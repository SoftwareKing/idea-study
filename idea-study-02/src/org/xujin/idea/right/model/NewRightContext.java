package org.xujin.idea.right.model;

public class NewRightContext {

    /**
     * 类名
     */
    private static String className;

    /**
     * 所选择的类的类型
     */
    private static String classType;

    /**
     * 创建java类所在的包
     */
    private static String  selectedPackage;


    public static String getClassType() {
        return classType;
    }

    public static void setClassType(String classType) {
        NewRightContext.classType = classType;
    }

    public static String getClassName() {
        return className;
    }

    public static void setClassName(String className) {
        NewRightContext.className = className;
    }

    public static String getSelectedPackage() {
        return selectedPackage;
    }

    public static void setSelectedPackage(String selectedPackage) {
        NewRightContext.selectedPackage = selectedPackage;
    }

    public static void clearAllSet() {
        classType = null;
        className = null;
        selectedPackage = null;
    }

    public static NewRightModel copyToNewRightModel() {
        NewRightModel newRightModel = new NewRightModel();
        newRightModel.setClassName(className);
        newRightModel.setSelectedPackage(selectedPackage);
        return newRightModel;
    }

}
