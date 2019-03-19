
package neco.mobility;

import implementationJADE.InitPlatform;
import implementationJADE.JoinPlatform;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import neco.tradeagents.agents.TradeAgent;

/**
 * Este agente es el encargado de atender los pedidos de movilidad interplatforma.
 * Recibe por mensaje un objeto de tipo TradeAgentDescription que deserealiza y usa
 * para crear el agente.
 * @author Camilo Báez Aneiros
 */
public class InterAgentCreator extends Agent{
    public static final String CREATOR_CONTAINER = "CreatorContainer";
    public static final String CREATOR_NAME = "InterMobilityCreator";
    @Override
    protected void setup(){
        super.setup();
        addBehaviour(new CyclicBehaviour(this) {
            
            @Override
            public void action() {
                MessageTemplate performativeMatch = MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE);
                ACLMessage msg = myAgent.receive(performativeMatch);
        
                if (msg == null) {
                    block();
                    return;
                }
               
                try {
                    TradeAgentDescription tad = (TradeAgentDescription) msg.getContentObject();
                    ((InterAgentCreator)myAgent).createTradeAgent(tad);
                } catch (UnreadableException ex) {
                    Logger.getLogger(InterAgentCreator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void createTradeAgent(TradeAgentDescription agentDescription){
        System.err.println("Received Migration Mssg ");
        HashMap<String, Object> arguments = new HashMap<String, Object>();
        
        arguments.put(TradeAgent.NEGOTIATION_PLAN, agentDescription.negotiationPlan);
        arguments.put(TradeAgent.RESOURCE_TYPE, agentDescription.resourceType);
        
        if(agentDescription.homePlatform != null)
            arguments.put(MobileAgent.HOME_PLATFORM, agentDescription.homePlatform);
        /*
        if(agentDescription.tradePlatform != null)
            arguments.put(MobileAgent.TRADE_PLATFORM, agentDescription.tradePlatform);
        */
        arguments.put(MobileAgent.HOME_CONTAINER, agentDescription.homeHost);
        arguments.put(MobileAgent.TRADE_CONTAINER, agentDescription.tradeHost);
        
        InitPlatform.addAgentToContainer(CREATOR_CONTAINER, agentDescription.agentName, agentDescription.agentType, new Object[]{arguments});
        
    }
}
