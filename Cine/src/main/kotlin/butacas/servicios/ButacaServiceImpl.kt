package org.example.butacas.servicios

import com.github.michaelbull.result.Result
import org.example.butacas.errors.ButacaError
import org.example.butacas.models.Butaca
import org.example.butacas.repositorio.ButacaRepositorio
import org.example.butacas.storage.ButacaStorage
import org.koin.core.annotation.Singleton
import java.io.File

@Singleton
class ButacaServiceImpl(
    var butacaRepositorio: ButacaRepositorio,
    var butacaStorage: ButacaStorage
) : ButacaService {
    override fun getFromFile(file : File): Result<List<Butaca>, ButacaError> {
        return butacaStorage.cargar(file)
    }
}