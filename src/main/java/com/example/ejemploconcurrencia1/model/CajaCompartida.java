package com.example.ejemploconcurrencia1.model;

public class CajaCompartida {


    private int cantidadVentas = 0;
    private int totalRecaudado = 0;




    /*Se utiliza syncronized para que las acciones no se peleen por recursos y se arme un caos de que uno realiza
    * una accion y el otro no, pero con el syncronized eso no ocurre, debido a que al hacerlo de forma
    * simultanea las acciones van en sus hilos respectivos*/
    public synchronized void registrarVenta(int monto){
        cantidadVentas++;
        totalRecaudado += monto;

    }


    public synchronized int getCantidadVentas() {
        return cantidadVentas;
    }

    public synchronized int getTotalRecaudado() {
        return totalRecaudado;
    }
}

