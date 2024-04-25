package org.example.butacas.servicios

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onSuccess
import org.example.butacas.errors.ButacaError
import org.example.butacas.models.Butaca
import org.example.butacas.repositorio.ButacaRepositorio
import org.example.butacas.storage.ButacaStorage
import org.example.butacas.validator.ButacaValidator
import org.koin.core.annotation.Singleton
import java.io.File
import java.time.LocalDateTime

@Singleton
class ButacaServiceImpl(
    var butacaRepositorio: ButacaRepositorio,
    var butacaStorage: ButacaStorage,
    var butacaValidator: ButacaValidator
) : ButacaService {
    override fun getFromFile(file : File): Result<List<Butaca>, ButacaError> {
        return butacaStorage.cargar(file)
    }

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
        )else return butacaStorage.exportar(list)
    }

}