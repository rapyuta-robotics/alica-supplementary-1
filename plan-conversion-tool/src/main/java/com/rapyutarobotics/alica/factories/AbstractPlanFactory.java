package com.rapyutarobotics.alica.factories;

import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AbstractPlanFactory extends Factory {

    public static void setVariables(Element node, AbstractPlan abstractPlan)
    {
        NodeList listOfVariableNodes = node.getElementsByTagName(VARIABLES);
        for (int i = 0; i < listOfVariableNodes.getLength(); i++) {
            Element variableNode = (Element) listOfVariableNodes.item(i);
            abstractPlan.addVariable(VariableFactory.create(variableNode));
        }
    }
}