package com.rapyutarobotics.alica.factories;

import com.rapyutarobotics.alica.ConversionProcess;
import com.rapyutarobotics.alica.Tags;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.alicamodel.Quantifier;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import javafx.util.Pair;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class QuantifierFactory extends Factory {

    public static Quantifier create (Element quantifierNode, ConversionProcess cp) {
        Quantifier quantifier = new Quantifier();
        String quantifierType = quantifierNode.getAttribute(Tags.XSITYPE);
        if (quantifierType.equals(Tags.ALLAGENTSTAG)) {
            quantifier.setQuantifierType(Types.QUANTIFIER_FORALL);
        } else {
            throw new RuntimeException("[QuantifierFactory] Unknown quantifier type: '" + quantifierType +"'");
        }
        Factory.setAttributes(quantifierNode, quantifier);
        cp.addElement(quantifier);

        cp.quantifierScopeReferences.put (quantifier.getId(), cp.getReferencedId(quantifierNode.getAttribute(Tags.SCOPE)));

        NodeList sortNodeList = quantifierNode.getElementsByTagName(Tags.SORTS);
        for (int i = 0; i < sortNodeList.getLength(); i++) {
            Element sortNode = (Element) sortNodeList.item(i);
            quantifier.addSort(sortNode.getTextContent());
        }

        return quantifier;
    }

    public static void attachReferences(ConversionProcess cp) {
        for (Pair<Long, Long> entry : cp.quantifierScopeReferences.getEntries()) {
            Quantifier quantifier = (Quantifier) cp.getElement(entry.getKey());
            PlanElement planElement = cp.getElement(entry.getValue());
            if (planElement == null) {
                throw new RuntimeException("[QuantifierFactory] Scope with ID " + entry.getValue() + " unknown!");
            }
            quantifier.setScope(planElement);
        }
        cp.quantifierScopeReferences.clear();
    }
}
