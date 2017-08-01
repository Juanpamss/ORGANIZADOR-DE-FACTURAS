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
    
    public String tema;
    public String [] cabezera;
    public Object [][] datos;

    public SeccionReporte(String tema,String[] cabezera, Object[][] datos) {
        this.tema=tema;
        this.cabezera = cabezera;
        this.datos = datos;
    }
    
    
}
