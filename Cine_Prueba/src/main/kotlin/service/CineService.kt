import org.example.models.Complemento
import org.example.models.Venta
import org.example.repository.ButacaRepository
import org.example.repository.ComplementoRepository
import org.example.repository.VentaRepository

class CinemaService(
    private val butacaRepository: ButacaRepository,
    private val complementoRepository: ComplementoRepository,
    private val ventaRepository: VentaRepository
) {
    fun comprarEntrada(idButaca: Int, idSocio: String, fechaCompra: String, complementos: List<Complemento>) {
        ventaRepository.realizarVenta(Venta(0, idButaca, idSocio, fechaCompra, complementos))
    }

    fun devolverEntrada(idVenta: Int) {
        ventaRepository.devolverEntrada(idVenta)
    }

}