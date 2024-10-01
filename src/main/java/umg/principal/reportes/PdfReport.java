package umg.principal.reportes;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import umg.principal.DaseDatos.model.Producto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class PdfReport {
    private static final Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLDITALIC);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

    public void generateProductReport(List<Producto> productos, String outputPath) throws DocumentException, IOException {
        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();

        addTitle(document);
        addProductTable(document, productos);

        document.close();
    }

    private void addTitle(Document document) throws DocumentException {
        Paragraph title = new Paragraph("Reportando Productos En PDF", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);
    }

    private void addProductTable(Document document, List<Producto> productos) throws DocumentException {
        PdfPTable table = new PdfPTable(5); // 4 columnas para id, descripción, origen y precio
        table.setWidthPercentage(100);
        addTableHeader(table);
        addRows(table, productos);
        document.add(table);
    }

    private void addTableHeader(PdfPTable table) {
       // Este código utiliza la clase Stream de Java para crear un flujo de datos con los títulos de las columnas de una tabla PDF. Luego,
        // para cada título de columna, se crea una celda de encabezado en la tabla con ciertas propiedades (color de fondo, ancho del borde, y texto).



        Stream.of("ID", "Descripción", "Origen", "Precio", "Cantidad")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.GREEN);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle, HEADER_FONT));
                    table.addCell(header);
                });

        //Stream.of("ID", "Descripción", "Origen", "Precio"):
        //Crea un flujo (Stream) de datos con los elementos "ID", "Descripción", "Origen", y "Precio".


//        Este código utiliza la clase Stream de Java para crear un flujo de datos con los títulos de las columnas de una tabla PDF. Luego, para cada título de columna, se crea una celda de encabezado en la tabla con ciertas propiedades (color de fondo, ancho del borde, y texto).
//                Explicación del Código
//        Stream.of("ID", "Descripción", "Origen", "Precio"):
//        Crea un flujo (Stream) de datos con los elementos "ID", "Descripción", "Origen", y "Precio".
//.forEach(columnTitle -> { ... }):
//        Para cada elemento en el flujo (en este caso, cada título de columna), ejecuta el bloque de código dentro de las llaves { ... }.
//        Dentro del bloque forEach:
//        PdfPCell header = new PdfPCell();: Crea una nueva celda para la tabla PDF.
//        header.setBackgroundColor(BaseColor.LIGHT_GRAY);: Establece el color de fondo de la celda a gris claro.
//                header.setBorderWidth(2);: Establece el ancho del borde de la celda a 2 puntos.
//                header.setPhrase(new Phrase(columnTitle, HEADER_FONT));: Establece el texto de la celda con el título de la columna y la fuente de encabezado.
//                table.addCell(header);: Añade la celda a la tabla.
//¿Qué es Stream.of?
//                Stream.of es un método estático de la clase Stream en Java que se utiliza para crear un flujo (Stream) a partir de una secuencia de elementos. En este caso, se está utilizando para crear un flujo de cadenas de texto ("ID", "Descripción", "Origen", "Precio").
//        Código

    } //Fin de addTableHeader

    private void addRows(PdfPTable table, List<Producto> productos) {
        for (Producto producto : productos) {
            table.addCell(new Phrase(String.valueOf(producto.getIdProducto()), NORMAL_FONT));
            table.addCell(new Phrase(producto.getDescripcion(), NORMAL_FONT));
            table.addCell(new Phrase(producto.getOrigen(), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf( producto.getPrecio()), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf( producto.getCantidad()), NORMAL_FONT));

        }
    }
}