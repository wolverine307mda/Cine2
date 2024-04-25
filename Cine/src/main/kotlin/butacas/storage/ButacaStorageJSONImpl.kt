package org.example.butacas.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.butacas.dto.ButacaDto
import org.example.butacas.errors.ButacaError
import org.example.butacas.mappers.toButacaDTO
import org.example.butacas.models.Butaca
import org.koin.core.annotation.Singleton
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime

@Singleton
class ButacaStorageJSONImpl : ButacaStorage {
    init {
        Files.createDirectories(Paths.get("data","butacas"))
    }

    override fun cargar(file: File): Result<List<Butaca>, ButacaError> {
        return Err(ButacaError.ButacaStorageError("Esta funcion no esta implementada"))
        /*No está implementado*/
    }

    override fun exportar(list: List<Butaca>): Result<Unit,ButacaError> {
        val json = Json { prettyPrint = true; ignoreUnknownKeys = true }
        val dateTime = LocalDateTime.now()
        println()
            var file = File("data${File.separator}butacas","butacas${dateTime.dayOfMonth}-${dateTime.monthValue}-${dateTime.year} ${dateTime.hour}_${dateTime.minute}_${dateTime.second}.json")
            file.createNewFile()
            val butacasDTO = list.map {
                it.toButacaDTO()
            }
            file.writeText(json.encodeToString<List<ButacaDto>>(butacasDTO))
        return Ok(Unit)
    }
}