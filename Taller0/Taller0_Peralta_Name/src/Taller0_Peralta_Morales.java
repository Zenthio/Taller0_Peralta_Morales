import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Taller0_Peralta_Morales {
    
    /** 
     * @version 1.0, 01/10/21
     * @author Nicolás Peralta | Matias Morales 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String[] nombres = new String [1500];
        String[] apellidos = new String[1500];
        String[] ruts = new String[1500];
        String[] rutsFormateados = new String[1500];
        String[] contraseñas = new String[1500];
        double[] saldos = new double [1500];   
        String[] estados = new String[1500];
        String[] peliculas = new String[6];
        String[] peliculasFormateadas = new String[6];
        String[] tipoPeliculas = new String[6];
        double[] recaudaciones = new double[6];
        double[] recaudacionesDiarias = new double[6];
        double recaudacionM = 0;
        double recaudacionT = 0;
        String[] funciones = new String[6];
        String[] salas = {"1M","1T","2M","2T","3M","3T"};
        String[][][] matrizSalas = new String[salas.length][10][30];
        String[][][] entradasCliente = new String[1500][6][6];
        String[] filasAsientos = {"A","B","C","D","E","F","G","H","I","J"};
        String[] columnasAsientos = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16",
        "17","18","19","20","21","22","23","24","25","26","27","28","29","30"};
        rellenarMatrices(matrizSalas, salas,filasAsientos,columnasAsientos);
        int cantClientes = lecturaClientes(nombres, apellidos, ruts, rutsFormateados, contraseñas, saldos);
        lecturaStatus(estados);
        int cantPeliculas = lecturaPeliculas(peliculas, tipoPeliculas, recaudaciones, funciones, peliculasFormateadas);
        formatearMatriz(cantClientes, cantPeliculas, salas, entradasCliente);
        inicio(nombres, apellidos, ruts, rutsFormateados, contraseñas, saldos, estados, peliculas, tipoPeliculas, recaudaciones,
        recaudacionesDiarias, recaudacionM, recaudacionT, funciones, salas, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, cantClientes, cantPeliculas, scanner, peliculasFormateadas);
        scanner.close();
    }

    
    /** 
     * The clientes.txt archive is read, saving all the needing data.
     * @param nombres List with the customers's names
     * @param apellidos List with the customer's surname
     * @param ruts List with the customers's ruts
     * @param rutsFormateados List with the customers's ruts without dots and hyphens
     * @param contraseñas List with the customers's passwords
     * @param saldos List with the customers's balances
     * @return int
     * @throws IOException
     */
    public static int lecturaClientes(String[] nombres, String[] apellidos, String[] ruts,
    String[] rutsFormateados, String[] contraseñas, double[] saldos) throws IOException 
    {
        Scanner arch = new Scanner(new File("clientes.txt"));
        int repeat = 0;
        while (arch.hasNextLine()){
            String line = arch.nextLine();
            String[] datos = line.split(",");
            String nombre = datos[0];
            String apellido = datos[1];
            String rut = datos[2];
            rut = rut.toLowerCase();
            String rutFormateado = formatearRut(rut);
            String contraseña = datos[3];
            double saldo = Integer.parseInt(datos[4]);
            nombres[repeat] = nombre;
            apellidos[repeat] = apellido;
            ruts[repeat] = rut;
            rutsFormateados[repeat] = rutFormateado;
            contraseñas[repeat] = contraseña;
            saldos[repeat] = saldo;
            repeat++;
        }
        arch.close();
        return repeat;
        
    }

    
    /** 
     * The status.txt archive is read, saving all the needed data
     * @param estados
     * @throws IOException Si es que el archivo status.txt no se encuentra
     */
    public static void lecturaStatus(String[] estados) throws IOException
    {
        Scanner arch = new Scanner(new File("status.txt"));
        int repeat = 0;
        while (arch.hasNextLine()) {
            String line = arch.nextLine();
            String[] datos = line.split(",");
            String estado = datos[1];
            estados[repeat] = estado;
            repeat++;
        }
        arch.close();
    }

    
    /** 
     * The peliculas.txt archive is read, saving all the needed data.
     * @param peliculas List with each movies's name
     * @param tipoPeliculas List with each movies's type
     * @param recaudaciones List with each movies's total income 
     * @param funciones List with each movies's show
     * @param peliculasFormateadas List of movie names only
     * @return int cantPeliculas, the amount of movies that are in the system
     * @throws IOException
     */
    public static int lecturaPeliculas(String[] peliculas, String[] tipoPeliculas, double[] recaudaciones, String[] funciones, String[] peliculasFormateadas) throws IOException 
    {   
        Scanner arch = new Scanner(new File("peliculas.txt"));
        int repeat = 0;
        while (arch.hasNextLine())
        {
            String line = arch.nextLine();
            String[] datos = line.split(",");
            String pelicula = datos[0];
            String peliculaFormat = formatearRut(pelicula);
            String tipoPelicula = datos[1];
            double recaudacion = Integer.parseInt(datos[2]);
            peliculas[repeat] = pelicula;
            peliculasFormateadas[repeat] = peliculaFormat;
            tipoPeliculas[repeat] = tipoPelicula;
            recaudaciones[repeat] = recaudacion;
            for (int i = 3; i < datos.length; i+=2)
            {
                String sala = datos[i];
                String horario = datos[i+1];
                if (datos.length == 5){
                    funciones[repeat] = sala+horario;
                } else {
                    if(i == 3){
                        funciones[repeat] = sala+horario+"-";
                    } else {
                    if (i != datos.length-2){
                        funciones[repeat] += sala+horario+"-";
                    } else {
                        funciones[repeat] += sala+horario;
                    }
                
                }
            }
        }       
        repeat++;
        }
        arch.close();
        return repeat;
    }
    
    
    /** 
     * Registers a customer's rut and general info if needed.
     * @param nombres List with the customers's names
     * @param apellidos List with the customer's surname
     * @param ruts List with the customers's ruts
     * @param rutsFormateados List with the customers's ruts without dots and hyphens
     * @param contraseñas List with the customers's passwords
     * @param saldos List with the customers's balances
     * @param estados List with the customers's movility pass state
     * @param peliculas List with each movies's name
     * @param tipoPeliculas List with each movies's type
     * @param recaudaciones List with each movies's total income 
     * @param recaudacionesDiarias List with each movies's daily income
     * @param recaudacionM Contains the income obtained during the morning
     * @param recaudacionT Contains the income obtained during the afternoon
     * @param funciones List with each movies's show
     * @param salas List with the movie theaters
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     * @param filasAsientos List that contains the theater's letter of row
     * @param columnasAsientos List that contains the theater's number of column
     * @param cantClientes The quantity of customers in the system
     * @param cantPeliculas The quantity of movies in the system
     * @param scanner Used to save information in the system
     * @param peliculasFormateadas List of movie names only
     * @throws IOException General exception
     */
    public static void registrarRut(String[] nombres, String[] apellidos, String[] ruts, String[] rutsFormateados, String[] contraseñas,
    double[] saldos, String[] estados, String[] peliculas, String[] tipoPeliculas, double[] recaudaciones, double[] recaudacionesDiarias, 
    double recaudacionM, double recaudacionT, String[] funciones, String[] salas, String[][][] matrizSalas, String[][][] entradasCliente,
    String[] filasAsientos, String[] columnasAsientos, int cantClientes, int cantPeliculas, Scanner scanner, String[] peliculasFormateadas) throws IOException{
        System.out.println("<================================> REGISTRO DE RUT <================================>\n");
        while (true){
        System.out.print("Ingrese rut a registrar: ");
        String rutRegistrar = scanner.nextLine();
        String rutRegistrarFormateado = formatearRut(rutRegistrar);
        int posRutRegistrar = indexString(rutsFormateados, rutRegistrarFormateado, cantClientes);
        if (posRutRegistrar == -1){
            System.out.print("Ingrese contraseña a registrar: ");
            String contraseñaRegistrar = scanner.nextLine();
            rutsFormateados[cantClientes] = rutRegistrarFormateado;
            contraseñas[cantClientes] = contraseñaRegistrar;
            String rutFormato = formatear(rutRegistrarFormateado);
            ruts[cantClientes] = rutFormato;
            System.out.print("Ingrese su nombre: ");
            String nombre = scanner.nextLine();
            nombres[cantClientes] = nombre;
            System.out.print("Ingrese su apellido: ");
            String apellido = scanner.nextLine();
            apellidos[cantClientes] = apellido;
            System.out.print("Ingrese su saldo: ");
            double saldo = Double.parseDouble(scanner.nextLine());
            saldos[cantClientes] = saldo;
            System.out.print("Ingrese estado de pase de movilidad (habilitado o no habilitado): ");
            String estado = scanner.nextLine();
            estado = estado.toUpperCase();
            estados[cantClientes] = estado;
            System.out.println("Se le llevará al inicio de sesión.");
            cantClientes++;
            formatearMatriz(cantClientes, cantPeliculas, salas, entradasCliente);
            break;
        }  else {
            System.out.println("Rut ya registrado.");
        }

    }
    System.out.println("<============================================================================>\n");
    inicio(nombres, apellidos, ruts, rutsFormateados, contraseñas, saldos, estados, peliculas, tipoPeliculas, recaudaciones,
    recaudacionesDiarias, recaudacionM, recaudacionT, funciones, salas, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, cantClientes, cantPeliculas, scanner, peliculasFormateadas);  
    }

    
    /** The login process is done here, if the "customer" types admin twice, he will log in as an admin.
     * If he uses his normal data (rut and password), he will log in as a normal customer
     * @param nombres List with the customers's names
     * @param apellidos List with the customer's surname
     * @param ruts List with the customers's ruts
     * @param rutsFormateados List with the customers's ruts without dots and hyphens
     * @param contraseñas List with the customers's passwords
     * @param saldos List with the customers's balances
     * @param estados List with the customers's movility pass state
     * @param peliculas List with each movies's name
     * @param tipoPeliculas List with each movies's type
     * @param recaudaciones List with each movies's total income 
     * @param recaudacionesDiarias List with each movies's daily income
     * @param recaudacionM Contains the income obtained during the morning
     * @param recaudacionT Contains the income obtained during the afternoon
     * @param funciones List with each movies's show
     * @param salas List with the movie theaters
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     * @param filasAsientos List that contains the theater's letter of row
     * @param columnasAsientos List that contains the theater's number of column
     * @param cantClientes The quantity of customers in the system
     * @param cantPeliculas The quantity of movies in the system
     * @param scanner Used to save information in the system
     * @param peliculasFormateadas List of movie names only
     * @return int
     * @throws IOException
     */
    public static int login(String[] nombres, String[] apellidos, String[] ruts, String[] rutsFormateados, String[] contraseñas,
    double[] saldos, String[] estados, String[] peliculas, String[] tipoPeliculas, double[] recaudaciones, double[] recaudacionesDiarias, 
    double recaudacionM, double recaudacionT, String[] funciones, String[] salas, String[][][] matrizSalas, String[][][] entradasCliente,
    String[] filasAsientos, String[] columnasAsientos, int cantClientes, int cantPeliculas, Scanner scanner, String[] peliculasFormateadas) throws IOException{
        System.out.println("<================================> LOGIN <================================>\n");
        while(true){
        System.out.print("Ingrese rut: ");
        String rut = scanner.nextLine();
        String rutFormateado = formatearRut(rut);
        if (rutFormateado.equals("admin")){
            System.out.print("Ingrese contraseña: ");
            String contraseña = scanner.nextLine();
            contraseña = contraseña.toLowerCase();
            if (contraseña.equals("admin")){
                return -2;
                
            }
        }
        int posRut = indexString(rutsFormateados, rutFormateado, cantClientes);
        if (posRut == -1){
            System.out.println("El rut ingresado no fue encontrado, desea registrar?\n[1] Si \n[2] No");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine();
            if (opcion.equals("1"));{
                System.out.println("<============================================================================>\n");
                registrarRut(nombres, apellidos, ruts, rutsFormateados, contraseñas, saldos, estados, peliculas, tipoPeliculas, recaudaciones,
                recaudacionesDiarias, recaudacionM, recaudacionT, funciones, salas, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, cantClientes, cantPeliculas, scanner, peliculasFormateadas);
            }

            } else {
                System.out.print("Ingrese contraseña: ");
                String contraseña = scanner.nextLine();
                int pContraseña = indexString(contraseñas, contraseña, cantClientes);
                if (pContraseña != -1){
                System.out.println("<============================================================================>\n");
                return posRut;
                } else {
                    System.out.println("Contraseña incorrecta.");
                }
            }
        }
    }

    
    /** The compiling begins here, the "customer" is asked about what he wants to do
     * Log in, register or log out. Depending on the situation, the customer will return here.
     * @param nombres List with the customers's names
     * @param apellidos List with the customer's surname
     * @param ruts List with the customers's ruts
     * @param rutsFormateados List with the customers's ruts without dots and hyphens
     * @param contraseñas List with the customers's passwords
     * @param saldos List with the customers's balances
     * @param estados List with the customers's movility pass state
     * @param peliculas List with each movies's name
     * @param tipoPeliculas List with each movies's type
     * @param recaudaciones List with each movies's total income 
     * @param recaudacionesDiarias List with each movies's daily income
     * @param recaudacionM Contains the income obtained during the morning
     * @param recaudacionT Contains the income obtained during the afternoon
     * @param funciones List with each movies's show
     * @param salas List with the movie theaters
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     * @param filasAsientos List that contains the theater's letter of row
     * @param columnasAsientos List that contains the theater's number of column
     * @param cantClientes The quantity of customers in the system
     * @param cantPeliculas The quantity of movies in the system
     * @param scanner Used to save information in the system
     * @param peliculasFormateadas List of movie names only
     * @throws IOException
     */
    public static void inicio(String[] nombres, String[] apellidos, String[] ruts, String[] rutsFormateados, String[] contraseñas,
    double[] saldos, String[] estados, String[] peliculas, String[] tipoPeliculas, double[] recaudaciones, double[] recaudacionesDiarias, 
    double recaudacionM, double recaudacionT, String[] funciones, String[] salas, String[][][] matrizSalas, String[][][] entradasCliente,
    String[] filasAsientos, String[] columnasAsientos, int cantClientes, int cantPeliculas, Scanner scanner, String[] peliculasFormateadas) throws IOException{
        System.out.println("<================================> SISTEMA DE CINE CUEVANA <================================>\n");
        System.out.println("[1] Iniciar Sesión\n[2] Registrar\n[3] Cerrar Sistema");
        System.out.print("Opcion: ");
        String opcion = scanner.nextLine();
        while (true){
        if (opcion.equals("1")){
            System.out.println("<============================================================================>\n");
            Sistema(nombres, apellidos, ruts, rutsFormateados, contraseñas, saldos, estados, peliculas, tipoPeliculas, recaudaciones,
            recaudacionesDiarias, recaudacionM, recaudacionT, funciones, salas, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, cantClientes, cantPeliculas, scanner, peliculasFormateadas);
        } else {
            if (opcion.equals("2")){
                System.out.println("<============================================================================>\n");
                registrarRut(nombres, apellidos, ruts, rutsFormateados, contraseñas, saldos, estados, peliculas, tipoPeliculas, recaudaciones,
                recaudacionesDiarias, recaudacionM, recaudacionT, funciones, salas, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, cantClientes, cantPeliculas, scanner, peliculasFormateadas);
                } else {
                    if (opcion.equals("3")){
                        break;
                    } else {
                        System.out.println("Opción digitada incorrectamente.\n");
                        System.out.println("[1] Iniciar Sesión\n[2] Registrar\n[3] Cerrar Sistema");
                        System.out.print("Opcion: ");
                        opcion = scanner.nextLine();                                     
                    }
                }
            }
        }
        System.out.println("<============================================================================>\n");
        cerrarSistema(nombres, apellidos, ruts, contraseñas, estados, saldos, peliculas, tipoPeliculas, recaudaciones, funciones, cantPeliculas, cantClientes);
    }

    
    /** Here is where the compiling ends, overwriting the original archives.
     * @param nombres List with the customers's names
     * @param apellidos List with the customer's surname
     * @param ruts List with the customers's ruts
     * @param contraseñas List with the customers's passwords
     * @param estados List with the customers's movility pass state
     * @param saldos List with the customers's balances
     * @param peliculas List with each movies's name
     * @param tipoPeliculas List with each movies's type
     * @param recaudaciones List with each movies's total income 
     * @param funciones List with each movies's show
     * @param cantPeliculas The quantity of movies in the system
     * @param cantClientes The quantity of customers in the system
     * @throws IOException
     */
    public static void cerrarSistema(String[] nombres, String[] apellidos, String[] ruts, String[] contraseñas, String[] estados, double[] saldos, 
    String[] peliculas, String[] tipoPeliculas, double[] recaudaciones, String[] funciones, int cantPeliculas, int cantClientes) throws IOException {  
        System.out.println("Cerrando sistema, que tenga un buen día.");
        BufferedWriter archClientes = new BufferedWriter(new FileWriter("clientes.txt"));
        for (int i = 0; i < cantClientes; i++){
            if (i == cantClientes-1){
                archClientes.write(nombres[i]+","+apellidos[i]+","+ruts[i]+","+contraseñas[i]+","+(int)saldos[i]);
            } else {
            archClientes.write(nombres[i]+","+apellidos[i]+","+ruts[i]+","+contraseñas[i]+","+(int)saldos[i]+"\n");
            }
        }
        BufferedWriter archPeliculas = new BufferedWriter(new FileWriter("peliculas.txt"));
        for (int i = 0; i < cantPeliculas; i++){
            String[] entradas = funciones[i].split("-");
            if (i == cantPeliculas-1){
                archPeliculas.write(peliculas[i]+","+tipoPeliculas[i]+","+(int)recaudaciones[i]+",");     
                for (int j = 0; j < entradas.length; j++) {
                    String[] partesEntradas = entradas[j].split("");
                    if (j != entradas.length - 1){
                        archPeliculas.write(partesEntradas[0]+","+partesEntradas[1]+",");
                    } else {
                        archPeliculas.write(partesEntradas[0]+","+partesEntradas[1]);
                    }                                
                }            
            } else {
                archPeliculas.write(peliculas[i]+","+tipoPeliculas[i]+","+(int)recaudaciones[i]+",");     
                for (int j = 0; j < entradas.length; j++) {
                    String[] partesEntradas = entradas[j].split("");
                    if (j != entradas.length - 1){
                        archPeliculas.write(partesEntradas[0]+","+partesEntradas[1]+",");
                    } else {
                        archPeliculas.write(partesEntradas[0]+","+partesEntradas[1]+"\n");
                    }                                
                }       
            }     
        }
        BufferedWriter archStatus = new BufferedWriter(new FileWriter("status.txt"));
        for (int i = 0; i < cantClientes; i++){
            if (i == cantClientes -1){
                archStatus.write(ruts[i]+","+estados[i]);
            } else {
            archStatus.write(ruts[i]+","+estados[i]+"\n");
            }
        }
        archClientes.close();
        archPeliculas.close();
        archStatus.close();
        System.exit(0);
    }

    
    /** The system subprogram that leads a customer to a admin menu or a customer menu depending on what he did in the login() method
     * @param nombres List with the customers's names
     * @param apellidos List with the customer's surname
     * @param ruts List with the customers's ruts
     * @param rutsFormateados List with the customers's ruts without dots and hyphens
     * @param contraseñas List with the customers's passwords
     * @param saldos List with the customers's balances
     * @param estados List with the customers's movility pass state
     * @param peliculas List with each movies's name
     * @param tipoPeliculas List with each movies's type
     * @param recaudaciones List with each movies's total income 
     * @param recaudacionesDiarias List with each movies's daily income
     * @param recaudacionM Contains the income obtained during the morning
     * @param recaudacionT Contains the income obtained during the afternoon
     * @param funciones List with each movies's show
     * @param salas List with the movie theaters
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     * @param filasAsientos List that contains the theater's letter of row
     * @param columnasAsientos List that contains the theater's number of column
     * @param cantClientes The quantity of customers in the system
     * @param cantPeliculas The quantity of movies in the system
     * @param scanner Used to save information in the system
     * @param peliculasFormateadas List of movie names only
     * @throws IOException
     */
    public static void Sistema(String[] nombres, String[] apellidos, String[] ruts, String[] rutsFormateados, String[] contraseñas,
    double[] saldos, String[] estados, String[] peliculas, String[] tipoPeliculas, double[] recaudaciones, double[] recaudacionesDiarias, 
    double recaudacionM, double recaudacionT, String[] funciones, String[] salas, String[][][] matrizSalas, String[][][] entradasCliente,
    String[] filasAsientos, String[] columnasAsientos, int cantClientes, int cantPeliculas, Scanner scanner, String[] peliculasFormateadas) throws IOException{
        int posRut = login(nombres, apellidos, ruts, rutsFormateados, contraseñas, saldos, estados, peliculas, tipoPeliculas, recaudaciones,
        recaudacionesDiarias, recaudacionM, recaudacionT, funciones, salas, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, cantClientes, cantPeliculas, scanner, peliculasFormateadas);
        if (posRut == -2){
            menuAdmin(nombres, apellidos, ruts, rutsFormateados, contraseñas, saldos, estados, peliculas, tipoPeliculas, recaudaciones,
            recaudacionesDiarias, recaudacionM, recaudacionT, funciones, salas, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, cantClientes, cantPeliculas, posRut, scanner, peliculasFormateadas);
        } else {
            menuCliente(nombres, apellidos, ruts, rutsFormateados, contraseñas, saldos, estados, peliculas, tipoPeliculas, recaudaciones,
            recaudacionesDiarias, recaudacionM, recaudacionT, funciones, salas, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, cantClientes, cantPeliculas, posRut, scanner, peliculasFormateadas);
            
        }
    }

    
    /** The menu is displayed for a NORMAL customer. Ending if he inputs "5" (log-out)
     * @param nombres Lista de los nombres de los clientes
     * @param apellidos List with the customer's surname
     * @param ruts List with the customers's ruts
     * @param rutsFormateados List with the customers's ruts without dots and hyphens
     * @param contraseñas List with the customers's passwords
     * @param saldos List with the customers's balances
     * @param estados List with the customers's movility pass state
     * @param peliculas List with each movies's name
     * @param tipoPeliculas List with each movies's type
     * @param recaudaciones List with each movies's total income 
     * @param recaudacionesDiarias List with each movies's daily income
     * @param recaudacionM Contains the income obtained during the morning
     * @param recaudacionT Contains the income obtained during the afternoon
     * @param funciones List with each movies's show
     * @param salas List with the movie theaters
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     * @param filasAsientos List that contains the theater's letter of row
     * @param columnasAsientos List that contains the theater's number of column
     * @param cantClientes The quantity of customers in the system
     * @param cantPeliculas The quantity of movies in the system
     * @param posRut The position in which the customer's rut is saved on the rut[] list
     * @param scanner Used to save information in the system
     * @param peliculasFormateadas List of movie names only
     * @throws IOException
     */
    public static void menuCliente(String[] nombres, String[] apellidos, String[] ruts, String[] rutsFormateados, String[] contraseñas,
    double[] saldos, String[] estados, String[] peliculas, String[] tipoPeliculas, double[] recaudaciones, double[] recaudacionesDiarias, 
    double recaudacionM, double recaudacionT, String[] funciones, String[] salas, String[][][] matrizSalas, String[][][] entradasCliente,
    String[] filasAsientos, String[] columnasAsientos, int cantClientes, int cantPeliculas, int posRut, Scanner scanner, String[] peliculasFormateadas) throws IOException{
        System.out.println("<================================> MENÚ CLIENTE <================================>\n");
        System.out.println("Ingrese que opción desea:\n[1] Comprar Entrada\n[2] Información Usuario"+"\n[3] Devolución Entrada"+
        "\n[4] Cartelera\n[5] Cerrar Sesion");
        System.out.print("Opcion: ");
        String opcionCliente = (scanner.nextLine());
        while (true){
        if (opcionCliente.equals("1")){
            System.out.println("<============================================================================>\n");
            comprarEntrada(cantPeliculas, posRut, tipoPeliculas, saldos, recaudaciones, recaudacionesDiarias, 
            recaudacionM, recaudacionT, salas, peliculas, funciones, estados, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, scanner, peliculasFormateadas);
        } else {
            if (opcionCliente.equals("2")){
                System.out.println("<============================================================================>\n");
                informacionUsuario(posRut, ruts, nombres, apellidos, saldos, peliculas, salas, entradasCliente);
            } else {
                if (opcionCliente.equals("3")){
                    System.out.println("<============================================================================>\n");
                    devolucionEntradas(peliculas, posRut, entradasCliente, cantPeliculas, tipoPeliculas, estados, salas, filasAsientos, columnasAsientos,
                    saldos, recaudaciones, recaudacionesDiarias, recaudacionM, recaudacionT, matrizSalas, scanner, peliculasFormateadas);
                } else {
                    if (opcionCliente.equals("4")){
                        System.out.println("<============================================================================>\n");
                        cartelera(peliculas, funciones, cantPeliculas);
                    } else {
        
                        if (opcionCliente.equals("5")){
                            break;
                        } else {
                            System.out.println("Opción digitada incorrectamente.");
                        }
                    }
                }
            }
        }
        System.out.println("Ingrese que opción desea:\n[1] Comprar Entrada\n[2] Información Usuario\n[3] Devolución Entrada"+
        "\n[4] Cartelera\n[5] Cerrar Sesion");
        opcionCliente = (scanner.nextLine());
        }
        System.out.println("<============================================================================>\n");
        inicio(nombres, apellidos, ruts, rutsFormateados, contraseñas, saldos, estados, peliculas, tipoPeliculas, recaudaciones,
        recaudacionesDiarias, recaudacionM, recaudacionT, funciones, salas, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, cantClientes, cantPeliculas, scanner, peliculasFormateadas);
    }

    
    /** The menu of a ADMIN customer is displayed, ending if he types "3" (log-out).
     * @param nombres List with the customers's names
     * @param apellidos List with the customer's surname
     * @param ruts List with the customers's ruts
     * @param rutsFormateados List with the customers's ruts without dots and hyphens
     * @param contraseñas List with the customers's passwords
     * @param saldos List with the customers's balances
     * @param estados List with the customers's movility pass state
     * @param peliculas List with each movies's name
     * @param tipoPeliculas List with each movies's type
     * @param recaudaciones List with each movies's total income 
     * @param recaudacionesDiarias List with each movies's daily income
     * @param recaudacionM Contains the income obtained during the morning
     * @param recaudacionT Contains the income obtained during the afternoon
     * @param funciones List with each movies's show
     * @param salas List with the movie theaters
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     * @param filasAsientos List that contains the theater's letter of row
     * @param columnasAsientos List that contains the theater's number of column
     * @param cantClientes The quantity of customers in the system
     * @param cantPeliculas The quantity of movies in the system
     * @param posRut The position in which the customer's rut is saved on the rut[] list
     * @param scanner Used to save information in the system
     * @param peliculasFormateadas List of movie names only
     * @throws IOException
     */
    public static void menuAdmin(String[] nombres, String[] apellidos, String[] ruts, String[] rutsFormateados, String[] contraseñas,
    double[] saldos, String[] estados, String[] peliculas, String[] tipoPeliculas, double[] recaudaciones, double[] recaudacionesDiarias, 
    double recaudacionM, double recaudacionT, String[] funciones, String[] salas, String[][][] matrizSalas, String[][][] entradasCliente,
    String[] filasAsientos, String[] columnasAsientos, int cantClientes, int cantPeliculas, int posRut, Scanner scanner, String[] peliculasFormateadas) throws IOException
    {
        System.out.println("<================================> MENÚ ADMIN <================================>\n");
        System.out.println("Ingrese que opción desea:\n[1] Taquilla\n[2] Información Cliente\n[3] Cerrar Sesión");
        System.out.print("Opcion: ");
        String opcionAdmin = (scanner.nextLine());
        while (true){
            if (opcionAdmin.equals("1")){
                taquilla(cantPeliculas, peliculas, recaudaciones, recaudacionesDiarias, recaudacionM, recaudacionT);
            } else {
                if (opcionAdmin.equals("2")){
                informacionCliente(rutsFormateados, cantClientes, ruts, nombres, apellidos, saldos, entradasCliente, peliculas, salas, scanner);
                } else {
                    if (opcionAdmin.equals("3")){
                        break;
                    } else {
                        System.out.println("Opción digitada incorrectamente.");
                    }
                }
            }
            System.out.println("Ingrese que opción desea:\n[1] Taquilla\n[2] Información Cliente\n[3] Cerrar Sesión");
            opcionAdmin = (scanner.nextLine());
        }
        System.out.println("<============================================================================>\n");
        inicio(nombres, apellidos, ruts, rutsFormateados, contraseñas, saldos, estados, peliculas, tipoPeliculas, recaudaciones,
        recaudacionesDiarias, recaudacionM, recaudacionT, funciones, salas, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, cantClientes, cantPeliculas, scanner, peliculasFormateadas);
        
    }

    
    /** Admin method, it displays the total and daily income of each movie, as well as the morning and afternoon income
     * @param cantPeliculas The quantity of movies in the system
     * @param peliculas List with each movies's name
     * @param recaudaciones List with each movies's total income 
     * @param recaudacionesDiarias List with each movies's daily income
     * @param recaudacionM Contains the income obtained during the morning
     * @param recaudacionT Contains the income obtained during the afternoon
     */
    public static void taquilla(int cantPeliculas, String[] peliculas, double[] recaudaciones, double[] recaudacionesDiarias, double recaudacionM, double recaudacionT) {
        System.out.println("<================================> TAQUILLA <================================>\n");
        for (int i = 0; i < cantPeliculas; i++){
            System.out.println("Pelicula: "+peliculas[i]+"\nRecaudacion total: "+recaudaciones[i]+"\nRecaudación diaria: "
            +recaudacionesDiarias[i]);
            System.out.println("Recaudación en la mañana: "+recaudacionM+"\nRecaudacion en la tarde: "+recaudacionT+"\n");
        }
        System.out.println("<============================================================================>\n");
    }

    
    /** Admin method, depending on the rut that the admin types, the information about a specific customer will be displayed.
     * @param rutsFormateados List with the customers's ruts without dots and hyphens
     * @param cantClientes The quantity of customers in the system
     * @param ruts List with the customers's ruts
     * @param nombres List with the customers's names
     * @param apellidos List with the customer's surname
     * @param saldos List with the customers's balances
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     * @param peliculas List with each movies's name
     * @param salas List with the movie theaters
     * @param scanner Used to save information in the system
     */
    public static void informacionCliente(String[] rutsFormateados, int cantClientes, String[] ruts, String[] nombres, String[] apellidos, double[] saldos,
    String[][][] entradasCliente, String[] peliculas, String[] salas, Scanner scanner) {
        System.out.println("<================================> INFORMACIÓN CLIENTE <================================>\n");
        System.out.print("Ingrese RUT del cliente que desea desplegar: ");
        String RUT = scanner.nextLine();
        String RUTFormateado = formatearRut(RUT);
        int pRutFormateado = indexString(rutsFormateados, RUTFormateado, cantClientes);
        while(true){
            if (pRutFormateado != -1){
                System.out.println("RUT: "+ruts[pRutFormateado]+"\n"+"Nombre: "+nombres[pRutFormateado]+"\n"+"Apellido: "+apellidos[pRutFormateado]+"\n"+"Saldo: "+saldos[pRutFormateado]+"\n");
                for (int j = 0; j < peliculas.length; j++){
                    System.out.println("Película: "+peliculas[j]);
                    int cantEntradasPelicula = 0;
                    for (int k = 0; k < salas.length; k++){
                        if (entradasCliente[pRutFormateado][j][k] != ""){
                        System.out.print("Horario: "+salas[k]+"Asientos: "+entradasCliente[pRutFormateado][j][k]);
                        cantEntradasPelicula++;
                        }
                    }
                    System.out.println("");
                    if (cantEntradasPelicula == 0) {
                        System.out.println("No tiene entradas para esta película.");
                    }
                }
                break;
            }
        }
        System.out.println("<============================================================================>\n");
    }

    
    /** The rut is re-formated (adding the needed dots and hyphen).
     * @param rut The rut that the system needs to be re-formated (adding dots and hyphen)
     * @return String
     */
    public static String formatear(String rut){
        int cont=0;
        String formato;
        if(rut.length() == 0){
            return "";
        }else{
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            formato = "-"+rut.substring(rut.length()-1);
            for(int i = rut.length()-2;i>=0;i--){
                formato = rut.substring(i, i+1)+formato;
                cont++;
                if(cont == 3 && i != 0){
                    formato = "."+formato;
                    cont = 0;
                }
            }
            return formato;
        }
    }

    
    /** A method that returns the position of the key value that we want to find in a list. If it doesn't exist here, a -1 will be returned.
     * @param array The list containing the key value that we want to track/find
     * @param key The primordial value that we want to find
     * @param cant The length of the list or the amount of data different to null/blank saved in the list
     * @return int
     */
    public static int indexString(String[] array, String key, int cant){

        for (int i = 0; i < cant; i++){
            if (key.equals(array[i])){
                return i; //Encontrado
            }
        }
        return -1; //No encontrado
    }

    
    /** Method in which the rut format is erased (Deleting dots and hyphen).
     * @param rut The rut that the system needs its format erased
     * @return String
     */
    public static String formatearRut(String rut) 
    {          
        String RUTFormateado = rut.replaceAll("\\p{Punct}", "");
        String RUTFormateado2 = RUTFormateado.toLowerCase();
        return RUTFormateado2;
        
    }

    
    /** The movie theater arrays are filled with their respective row/column format (Ex, J7/I21).
    
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param salas List with the movie theaters
     * @param filasAsientos List that contains the theater's letter of row
     * @param columnasAsientos List that contains the theater's number of column
     */
    public static void rellenarMatrices(String[][][] matrizSalas, String[] salas, String[] filasAsientos, String[] columnasAsientos){
        for (int i = 0; i < salas.length; i++){
            for (int j = 0; j < 10; j++){
                for (int k = 0; k < 30; k++){
                    if (j < 4){
                        if (k < 5){
                            matrizSalas[i][j][k] = "--";
                        } else {
                            if (k > 24){
                                matrizSalas[i][j][k] = "---";
                            } else {
                                matrizSalas[i][j][k] = filasAsientos[j]+columnasAsientos[k];
                            }
                        }
                    } else {
                        matrizSalas[i][j][k] = filasAsientos[j]+columnasAsientos[k];
                    }
                }
            }
        }
    }
    /** The process begins with the customer being asked about which movie does he want to see/buy tickets,
    * @param cantPeliculas The quantity of movies in the system
    * @param posRut The position in which the customer's rut is saved on the rut[] list
    * @param tipoPeliculas List with each movies's type
    * @param saldos List with the customers's balances
    * @param recaudaciones List with each movies's total income 
     * @param recaudacionesDiarias List with each movies's daily income
     * @param recaudacionM Contains the income obtained during the morning
     * @param recaudacionT Contains the income obtained during the afternoon
     * @param salas List with the movie theaters
     * @param peliculas List with each movies's name
     * @param funciones List with each movies's show
     * @param estados List with the customers's movility pass state
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     * @param filasAsientos List that contains the theater's letter of row
     * @param columnasAsientos List that contains the theater's number of column
     * @param scanner Used to save information in the system
     * @param peliculasFormateadas List of movie names only
     */
    public static void comprarEntrada(int cantPeliculas, int posRut, String[] tipoPeliculas, double[] saldos, double[] recaudaciones, double[] recaudacionesDiarias, double recaudacionM
    , double recaudacionT, String[] salas, String[] peliculas, String[] funciones, String[] estados, String[][][] matrizSalas, String[][][] entradasCliente, String[] filasAsientos, String[] columnasAsientos, Scanner scanner, String[] peliculasFormateadas){
        System.out.println("<================================> COMPRAR ENTRADAS <================================>\n");
        double descuentoEntrada = checkEstado(posRut, estados);
        int pPelicula = 0;
        while (true){  
        System.out.println("Las películas disponibles en la cartelera son: ");
        for (int i = 0; i < cantPeliculas; i++){
            System.out.print(peliculas[i]+", ");
        }
        System.out.println("Ingrese nombre de la película: ");
        String pelicula = scanner.nextLine();
        String peliculaFormat = formatearRut(pelicula);
        pPelicula = indexString(peliculasFormateadas, peliculaFormat, cantPeliculas);
        if (pPelicula != -1){
            break;
        } else {
            System.out.println("La película escrita no se encuentra o la ingreso incorrectamente.");
        }
    }
    
    comprarEntrada2(descuentoEntrada, pPelicula, cantPeliculas, posRut, tipoPeliculas, saldos, recaudaciones, recaudacionesDiarias, recaudacionM, recaudacionT, salas, peliculas, funciones, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, scanner);
    }

    
    /** Here, the customer is asked about which movie show will he choose,
     * @param descuentoEntrada The discount that will be applied to the movie tickets
     * @param pPelicula The position in which the movie that the customer wants to buy a/the ticket(s) is saved
     * @param cantPeliculas The quantity of movies in the system
     * @param posRut The position in which the customer's rut is saved on the rut[] list
     * @param tipoPeliculas List with each movies's type
     * @param saldos List with the customers's balances
     * @param recaudaciones List with each movies's total income 
     * @param recaudacionesDiarias List with each movies's daily income
     * @param recaudacionM Contains the income obtained during the morning
     * @param recaudacionT Contains the income obtained during the afternoon
     * @param salas List with the movie theaters
     * @param peliculas List with each movies's name
     * @param funciones List with each movies's show
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     * @param filasAsientos List that contains the theater's letter of row
     * @param columnasAsientos List that contains the theater's number of column
     * @param scanner Used to save information in the system
     */
    public static void comprarEntrada2(double descuentoEntrada, int pPelicula, int cantPeliculas, int posRut, String[] tipoPeliculas
    , double[] saldos, double[] recaudaciones, double[] recaudacionesDiarias, double recaudacionM
    , double recaudacionT, String[] salas, String[] peliculas, String[] funciones, String[][][] matrizSalas, String[][][] entradasCliente, String[] filasAsientos, String[] columnasAsientos, Scanner scanner){
        int pFuncion = 0;
        while (true){
            System.out.println("Las funciones disponibles para la película que eligió son: ");
            System.out.println(funciones[pPelicula]);
            System.out.println("");
            System.out.println("Ingrese función: ");
            String funcion = scanner.nextLine();
            String funcionFormat = funcion.toUpperCase();
            pFuncion = indexString(salas, funcionFormat, cantPeliculas);
            if (pFuncion != -1){
                break;
            } else {
                System.out.println("La función ingresada no existe o la escribió incorrectamente.");
            }
        }
        comprarEntrada3(descuentoEntrada, pPelicula, cantPeliculas, posRut, pFuncion, tipoPeliculas, saldos, recaudaciones, recaudacionesDiarias, recaudacionM, recaudacionT, salas, peliculas, funciones, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, scanner);
    }

    
    /** In the 3rd part of the buy/payment process, the customer will be asked about the amount of tickets that he/she will buy.
     * Also, he/she will be asked if the chair tickets will be all together, or separated.
     * @param descuentoEntrada The discount that will be applied to the movie tickets
     * @param pPelicula The position in which the movie that the customer wants to buy a/the ticket(s) is saved
     * @param cantPeliculas The quantity of movies in the system
     * @param posRut The position in which the customer's rut is saved on the rut[] list
     * @param pFuncion The position in which the show of the movie that the customer wants to buy a/the ticket(s) is saved.
     * @param tipoPeliculas List with each movies's type
     * @param saldos List with the customers's balances
     * @param recaudaciones List with each movies's total income 
     * @param recaudacionesDiarias List with each movies's daily income
     * @param recaudacionM Contains the income obtained during the morning
     * @param recaudacionT Contains the income obtained during the afternoon
     * @param salas List with the movie theaters
     * @param peliculas List with each movies's name
     * @param funciones List with each movies's show
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     * @param filasAsientos List that contains the theater's letter of row
     * @param columnasAsientos List that contains the theater's number of column
     * @param scanner Used to save information in the system
     */
    public static void comprarEntrada3(double descuentoEntrada, int pPelicula, int cantPeliculas, int posRut, int pFuncion, String[] tipoPeliculas
    ,double[] saldos, double[] recaudaciones, double[] recaudacionesDiarias, double recaudacionM
    , double recaudacionT, String[] salas, String[] peliculas, String[] funciones, String[][][] matrizSalas, String[][][] entradasCliente, String[] filasAsientos, String[] columnasAsientos, Scanner scanner){
        int cantEntradas;
        while (true){
        System.out.println("Ingrese cantidad de entradas a comprar");
        cantEntradas = Integer.parseInt(scanner.nextLine());
        System.out.println("Las entradas que comprará, estarán todas juntas o separadas?\n[1] Juntas\n[2] Separadas");
        String opcionAsientos = scanner.nextLine();
        opcionAsientos = opcionAsientos.toLowerCase();
        if (opcionAsientos.equals("1")){
            comprarEntradasJuntas(descuentoEntrada, pPelicula, cantPeliculas, posRut, pFuncion, cantEntradas, tipoPeliculas, saldos, recaudaciones, recaudacionesDiarias, recaudacionM, recaudacionT, salas, peliculas, funciones, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, scanner);
            break;
        } else {
            if (opcionAsientos.equals("2")){
                comprarEntradasSeparadas(descuentoEntrada, pPelicula, cantPeliculas, posRut, pFuncion, cantEntradas, tipoPeliculas, saldos, recaudaciones, recaudacionesDiarias, recaudacionM, recaudacionT, salas, peliculas, funciones, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, scanner);
                break;
            } else {
                System.out.println("Opción no valida, escriba el número correspondiente.");
            }
        }
        }
    }

    
    /** If the chair tickets that the customer will buy are all together, the chair arrangement will be filled in this different way
     * @param descuentoEntrada The discount that will be applied to the movie tickets
     * @param pPelicula The position in which the movie that the customer wants to buy a/the ticket(s) is saved
     * @param cantPeliculas The quantity of movies in the system
     * @param posRut The position in which the customer's rut is saved on the rut[] list
     * @param pFuncion The position in which the show of the movie that the customer wants to buy a/the ticket(s) is saved.
     * @param cantEntradas The amount of movie tickets that the customer wants to buy
     * @param tipoPeliculas List with each movies's type
     * @param saldos List with the customers's balances
     * @param recaudaciones List with each movies's total income 
     * @param recaudacionesDiarias List with each movies's daily income
     * @param recaudacionM Contains the income obtained during the morning
     * @param recaudacionT Contains the income obtained during the afternoon
     * @param salas List with the movie theaters
     * @param peliculas List with each movies's name
     * @param funciones List with each movies's show
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     * @param filasAsientos List that contains the theater's letter of row
     * @param columnasAsientos List that contains the theater's number of column
     * @param scanner Used to save information in the system
     */
    public static void comprarEntradasJuntas(double descuentoEntrada, int pPelicula, int cantPeliculas, int posRut, int pFuncion, int cantEntradas, String[] tipoPeliculas
    , double[] saldos, double[] recaudaciones, double[] recaudacionesDiarias, double recaudacionM, double recaudacionT, String[] salas, String[] peliculas, String[] funciones,
    String[][][] matrizSalas, String[][][] entradasCliente, String[] filasAsientos, String[] columnasAsientos, Scanner scanner){
        String posicionAsiento = "";
        System.out.println("Esta es la disposición de la sala actualmente:");
        int posFinalColumna = 0;
        int posFinalFila = 0;
        for (int x = 0; x < cantEntradas; x++)
        { 
                for (int j = 0; j < 10; j++)
                {
                    for (int k = 0; k < 30; k++)
                    {
                        System.out.print(matrizSalas[pFuncion][j][k]+" ");

                    }
                    System.out.println("");
                }
            while (true){    
            System.out.println("Ingrese posición que sea comprar: ");
            posicionAsiento = scanner.nextLine();
            posicionAsiento = posicionAsiento.toUpperCase();
            int[] posAsiento = checkAsiento(matrizSalas, posicionAsiento, pFuncion);
            if (posAsiento[0] != -1 && (matrizSalas[pFuncion][posAsiento[0]][posAsiento[1]-1] != "O" & matrizSalas[pFuncion][posAsiento[0]][posAsiento[1]+1] != "O" )){
                matrizSalas[pFuncion][posAsiento[0]][posAsiento[1]] = "X"; //Se concreta el asiento
                                        
                posFinalColumna = posAsiento[1];
                posFinalFila = posAsiento[0];
                if (x == 0){
                    entradasCliente[posRut][pPelicula][pFuncion] = posicionAsiento+"-";
                    System.out.println(entradasCliente[posRut][pPelicula][pFuncion]); 
                } else {
                    if( x == cantEntradas-1){

                        entradasCliente[posRut][pPelicula][pFuncion]+= posicionAsiento;
                        System.out.println(entradasCliente[posRut][pPelicula][pFuncion]); 
                    } else {
                        entradasCliente[posRut][pPelicula][pFuncion] += posicionAsiento+"-";
                        System.out.println(entradasCliente[posRut][pPelicula][pFuncion]); 
                    }
                }
                break;
            } else {
                System.out.println("La posicion ingresada no esta disponible/ no es correcta.");
            }
            }
        }      
        if (matrizSalas[pFuncion][posFinalFila][posFinalColumna-cantEntradas].equals("--") && posFinalColumna-cantEntradas+1 == 0){
            matrizSalas[pFuncion][posFinalFila][posFinalColumna+1] = "O";
        } else {
            if (matrizSalas[pFuncion][posFinalFila][posFinalColumna+1].equals("---") && posFinalColumna+1 == 29){
            matrizSalas[pFuncion][posFinalFila][posFinalColumna-cantEntradas] = "O";
            } else {
                matrizSalas[pFuncion][posFinalFila][posFinalColumna+1] = "O";
                matrizSalas[pFuncion][posFinalFila][posFinalColumna-cantEntradas] = "O";
            }
        }
        comprarEntradas4(descuentoEntrada, pPelicula, posRut, pFuncion, cantEntradas, tipoPeliculas, saldos, recaudaciones, recaudacionesDiarias, recaudacionM, recaudacionT, salas, peliculas, funciones, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, scanner);
    }

    
    /** If the chair tickets that the customer will buy are all separated, the chair arrangement will be filled in this different way
     * @param descuentoEntrada The discount that will be applied to the movie tickets
     * @param pPelicula The position in which the movie that the customer wants to buy a/the ticket(s) is saved
     * @param cantPeliculas The quantity of movies in the system
     * @param posRut The position in which the customer's rut is saved on the rut[] list
     * @param pFuncion The position in which the show of the movie that the customer wants to buy a/the ticket(s) is saved.
     * @param cantEntradas The amount of movie tickets that the customer wants to buy
     * @param tipoPeliculas List with each movies's type
     * @param saldos List with the customers's balances
     * @param recaudaciones List with each movies's total income 
     * @param recaudacionesDiarias List with each movies's daily income
     * @param recaudacionM Contains the income obtained during the morning
     * @param recaudacionT Contains the income obtained during the afternoon
     * @param salas List with the movie theaters
     * @param peliculas List with each movies's name
     * @param funciones List with each movies's show
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     * @param filasAsientos List that contains the theater's letter of row
     * @param columnasAsientos List that contains the theater's number of column
     * @param scanner Used to save information in the system
     */
    public static void comprarEntradasSeparadas(double descuentoEntrada, int pPelicula, int cantPeliculas, int posRut, int pFuncion, int cantEntradas, String[] tipoPeliculas
    , double[] saldos, double[] recaudaciones, double[] recaudacionesDiarias, double recaudacionM
    , double recaudacionT, String[] salas, String[] peliculas, String[] funciones, String[][][] matrizSalas, String[][][] entradasCliente, String[] filasAsientos, String[] columnasAsientos, Scanner scanner){
        String posicionAsiento = "";
        System.out.println("Esta es la disposición de la sala actualmente:");
            for (int x = 0; x < cantEntradas; x++)
            { 
                for (int j = 0; j < 10; j++)
                {
                    for (int k = 0; k < 30; k++)
                    {
                        System.out.print(matrizSalas[pFuncion][j][k]+" ");

                    }
                    System.out.println("");
                }   
                while (true){    
                    System.out.println("Ingrese posición que sea comprar: ");
                    posicionAsiento = scanner.nextLine();
                    posicionAsiento = posicionAsiento.toUpperCase();
                    int[] posAsiento = checkAsiento(matrizSalas, posicionAsiento, pFuncion);
                    if (posAsiento[0] != -1 && (matrizSalas[pFuncion][posAsiento[0]][posAsiento[1]-1] != "O" & matrizSalas[pFuncion][posAsiento[0]][posAsiento[1]+1] != "O" )){
                        matrizSalas[pFuncion][posAsiento[0]][posAsiento[1]] = "X"; //Se concreta el asiento
                        matrizSalas[pFuncion][posAsiento[0]][posAsiento[1]+1] = "O";
                        matrizSalas[pFuncion][posAsiento[0]][posAsiento[1]-1] = "O";
                                
                        if (x == 0){
                            entradasCliente[posRut][pPelicula][pFuncion] = posicionAsiento+"-"; 
                        } else {
                            if( x == cantEntradas-1){

                                entradasCliente[posRut][pPelicula][pFuncion]+= posicionAsiento;
                            } else {
                                entradasCliente[posRut][pPelicula][pFuncion] += posicionAsiento+"-";
                            }
                        }
                        break;                                       
                    } else {
                        System.out.println("El asiento ingresado no esta disponible/no existe.");
                    }
                            
                }
                                
            }
            comprarEntradas4(descuentoEntrada, pPelicula, posRut, pFuncion, cantEntradas, tipoPeliculas, saldos, recaudaciones, recaudacionesDiarias, recaudacionM, recaudacionT, salas, peliculas, funciones, matrizSalas, entradasCliente, filasAsientos, columnasAsientos, scanner);
        }
                
    /** Here, the payment occurs, and the movie tickets are added to the customer's "pocket"
     * @param descuentoEntrada The discount that will be applied to the movie tickets
     * @param pPelicula The position in which the movie that the customer wants to buy a/the ticket(s) is saved
     * @param posRut The position in which the customer's rut is saved on the rut[] list
     * @param pFuncion The position in which the show of the movie that the customer wants to buy a/the ticket(s) is saved.
     * @param cantEntradas The amount of movie tickets that the customer wants to buy
     * @param tipoPeliculas List with each movies's type
     * @param saldos List with the customers's balances
     * @param recaudaciones List with each movies's total income 
     * @param recaudacionesDiarias List with each movies's daily income
     * @param recaudacionM Contains the income obtained during the morning
     * @param recaudacionT Contains the income obtained during the afternoon
     * @param salas List with the movie theaters
     * @param peliculas List with each movies's name
     * @param funciones List with each movies's show
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     * @param filasAsientos List that contains the theater's letter of row
     * @param columnasAsientos List that contains the theater's number of column
     * @param scanner Used to save information in the system
     */
    public static void comprarEntradas4(double descuentoEntrada, int pPelicula, int posRut, int pFuncion, int cantEntradas, String[] tipoPeliculas
    , double[] saldos, double[] recaudaciones, double[] recaudacionesDiarias, double recaudacionM, double recaudacionT, String[] salas, String[] peliculas, 
    String[] funciones, String[][][] matrizSalas, String[][][] entradasCliente, String[] filasAsientos, String[] columnasAsientos, Scanner scanner){
        int valorPelicula = checkValorPelicula(tipoPeliculas, pPelicula);
        double valorEntradas = (cantEntradas*valorPelicula)* descuentoEntrada;
        while (true){
            System.out.println("El valor de sus entradas es "+valorEntradas);
            System.out.println("Está seguro de querer comprar sus entradas?\n[1] Si\n[2] No");
            String opcionComprar = scanner.nextLine();
            opcionComprar = opcionComprar.toLowerCase();
            if (opcionComprar.equals("1")){
                if (valorEntradas <= saldos[posRut]){
                    saldos[posRut] -= valorEntradas;
                    recaudaciones[pPelicula] += valorEntradas;
                    recaudacionesDiarias[pPelicula] += valorEntradas;
                    //Checkear horario de funcion
                    String [] datos = salas[pFuncion].split("");
                    String horario = datos[1];
                    if (horario.equals("M")){
                        recaudacionM += valorEntradas;
                    } else {
                        recaudacionT += valorEntradas;
                    }
                    System.out.println("<============================================================================>\n");
                    break;
                } else {
                    System.out.println("Saldo insuficiente, desea agregar saldo?\n[1] Si\n[2] No");
                    String opcionSaldo = scanner.nextLine();
                    opcionSaldo = opcionSaldo.toLowerCase();
                    if (opcionSaldo.equals("1")){
                        agregarSaldo(posRut, saldos, scanner);
                    }
                }
            } else {
                if (opcionComprar.equals("2")){
                    removerEntradas(matrizSalas, posRut, pFuncion, pPelicula, entradasCliente, filasAsientos, columnasAsientos);
                    System.out.println("<============================================================================>\n");
                    break;
                } else {
                    System.out.println("Opción no valida");
                }
            } 
        } 
    }

    
    /** Checks the value of the movie ticket depending on the type of the movie.
     * @param tipoPeliculas[] List with each movies's type
     * @param pPelicula The position in which the movie that the customer wants to buy a/the ticket(s) is saved
     * @return int This int value will be $4000 if the movie is a released movie, and $5500 if its a premiere.
     */
    public static int checkValorPelicula(String tipoPeliculas[], int pPelicula) {
        if (tipoPeliculas[pPelicula].equals("estreno")){
            return 5500;
        } else {
            return 4000;
        }
    }

    
    /** Adds cash/money to the balance if the customer needs to
     * @param posRut The position in which the customer's rut is saved on the rut[] list
     * @param saldos List with the customers's balances
     * @param scanner Used to save information in the system
     */
    public static void agregarSaldo(int posRut, double[] saldos, Scanner scanner){
        System.out.println("Ingrese saldo a agregar: ");
        double saldo = Double.parseDouble(scanner.nextLine());
        saldos[posRut] += saldo;
    }

    
    /** Removes the customer's owned chairs from a specific movie show's chair arrangements.
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param posRut The position in which the customer's rut is saved on the rut[] list
     * @param pFuncion The position in which the show of the movie that the customer wants to buy a/the ticket(s) is saved.
     * @param pPelicula The position in which the movie that the customer wants to buy a/the ticket(s) is saved
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     * @param filasAsientos List that contains the theater's letter of row
     * @param columnasAsientos List that contains the theater's number of column
     */
    public static void removerEntradas(String[][][] matrizSalas, int posRut, int pFuncion, int pPelicula, String[][][] entradasCliente,
    String[] filasAsientos, String[] columnasAsientos){
    String[] entradas = entradasCliente[posRut][pPelicula][pFuncion].split("-");
    for (int i = 0; i < entradas.length; i++){
        for (int j = 0; j < 10; j++){
            for (int k = 0; k < 30; k++){
                if (matrizSalas[pFuncion][j][k].equals(entradas[i])){
                    matrizSalas[pFuncion][j][k] = filasAsientos[j]+columnasAsientos[k];
                }
            }
        }
    }
    entradasCliente[posRut][pPelicula][pFuncion] = "";
    }

    
    /** Checks the state of the customer's movility pass
     * @param posRut The position in which the customer's rut is saved on the rut[] list
     * @param estados List with the customers's movility pass state
     * @return double The discount that will be apllied to the customer, if its movility pass is "enabled", the discount will apply.
     */
    public static double checkEstado(int posRut, String[] estados){
        if (estados[posRut].equals("HABILITADO")){
            return 0.85;
        } else {
            return 1;
        }
    }
    
    
    /** Displays the crucial information of the customer along with his movie tickets's information.
     * @param posRut The position in which the customer's rut is saved on the rut[] list
     * @param ruts List with the customers's ruts
     * @param nombres List with the customers's names
     * @param apellidos List with the customer's surname
     * @param saldos List with the customers's balances
     * @param peliculas List with each movies's name
     * @param salas List with the movie theaters
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     */
    public static void informacionUsuario(int posRut, String[] ruts, String[] nombres, String[] apellidos, double[] saldos, String[] peliculas, String[] salas, String[][][] entradasCliente){
        System.out.println("<================================> INFORMACIÓN USUARIO <================================>\n");
        System.out.println("RUT: "+ruts[posRut]+"\n"+"Nombre: "+nombres[posRut]+"\n"+"Apellido: "+apellidos[posRut]+"\n"+"Saldo: "+saldos[posRut]+"\n");
        for (int j = 0; j < peliculas.length; j++){
            System.out.println("Película: "+peliculas[j]);
            int cantEntradasPelicula = 0;
            for (int k = 0; k < salas.length; k++){
                if (entradasCliente[posRut][j][k] != ""){
                System.out.print(" Horario: "+salas[k]+", Asientos: "+entradasCliente[posRut][j][k]);
                System.out.println("<-------------------------------------------------------------->");
                cantEntradasPelicula++;
                }
            }
            if (cantEntradasPelicula == 0) {
                System.out.println("No tiene entradas para esta película.");
            }
            System.out.println("");
        }
        System.out.println("<============================================================================>\n");
    }

    
    /** Deletes some or all the movie tickets that a customer holds, deletes the marked chairs that the customer had for the
     * movie, and changes the movie's income and the customer's balance.
     * @param peliculas List with each movies's name
     * @param posRut The position in which the customer's rut is saved on the rut[] list
     * @param entradasCliente List of Arrays that contain each customer's movie ticket
     * @param cantPeliculas The quantity of movies in the system
     * @param tipoPeliculas List with each movies's type
     * @param estados List with the customers's movility pass state
     * @param salas List with the movie theaters
     * @param filasAsientos List that contains the theater's letter of row
     * @param columnasAsientos List that contains the theater's number of column
     * @param saldos List with the customers's balances
     * @param recaudaciones List with each movies's total income 
     * @param recaudacionesDiarias List with each movies's daily income
     * @param recaudacionM Contains the income obtained during the morning
     * @param recaudacionT Contains the income obtained during the afternoon
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param scanner Used to save information in the system
     * @param peliculasFormateadas List of movie names only
     */
    public static void devolucionEntradas(String[] peliculas, int posRut, String[][][] entradasCliente, int cantPeliculas, String[] tipoPeliculas, String[] estados, 
    String[] salas, String[] filasAsientos, String[] columnasAsientos, double[] saldos, double[] recaudaciones, double[] recaudacionesDiarias, double recaudacionM, 
    double recaudacionT, String[][][] matrizSalas, Scanner scanner, String[] peliculasFormateadas){
        System.out.println("<================================> DEVOLUCIÓN ENTRADAS <================================>\n");
        while (true){
        for (int j = 0; j < peliculas.length; j++){
            System.out.println("Película: "+peliculas[j]);
            int cantEntradasPelicula = 0;
            for (int k = 0; k < salas.length; k++){
                if (entradasCliente[posRut][j][k] != ""){
                System.out.print(" Horario: "+salas[k]+" Asientos: "+entradasCliente[posRut][j][k]);
                cantEntradasPelicula++;
                }
            }
            System.out.println("");
            if (cantEntradasPelicula == 0) {
                System.out.println("No tiene entradas para esta película.");
            }
        }
        System.out.println("Ingrese nombre de película a eliminar");

        String peliculaEliminar = scanner.nextLine();
        String peliculaEliminarFormat = formatearRut(peliculaEliminar);
        int pPeliculaEliminar = indexString(peliculasFormateadas, peliculaEliminarFormat, cantPeliculas);
        if (pPeliculaEliminar != -1){
            int valorEntrada = checkValorPelicula(tipoPeliculas, pPeliculaEliminar);
            double descuento = checkEstado(posRut, estados);
            double valorAgregar = 1;
            System.out.println("Ingrese función: ");
            String funcionEliminar = scanner.nextLine();
            int pFuncionEliminar = indexString(salas, funcionEliminar, salas.length);
            if (pFuncionEliminar != -1 && !entradasCliente[posRut][pPeliculaEliminar][pFuncionEliminar].equals("")){
                String[] partes = salas[pFuncionEliminar].split("");
                System.out.println("Ingrese cantidad de asientos a eliminar: ");
                int cantAsientosEliminar = Integer.parseInt(scanner.nextLine());
                String[] entradas  = entradasCliente[posRut][pPeliculaEliminar][pFuncionEliminar].split("-");
                for (int i = 0; i < cantAsientosEliminar; i++){
                    if (cantAsientosEliminar <= entradas.length){
                        System.out.println("Ingrese asiento a eliminar: ");
                        String asientoEliminar = scanner.nextLine();
                        int pAsientoEliminar = indexString(entradas, asientoEliminar, entradas.length);
                        if (pAsientoEliminar != -1) {
                            String[] datosAsiento = asientoEliminar.split("");
                            int posFila = indexString(filasAsientos, datosAsiento[0], filasAsientos.length);
                            int posColumna = indexString(columnasAsientos, datosAsiento[1], columnasAsientos.length);
                            entradas[pAsientoEliminar] = "";
                            
                                for (int j = 0; j < 10; j++){
                                    for (int k = 0; k < 30; k++){
                                        matrizSalas[pFuncionEliminar][posFila][posColumna] = filasAsientos[j]+columnasAsientos[k];
                                        if (matrizSalas[pFuncionEliminar][posFila][posColumna-1].equals("O")){
                                            matrizSalas[pFuncionEliminar][posFila][posColumna-1] = filasAsientos[j]+columnasAsientos[k-1];
                                        }
                                        if (matrizSalas[pFuncionEliminar][posFila][posColumna+1].equals("O")){
                                            matrizSalas[pFuncionEliminar][posFila][posColumna+1] = filasAsientos[j]+columnasAsientos[k+1];
                                        }
                                    }
                                }
                                    
                            
                                
                        } else {
                            System.out.println("Asiento no encontrado, inténtelo nuevamente.");
                        }
                            
                        valorAgregar = (valorAgregar*descuento*valorEntrada)*0.80;
                    } else {
                        System.out.println("La cantidad de entradas a eliminar es mayor a las que tiene, intentelo nuevamente.");
                    }
                }
                saldos[posRut] = saldos[posRut] + valorAgregar;     
                recaudaciones[pPeliculaEliminar] = recaudaciones[pPeliculaEliminar] - valorAgregar;
                recaudacionesDiarias[pPeliculaEliminar] = recaudacionesDiarias[pPeliculaEliminar] - valorAgregar;
                if (partes[1].equals("M")){
                    recaudacionM = recaudacionM - valorAgregar;
                } else {
                    recaudacionT = recaudacionT - valorAgregar;
                }
                System.out.println("<============================================================================>\n");
                break;
            } else {
                System.out.println("Función no encontrada/No tiene asientos, inténtelo nuevamente. ");
                break;
            }
        } else {
            System.out.println("Pelicula no encontrada.");
            break;
        }    
    }
    }

    
    /** Displays all the movies available in the cinema with all of their shows
     * @param peliculas List with each movies's name
     * @param funciones List with each movies's show
     * @param cantPeliculas The quantity of movies in the system
     */
    public static void cartelera(String[] peliculas, String[] funciones, int cantPeliculas) {
        System.out.println("<================================> CARTELERA <================================>\n");
        for (int i = 0; i < cantPeliculas; i++){
            System.out.println("Pelicula: "+peliculas[i]+", y sus funciones son: "+funciones[i]);
            
        }
        System.out.println("<============================================================================>\n");
    }

    
    /** Changes each "null" value of a list of arrays filled with Strings to "".
     * @param cantClientes The quantity of customers in the system
     * @param cantPeliculas The quantity of movies in the system
     * @param salas List with the movie theaters
     * @param matrizEntradasCliente List of Arrays that contain each customer's movie ticket
     */
    public static void formatearMatriz (int cantClientes, int cantPeliculas, String[] salas, String[][][] matrizEntradasCliente){
        for (int i = 0; i < cantClientes; i++){
            for (int j = 0; j < cantPeliculas; j++){
                for (int k = 0; k < salas.length; k++){
                    matrizEntradasCliente[i][j][k] = "";
                }
            }
            
        }
    }

    
    /** Identifies the positions in which the movie chair that the customer owns for a show is located. If it's not there, it will return a -1 on each position.
     * @param matrizSalas List of Arrays that contain the movie theaters's chair arrangement
     * @param asiento The position in which the chair that the customer owns for the movie show is located
     * @param pFuncion The position in which the show of the movie that the customer wants to buy a/the ticket(s) is saved.
     * @return int[] The positions in which the movie chair that the customer owns is located. (pos[0] is the row, pos[1] is the column)
     */
    public static int[] checkAsiento(String[][][] matrizSalas, String asiento, int pFuncion){
        int[] posAsientos = new int [2];
        for (int j = 0; j < 10; j++) {
            for (int k = 0; k < 30; k++){
                if (asiento.equals(matrizSalas[pFuncion][j][k])){
                    posAsientos[0] = j;
                    posAsientos[1] = k;
                    return posAsientos;
                }
            }
        
        }
        posAsientos[0] = -1;
        posAsientos[1] = -1;
        return posAsientos;
    }
    
}
