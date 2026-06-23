import java.util.LinkedList;
import java.util.Scanner;

public class ProyectoHashing {

    static class TablaHashResiduo {
        private Integer[] tabla;
        private int tamano;
        private int colisionesTotales = 0;

        public TablaHashResiduo(int tamano) {
            this.tamano = tamano;
            this.tabla = new Integer[tamano];
        }

        public void insertar(int Valor) {
            long inicio = System.nanoTime();
            int indice = Valor % tamano;
            int iteraciones = 0;

            while (tabla[indice] != null) {
                colisionesTotales++;
                indice = (indice + 1) % tamano;
                iteraciones = iteraciones + 1;
                if (iteraciones == tamano) {
                    System.out.println("La tabla está llena. No se insertó: " + Valor);
                    return;
                }
            }
            tabla[indice] = Valor;
            long fin = System.nanoTime();
            System.out.println("Insertado: " + Valor + " en índice: " + indice + " | Tiempo(ns): " + (fin - inicio));
        }

        public void buscar(int Valor) {
            long inicio = System.nanoTime();
            int indice = Valor % tamano;
            int iteraciones = 0;

            while (tabla[indice] != null) {
                if (tabla[indice] == Valor) {
                    long fin = System.nanoTime();
                    System.out.println("Encontrado: " + Valor + " en índice: " + indice + " | Tiempo(ns): " + (fin - inicio));
                    return;
                }
                indice = (indice + 1) % tamano;
                iteraciones++;
                if (iteraciones == tamano) break;
            }
            System.out.println("No se encontró la Valor: " + Valor);
        }

        public int getColisionesTotales() { return colisionesTotales; }
    }

    static class TablaHashCuadradoMedio {
        private LinkedList<Integer>[] tabla;
        private int tamano;
        private int colisionesTotales = 0;

        @SuppressWarnings("unchecked")
        public TablaHashCuadradoMedio(int tamano) {
            this.tamano = tamano;
            this.tabla = new LinkedList[tamano];
            for (int i = 0; i < tamano; i++) {
                tabla[i] = new LinkedList<>();
            }
        }

        private int hashCuadradoMedio(int Valor) {
            long cuadrado = (long) Valor * Valor;
            String strCuadrado = String.valueOf(cuadrado);
            int mid = strCuadrado.length() / 2;
            int inicio = Math.max(0, mid - 1);
            int fin = Math.min(strCuadrado.length(), mid + 1);
            String medio = strCuadrado.substring(inicio, fin);
            if(medio.isEmpty()) {
                return 0;
            }
            return Integer.parseInt(medio) % tamano;
        }

        public void insertar(int Valor) {
            long inicio = System.nanoTime();
            int indice = hashCuadradoMedio(Valor);

            if (!tabla[indice].isEmpty()) {
                colisionesTotales++;
            }
            tabla[indice].add(Valor);
            long fin = System.nanoTime();
            System.out.println("Insertado: " + Valor + " en lista del índice: " + indice + " | Tiempo(ns): " + (fin - inicio));
        }

        public void buscar(int Valor) {
            long inicio = System.nanoTime();
            int indice = hashCuadradoMedio(Valor);

            if (tabla[indice].contains(Valor)) {
                long fin = System.nanoTime();
                System.out.println("Encontrado: " + Valor + " en lista del índice: " + indice + " | Tiempo(ns): " + (fin - inicio));
            } else {
                System.out.println("No se encontró la Valor: " + Valor);
            }
        }

        public int getColisionesTotales() { return colisionesTotales; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== SISTEMA DE HASHING ===");
        System.out.print("Ingrese el tamaño de la tabla hash: ");
        int tamano = sc.nextInt();

        TablaHashResiduo tablaResiduo = new TablaHashResiduo(tamano);
        TablaHashCuadradoMedio tablaCuadradoMedio = new TablaHashCuadradoMedio(tamano);

        int opcion;
        do {
            System.out.println("\n1. Insertar Valor");
            System.out.println("2. Buscar Valor");
            System.out.println("3. Ver total de colisiones");
            System.out.println("4. Salir");
            System.out.print("Seleccione opción: ");
            opcion = sc.nextInt();

            if (opcion == 1) {
                System.out.print("Ingrese la Valor a insertar: ");
                int Valor = sc.nextInt();
                System.out.println("\n--- Ejecución Hash Residuo ---");
                tablaResiduo.insertar(Valor);
                System.out.println("--- Ejecución Hash Cuadrado Medio ---");
                tablaCuadradoMedio.insertar(Valor);
            } else if (opcion == 2) {
                System.out.print("Ingrese la Valor a buscar: ");
                int Valor = sc.nextInt();
                System.out.println("\n--- Búsqueda Hash Residuo ---");
                tablaResiduo.buscar(Valor);
                System.out.println("--- Búsqueda Hash Cuadrado Medio ---");
                tablaCuadradoMedio.buscar(Valor);
            } else if (opcion == 3) {
                System.out.println("Colisiones Hash Residuo: " + tablaResiduo.getColisionesTotales());
                System.out.println("Colisiones Hash Cuadrado Medio: " + tablaCuadradoMedio.getColisionesTotales());
            }
        } while (opcion != 4);

        sc.close();
    }
}
