package com.rapyutarobotics.alica.factories;

import com.rapyutarobotics.alica.ConversionProcess;
import com.rapyutarobotics.alica.Tags;
import de.unikassel.vs.alica.planDesigner.alicamodel.Condition;
import de.unikassel.vs.alica.planDesigner.alicamodel.Variable;
import javafx.util.Pair;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ConditionFactory extends Factory {
    public static void fillConditon(Element conditionNode, Condition condition, ConversionProcess cp) {
        Factory.setAttributes(conditionNode, condition);
        cp.addElement(condition);
        condition.setConditionString(conditionNode.getAttribute(Tags.CONDITIONSTRING));
        condition.setPluginName(conditionNode.getAttribute(Tags.PLUGINNAME));
        NodeList varNodes = conditionNode.getElementsByTagName(Tags.VARIABLES);
        for (int i = 0; i  < varNodes.getLength(); i++) {
            Element varNode = (Element) varNodes.item(i);
            cp.conditionVarReferences.put(condition.getId(), cp.getReferencedId(varNode.getTextContent()));
        }
        NodeList quantifierNodes = conditionNode.getElementsByTagName(Tags.QUANTIFIERS);
        for (int i = 0; i  < quantifierNodes.getLength(); i++) {
            Element quantifierNode = (Element) quantifierNodes.item(i);
            condition.addQuantifier(QuantifierFactory.create(quantifierNode, cp));
        }
    }

    public static void attachReferences(ConversionProcess cp) {
        QuantifierFactory.attachReferences(cp);

        for (Pair<Long, Long> entry: cp.conditionVarReferences.getEntries()) {
            Condition condition = (Condition) cp.getElement(entry.getKey());
            Variable variable = (Variable) cp.getElement(entry.getValue());
            if (variable == null) {
                throw new RuntimeException("[ConditionFactory] Variable with ID " + entry.getValue() + " unknown!");
            }
            condition.addVariable(variable);
        }
        cp.conditionVarReferences.clear();
    }
}
