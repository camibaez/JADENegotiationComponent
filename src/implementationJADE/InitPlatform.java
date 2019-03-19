package implementationJADE;

import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.behaviours.Behaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Esta clase contiene la inicializaci�n y el trabajo con la plataforma JADE.
 *
 * @author Ing. Inf. Altern�n Carrasco Bustamante
 * @enterprise Complejo de Investigaciones Tecnol�gicas Integradas - CITI
 * @email acarrasco@udio.cujae.edu.cu
 *
 */
public class InitPlatform {

    private static boolean showGUI;
    private static String mainPort = "";
    private static String inPortJADE;
    private static String outPortJADE;
    private static boolean autocleanupDF;
    private static boolean interPlatformService;
    private static jade.core.Runtime rt;

    private static HashMap<String, AgentContainer> agentContainers;
    private static HashMap<String, StandardAgent> agentsList;

    public static void init(boolean showGUI, String mainPort, boolean autoCleanupDF, boolean interPlatformService) {

        agentContainers = new HashMap<>();
        agentsList = new HashMap<>();

        InitPlatform.showGUI = showGUI;
        InitPlatform.mainPort = mainPort == null || mainPort.isEmpty() ? "1099" : mainPort;
        InitPlatform.autocleanupDF = autoCleanupDF;
        InitPlatform.interPlatformService = interPlatformService;

        executeJADE();
    }

    /**
     * Ejecuta la plataforma JADE con los par�metros que fueron pasados por el
     * m�todo init. Se utiliza internamente y no est� disponible para el
     * desarrollador.
     */
    private static void executeJADE() {
        new Thread() {
            @Override
            public void run() {
                ArrayList<String> arguments = new ArrayList<String>();

                if (showGUI) {
                    arguments.add("-gui");
                }

                arguments.add("-port");
                arguments.add(mainPort);

                if (autocleanupDF) {
                    arguments.add("-jade_domain_df_autocleanup");
                    arguments.add("true");
                }

                if (inPortJADE != null) {
                    arguments.add("-jade_mtp_http_port");
                    arguments.add(inPortJADE);
                    arguments.add("-jade_mtp_http_outPort");
                    arguments.add(outPortJADE);
                }

                if (interPlatformService) {
                    arguments.add("-services");
                    arguments.add("jade.core.mobility.AgentMobilityService;jade.core.migration.InterPlatformMobilityService");
                }

                // MessageTransportProtocol asd = new MessageTransportProtocol();
                // asd.activate(disp)
                // si se usa este parametro, cuando se envie un mensaje hacia esta plataforma
                // hay que poner en receiver.addAddresses esta informacion
                // sino se usa hay que poner la informacion que se coloco para el contenedor
                /*
				 * En cualquier caso que se quiera enviar un mensaje de una plataforma a otra al
				 * mensaje a enviar hay que colocarle un receiver con la direccion MTP del
				 * contenedor donde esta el agente. Pero si este comando -mtp se le instala a la
				 * plataforma, se puede usar esta informacion para la direccion MTP del receiver, en
				 * lugar del usado normalmente (el del contenedor).
                 */
                // arr.add("-mtp");
                // arr.add("jade.mtp.http.MessageTransportProtocol(http://10.9.3.53:5004)");
                // arr.add("-jade_mtp_http_proxyHost");
                // arr.add("10.9.3.53");
                // arr.add("-jade_mtp_http_proxyPort");
                // arr.add("5005");
                String[] args = new String[arguments.size()];

                for (int i = 0; i < args.length; i++) {
                    args[i] = arguments.get(i);
                }

                jade.Boot.main(args);
            }
        }.start();

        // Get a hold on JADE runtime
        rt = jade.core.Runtime.instance();
        // Exit the JVM when there are no more containers around
        rt.setCloseVM(true);

        // Waits for JADE to start
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitAwakeJADE();
    }

