package com.fk.activiti.diagram;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成的图形去掉上面和左边的空白
 * 最终结果大小自适应
 */
public class DiagramGenerator extends DefaultProcessDiagramGenerator {
    private final double paddingLeft = 10d;
    private final double paddingTop = 10d;

    @Override
    protected void prepareBpmnModel(BpmnModel bpmnModel) {
        List<GraphicInfo> allGraphicInfos = new ArrayList<GraphicInfo>();
        if (bpmnModel.getLocationMap() != null) {
            allGraphicInfos.addAll(bpmnModel.getLocationMap().values());
        }
        if (bpmnModel.getLabelLocationMap() != null) {
            allGraphicInfos.addAll(bpmnModel.getLabelLocationMap().values());
        }
        if (bpmnModel.getFlowLocationMap() != null) {
            for (List<GraphicInfo> flowGraphicInfos : bpmnModel.getFlowLocationMap().values()) {
                allGraphicInfos.addAll(flowGraphicInfos);
            }
        }

        if (allGraphicInfos.size() > 0) {
            double lowestX = 0.0;
            double lowestY = 0.0;

            // Collect lowest x and y
            for (int i=0; i<allGraphicInfos.size(); i++) {
                GraphicInfo graphicInfo = allGraphicInfos.get(i);
                double x = graphicInfo.getX();
                double y = graphicInfo.getY();

                if (i == 0) {
                    lowestX = x;
                    lowestY = y;
                }

                if (x < lowestX) {
                    lowestX = x;
                }
                if (y < lowestY) {
                    lowestY = y;
                }
            }

            for (GraphicInfo graphicInfo : allGraphicInfos) {
                graphicInfo.setX(graphicInfo.getX() - lowestX + paddingLeft);
                graphicInfo.setY(graphicInfo.getY() - lowestY + paddingTop);
            }
        }
    }
}
