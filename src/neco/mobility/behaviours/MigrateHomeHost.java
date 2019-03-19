package neco.mobility.behaviours;

import jade.core.Location;
import neco.mobility.MobileAgent;

/**
 * Behaviour que facilita la tarea de hacer migrar al agente hacia el contenedor
 * de origen. Asigna al valor de su atributo destination el valor que tiene el
 * agente como contenedor de origen.
 *
 * @author Camilo Báez Aneiros
 */
public class MigrateHomeHost extends MoveBehaviour {

    @Override
    public void action() {
        MobileAgent agent = (MobileAgent) myAgent;
        if (agent.getHomePlatform() != null) {
            return;
            
        } else {
            destination = agent.getHomeHost();
        }

        super.action();
    }
}
