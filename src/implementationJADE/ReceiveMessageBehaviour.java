package implementationJADE;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Esta clase implementa un CyclicBehaviour, para obtener cíclicamente los mensajes ACL que le
 * llegan al agente al que pertenece. Brinda la posibilidad de extender el método "processMessage",
 * que se ejecuta cuando se obtiene un mensaje.
 * 
 * @author Ing. Inf. Alternán Carrasco Bustamante
 * @enterprise Complejo de Investigaciones Tecnológicas Integradas - CITI
 * @email acarrasco@udio.cujae.edu.cu
 * 
 */
public abstract class ReceiveMessageBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = 1L;
	private MessageTemplate mt;
        
        public ReceiveMessageBehaviour(){
            this(null);
        }
        
        
	public ReceiveMessageBehaviour(MessageTemplate pMessageTemplate)
	{
		mt = pMessageTemplate;
		setBehaviourName(this.getClass().getSimpleName());
	}

	@Override
	public void action()
	{
		ACLMessage msg;
		if (mt == null)
		{
			msg = myAgent.receive();
		}
		else
		{
			msg = myAgent.receive(mt);
		}

		if (msg != null)
		{
			processMessage(msg);
		}
		else
		{
			block();
		}
	}

	/**
	 * Cuando un mensaje es recibido por el agente, se llama a esta función para procesarlo.
	 * 
	 * @param msg
	 *            Mensaje que fue recibido.
	 */
	public abstract void processMessage(ACLMessage msg);
}