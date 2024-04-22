package org.example.butacas.servicios

import com.github.michaelbull.result.Result
import org.example.butacas.errors.ButacaError
import org.example.butacas.models.Butaca
import java.io.File

interface ButacaService {
    fun getFromFile(file : File) : Result<List<Butaca>, ButacaError>
}