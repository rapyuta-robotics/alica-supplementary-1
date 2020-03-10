package com.rapyutarobotics.alica.factories;

import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class StateFactory extends Factory {

    public static State create(Element stateNode) {
        State state = new State();
        Factory.setAttributes(stateNode, state);
        conversionTool.planElements.put(state.getId(), state);
        NodeList inTransitionNodes = stateNode.getElementsByTagName(INTRANSITIONS);
        for (int i = 0; i  < inTransitionNodes.getLength(); i++) {
            Element inTransitionNode = (Element) inTransitionNodes.item(i);
            Factory.stateInTransitionReferences.put(state.getId(), Factory.getReferencedId(inTransitionNode.getTextContent()));
        }
        NodeList outTransitionNodes = stateNode.getElementsByTagName(OUTTRANSITIONS);
        for (int i = 0; i  < outTransitionNodes.getLength(); i++) {
            Element outTransitionNode = (Element) outTransitionNodes.item(i);
            Factory.stateOutTransitionReferences.put(state.getId(), Factory.getReferencedId(outTransitionNode.getTextContent()));
        }
        NodeList abstractPlanNodes = stateNode.getElementsByTagName(PLANS);
        for (int i = 0; i  < abstractPlanNodes.getLength(); i++) {
            Element abstractPlanNode = (Element) abstractPlanNodes.item(i);
            Factory.stateAbstractPlanReferences.put(state.getId(), Factory.getReferencedId(abstractPlanNode.getTextContent()));
        }
        // TODO VariableBindings
//        state.addVariableBinding();

        return state;
    }
}
