package org.example.butacas.servicios

import com.github.michaelbull.result.*
import org.example.butacas.errors.ButacaError
import org.example.butacas.models.Butaca
import org.example.butacas.repositorio.ButacaRepositorio
import org.example.butacas.storage.ButacaStorage
import org.example.butacas.validator.ButacaValidator
import org.example.config.Config
import org.example.database.manager.logger
import java.io.File
import java.time.LocalDateTime

class ButacaServiceImpl(
    var butacaRepositorio: ButacaRepositorio,
    var butacaStorageOut: ButacaStorage,
    var butacaStorageIn: ButacaStorage,
    var butacaValidator: ButacaValidator,
    val config : Config
) : ButacaService {

    override fun findAll(): Result<List<Butaca>, ButacaError> {
        val result = butacaRepositorio.findAll()
        if (result.isNotEmpty()) return Ok(result)
        else return Err(ButacaError.ButacaStorageError("No hay ninguna butaca en la base de datos"))
    }

    override fun findById(id: String): Result<Butaca, ButacaError> {
        val butaca = butacaRepositorio.findById(id)
        return if (butaca != null) {
            Ok(butaca)
        } else {
            Err(ButacaError.ButacaNotFoundError("La butaca con ID $id no existe"))
        }
    }


    override fun update(id: String, butaca: Butaca): Result<Butaca, ButacaError> {
        val existingButaca = butacaRepositorio.findById(id)
        return if (existingButaca != null) {
            val updatedButaca = butaca.copy(id = id)
            val result = butacaRepositorio.update(id, updatedButaca)
            if (result != null) {
                Ok(updatedButaca)
            } else {
                Err(ButacaError.ButacaStorageError("No se pudo actualizar la butaca"))
            }
        } else {
            Err(ButacaError.ButacaNotFoundError("La butaca con ID $id no existe"))
        }
    }

    override fun findAllByDate(date: LocalDateTime): Result<List<Butaca>, ButacaError> {
        val result = butacaRepositorio.findAllBasedOnDate(date)
        if (result.isNotEmpty()) return Ok(result)
        else return Err(ButacaError.ButacaStorageError("No hay ninguna butaca en la base de datos"))
    }

    override fun exportAllToFile(date: LocalDateTime): Result<Unit, ButacaError> {
        val list = butacaRepositorio.findAllBasedOnDate(date)
        if (list.isEmpty()) return Err(
            ButacaError.ButacaStorageError("No hay butacas creadas antes de ${date.dayOfMonth}/${date.monthValue}/${date.year}")
        )else return butacaStorageOut.exportar(list)
    }

    override fun cargarButacas(): Result<Unit, ButacaError> {
        logger.debug { "Importando datos de butacas" }
        val url = ClassLoader.getSystemResource(config.butacaSampleFile)
        if (url != null){
            butacaStorageIn
                .cargar(File(url.toURI()))
                .onSuccess {
                    it.forEach {
                        butacaValidator.validate(it).onSuccess {
                            butacaRepositorio.save(it)
                            logger.debug { "Añadida la butaca con id: ${it.id}" }
                        }.onFailure { logger.debug { it.message } }
                    }
                }
        }else return Err(ButacaError.ButacaStorageError("No se pudo leer el fichero correctamente"))
        return Ok(Unit)
    }

}