package utils;

import model.Person;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableUtils {
    private static List<String> getClassFields(Object object) {
        List<String> allColumnsNames = new ArrayList<>();
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true); // make private fields accessible
                if (field.getName().equals("id")) {
                    allColumnsNames.add(0, field.getName());
                } else if (field.getName().contains("name")) {
                    allColumnsNames.add(1, field.getName());
                } else if (field.getName().contains("gender")) {
                    allColumnsNames.add(2, field.getName());
                } else {
                    allColumnsNames.add(field.getName());
                }
            }
            clazz = clazz.getSuperclass();
        }
        return allColumnsNames;
    }

    // table.addCell(dataAsString);
    private static void populateTable(Object dataAsObject, List<String> allColumns, Table table) {
        Map<String, Object> dataMap = new LinkedHashMap<>();
        if (dataAsObject == null || allColumns == null || table == null) {
            throw new IllegalArgumentException("None of the parameters can be null.");
        }
        Class<?> currentClass = dataAsObject.getClass();
        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    for (String col : allColumns) {
                        if (col.equalsIgnoreCase(field.getName())) {
                            dataMap.put(col, field.get(dataAsObject).toString());
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        // populate
        for (String col : allColumns) {
            table.addCell(dataMap.get(col).toString());
        }
    }

    private static Object castObject(Object object, String className) {
        try {
            // this is a little bit manual, but we will fix it later haha!
            Class<?> clazz = Class.forName("model." + className);
            if (clazz.isInstance(object)) {
                return clazz.cast(object);
            } else {
                throw new IllegalArgumentException("The object is not an instance of the class");

            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("The class is not found");
        }
    }

    public static void renderMenu(List<String> items, String menu) {
        Table table = new Table(2, BorderStyle.UNICODE_BOX_HEAVY_BORDER,
                ShownBorders.SURROUND_HEADER_AND_COLUMNS);
        CellStyle centerStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        int counter = 0;
        table.addCell(menu, centerStyle, 2);
        for (var item : items) {
            table.addCell(++counter + "");
            table.addCell(item);
        }
        System.out.println(table.render());

    }

    public static void renderObjectToTable(List<? extends Person> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("There is no record to render the table");
            return;
        }
        String type = data.get(0).getClass().getSimpleName();
        List<String> allColumns = getClassFields(data.get(0));
//            CellStyle numberStyle = new CellStyle(CellStyle.HorizontalAlign.right);
        Table table = new Table(allColumns.size(), BorderStyle.UNICODE_BOX_HEAVY_BORDER,
                ShownBorders.SURROUND_HEADER_FOOTER_AND_COLUMNS);
        for (String col : allColumns) {
            table.addCell(col);
        }
        //  System.out.println("Here all the columns " + allColumns);
        for (Object dataAsObject : data) {
            populateTable(castObject(dataAsObject, type), allColumns, table);
        }

        table.addCell("Total Records ", allColumns.size() / 2);
        table.addCell(data.size() + "", (int) Math.ceil(allColumns.size() / (float) 2));
        System.out.println(table.render());

    }
}