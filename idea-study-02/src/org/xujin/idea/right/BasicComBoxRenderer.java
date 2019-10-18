package org.xujin.idea.right;


import org.xujin.idea.right.model.SelectedTypeModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 下拉列表渲染
 */
public class BasicComBoxRenderer extends DefaultListCellRenderer {
    private Map<String, ImageIcon> iconMap = new HashMap<>();
    private Color background = new Color(0, 100, 255, 15);
    private Color defaultBackground = (Color) UIManager.get("List.background");

    public BasicComBoxRenderer() {
        iconMap.put(HaloConstant.COMBOX_CONTROLLER,new ImageIcon(getClass().getResource("/icons/right/controller.png")));
        iconMap.put(HaloConstant.COMBOX_MAPPER,new ImageIcon(getClass().getResource("/icons/right/mapper.png")));

    }

    public static List<SelectedTypeModel> getSelectedList(){
        List<SelectedTypeModel> list=new ArrayList<SelectedTypeModel>();
        SelectedTypeModel blank=new SelectedTypeModel();
        blank.setName("");
        blank.setValue(HaloConstant.COMBOX_BLANK);
        list.add(blank);

        SelectedTypeModel controller=new SelectedTypeModel();
        controller.setName("Controller");
        controller.setValue(HaloConstant.COMBOX_CONTROLLER);
        list.add(controller);

        SelectedTypeModel mapper=new SelectedTypeModel();
        mapper.setName("Mapper");
        mapper.setValue(HaloConstant.COMBOX_MAPPER);
        list.add(mapper);



        return list;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        SelectedTypeModel emp = (SelectedTypeModel) value;
        this.setText(emp.getName());
        this.setIcon(iconMap.get(emp.getValue()));
        if (!isSelected) {
            this.setBackground(index % 2 == 0 ? background : defaultBackground);
        }
        return this;
    }
}