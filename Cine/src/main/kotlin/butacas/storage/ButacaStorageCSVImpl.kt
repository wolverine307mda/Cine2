package org.example.butacas.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.butacas.errors.ButacaError
import org.example.butacas.mappers.elegirEstado
import org.example.butacas.mappers.elegirOcupamiento
import org.example.butacas.mappers.elegirTipo
import org.example.butacas.models.Butaca
import org.example.butacas.validator.ButacaValidator
import org.example.database.manager.logger
import org.koin.core.annotation.Singleton
import java.io.File

@Singleton
class ButacaStorageCSVImpl(
    var butacaValidator: ButacaValidator
) : ButacaStorage {

    override fun exportar(list: List<Butaca>): Boolean {
        println("Una función para exportar una lista de butacas en CSV")
        return true
    }

    override fun cargar(file: File) : Result<List<Butaca>, ButacaError>{
        try {
            return Ok(
                file.readLines().map {
                    val butaca = it.split(',')
                    Butaca(
                        id = butaca[0],
                        estado = elegirEstado(butaca[1]),
                        ocupamiento = elegirOcupamiento(butaca[2]),
                        tipo = elegirTipo(butaca[3])
                    )
                }
            )
        }catch (e : Exception){
            logger.debug { "Hubo un error al cargar las butacas del archivo ${file.name}" }
            return Err(ButacaError.ButacaStorageError("Hubo un error al cargar las butacas del archivo ${file.name}"))
        }
    }
}