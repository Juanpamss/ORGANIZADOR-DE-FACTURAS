/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factura;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Juan Pa
 */
public class Cargar_XMLTest {
    
    public Cargar_XMLTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of cargarXml method, of class Cargar_XML.
     */
    @Test
    public void testCargarXml() throws Exception {
        System.out.println("cargarXml");
        String name = "";
        Cargar_XML instance = new Cargar_XML();
        DatosFactura expResult = null;
        DatosFactura result = instance.cargarXml("C:\\Users\\Juan Pa\\Desktop\\facts\\eta.xml");
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenerDatos method, of class Cargar_XML.
     */
    @Test
    public void testObtenerDatos() {
        System.out.println("obtenerDatos");
        String prov_nombre = "";
        String prov_ruc = "";
        String prov_dir = "";
        String cliente_ci = "";
        String cliente_nombre = "";
        String fac_cod = "";
        String fac_fecha = "";
        double fac_total_sin_iva = 0.0;
        double fac_iva = 0.0;
        double fac_total_con_iva = 0.0;
        Object[][] datosProductos = null;
        Cargar_XML instance = new Cargar_XML();
        DatosFactura expResult = null;
        DatosFactura result = instance.obtenerDatos(prov_nombre, prov_ruc, prov_dir, cliente_ci, cliente_nombre, fac_cod, fac_fecha, fac_total_sin_iva, fac_iva, fac_total_con_iva, datosProductos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
