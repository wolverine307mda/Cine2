package org.example.cuenta.repositorio

import org.example.cuenta.models.Cuenta
import org.example.database.manager.DataBaseManager
import org.koin.core.annotation.Singleton

@Singleton
class CuentaRepositorioImpl(
    val dataBaseManager: DataBaseManager
) : CuentaRepositorio {
    override fun findAll(): List<Cuenta> {
        TODO("Not yet implemented")
    }

    override fun findById(id: String): Cuenta? {
        TODO("Not yet implemented")
    }

    override fun save(cuenta: Cuenta): Cuenta? {
        TODO("Not yet implemented")
    }

    override fun update(id: String, cuenta: Cuenta): Cuenta? {
        TODO("Not yet implemented")
    }

    override fun delete(id: String): Cuenta? {
        TODO("Not yet implemented")
    }
}