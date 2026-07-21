package fis.dsw.sgc.finanzas.service;

import fis.dsw.sgc.finanzas.model.Pago;

public interface IPagoFactory {
    Pago crearPago(int idDeuda, double valor, String metodo); // Interface que define el método para crear[cite: 2]
}