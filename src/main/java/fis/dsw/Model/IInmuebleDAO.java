public interface IInmuebleDAO {
    void guardar(Inmueble inmueble);
    Inmueble buscarPorCodigo(String codigo);
    List<Inmueble> listarTodo();
}