package com.example.ejemploconcurrencia1.controller;

import com.example.ejemploconcurrencia1.model.CajaCompartida;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

public class VentasController {

    @FXML
    private ProgressBar prgVendedor1;

    @FXML
    private ProgressBar prgVendedor2;

    @FXML
    private ProgressBar prgVendedor3;

    @FXML
    private Label lblVendedor1;

    @FXML
    private Label lblVendedor2;

    @FXML
    private Label lblVendedor3;


    @FXML
    private Label lblVentasRealizadas;

    @FXML
    private Label lblTotalRecaudado;

    @FXML
    private TextArea txtVentasProcesadas;

    @FXML
    private Button btnIniciarVentas;

    @FXML
    private Button btnLimpiar;

    /*Objeto compartido por todos los hilos(vendedores)*/

    private CajaCompartida cajaCompartida;


    @FXML
    private void iniciarVentas(){
        // Solo cuando se inicia una venta, el objeto de caja compartida se debe de crear
        cajaCompartida = new CajaCompartida();

        //Cada ve que se inicia el proceso de ventas, la interfaz se reinicia;
        limpiar();
        btnIniciarVentas.setDisable(true);

        Thread vendedor1 = new Thread(() -> procesarVentas("Vendedor 1", prgVendedor1, lblVendedor1));
        Thread vendedor2 = new Thread(() -> procesarVentas("Vendedor 2", prgVendedor2, lblVendedor2));
        Thread vendedor3 = new Thread(() -> procesarVentas("Vendedor 3", prgVendedor3, lblVendedor3));

        /*Iniciamos la ejecucion de los tres hilos (vendedores)*/

        vendedor1.start();
        vendedor2.start();
        vendedor3.start();

        /*Crear un Thread adicional enccargado de esperar que los tres vendedores terminen*/

        Thread supervisor1 = new Thread(() -> {
            try {
                vendedor1.join();
                vendedor2.join();
                vendedor3.join();

                Platform.runLater(() -> {
                        txtVentasProcesadas.appendText("--- Procesamiento finalizado ---");
                        btnIniciarVentas.setDisable(false);
                    });


            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

            }
        });

        supervisor1.start();




    }

    private void procesarVentas(String vendedor, ProgressBar prgVendedor, Label lblVendedor){
        for (int i = 1; i <= 5; i++){
            try {
                Thread.sleep(1000);
                cajaCompartida.registrarVenta(100);

                int numeroVenta = i;
                double progreso = numeroVenta /5.0;

                Platform.runLater(() -> {
                    prgVendedor.setProgress(progreso);
                    lblVendedor.setText("Procesando venta " + numeroVenta + " de 5");
                    txtVentasProcesadas.appendText(vendedor + " registro una venta de C$ 100\n");
                    actualizarTotales();
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Platform.runLater(() -> {
            lblVendedor.setText("Proceso completado");
        });
    }

    private void actualizarTotales(){
        lblVentasRealizadas.setText(String.valueOf(cajaCompartida.getCantidadVentas()));
        lblTotalRecaudado.setText("C$" + cajaCompartida.getTotalRecaudado());

    }
    private void reiniciarInterfaz(){

        prgVendedor1.setProgress(0);
        prgVendedor2.setProgress(0);
        prgVendedor3.setProgress(0);
        lblVendedor1.setText("Iniciando...");
        lblVendedor2.setText("Iniciando...");
        lblVendedor3.setText("Iniciando...");
        lblTotalRecaudado.setText("C$");
        lblVentasRealizadas.setText("0");
        txtVentasProcesadas.setText("");
    }


    @FXML
    private void limpiar(){
        prgVendedor1.setProgress(0);
        prgVendedor2.setProgress(0);
        prgVendedor3.setProgress(0);
        lblVendedor1.setText("Esperando...");
        lblVendedor2.setText("Esperando...");
        lblVendedor3.setText("Esperando...");
        lblTotalRecaudado.setText("C$0");
        lblVentasRealizadas.setText("0");
        txtVentasProcesadas.setText("");


    }
}
