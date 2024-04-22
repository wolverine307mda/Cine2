package org.example.butacas.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import org.example.butacas.errors.ButacaError
import org.example.butacas.models.Butaca
import java.io.File

class ButacaStorageJSONImpl : ButacaStorage {
    override fun cargar(file: File): Result<List<Butaca>, ButacaError> {
        return Err(ButacaError.ButacaStorageError("Esta funcion no esta implementada"))
        /*No está implementado*/
    }

    override fun exportar(list: List<Butaca>): Boolean {
        TODO("Not yet implemented")
    }
}