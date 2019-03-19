
package neco.onto.actions;

import jade.content.AgentAction;
import jade.core.AID;

/**
 * Accion de agente que representa la notificacion por parte del Matchmaker hacia
 * un proveedor de que se encontro una coincidencia.
 * @author Camilo Báez Aneiros
 */
public class MatchNotifyAction implements AgentAction{
    protected AID matchingAgent;

    public AID getMatchingAgent() {
        return matchingAgent;
    }

    public void setMatchingAgent(AID matchingAgent) {
        this.matchingAgent = matchingAgent;
    }
}
