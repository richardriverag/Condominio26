import java.util.List;
import java.util.ArrayList;

public class Deuda {
    private String idDeuda;
    private String motivo;
    private double valor;
    private Date fechaMaximaPago;
    private String estado;

    private List<Pago> pago = new ArrayList<>();

}