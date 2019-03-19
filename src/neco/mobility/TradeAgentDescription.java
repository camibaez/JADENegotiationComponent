
package neco.mobility;

import jade.core.Location;
import jade.core.PlatformID;
import java.io.Serializable;
import neco.negotiation_plan.NegotiationPlan;
import neco.tradeagents.agents.TradeAgent;


/**
 * Esta clase describe a un agente negociador y es usada para la movilidad interpltaforma.
 * Este objeto se serailza y se manda al agente InterAgentCreator
 * @author Camilo Báez Aneiros
 */
public class TradeAgentDescription implements Serializable{
    public NegotiationPlan negotiationPlan;
    public String agentType;
    public String resourceType;
    public String agentName;
    
     /**
     * Identificador de la plataforma de origen
     */
    public PlatformID homePlatform;
    /**
     * Identificador de la plataforma de negociacion (destino)
     */
    public PlatformID tradePlatform;
    /**
     * Identificador del contendor de origen
     */
    public Location homeHost;
    /**
     * Identificador del contenedor de negociacion (destino)
     */
    public Location tradeHost;
    


    public TradeAgentDescription(TradeAgent tradeAgent) {
        negotiationPlan = tradeAgent.getNegotiationPlan();
        agentType = tradeAgent.getClass().getName();
        resourceType = tradeAgent.getResourceType();
        agentName = tradeAgent.getAID().getName();
        homePlatform = tradeAgent.getHomePlatform();
        tradePlatform = tradeAgent.getTradePlatform();
        homeHost = tradeAgent.getHomeHost();
        tradeHost = tradeAgent.getTradeHost();  
    }
}
