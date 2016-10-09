package com.example.jaen.proyecto;

/**
 * Created by jaen on 14/09/2015.
 */
public class Factura {

    private int nFactura;
    private Cliente cliente;
    private String lineasPedido;
    private int iva;
    private int total;

    /**
     * Constructor class Factura.
     * @param nFactura
     * @param cliente
     * @param lineasPedido
     * @param iva
     * @param total
     */
    public Factura(int nFactura, Cliente cliente, String lineasPedido, int iva, int total) {
        this.nFactura = nFactura;
        this.cliente = cliente;
        this.lineasPedido = lineasPedido;
        this.iva = iva;
        this.total = total;
    }

    // Getters & Setters

    public int getnFactura() {
        return nFactura;
    }

    public void setnFactura(int nFactura) {
        this.nFactura = nFactura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(String lineasPedido) {
        this.lineasPedido = lineasPedido;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
