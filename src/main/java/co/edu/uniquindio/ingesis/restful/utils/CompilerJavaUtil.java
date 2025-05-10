package co.edu.uniquindio.ingesis.restful.utils;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
public class CompilerJavaUtil {
    private static final String CLASS_NAME = "DynamicClass";
    private static final String CLASS_TEMPLATE = """
            public class DynamicClass {
                public static void main(String[] args) {
                    %s
                }
            }
            """;

    public static String execute(String code) throws Exception {
        String sourceCode = String.format(CLASS_TEMPLATE, code);
        Path sourceFilePath = Files.createTempFile(CLASS_NAME, ".java");

        // Escribir el código en un archivo temporal
        Files.writeString(sourceFilePath, sourceCode);

        // Obtener el compilador de Java
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("No Java compiler available.");
        }

        // Configurar compilación
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(sourceFilePath.toFile());
        boolean success = compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();
        fileManager.close();

        if (!success) {
            throw new IllegalArgumentException("Compilation failed.");
        }

        // Cargar la clase compilada
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{sourceFilePath.getParent().toUri().toURL()});
        Class<?> compiledClass = Class.forName(CLASS_NAME, true, classLoader);

        // Ejecutar el método main y capturar la salida
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Method mainMethod = compiledClass.getMethod("main", String[].class);
        mainMethod.invoke(null, (Object) new String[]{});

        // Restaurar salida y retornar resultado
        System.setOut(originalOut);
        return outputStream.toString().trim();
    }
}
