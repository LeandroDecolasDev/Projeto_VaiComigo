package com.vaic.vaic_springboot.services;

import com.vaic.vaic_springboot.client.OSRMClient;
import org.springframework.stereotype.Service;

@Service
public class RotaService {

    private final OSRMClient osrmClient;

    public RotaService(OSRMClient osrmClient) {
        this.osrmClient = osrmClient;
    }

    public Object calcularRota(
            double origemLat,
            double origemLng,
            double destinoLat,
            double destinoLng
    ) {
        return osrmClient.calcularRota(
                origemLat, origemLng, destinoLat, destinoLng
        );
    }
}
