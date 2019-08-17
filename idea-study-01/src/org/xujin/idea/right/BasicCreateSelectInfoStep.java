package org.xujin.idea.right;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.xujin.idea.right.model.SelectedTypeModel;

import javax.swing.*;
import java.util.List;

/**
 * @author xujin
 */
public class BasicCreateSelectInfoStep extends ModuleWizardStep {

    private JPanel myMainPanel;

    private JPanel myPackagePanel;

    /**
     * 类名
     */
    private JTextField className;

    /**
     * 下拉列表框
     */
    private JComboBox<SelectedTypeModel> kind;




    private Project myProject;
    private Module myModule;




    /**
     * 创建Mapper选择数据对象相关
     */
    private JLabel dataObjectTip;
    private JTextField dataObjectValue;
    private JButton dataObjectChooseButton;

    /**
     * @param project
     * @param module
     */
    public BasicCreateSelectInfoStep(Project project, Module module) {
        myProject = project;
        myModule = module;
        initComBox();
    }



    private void initComBox() {

        List<SelectedTypeModel> list = BasicComBoxRenderer.getSelectedList();
        for (SelectedTypeModel seleType : list) {
            kind.addItem(seleType);
        }
        kind.setRenderer(new BasicComBoxRenderer());
        //Trinity trinity=new Trinity<>("Class", IconUtil.HALO , JavaTemplateUtil.INTERNAL_CLASS_TEMPLATE_NAME);
        //comboBox1.addItem(trinity);


    }

    @Override
    public JComponent getComponent() {
        return myMainPanel;
    }

    @Override
    public boolean validate() throws ConfigurationException {

        return super.validate();
    }


    @Override
    public void updateDataModel() {

    }

}
