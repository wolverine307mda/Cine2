package org.example.butacas.servicios

import org.example.butacas.repositorio.ButacaRepositorio
import org.koin.core.annotation.Singleton

@Singleton
class ButacaServiceImpl(
    var butacaRepositorio: ButacaRepositorio
) : ButacaService {
}