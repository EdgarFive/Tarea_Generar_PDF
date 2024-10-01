package umg.principal.reportes;

import umg.principal.DaseDatos.Service.ProductoService;
import umg.principal.DaseDatos.model.Producto;

import javax.swing.*;
import java.util.List;

public class pruebas {

    //Prueba para commit
        public static void main(String[] args) {
            try{


                String[] eeopciones = {"Todos los productos","Precios menores a Q100.00", "Productos con menos de 30 existencias", "Productos Con precios entre 200 y 400", "Productos ordenandos de mayor a menor precio", "Productos de menor a mayor existencias"};

                JComboBox<String> combo = new JComboBox<>(eeopciones);

                int eenum = JOptionPane.showConfirmDialog(null, combo, "Reporte de productos", JOptionPane.OK_CANCEL_OPTION);

                if (eenum == JOptionPane.OK_OPTION) {
                    String eeopccion_seleccionada = (String) combo.getSelectedItem();
                    String consulta = new ProductoService().opteneropciopon(eeopccion_seleccionada);

                    // List<Producto> prod = new ProductoService().obtenerTodosLosProductos();
                    List<Producto> prod = new ProductoService().obtenerGenericos(consulta);


                    new PdfReport().generateProductReport(prod, "C:\\Users\\GMG\\Desktop\\PROGRAMACIÓN (Programas)\\0. JAVA-AA\\0. PDF Guardados\\reporte.pdf");

                    JOptionPane.showMessageDialog(null, "Reporte de Productos Creados en: C:\\Users\\GMG\\Desktop\\PROGRAMACIÓN (Programas)\\0. JAVA-AA\\0. PDF Guardados");

                }

                /*
               // List<Producto> prod = new ProductoService().obtenerTodosLosProductos();
                List<Producto> prod = new ProductoService().obtenerGenericos("cantidad < 30;");


                new PdfReport().generateProductReport(prod, "C:\\Users\\GMG\\Desktop\\PROGRAMACIÓN (Programas)\\0. JAVA-AA\\0. PDF Guardados\\reporte.pdf");

                JOptionPane.showMessageDialog(null, "Reporte de Productos Creados en: C:\\Users\\GMG\\Desktop\\PROGRAMACIÓN (Programas)\\0. JAVA-AA\\0. PDF Guardados");


                 */
            }catch  (Exception e){
                System.out.println("Error Mamalon: "+e);

            }
        }
}
