package org.example.ventas.servicio

import com.github.michaelbull.result.*
import org.example.butacas.storage.VentaStorage
import org.example.ventas.errors.VentaError
import org.example.ventas.models.Venta
import org.example.ventas.respositorio.VentaRepositorio
import org.koin.core.annotation.Singleton
import java.time.LocalDateTime

@Singleton
class VentaServicioImpl (
    var ventaRepositorio: VentaRepositorio,
    var ventaStorage: VentaStorage
) : VentaServicio {
    override fun save(venta: Venta): Result<Venta, VentaError> {
        ventaRepositorio.save(venta)?.let {
            return Ok(it)
        }
        return Err(VentaError.VentaStorageError("No se ha podido guardar la venta con id: ${venta.id}"))
    }

    override fun findAll(): Result<List<Venta>, VentaError> {
        return Ok(ventaRepositorio.findAll().filter { !it.isDeleted })
    }

    override fun findById(id: String): Result<Venta, VentaError> {
        ventaRepositorio.findById(id)?.let {
            return Ok(it)
        }
        return Err(VentaError.VentaStorageError("No existe ninguna venta con el id: $id en la base de datos"))
    }

    override fun update(id: String, venta: Venta): Result<Venta, VentaError> {
        ventaRepositorio.update(id,venta)?.let {
            return Ok(it)
        }
        return Err(VentaError.VentaStorageError("No existe ninguna venta con el id: $id en la base de datos"))
    }

    override fun findAllByDate(date: LocalDateTime): Result<List<Venta>, VentaError> {
        return Ok(ventaRepositorio.findAllByDate(date))
    }

    override fun exportVenta(venta: Venta): Result<Unit, VentaError>{
        ventaStorage.exportar(venta).onFailure {
            return Err(it)
        }
        return Ok(Unit)
    }

    override fun getAllVentasByDate(date : LocalDateTime): Result<List<Venta>, VentaError> {
        return Ok(ventaRepositorio.findAllByDate(date))
    }

    override fun delete(id: String): Result<Venta, VentaError> {
        ventaRepositorio.delete(id)?.let {
            return Ok(it)
        }
        return Err(VentaError.VentaStorageError("La venta que está intentando borrar no existe"))
    }


}