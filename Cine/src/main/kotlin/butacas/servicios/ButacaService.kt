package org.example.butacas.servicios

import com.github.michaelbull.result.Result
import org.example.butacas.errors.ButacaError
import org.example.butacas.models.Butaca
import java.io.File

interface ButacaService {
    fun getFromFile(file : File) : Result<List<Butaca>, ButacaError>
    fun findAll() : Result<List<Butaca>, ButacaError>
    fun findById(id : String) : Result<Butaca,ButacaError>
    fun update(id: String, butaca: Butaca): Result<Butaca,ButacaError>
}