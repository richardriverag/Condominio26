package fis.dsw.sgc.app;

import fis.dsw.sgc.inmuebles.dao.InmuebleDAOMySQL;
import fis.dsw.sgc.inmuebles.model.Inmueble;

public class TestDB {
    public static void main(String[] args) {
        try {
            InmuebleDAOMySQL dao = new InmuebleDAOMySQL();
            System.out.println("Inmuebles (Resumen): " + dao.buscarResumen("").size());
            
            Inmueble inmueble = new Inmueble();
            inmueble.setIdEdificio(1);
            inmueble.setIdTipoInmueble(1);
            inmueble.setCodigo("TEST-001");
            inmueble.setPiso(1);
            inmueble.setNumero("101");
            inmueble.setAreaM2(100.0);
            inmueble.setNumeroHabitaciones(2);
            inmueble.setNumeroBanos(1);
            inmueble.setDescripcion("Test");
            inmueble.setDisponibleAlquiler(true);
            inmueble.setDisponibleVenta(true);
            inmueble.setEstado("DISPONIBLE");
            
            int id = dao.guardar(inmueble);
            System.out.println("Guardado con ID: " + id);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
