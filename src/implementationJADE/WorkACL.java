package implementationJADE;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.io.Serializable;





/**
 * Esta clase contiene todo el trabajo que se realiza con el uso de los mensajes ACL.
 * 
 * @author Ing. Inf. Alternán Carrasco Bustamante
 * @enterprise Complejo de Investigaciones Tecnológicas Integradas - CITI
 * @email acarrasco@udio.cujae.edu.cu
 * 
 */
public class WorkACL
{
	/**
	 * Envía un mensaje configurado con los parámetros que son dados. Es necesario saber el nombre
	 * del tipo de registro (en la base de datos del Agente DF), que tiene el agente al que se le
	 * quiere enviar el mensaje.
	 * 
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param type
	 *            Nombre del tipo de registro del agente receptor.
	 * @param content
	 *            Contenido del mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage_ByType(Agent sender, String type, String content)
	{
		AID aid_receiver = WorkDF.getAIDAgentByType(sender, type);
		ACLMessage msg = null;
		if (aid_receiver != null)
		{
			msg = createACLMessage(-1, sender, aid_receiver, content);
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados. Es necesario saber el nombre
	 * del tipo de registro (en la base de datos del Agente DF), que tiene el agente al que se le
	 * quiere enviar el mensaje.
	 * 
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param type
	 *            Nombre del tipo de registro del agente receptor.
	 * @param object
	 *            Objeto del mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage_ByType(Agent sender, String type, Serializable object)
	{
		AID aid_receiver = WorkDF.getAIDAgentByType(sender, type);
		ACLMessage msg = null;
		if (aid_receiver != null)
		{
			msg = createACLMessage(-1, sender, aid_receiver, object);
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados. Es necesario saber el nombre
	 * del tipo de registro (en la base de datos del Agente DF), que tiene el agente al que se le
	 * quiere enviar el mensaje.
	 * 
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param language
	 *            Lenguaje del mensaje.
	 * @param type
	 *            Nombre del tipo de registro del agente receptor.
	 * @param object
	 *            Objeto del mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage_ByType(Agent sender, String language, String type, Serializable object)
	{
		AID aid_receiver = WorkDF.getAIDAgentByType(sender, type);
		ACLMessage msg = null;
		if (aid_receiver != null)
		{
			msg = createACLMessage(-1, sender, language, aid_receiver, null, null, object);
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados. Es necesario saber el nombre
	 * del agente (en la base de datos del Agente AMS), a quien se le quiere enviar el mensaje.
	 * 
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receiverName
	 *            El nombre del agente receptor.
	 * @param content
	 *            Contenido del mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage_ByName(Agent sender, String receiverName, String content)
	{
		ACLMessage msg = createACLMessage(-1, sender, receiverName, content);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}
	
	/**
	 * Envía un mensaje configurado con los parámetros que son dados. Es necesario saber el nombre
	 * del agente (en la base de datos del Agente AMS), a quien se le quiere enviar el mensaje.
	 * 
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receiverName
	 *            El nombre del agente receptor.
	 * @param object
	 *            Objeto Serializable que se quiere enviar con el mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage_ByName(Agent sender, String receiverName, Serializable object)
	{
		ACLMessage msg = createACLMessage(-1, sender, receiverName, object);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados. Es necesario saber el nombre
	 * del agente (en la base de datos del Agente AMS), a quien se le quiere enviar el mensaje.
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receiverName
	 *            El nombre del agente receptor.
	 * @param content
	 *            Contenido del mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage_ByName(int performative, Agent sender, String receiverName, String content)
	{
		ACLMessage msg = createACLMessage(performative, sender, receiverName, content);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados. Es necesario saber el nombre
	 * del agente (en la base de datos del Agente AMS), a quien se le quiere enviar el mensaje.
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param language
	 *            Lenguaje del mensaje.
	 * @param receiverName
	 *            El nombre del agente receptor.
	 * @param content
	 *            Contenido del mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage_ByName(int performative, Agent sender, String language, String receiverName, String content)
	{
		AMSAgentDescription[] ad = WorkAMS.get_AgentDescription_ByName(sender, receiverName);
		ACLMessage msg = null;
		if (ad.length > 0)
		{
			msg = createACLMessage(performative, sender, language, ad[0].getName(), null, content, null);
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados. Es necesario saber los nombres
	 * de los agentes (en la base de datos del Agente AMS), a quienes se le quiere enviar el
	 * mensaje. enviar el mensaje.
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param language
	 *            Lenguaje del mensaje.
	 * @param receiversName
	 *            Los nombres de los agentes receptores.
	 * @param content
	 *            Contenido del mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage_ByName(int performative, Agent sender, String language, String[] receiversName, String content)
	{
		ACLMessage msg = null;
		if (receiversName != null)
		{
			AID[] aids = new AID[receiversName.length];
			for (int i = 0; i < receiversName.length; i++)
			{
				AMSAgentDescription[] ad = WorkAMS.get_AgentDescription_ByName(sender, receiversName[i]);
				if (ad.length > 0)
				{
					aids[i] = ad[0].getName();
				}
			}

			if (aids.length > 0)
			{
				msg = createACLMessage(performative, sender, language, null, aids, content, null);
				sender.send(msg);
			}
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados. Es necesario saber los nombres
	 * de los agentes (en la base de datos del Agente AMS), a quienes se le quiere enviar el
	 * mensaje. enviar el mensaje.
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param language
	 *            Lenguaje del mensaje.
	 * @param receiversName
	 *            Los nombres de los agentes receptores.
	 * @param object
	 *            Objeto Serializable que se quiere enviar con el mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage_ByName(int performative, Agent sender, String language, String[] receiversName, Serializable object)
	{
		ACLMessage msg = null;
		if (receiversName != null)
		{
			AID[] aids = new AID[receiversName.length];
			for (int i = 0; i < receiversName.length; i++)
			{
				AMSAgentDescription[] ad = WorkAMS.get_AgentDescription_ByName(sender, receiversName[i]);
				if (ad.length > 0)
				{
					aids[i] = ad[0].getName();
				}
			}

			if (aids.length > 0)
			{
				msg = createACLMessage(performative, sender, language, null, aids, null, object);
				sender.send(msg);
			}
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados. Es necesario saber el nombre
	 * del agente (en la base de datos del Agente AMS), que tiene el agente al que se le quiere
	 * enviar el mensaje.
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param language
	 *            Lenguaje del mensaje.
	 * @param receiverName
	 *            El nombre del agente receptor.
	 * @param object
	 *            Objeto que se quiere enviar con el mensaje. Para revisar este valor, se debe pasar
	 *            null a content.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage_ByName(int performative, Agent sender, String language, String receiverName, Serializable object)
	{
		AMSAgentDescription[] ad = WorkAMS.get_AgentDescription_ByName(sender, receiverName);
		ACLMessage msg = null;
		if (ad.length > 0)
		{
			msg = createACLMessage(performative, sender, language, ad[0].getName(), null, null, object);
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados. Es necesario saber el nombre
	 * del agente (en la base de datos del Agente AMS), que tiene el agente al que se le quiere
	 * enviar el mensaje. Esta función también añade un UserDefinedParameter con el valor pasado en
	 * el parámetro commandDynamicBeh. Se asume que en el parámetro commandDynamicBeh entra un valor
	 * de la clase CommandsBehaviourProtocol. Si commandDynamicBeh == ADD_JAVA_FILE, object
	 * 
	 * @param commandDynamicBeh
	 *            Comando que se desea añadir como un UserDefinedParameter al mensaje ACL que se
	 *            envía. Debe ser un valor de la clase CommandsBehaviourProtocol.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receiverName
	 *            El nombre del agente receptor.
	 * @param object
	 *            Comportamiento que se quiere enviar con el mensaje. Para revisar este valor, se
	 *            debe pasar null a content.
	 * @param arrByteFile
	 *            Arreglo de bytes del fichero .java que corresponde al behaviour pasado por
	 *            parámetro.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessageBehaviour_ByName(String commandDynamicBeh, Agent sender, String receiverName, Behaviour object, byte[] arrByteFile)
	{
		AMSAgentDescription[] ad = WorkAMS.get_AgentDescription_ByName(sender, receiverName);
		ACLMessage msg = null;
		if (ad.length > 0)
		{
			msg = createACLMessage(ACLMessage.REQUEST, sender, null, ad[0].getName(), null, null, object);
			/*msg.setProtocol(CommandsOnlineAgent.COMMANDS_DYNAMIC_BEHAVIOUR);
			msg.addUserDefinedParameter("commandDynamicBeh", commandDynamicBeh);
			if (commandDynamicBeh.equals(CommandsOnlineAgent.ADD_BEHAVIOUR))
			{
				//msg.setByteSequenceContent(arrByteFile);
			}*/

			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados
	 * 
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receiver
	 *            El AID del agente receptor.
	 * @param content
	 *            Contenido del mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage(Agent sender, AID receiver, String content)
	{
		ACLMessage msg = createACLMessage(-1, sender, null, receiver, null, content, null);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje de respuesta (reply) con los parámetros que son dados.
	 * 
	 * @param msgIni
	 *            Mensaje inicial al que se quiere responder.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param content
	 *            Contenido del mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessageReply(ACLMessage msgIni, Agent sender, String content)
	{
		ACLMessage msg = createACLMessageReply(msgIni, -1, sender, null, content, null);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados
	 * 
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receivers
	 *            Arreglo de AID de los agentes receptores.
	 * @param content
	 *            Contenido del mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage(Agent sender, AID[] receivers, String content)
	{
		ACLMessage msg = createACLMessage(-1, sender, null, null, receivers, content, null);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados
	 * 
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receiver
	 *            El AID del agente receptor.
	 * @param object
	 *            Objeto que se quiere enviar con el mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage(Agent sender, AID receiver, Serializable object)
	{
		ACLMessage msg = createACLMessage(-1, sender, null, receiver, null, null, object);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje de respuesta (reply) con los parámetros que son dados.
	 * 
	 * @param msgIni
	 *            Mensaje inicial al que se quiere responder.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param object
	 *            Objeto que se quiere enviar con el mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessageReply(ACLMessage msgIni, Agent sender, String content, Serializable object)
	{
		ACLMessage msg = createACLMessageReply(msgIni, -1, sender, null, null, object);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados
	 * 
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receivers
	 *            Arreglo de AID de los agentes receptores.
	 * @param object
	 *            Objeto que se quiere enviar con el mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage(Agent sender, AID[] receivers, Serializable object)
	{
		ACLMessage msg = createACLMessage(-1, sender, null, null, receivers, null, object);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receiver
	 *            El AID del agente receptor.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage(int performative, Agent sender, AID receiver)
	{
		ACLMessage msg = createACLMessage(performative, sender, null, receiver, null, null, null);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje de respuesta (reply) con los parámetros que son dados.
	 * 
	 * @param msgIni
	 *            Mensaje inicial al que se quiere responder.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param object
	 *            Objeto que se quiere enviar con el mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessageReply(ACLMessage msgIni, int performative, Agent sender)
	{
		ACLMessage msg = createACLMessageReply(msgIni, performative, sender, null, null, null);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receiver
	 *            El AID del agente receptor.
	 * @param content
	 *            Contenido del mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage(int performative, Agent sender, AID receiver, String content)
	{
		ACLMessage msg = createACLMessage(performative, sender, null, receiver, null, content, null);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receivers
	 *            Arreglo de AID de los agentes receptores.
	 * @param content
	 *            Contenido del mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage(int performative, Agent sender, AID[] receivers, String content)
	{
		ACLMessage msg = createACLMessage(performative, sender, null, null, receivers, content, null);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receiver
	 *            El AID del agente receptor.
	 * @param object
	 *            Objeto que se quiere enviar con el mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage(int performative, Agent sender, AID receiver, Serializable object)
	{
		ACLMessage msg = createACLMessage(performative, sender, null, receiver, null, null, object);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receivers
	 *            Arreglo de AID de los agentes receptores.
	 * @param object
	 *            Objeto que se quiere enviar con el mensaje.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage(int performative, Agent sender, AID[] receivers, Serializable object)
	{
		ACLMessage msg = createACLMessage(performative, sender, null, null, receivers, null, object);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados.
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param language
	 *            Lenguaje del mensaje.
	 * @param receiver
	 *            El AID del agente receptor. Si se coloca un valor aquí, no se revisa el arreglo de
	 *            receivers.
	 * @param receivers
	 *            Arreglo de AID de los agentes receptores. Para revisar este valor, se debe pasar
	 *            null a receiver.
	 * @param content
	 *            Contenido del mensaje. Es un String que no puede emparejarse al parámetro object.
	 *            Solo uno de los 2 se puede enviar. Si se coloca un valor aquí, no se revisa
	 *            object.
	 * @param object
	 *            Objeto que se quiere enviar con el mensaje. Para revisar este valor, se debe pasar
	 *            null a content.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage(int performative, Agent sender, String language, AID receiver, AID[] receivers, String content, Serializable object)
	{
		ACLMessage msg = createACLMessage(performative, sender, language, receiver, receivers, content, object);
		if (msg != null)
		{
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados.
	 * 
	 * @param msgIni
	 *            Mensaje inicial al que se quiere responder.
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param language
	 *            Lenguaje del mensaje.
	 * @param sendCopy
	 *            El AID de un agente al que se le quiere enviar una copia del mensaje respuesta.
	 * @param content
	 *            Contenido del mensaje. Es un String que no puede emparejarse al parámetro object.
	 *            Solo uno de los 2 se puede enviar. Si se coloca un valor aquí, no se revisa
	 *            object.
	 * @param object
	 *            Objeto que se quiere enviar con el mensaje. Para revisar este valor, se debe pasar
	 *            null a content.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessageReply(ACLMessage msgIni, int performative, Agent sender, String language, AID sendCopy, String content, Serializable object)
	{
		ACLMessage msg = createACLMessageReply(msgIni, performative, sender, language, content, object);
		if (msg != null)
		{
			msg.addReceiver(sendCopy);
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Envía un mensaje configurado con los parámetros que son dados.
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param language
	 *            Lenguaje del mensaje.
	 * @param receiver
	 *            El AID del agente receptor. Si se coloca un valor aquí, no se revisa el arreglo de
	 *            receivers.
	 * @param receivers
	 *            Arreglo de AID de los agentes receptores. Para revisar este valor, se debe pasar
	 *            null a receiver.
	 * @param content
	 *            Contenido del mensaje. Es un String que no puede emparejarse al parámetro object.
	 *            Solo uno de los 2 se puede enviar. Si se coloca un valor aquí, no se revisa
	 *            object.
	 * @param object
	 *            Objeto que se quiere enviar con el mensaje. Para revisar este valor, se debe pasar
	 *            null a content.
	 * @return Instancia del ACLMessage que se envió.
	 */
	public static ACLMessage sendMessage(String ConversationId, int performative, Agent sender, String language, AID receiver, AID[] receivers, String content, Serializable object)
	{
		ACLMessage msg = createACLMessage(performative, sender, language, receiver, receivers, content, object);
		if (msg != null)
		{
			msg.setConversationId(ConversationId);
			sender.send(msg);
		}
		return msg;
	}

	/**
	 * Devuelve una instancia de ACLMessage configurada con los parámetros que son dados.
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receiverName
	 *            El nombre del agente receptor. Si se coloca un valor aquí, no se revisa el arreglo
	 *            de receivers.
	 * @param content
	 *            Contenido del mensaje.
	 * @return Instancia del ACLMessage creado.
	 */
	public static ACLMessage createACLMessage(int performative, Agent sender, String receiverName, String content)
	{
		AMSAgentDescription[] ad = WorkAMS.get_AgentDescription_ByName(sender, receiverName);

		ACLMessage msg = null;
		if (ad.length > 0)
		{
			msg = createACLMessage(performative, sender, null, ad[0].getName(), null, content, null);
		}
		return msg;
	}

	/**
	 * Devuelve una instancia de ACLMessage configurada con los parámetros que son dados.
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receiver
	 *            El AID del agente receptor. Si se coloca un valor aquí, no se revisa el arreglo de
	 *            receivers.
	 * @param content
	 *            Contenido del mensaje.
	 * @return Instancia del ACLMessage creado.
	 */
	public static ACLMessage createACLMessage(int performative, Agent sender, AID receiver, String content)
	{
		ACLMessage msg = createACLMessage(performative, sender, null, receiver, null, content, null);
		return msg;
	}

	/**
	 * Devuelve una instancia de ACLMessage configurada con los parámetros que son dados.
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receiverName
	 *            El nombre del agente receptor. Si se coloca un valor aquí, no se revisa el arreglo
	 *            de receivers.
	 * @param object
	 *            Objeto que se quiere enviar con el mensaje.
	 * @return Instancia del ACLMessage creado.
	 */
	public static ACLMessage createACLMessage(int performative, Agent sender, String receiverName, Serializable object)
	{
		AMSAgentDescription[] ad = WorkAMS.get_AgentDescription_ByName(sender, receiverName);

		ACLMessage msg = null;
		if (ad.length > 0)
		{
			msg = createACLMessage(performative, sender, null, ad[0].getName(), null, null, object);
		}
		return msg;
	}

	/**
	 * Devuelve una instancia de ACLMessage configurada con los parámetros que son dados.
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param receiver
	 *            El AID del agente receptor. Si se coloca un valor aquí, no se revisa el arreglo de
	 *            receivers.
	 * @param object
	 *            Objeto que se quiere enviar con el mensaje.
	 * @return Instancia del ACLMessage creado.
	 */
	public static ACLMessage createACLMessage(int performative, Agent sender, AID receiver, Serializable object)
	{
		ACLMessage msg = createACLMessage(performative, sender, null, receiver, null, null, object);
		return msg;
	}

	/**
	 * Devuelve una instancia de ACLMessage configurada con los parámetros que son dados.
	 * 
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param language
	 *            Lenguaje del mensaje.
	 * @param receiver
	 *            El AID del agente receptor. Si se coloca un valor aquí, no se revisa el arreglo de
	 *            receivers.
	 * @param receivers
	 *            Arreglo de AID de los agentes receptores. Para revisar este valor, se debe pasar
	 *            null a receiver.
	 * @param content
	 *            Contenido del mensaje. Es un String que no puede emparejarse al parámetro object.
	 *            Solo uno de los 2 se puede enviar. Si se coloca un valor aquí, no se revisa
	 *            object.
	 * @param object
	 *            Objeto que se quiere enviar con el mensaje. Para revisar este valor, se debe pasar
	 *            null a content.
	 * @return Instancia del ACLMessage creado.
	 */
	public static ACLMessage createACLMessage(int performative, Agent sender, String language, AID receiver, AID[] receivers, String content, Serializable object)
	{
		ACLMessage msg = null;
		if (performative == -1)
		{
			msg = new ACLMessage(ACLMessage.UNKNOWN);
		}
		else
		{
			msg = new ACLMessage(performative);
		}
		// poniendole un valor en ConversationId para identificarlo
		msg.setConversationId(String.valueOf(System.currentTimeMillis()));

		msg.setSender(sender.getAID());
		msg.setLanguage(language);

		if (receiver != null)
		{
			msg.addReceiver(receiver);
		}
		else
		{
			if (receivers != null)
			{
				int length = receivers.length;
				if (receivers != null && length > 0)
				{
					for (int i = 0; i < length; i++)
					{
						msg.addReceiver(receivers[i]);
					}
				}
			}
		}

		// solo uno de los 2 parametro puede ser puesto
		// si se llenan los 2, solo se ve el ultimo que se guardo
		if (content != null && content != "")
		{
			msg.setContent(content);
		}
		else
		{
			if (object != null)
			{
				try
				{
					msg.setContentObject(object);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return msg;
	}

	/**
	 * Devuelve una instancia de ACLMessage configurada con los parámetros que son dados. Pero esta
	 * instancia es un mensaje creado como respuesta (reply) de otro.
	 * 
	 * @param msgIni
	 *            Mensaje inicial al que se quiere responder.
	 * @param performative
	 *            Intención del mensaje.
	 * @param sender
	 *            Agente emisor del mensaje.
	 * @param language
	 *            Lenguaje del mensaje.
	 * @param content
	 *            Contenido del mensaje. Es un String que no puede emparejarse al parámetro object.
	 *            Solo uno de los 2 se puede enviar. Si se coloca un valor aquí, no se revisa
	 *            object.
	 * @param object
	 *            Objeto que se quiere enviar con el mensaje. Para revisar este valor, se debe pasar
	 *            null a content.
	 * @return Instancia del ACLMessage creado.
	 */
	public static ACLMessage createACLMessageReply(ACLMessage msgIni, int performative, Agent sender, String language, String content, Serializable object)
	{
		ACLMessage msg = msgIni.createReply();
		if (performative == -1)
		{
			msg.setPerformative(ACLMessage.UNKNOWN);
		}
		else
		{
			msg.setPerformative(performative);
		}

		msg.setSender(sender.getAID());
		msg.setLanguage(language);

		// solo uno de los 2 parámetros puede ser colocado
		// si se llenan los 2, solo se ve el ultimo que se guardo
		if (content != null && content != "")
		{
			msg.setContent(content);
		}
		else
		{
			try
			{
				msg.setContentObject(object);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return msg;
	}
}
