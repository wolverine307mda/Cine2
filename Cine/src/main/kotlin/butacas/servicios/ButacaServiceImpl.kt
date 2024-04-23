package org.example.butacas.servicios

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.butacas.errors.ButacaError
import org.example.butacas.models.Butaca
import org.example.butacas.repositorio.ButacaRepositorio
import org.example.butacas.storage.ButacaStorage
import org.example.butacas.validator.ButacaValidator
import org.koin.core.annotation.Singleton
import java.io.File

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

    override fun findById(id : String): Result<Butaca, ButacaError> {
        TODO("Not yet implemented")
    }
}