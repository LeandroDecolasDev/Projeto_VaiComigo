package com.vaic.vaic_springboot.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OSRMClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public Object calcularRota(
            double origemLat,
            double origemLng,
            double destinoLat,
            double destinoLng
    ) {

        String url = String.format(
                "https://router.project-osrm.org/route/v1/driving/%f,%f;%f,%f" +
                        "?overview=full&geometries=geojson",
                origemLng, origemLat, destinoLng, destinoLat
        );

        return restTemplate.getForObject(url, Object.class);
    }

}