    /**
     * Espera a que la plataforma JADE est� operativa.
     */
    private static void waitAwakeJADE() {
        boolean notConnected = true;
        while (notConnected) {
            try {
                new Socket("localhost", Integer.parseInt(mainPort)); // default 1099
                notConnected = false;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                System.err.println("Reconnecting in one second");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * Crea un contenedor en la plataforma. Este m�todo se utiliza cuando se
     * inicializa la plataforma con un puerto diferente al est�ndar 1099, lo
     * que ocasiona que el contenedor "Main-Container" tenga un puerto diferente
     * al 1099, siendo este el par�metro que es necesario pasar en
     * portMainContainer.
     *
     * @param containerName Nombre del contenedor que se desa crear.
     */
    public static void createAgentContainer(String containerName) {
        if (!agentContainers.containsKey(containerName)) {
            Profile profile = new ProfileImpl();
            profile.setParameter(Profile.CONTAINER_NAME, containerName);
            profile.setParameter(Profile.MAIN_PORT, mainPort);

            AgentContainer agentContainer = rt.createAgentContainer(profile);
            agentContainers.put(containerName, agentContainer);
        }
    }

    /**
     * Crea un contenedor en la plataforma.
     *
     * @param containerName Nombre del contenedor que se desa crear.
     * @param localPort Puerto por el cual escucha la entrada de datos.
     */
    public static void createAgentContainer(String containerName, String localPort) {
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.CONTAINER_NAME, containerName);
        profile.setParameter(Profile.MAIN_PORT, mainPort);
        profile.setParameter(Profile.LOCAL_PORT, localPort);

        AgentContainer agentContainer = rt.createAgentContainer(profile);
        agentContainers.put(containerName, agentContainer);
    }

    /**
     * Crea un contenedor en la plataforma. Si se quieren enviar mensajes entre
     * plataformas por puertos espec�ficos hay que usar esta funcion para
     * activar "jade.mtp.http.MessageTransportProtocol. Ambos contenedores con
     * los agentes que se comunicar�n deben tenerlo activado para que
     * funcione.
     *
     * @param containerName Nombre del contenedor que se desa crear.
     * @param portMainContainer Puerto por el cual est� escuchando el
     * contenedor "Main-Container" de la plataforma.
     */
    public static void createAgentContainer_UrlForMTP(String containerName, String urlForMTP) {
        // Create a default profile
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.CONTAINER_NAME, containerName);
        profile.setParameter(Profile.MAIN_PORT, mainPort);
        profile.setParameter(Profile.MTPS, "jade.mtp.http.MessageTransportProtocol(" + urlForMTP + ")");

        AgentContainer agentContainer = rt.createAgentContainer(profile);
        agentContainers.put(containerName, agentContainer);
    }

    public static void createAgentContainer(String containerName, String localPort, String urlForMTP, String outPortMTP) {
        // Create a default profile
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.CONTAINER_NAME, containerName);
        profile.setParameter(Profile.MAIN_PORT, mainPort);
        profile.setParameter(Profile.LOCAL_PORT, localPort);
        profile.setParameter(Profile.MTPS, "jade.mtp.http.MessageTransportProtocol(" + urlForMTP + ")");
        profile.setParameter("jade_mtp_http_outPort", outPortMTP);

        AgentContainer agentContainer = rt.createAgentContainer(profile);
        agentContainers.put(containerName, agentContainer);
    }

    /**
     * Crea un agente dentro de un contenedor previamente creado.
     *
     * @param containerName Contenedor donde se ejecutar� el agente.
     * @param agentName Nombre del agente que se quiere ejecutar.
     * @param agentClass Clase que implementa el agente que se quiere ejecutar.
     * Ej: packageSystem.packageAgents.classMyAgent
     * @param agentArgs Arreglo de tipo Object que se le pasar� al agente como
     * argumentos para su ejecuci�n.
     */
    public static void addAgentToContainer(final String containerName, final String agentName, final String agentClass, final Object[] agentArgs) {
        if (!agentsList.containsKey(agentName)) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        AgentContainer agentContainer = agentContainers.get(containerName);
                        AgentController agentController = agentContainer.createNewAgent(agentName, agentClass, agentArgs);
                        System.out.println("Starting up " + agentName + "...");
                        agentController.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    public static StandardAgent getAgent(String nombre) {
        return agentsList.get(nombre);
    }

    public static HashMap<String, StandardAgent> getAgentsList() {
        return agentsList;
    }

    public static void addBehaviourToAgent(String agentName, Behaviour behaviour) {
        if (agentsList.containsKey(agentName)) {
            agentsList.get(agentName).addBehaviour(behaviour);
        }
    }

    public static void removeBehaviourInAgent(String agentName, String behaviourName) {
        if (agentsList.containsKey(agentName)) {
            agentsList.get(agentName).removeBehaviour(behaviourName);
        }
    }

    public static void putStandardAgent(StandardAgent standardAgent) {
        if (agentsList != null) {
            agentsList.put(standardAgent.getLocalName(), standardAgent);
        }
    }
}
