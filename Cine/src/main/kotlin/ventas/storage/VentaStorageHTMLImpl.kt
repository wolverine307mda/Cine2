package org.example.ventas.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.butacas.errors.ButacaError
import org.example.butacas.storage.VentaStorage
import org.example.database.manager.logger
import org.example.ventas.errors.VentaError
import org.example.ventas.models.Venta
import org.koin.core.annotation.Singleton
import java.io.File
import kotlin.math.E

@Singleton
class VentaStorageHTMLImpl : VentaStorage {
    override fun cargar(file: File): Result<List<Venta>, ButacaError>{
        println("No está implementada")
        return Ok(emptyList())
    }

    override fun exportar(venta : Venta) : Result<Unit,VentaError>{
        try {
            var output = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Document</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    ""
            getTableRows(venta)
            "</body>\n" +
                    "</html>"
            return Ok(Unit)
        }catch (e : Exception){
            logger.error { "Hubo un fallo al generar su recibo" }
            return Err(VentaError.VentaStorageError("Hubo un fallo al generar su recibo de la venta con id: ${venta.id}"))
        }
    }

    private fun getTableRows(venta: Venta) : String{
        TODO()
    }

}