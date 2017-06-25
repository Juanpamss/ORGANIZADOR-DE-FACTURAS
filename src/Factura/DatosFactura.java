/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factura;

/**
 *
 * @author jairo
 */
public class DatosFactura {

    
    //PROVEEDOR
    private String proveedor_nombre;
    private String proveedor_ruc;
    private String proveedor_direccion;
    private String proveedor_ciudad;
    //USUARIO
    private String cliente_ci;
    private String cliente_nombre;
    //Factura
    private String factura_codigo;
    private String factura_fecha;
    //gastos por sector (vivienda,salud,bla bla) se los saca manualmente
    
    private double factura_total_sin_iva;
    private double factura_iva;
    private double factura_total_con_iva;
    
    private double total_vivienda;
    private double total_educacion;
    private double total_salud;
    private double total_vestimenta;
    private double total_alimentacion;
    private double total_otros;
    
    
    private Object [][] datosProductos;

    public String getProveedor_nombre() {
        return proveedor_nombre;
    }

    public void setProveedor_nombre(String proveedor_nombre) {
        this.proveedor_nombre = proveedor_nombre;
    }

    public String getProveedor_ruc() {
        return proveedor_ruc;
    }

    public void setProveedor_ruc(String proveedor_ruc) {
        this.proveedor_ruc = proveedor_ruc;
    }

    public String getProveedor_direccion() {
        return proveedor_direccion;
    }

    public void setProveedor_direccion(String proveedor_direccion) {
        this.proveedor_direccion = proveedor_direccion;
    }

    public String getProveedor_ciudad() {
        return proveedor_ciudad;
    }

    public void setProveedor_ciudad(String proveedor_ciudad) {
        this.proveedor_ciudad = proveedor_ciudad;
    }

    public String getCliente_ci() {
        return cliente_ci;
    }

    public void setCliente_ci(String cliente_ci) {
        this.cliente_ci = cliente_ci;
    }

    public String getCliente_nombre() {
        return cliente_nombre;
    }

    public void setCliente_nombre(String cliente_nombre) {
        this.cliente_nombre = cliente_nombre;
    }

    public String getFactura_codigo() {
        return factura_codigo;
    }

    public void setFactura_codigo(String factura_codigo) {
        this.factura_codigo = factura_codigo;
    }

    public String getFactura_fecha() {
        return factura_fecha;
    }

    public void setFactura_fecha(String factura_fecha) {
        this.factura_fecha = factura_fecha;
    }

    public double getFactura_total_sin_iva() {
        return factura_total_sin_iva;
    }

    public void setFactura_total_sin_iva(double factura_total_sin_iva) {
        this.factura_total_sin_iva = factura_total_sin_iva;
    }

    public double getFactura_iva() {
        return factura_iva;
    }

    public void setFactura_iva(double factura_iva) {
        this.factura_iva = factura_iva;
    }

    public double getFactura_total_con_iva() {
        return factura_total_con_iva;
    }

    public void setFactura_total_con_iva(double factura_total_con_iva) {
        this.factura_total_con_iva = factura_total_con_iva;
    }

    public double getTotal_vivienda() {
        return total_vivienda;
    }

    public void setTotal_vivienda(double total_vivienda) {
        this.total_vivienda = total_vivienda;
    }

    public double getTotal_educacion() {
        return total_educacion;
    }

    public void setTotal_educacion(double total_educacion) {
        this.total_educacion = total_educacion;
    }

    public double getTotal_salud() {
        return total_salud;
    }

    public void setTotal_salud(double total_salud) {
        this.total_salud = total_salud;
    }

    public double getTotal_vestimenta() {
        return total_vestimenta;
    }

    public void setTotal_vestimenta(double total_vestimenta) {
        this.total_vestimenta = total_vestimenta;
    }

    public double getTotal_alimentacion() {
        return total_alimentacion;
    }

    public void setTotal_alimentacion(double total_alimentacion) {
        this.total_alimentacion = total_alimentacion;
    }
    
    public double getTotal_otros() {
        return total_otros;
    }

    public void setTotal_otros(double total_otros) {
        this.total_otros = total_otros;
    }

    public Object[][] getDatosProductos() {
        return datosProductos;
    }

    public void setDatosProductos(Object[][] datosProductos) {
        this.datosProductos = datosProductos;
    }
}