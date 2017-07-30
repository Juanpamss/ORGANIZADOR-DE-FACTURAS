/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectolibres;

/**
 *
 * @author jairo
 */
public class TipoItem {
    
    String tipo;
    double total;

    public TipoItem(String tipo, double total) {
        this.tipo = tipo;
        this.total = total;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "TipoItem{" + "tipo=" + tipo + ", total=" + total + '}';
    }
    
    
}
