
package neco.tradeagents.behaviours.registration;

import implementationJADE.WorkDF;
import neco.tradeagents.agents.TradeAgent;
import neco.matchmaker.MatchmakerAgent;
import neco.mobility.behaviours.MigrateHomeHost;
import jade.core.AID;
import jade.core.Location;
import jade.core.behaviours.OneShotBehaviour;

/**
 *
 * @author Camilo Báez Aneiros
 */
public class IdentifyEnviorment extends OneShotBehaviour{

    @Override
    public void action() {
        AID matchmakerAgent = WorkDF.getAIDAgentByType(myAgent,MatchmakerAgent.MATCHMAKER_SERVICE);
        if(matchmakerAgent == null){
            myAgent.addBehaviour(new MigrateHomeHost());
            return;
        }
        
       ((TradeAgent)myAgent).setMatchmaker(matchmakerAgent);
        
        
        myAgent.addBehaviour(new ProposeRegistration());
    }

}
