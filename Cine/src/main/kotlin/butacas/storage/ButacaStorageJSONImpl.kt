package org.example.butacas.storage

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.butacas.errors.ButacaError
import org.example.butacas.models.Butaca
import java.io.File

class ButacaStorageJSONImpl : ButacaStorage {
    override fun cargar(file: File): Result<List<Butaca>, ButacaError> {
        return Ok(emptyList())
        /*No está implementado*/
    }

    override fun exportar(list: List<Butaca>): Boolean {
        TODO("Not yet implemented")
    }
}