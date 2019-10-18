package org.xujin.idea.right.model;

public class NewRightModel {

    /**
     * 类名
     */
    private  String className;

    /**
     * 所选择的类的类型
     */
    private  String classType;

    /**
     * 创建java类所在的包
     */
    private  String  selectedPackage;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getSelectedPackage() {
        return selectedPackage;
    }

    public void setSelectedPackage(String selectedPackage) {
        this.selectedPackage = selectedPackage;
    }
}
