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
public class SeccionReporte {
    
    public String [] cabezera;
    public Object [][] datos;

    public SeccionReporte(String[] cabezera, Object[][] datos) {
        this.cabezera = cabezera;
        this.datos = datos;
    }
    
    
}
