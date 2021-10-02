public class experimentoborrar {
    public static void main(String[] args) {
        
        String[] salas = {"1M","1T","2M","2T","3M","3T"};
        String[][][] matrizSalas = new String[salas.length][10][30];
        String[] filasAsientos = {"A","B","C","D","E","F","G","H","I","J"};
        String[] columnasAsientos = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16",
        "17","18","19","20","21","22","23","24","25","26","27","28","29","30"};
        String prueba = "Rapidos y furiosos";
        String pruebaformat = formatearRut(prueba);
        System.out.println(pruebaformat);

        rellenarMatrices(matrizSalas, salas, filasAsientos, columnasAsientos);

    }

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

    public static String formatearRut(String rut) 
    {          
        String RUTFormateado = rut.replaceAll("\\p{Punct}", "");
        String RUTFormateado2 = RUTFormateado.toLowerCase();
        return RUTFormateado2;
        
    }
}
