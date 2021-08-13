package com.example.BasketService.models.dto;

import lombok.Data;


public class BasketInfo {
    private Double araToplam;

    private Double kargo;

    private Double toplam;


    public BasketInfo(Double araToplam, Double kargo) {
        this.araToplam = araToplam;
        this.kargo = kargo;
        this.toplam = araToplam + kargo;
    }

    public Double getAraToplam() {
        return araToplam;
    }

    public void setAraToplam(Double araToplam) {
        this.araToplam = araToplam;
    }

    public Double getKargo() {
        return kargo;
    }

    public void setKargo(Double kargo) {
        this.kargo = kargo;
    }

    public Double getToplam() {
        return toplam;
    }

    public void setToplam(Double toplam) {
        this.toplam = toplam;
    }
}
