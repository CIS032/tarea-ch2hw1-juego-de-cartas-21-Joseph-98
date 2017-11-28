/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoblackjack;
import java.io.*;
import java.util.IllegalFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Andres
 */
public class TextIO {
    
    /* Modificado en noviembre de 2007 para vaciar el búfer de entrada TextIO al cambiar de uno
      * fuente de entrada a otra. Esto corrige un error que permite la entrada de la entrada anterior
      * fuente que se leerá después de que se haya seleccionado la nueva fuente.
     */

    /**
     * El valor devuelto por el método peek () cuando la entrada está al final del archivo.
      * (El valor de esta constante es (char) 0xFFFF.)
     */
    public final static char EOF = (char)0xFFFF; 

    /**
    * El valor devuelto por el método peek () cuando la entrada está al final de la línea.
      * El valor de esta constante es el carácter '\ n'.
     */
    public final static char EOLN = '\n';          // El valor devuelto por peek () cuando está al final de la línea.
    

    /**
     * Después de invocar este método, la entrada se leerá desde la entrada estándar (ya que
      * está en el estado predeterminado). Si un archivo o secuencia era anteriormente la fuente de entrada, ese archivo
      * o la transmisión está cerrada.
     */
    public static void readStandardInput() {
        if (readingStandardInput)
            return;
        try {
            in.close();
        }
        catch (Exception e) {
        }
        emptyBuffer();  // Added November 2007
        in = standardInput;
        inputFileName = null;
        readingStandardInput = true;
        inputErrorCount = 0;
    }
    
    /**
    * Después de invocar este método, la entrada se leerá desde InputStream, siempre que
      * no es nulo Si inputStream es nulo, entonces este método tiene el mismo efecto
      * como llamando a readStandardInput (); es decir, la entrada futura vendrá de la
      * flujo de entrada estándar.
     */
    public static void readStream(InputStream inputStream) {
        if (inputStream == null)
            readStandardInput();
        else
            readStream(new InputStreamReader(inputStream));
    }
    
    /**
     * Después de invocar este método, la entrada se leerá desde InputStream, siempre que
      * no es nulo Si inputStream es nulo, entonces este método tiene el mismo efecto
      * como llamando a readStandardInput (); es decir, la entrada futura vendrá de la
      * flujo de entrada estándar.
     */
    public static void readStream(Reader inputStream) {
        if (inputStream == null)
            readStandardInput();
        else {
            if ( inputStream instanceof BufferedReader)
                in = (BufferedReader)inputStream;
            else
                in = new BufferedReader(inputStream);
            emptyBuffer();  // Added November 2007
            inputFileName = null;
            readingStandardInput = false;
            inputErrorCount = 0;
        }
    }
    
    /**
    * Abre un archivo con un nombre específico para la entrada. Si el nombre del archivo es nulo, esto tiene
      * el mismo efecto que llamar a readStandardInput (); es decir, la entrada se leerá desde el estándar
      * entrada. Si una
      * se produce un error al intentar abrir el archivo, una excepción de tipo IllegalArgumentException
      * se lanza, y la fuente de entrada no cambia. Si el archivo está abierto
      * con éxito, luego de que se llame a este método, todas las rutinas de entrada leerán
      * desde el archivo, en lugar de desde la entrada estándar.
     */
    public static void readFile(String fileName) {
        if (fileName == null) // Regrese a la entrada estándar de lectura
            readStandardInput();
        else {
            BufferedReader newin;
            try {
                newin = new BufferedReader( new FileReader(fileName) );
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Can't open file \"" + fileName + "\" for input.\n"
                                 + "(Error :" + e + ")");
            }
            if (! readingStandardInput) { //cerrar corriente de entrada actual
                try {
                    in.close();
                }
                catch (Exception e) {
                }
            }
            emptyBuffer();  // Added November 2007
            in = newin;
            readingStandardInput = false;
            inputErrorCount = 0;
            inputFileName = fileName;
        }
    }

    /**
     * Pone un cuadro de diálogo de selección de archivos GUI en la pantalla en la que el usuario puede seleccionar
     * un archivo de entrada. Si el usuario cancela el diálogo en lugar de seleccionar un archivo, es
     * no se considera un error, pero el valor de retorno de la subrutina es falso.
     * Si el usuario selecciona un archivo, pero hay un error al intentar abrir el
     * archivo, se lanza una excepción de tipo IllegalArgumentException. Finalmente, si
     * el usuario selecciona un archivo y se abre con éxito, luego el valor de retorno de la
     * la subrutina es verdadera, y las rutinas de entrada se leerán del archivo, en lugar de
     * desde entrada estándar. Si el usuario cancela, o si ocurre algún error, entonces el
     * la fuente de entrada anterior no se cambia.
     * <p> NOTA: al llamar a este método, se inicia un subproceso de la interfaz de usuario de GUI, que puede continuar
     * para ejecutar incluso si el subproceso que ejecuta el programa principal finaliza. Si usas este método
     * en un programa que no es una GUI, puede ser necesario llamar a System.exit (0) al final de main ()
     * rutina para cerrar por completo la máquina virtual Java.
     */
    public static boolean readUserSelectedFile() {
        if (fileDialog == null)
            fileDialog = new JFileChooser();
        fileDialog.setDialogTitle("Select File for Input");
        int option = fileDialog.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION)
            return false;
        File selectedFile = fileDialog.getSelectedFile();
        BufferedReader newin;
        try {
            newin = new BufferedReader( new FileReader(selectedFile) );
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Can't open file \"" + selectedFile.getName() + "\" for input.\n"
                             + "(Error :" + e + ")");
        }
        if (!readingStandardInput) { // close current file
            try {
                in.close();
            }
            catch (Exception e) {
            }
        }
        emptyBuffer();  // Added November 2007
        in = newin;
        inputFileName = selectedFile.getName();
        readingStandardInput = false;
        inputErrorCount = 0;
        return true;
    }
    
    /**
     * Después de invocar este método, la salida se escribirá en salida estándar (ya que
      * está en el estado predeterminado). Si un archivo o secuencia se abrió anteriormente para la salida,
      * estara cerrado.
     */
    public static void writeStandardOutput() {
        if (writingStandardOutput)
            return;
        try {
            out.close();
        }
        catch (Exception e) {
        }
        outputFileName = null;
        outputErrorCount = 0;
        out = standardOutput;
        writingStandardOutput = true;
    }
    

    /**
   * Después de invocar este método, la salida se enviará a outputStream, siempre que
      * no es nulo Si outputStream es nulo, entonces este método tiene el mismo efecto
      * como llamar a writeStandardOutput (); es decir, la producción futura se enviará a la
      * flujo de salida estándar.
     */
    public static void writeStream(OutputStream outputStream) {
        if (outputStream == null)
            writeStandardOutput();
        else
            writeStream(new PrintWriter(outputStream));
    }
    
    /**
   * Después de invocar este método, la salida se enviará a outputStream, siempre que
      * no es nulo Si outputStream es nulo, entonces este método tiene el mismo efecto
      * como llamar a writeStandardOutput (); es decir, la producción futura se enviará a la
      * flujo de salida estándar.
     */
    public static void writeStream(PrintWriter outputStream) {
        if (outputStream == null)
            writeStandardOutput();
        else {
            out = outputStream;
            outputFileName = null;
            outputErrorCount = 0;
            writingStandardOutput = false;
        }
    }
    

    /**
    * Abre un archivo con un nombre específico para la salida. Si el nombre del archivo es nulo, esto tiene
      * el mismo efecto que llamar a writeStandardOutput (); es decir, la salida se enviará al estándar
      * salida. Si una
      * se produce un error al intentar abrir el archivo, una excepción de tipo IllegalArgumentException
      * es aventado. Si el archivo se abre con éxito, luego de llamar a este método,
      * todas las rutinas de salida escribirán en el archivo, en lugar de en la salida estándar.
      * Si ocurre un error, el destino de salida no cambia.
      * <p> NOTA: al llamar a este método, se inicia un subproceso de la interfaz de usuario de GUI, que puede continuar.
      * para ejecutar incluso si el subproceso que ejecuta el programa principal finaliza. Si usas este método
      * en un programa que no es una GUI, puede ser necesario llamar a System.exit (0) al final de main ()
      * rutina para cerrar por completo la máquina virtual Java.
     */
    public static void writeFile(String fileName) {
        if (fileName == null)  // Go back to reading standard output
            writeStandardOutput();
        else {
            PrintWriter newout;
            try {
                newout = new PrintWriter(new FileWriter(fileName));
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Can't open file \"" + fileName + "\" for output.\n"
                                 + "(Error :" + e + ")");
            }
            if (!writingStandardOutput) {
                try {
                    out.close();
                }
                catch (Exception e) {
                }
            }
            out = newout;
            writingStandardOutput = false;
            outputFileName = fileName;
            outputErrorCount = 0;
        }
    }
    
    /**
    * Pone un cuadro de diálogo de selección de archivos GUI en la pantalla en la que el usuario puede seleccionar
      * un archivo de salida. Si el usuario cancela el diálogo en lugar de seleccionar un archivo, es
      * no se considera un error, pero el valor de retorno de la subrutina es falso.
      * Si el usuario selecciona un archivo, pero hay un error al intentar abrir el
      * archivo, se lanza una excepción de tipo IllegalArgumentException. Finalmente, si
      * el usuario selecciona un archivo y se abre con éxito, luego el valor de retorno de la
      * la subrutina es verdadera, y las rutinas de salida escribirán en el archivo, en lugar de
      * a salida estándar. Si el usuario cancela, o si ocurre un error, entonces el actual
      * el destino de salida no se cambia.
     */
    public static boolean writeUserSelectedFile() {
        if (fileDialog == null)
            fileDialog = new JFileChooser();
        fileDialog.setDialogTitle("Select File for Output");
        File selectedFile;
        while (true) {
            int option = fileDialog.showSaveDialog(null);
            if (option != JFileChooser.APPROVE_OPTION)
                return false;  // user canceled
            selectedFile = fileDialog.getSelectedFile();
            if (selectedFile.exists()) {
                int response = JOptionPane.showConfirmDialog(null,
                        "The file \"" + selectedFile.getName() + "\" already exists.  Do you want to replace it?",
                        "Replace existing file?",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.YES_OPTION)
                    break;
            }
            else {
                break;
            }
        }
        PrintWriter newout;
        try {
            newout = new PrintWriter(new FileWriter(selectedFile));
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Can't open file \"" + selectedFile.getName() + "\" for output.\n"
                             + "(Error :" + e + ")");
        }
        if (!writingStandardOutput) {
            try {
                out.close();
            }
            catch (Exception e) {
            }
        }
        out = newout;
        writingStandardOutput = false;
        outputFileName = selectedFile.getName();
        outputErrorCount = 0;
        return true;
    }
    

    /**
    * Si TextIO está leyendo actualmente de un archivo, el valor de retorno es el nombre del archivo.
      * Si la clase lee de la entrada estándar o de una secuencia, el valor de retorno es nulo.
     */
    public static String getInputFileName() {
        return inputFileName;
    }
    

    /**
     * Si TextIO está escribiendo actualmente en un archivo, entonces el valor de retorno es el nombre del archivo.
      * Si la clase está escribiendo en una salida estándar o en una transmisión, el valor de retorno es nulo.
     */
    public static String getOutputFileName() {
        return outputFileName;
    }
    

    // *************************** Output Methods *********************************
        
    /**
    * Escriba un valor único para el destino de salida actual, utilizando el formato predeterminado
      * y sin espacios adicionales. Este método manejará cualquier tipo de parámetro, incluso uno
      * cuyo tipo es uno de los tipos primitivos.
     */
    public static void put(Object x) { 
        out.print(x); 
        out.flush();
        if (out.checkError())
            outputError("Error while writing output.");
    }
    
    /**
    * Escriba un valor único para el destino de salida actual, utilizando el formato predeterminado
      * y obteniendo como mínimo caracteres minChars (con espacios adicionales añadidos antes del
      * valor de salida si es necesario). Este método manejará cualquier tipo de parámetro, incluso uno
      * cuyo tipo es uno de los tipos primitivos.
      * @param x El valor que se va a generar, que puede ser de cualquier tipo.
      * @param minChars El número mínimo de caracteres para usar para la salida. Si x requiere menos
      * luego esta cantidad de caracteres, luego espacios adicionales se agregan al frente de x para traer
      * el total hasta minChars. Si minChars es menor o igual a cero, entonces x se imprimirá
      * en el mínimo de espacios posibles.

     */
    public static void put(Object x, int minChars)  { 
        if (minChars <= 0)
            out.print(x);
        else
            out.printf("%" + minChars + "s", x);
        out.flush();
        if (out.checkError())
            outputError("Error while writing output.");
    }
        
    /**
     * Esto es equivalente a put (x), seguido de un final de línea.
     */
    public static void putln(Object x) { 
        out.println(x);
        out.flush();
        if (out.checkError())
            outputError("Error while writing output.");
    }
    
    /**
     *Esto es equivalente a put (x, minChars), seguido de un final de línea.
     */
    public static void putln(Object x, int minChars) {
        put(x,minChars);
        out.println();
        out.flush();
        if (out.checkError())
            outputError("Error while writing output.");
    }

    /**
     * Escriba un carácter de fin de línea en el destino de salida actual.
     */
    public static void putln() {
        out.println();
        out.flush();
        if (out.checkError())
            outputError("Error while writing output.");
    }
    
    /**
     * Escribe valores de salida formateados en el destino de salida actual. Este método tiene el
      * la misma función que System.out.printf (); los detalles de la salida formateada no se discuten
      * aquí. El primer parámetro es una cadena que describe el formato de la salida. Ahí
      * puede ser cualquier cantidad de parámetros adicionales; estos especifican los valores a emitir y
      * puede ser de cualquier tipo. Este método lanzará una IllegalArgumentException si el
      * la cadena de formato es nula o si la cadena de formato es ilegal para los valores que se están
      * salida.
     */
    public static void putf(String format, Object... items) {
        if (format == null)
            throw new IllegalArgumentException("Null format string in TextIO.putf() method.");
        try {
            out.printf(format,items);
        }
        catch (IllegalFormatException e) {
            throw new IllegalArgumentException("Illegal format string in TextIO.putf() method.");
        }
        out.flush();
        if (out.checkError())
            outputError("Error while writing output.");
    }
    
    // *************************** Input Methods *********************************

    /**
     * Compruebe si el siguiente carácter en la fuente de entrada actual es un final de línea. Tenga en cuenta que
      * este método NO omite espacios en blanco antes de realizar pruebas para el final de la línea, si desea hacerlo
      * eso, llama primero a skipBlanks ().
     */
    public static boolean eoln() { 
        return peek() == '\n'; 
    }

    /**
    * Compruebe si el siguiente carácter en la fuente de entrada actual es un final de archivo. Tenga en cuenta que
      * este método NO omite espacios en blanco antes de realizar pruebas para el final de la línea, si desea hacerlo
      * eso, llama primero a skipBlanks () o skipWhitespace ().
     */
    public static boolean eof()  { 
        return peek() == EOF; 
    }
    
    /**
    * Lee el siguiente carácter de la fuente de entrada actual. El personaje puede ser un espacio en blanco
      * personaje; compare esto con el método getChar (), que se salta el espacio en blanco y devuelve el
      * siguiente personaje que no sea de espacio en blanco. Siempre se devuelve un final de línea como el carácter '\ n', incluso
      * cuando el final de línea real en la fuente de entrada es otra cosa, como '\ r' o "\ r \ n".
      * Este método arrojará una IllegalArgumentException si la entrada está al final del archivo (lo que hará
      * no ocurre normalmente si se lee desde la entrada estándar).
     */
    public static char getAnyChar() { 
        return readChar(); 
    }

    /**
    * Devuelve el siguiente carácter en la fuente de entrada actual, sin eliminarlo
      * carácter de la entrada. El personaje puede ser un personaje en blanco y puede ser el
      * carácter de fin de archivo (especificado por la constante TextIO.EOF). Siempre se devuelve un final de línea
      * como el caracter '\ n', incluso cuando el final de línea real en la fuente de entrada es otra cosa,
      * como '\ r' o "\ r \ n". Este método nunca causa un error.
     */
    public static char peek() { 
        return lookChar();
    }
    
    /**
     * Salta sobre cualquier espacio en blanco, excepto para el final de las líneas. Después de que se llama este método,
      * el siguiente carácter de entrada es un carácter de final de línea, final de archivo o no espacio en blanco.
      * Este método nunca causa un error. (Normalmente, el final de archivo no es posible cuando se lee desde
      * entrada estándar.)
     */
    public static void skipBlanks() { 
        char ch=lookChar();
        while (ch != EOF && ch != '\n' && Character.isWhitespace(ch)) {
            readChar();
            ch = lookChar();
        }
    }

    /**
     * Salta por encima de cualquier espacio en blanco, incluso para el final de las líneas. Después de que se llama este método,
      * el siguiente carácter de entrada es un carácter de final de archivo o no de espacio en blanco.
      * Este método nunca causa un error. (Normalmente, el final de archivo no es posible cuando se lee desde
      * entrada estándar.)
     */
    private static void skipWhitespace() {
        char ch=lookChar();
        while (ch != EOF && Character.isWhitespace(ch)) {
            readChar();
            if (ch == '\n' && readingStandardInput && writingStandardOutput) {
                out.print("? ");
                out.flush();
            }
            ch = lookChar();
        }
    }

    /**
     * Salta los espacios en blanco de los caracteres y luego lee un valor de tipo byte desde la entrada, descartando el resto de
      * la línea de entrada actual (incluido el siguiente carácter de final de línea, si corresponde). Cuando se usa IO estándar,
      * esto no producirá un error; el usuario se le pedirá repetidamente para la entrada hasta un valor legal
      * es entrada. En otros casos, se lanzará una excepción IllegalArgumentException si no se encuentra un valor legal.
     */
    public static byte getlnByte() { 
        byte x=getByte(); 
        emptyBuffer(); 
        return x; 
    }
    
    /**
    * Salta los espacios en blanco de los caracteres y luego lee un valor de tipo corto de la entrada, descartando el resto de
      * la línea de entrada actual (incluido el siguiente carácter de final de línea, si corresponde). Cuando se usa IO estándar,
      * esto no producirá un error; el usuario se le pedirá repetidamente para la entrada hasta un valor legal
      * es entrada. En otros casos, se lanzará una excepción IllegalArgumentException si no se encuentra un valor legal.
     */
    public static short getlnShort() {
        short x=getShort();
        emptyBuffer(); 
        return x; 
    }
    
    /**
     * Salta los espacios en blanco de los caracteres y luego lee un valor de tipo int desde la entrada, descartando el resto de
      * la línea de entrada actual (incluido el siguiente carácter de final de línea, si corresponde). Cuando se usa IO estándar,
      * esto no producirá un error; el usuario se le pedirá repetidamente para la entrada hasta un valor legal
      * es entrada. En otros casos, se lanzará una excepción IllegalArgumentException si no se encuentra un valor legal.
     */
    public static int getlnInt() { 
        int x=getInt(); 
        emptyBuffer(); 
        return x; 
    }
    
    /**
     * Skips whitespace characters and then reads a value of type long from input, discarding the rest of 
     * the current line of input (including the next end-of-line character, if any).  When using standard IO,
     * this will not produce an error; the user will be prompted repeatedly for input until a legal value
     * is input.  In other cases, an IllegalArgumentException will be thrown if a legal value is not found.
     */
    public static long getlnLong() {
        long x=getLong(); 
        emptyBuffer(); 
        return x;
    }
    
    /**
     * Skips whitespace characters and then reads a value of type float from input, discarding the rest of 
     * the current line of input (including the next end-of-line character, if any).  When using standard IO,
     * this will not produce an error; the user will be prompted repeatedly for input until a legal value
     * is input.  In other cases, an IllegalArgumentException will be thrown if a legal value is not found.
     */
    public static float getlnFloat() {
        float x=getFloat(); 
        emptyBuffer(); 
        return x;
    }
    
    /**
     * Skips whitespace characters and then reads a value of type double from input, discarding the rest of 
     * the current line of input (including the next end-of-line character, if any).  When using standard IO,
     * this will not produce an error; the user will be prompted repeatedly for input until a legal value
     * is input.  In other cases, an IllegalArgumentException will be thrown if a legal value is not found.
     */
    public static double getlnDouble() { 
        double x=getDouble(); 
        emptyBuffer(); 
        return x; 
    }
    
    /**
     * Skips whitespace characters and then reads a value of type char from input, discarding the rest of 
     * the current line of input (including the next end-of-line character, if any).  Note that the value
     * that is returned will be a non-whitespace character; compare this with the getAnyChar() method.
     * When using standard IO, this will not produce an error.  In other cases, an error can occur if
     * an end-of-file is encountered.
     */
    public static char getlnChar() {
        char x=getChar(); 
        emptyBuffer(); 
        return x;
    }
    
    /**
     * Skips whitespace characters and then reads a value of type boolean from input, discarding the rest of 
     * the current line of input (including the next end-of-line character, if any).  When using standard IO,
     * this will not produce an error; the user will be prompted repeatedly for input until a legal value
     * is input.  In other cases, an IllegalArgumentException will be thrown if a legal value is not found.
     * <p>Legal inputs for a boolean input are: true, t, yes, y, 1, false, f, no, n, and 0; letters can be
     * either upper case or lower case. One "word" of input is read, using the getWord() method, and it
     * must be one of these; note that the "word"  must be terminated by a whitespace character (or end-of-file).
     */
    public static boolean getlnBoolean() { 
        boolean x=getBoolean(); 
        emptyBuffer();
        return x; 
    }
    
    /**
     * Salta los espacios en blanco de los caracteres y luego lee una "palabra" de la entrada, descartando el resto de
      * la línea de entrada actual (incluido el siguiente carácter de final de línea, si corresponde). Una palabra se define como
      * una secuencia de caracteres que no sean espacios en blanco (¡no solo letras!). Cuando se usa IO estándar,
      * esto no producirá un error. En otros casos, se lanzará una IllegalArgumentException
      * si se encuentra un final de archivo.
     */
    public static String getlnWord() {
        String x=getWord(); 
        emptyBuffer(); 
        return x; 
    }
    
    /**
     * Esto es idéntico a getln ().
     */
    public static String getlnString() {
        return getln();
    } 
    
    /**
     * Lee todos los caracteres de la fuente de entrada actual, hasta el siguiente final de línea. El final de línea
      * se lee, pero no está incluido en el valor de retorno. Cualquier otro carácter en blanco en la línea se conserva,
      * incluso si ocurren al comienzo de la entrada. El valor de retorno será una cadena vacía si hay
      * sin caracteres antes del final de línea. Al usar IO estándar, esto no producirá un error.
      * En otros casos, se lanzará una IllegalArgumentException si se encuentra un final de archivo.
     */
    public static String getln() {
        StringBuffer s = new StringBuffer(100);
        char ch = readChar();
        while (ch != '\n') {
            s.append(ch);
            ch = readChar();
        }
        return s.toString();
    }
    
    /**
     * Salta los espacios en blanco de los caracteres y luego lee un valor de tipo byte desde la entrada. Cualquier personaje adicional en
      * la línea de entrada actual se retiene, y será leída por la próxima operación de entrada. Cuando se usa IO estándar,
      * esto no producirá un error; el usuario se le pedirá repetidamente para la entrada hasta un valor legal
      * es entrada. En otros casos, se lanzará una excepción IllegalArgumentException si no se encuentra un valor legal.
     */
    public static byte getByte()   { 
        return (byte)readInteger(-128L,127L); 
    }

    /**
     * Salta caracteres de espacios en blanco y luego lee un valor de tipo abreviado de entrada. Cualquier personaje adicional en
      * la línea de entrada actual se retiene, y será leída por la próxima operación de entrada. Cuando se usa IO estándar,
      * esto no producirá un error; el usuario se le pedirá repetidamente para la entrada hasta un valor legal
      * es entrada. En otros casos, se lanzará una excepción IllegalArgumentException si no se encuentra un valor legal.
     */
    public static short getShort() { 
        return (short)readInteger(-32768L,32767L);
    }   
    
    /**
     * Salta los espacios en blanco de los caracteres y luego lee un valor de tipo int desde la entrada. Cualquier personaje adicional en
      * la línea de entrada actual se retiene, y será leída por la próxima operación de entrada. Cuando se usa IO estándar,
      * esto no producirá un error; el usuario se le pedirá repetidamente para la entrada hasta un valor legal
      * es entrada. En otros casos, se lanzará una excepción IllegalArgumentException si no se encuentra un valor legal.

     */
    public static int getInt()     { 
        return (int)readInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    /**
     * Salta los espacios en blanco de los caracteres y luego lee un valor de tipo largo desde la entrada. Cualquier personaje adicional en
      * la línea de entrada actual se retiene, y será leída por la próxima operación de entrada. Cuando se usa IO estándar,
      * esto no producirá un error; el usuario se le pedirá repetidamente para la entrada hasta un valor legal
      * es entrada. En otros casos, se lanzará una excepción IllegalArgumentException si no se encuentra un valor legal.
     */
    public static long getLong()   { 
        return readInteger(Long.MIN_VALUE, Long.MAX_VALUE); 
    }
    
    /**
    * Salta los espacios en blanco de los caracteres y luego lee un solo carácter que no sea de espacio en blanco desde la entrada. Cualquier personaje adicional en
      * la línea de entrada actual se retiene, y será leída por la próxima operación de entrada. Cuando se usa IO estándar,
      * esto no producirá un error. En otros casos, se lanzará una IllegalArgumentException si un final de archivo
      * se encuentra.
     */
    public static char getChar() { 
        skipWhitespace();
        return readChar();
    }
    
    /**
    * Salta los espacios en blanco de los caracteres y luego lee un valor de tipo flotante desde la entrada. Cualquier personaje adicional en
      * la línea de entrada actual se retiene, y será leída por la próxima operación de entrada. Cuando se usa IO estándar,
      * esto no producirá un error; el usuario se le pedirá repetidamente para la entrada hasta un valor legal
      * es entrada. En otros casos, se lanzará una excepción IllegalArgumentException si no se encuentra un valor legal.
     */
    public static float getFloat() {
        float x = 0.0F;
        while (true) {
            String str = readRealString();
            if (str == null) {
                errorMessage("Floating point number not found.",
                        "Real number in the range " + (-Float.MAX_VALUE) + " to " + Float.MAX_VALUE);
            }
            else {
                try { 
                    x = Float.parseFloat(str); 
                }
                catch (NumberFormatException e) {
                    errorMessage("Illegal floating point input, " + str + ".",
                            "Real number in the range " +  (-Float.MAX_VALUE) + " to " + Float.MAX_VALUE);
                    continue;
                }
                if (Float.isInfinite(x)) {
                    errorMessage("Floating point input outside of legal range, " + str + ".",
                            "Real number in the range " +  (-Float.MAX_VALUE) + " to " + Float.MAX_VALUE);
                    continue;
                }
                break;
            }
        }
        inputErrorCount = 0;
        return x;
    }
    
    /**
     * Salta los espacios en blanco de los caracteres y luego lee un valor de tipo doble desde la entrada. Cualquier personaje adicional en
      * la línea de entrada actual se retiene, y será leída por la próxima operación de entrada. Cuando se usa IO estándar,
      * esto no producirá un error; el usuario se le pedirá repetidamente para la entrada hasta un valor legal
      * es entrada. En otros casos, se lanzará una excepción IllegalArgumentException si no se encuentra un valor legal.
     */
    public static double getDouble() {
        double x = 0.0;
        while (true) {
            String str = readRealString();
            if (str == null) {
                errorMessage("Floating point number not found.",
                        "Real number in the range " + (-Double.MAX_VALUE) + " to " + Double.MAX_VALUE);
            }
            else {
                try { 
                    x = Double.parseDouble(str); 
                }
                catch (NumberFormatException e) {
                    errorMessage("Illegal floating point input, " + str + ".",
                            "Real number in the range " + (-Double.MAX_VALUE) + " to " + Double.MAX_VALUE);
                    continue;
                }
                if (Double.isInfinite(x)) {
                    errorMessage("Floating point input outside of legal range, " + str + ".",
                            "Real number in the range " + (-Double.MAX_VALUE) + " to " + Double.MAX_VALUE);
                    continue;
                }
                break;
            }
        }
        inputErrorCount = 0;
        return x;
    }
    
    /**
     * Salta los espacios en blanco de los caracteres y luego lee una "palabra" de la entrada. Cualquier personaje adicional en
      * la línea de entrada actual se retiene, y será leída por la próxima operación de entrada. Una palabra se define como
      * una secuencia de caracteres que no sean espacios en blanco (¡no solo letras!). Cuando se usa IO estándar,
      * esto no producirá un error. En otros casos, se lanzará una IllegalArgumentException
      * si se encuentra un final de archivo.

     */
    public static String getWord() {
        skipWhitespace();
        StringBuffer str = new StringBuffer(50);
        char ch = lookChar();
        while (ch == EOF || !Character.isWhitespace(ch)) {
            str.append(readChar());
            ch = lookChar();
        }
        return str.toString();
    }
    
    /**
    * Salta los espacios en blanco de los caracteres y luego lee un valor de tipo booleano desde la entrada. Cualquier personaje adicional en
      * la línea de entrada actual se retiene, y será leída por la próxima operación de entrada. Cuando se usa IO estándar,
      * esto no producirá un error; el usuario se le pedirá repetidamente para la entrada hasta un valor legal
      * es entrada. En otros casos, se lanzará una excepción IllegalArgumentException si no se encuentra un valor legal.
      * <p> Las entradas legales para una entrada booleana son: verdadero, t, sí, y, 1, falso, f, no, n y 0; las letras pueden ser
      * ya sea en mayúscula o minúscula. Se lee una "palabra" de entrada, usando el método getWord (), y
      * debe ser uno de estos; tenga en cuenta que la "palabra" debe terminar con un carácter de espacio en blanco (o al final del archivo).
     */
    public static boolean getBoolean() {
        boolean ans = false;
        while (true) {
            String s = getWord();
            if ( s.equalsIgnoreCase("true") || s.equalsIgnoreCase("t") ||
                    s.equalsIgnoreCase("yes")  || s.equalsIgnoreCase("y") ||
                    s.equals("1") ) {
                ans = true;
                break;
            }
            else if ( s.equalsIgnoreCase("false") || s.equalsIgnoreCase("f") ||
                    s.equalsIgnoreCase("no")  || s.equalsIgnoreCase("n") ||
                    s.equals("0") ) {
                ans = false;
                break;
            }
            else
                errorMessage("Illegal boolean input value.",
                "one of:  true, false, t, f, yes, no, y, n, 0, or 1");
        }
        inputErrorCount = 0;
        return ans;
    }
    
    // ***************** Todo más allá de este punto es detalle de implementación privada *******************
    
    private static String inputFileName;  // Nombre del archivo que es la fuente de entrada actual, o nulo si la fuente no es un archivo.
    private static String outputFileName; // Nombre del archivo que es el destino de salida actual, o nulo si el destino no es un archivo.
    
    private static JFileChooser fileDialog; // Diálogo utilizado por readUserSelectedFile () y writeUserSelectedFile ()
    
    private final static BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in));  // envuelve la corriente de entrada estándar
    private final static PrintWriter standardOutput = new PrintWriter(System.out);  // envuelve flujo de salida estándar
    private static BufferedReader in = standardInput;  // Transmita desde donde se leen los datos; la fuente de entrada actual.
    private static PrintWriter out = standardOutput;   // Transmite los datos en los que se escribe; el destino de salida actual.
    
    private static boolean readingStandardInput = true;
    private static boolean writingStandardOutput = true;
    
    private static int inputErrorCount;  // Número de errores consecutivos en la entrada estándar; restablecer a 0 cuando se produce una lectura exitosa.
    private static int outputErrorCount;  // Número de errores en la salida estándar desde que se seleccionó como el destino de salida.
    
    private static Matcher integerMatcher;  // Utilizado para leer números enteros; creado a partir del patrón Regex entero.
    private static Matcher floatMatcher;   // Se usa para leer números de coma flotante;
    private final static Pattern integerRegex = Pattern.compile("(\\+|-)?[0-9]+");
    private final static Pattern floatRegex = Pattern.compile("(\\+|-)?(([0-9]+(\\.[0-9]*)?)|(\\.[0-9]+))((e|E)(\\+|-)?[0-9]+)?");
    
    private static String buffer = null;  // Una línea leída de la entrada.
    private static int pos = 0;           // Posición del siguiente carácter en la línea de entrada que aún no se ha procesado.
    
    private static String readRealString() {   // leer caracteres de entrada siguiendo la sintaxis de números reales
        skipWhitespace();
        if (lookChar() == EOF)
            return null;
        if (floatMatcher == null)
            floatMatcher = floatRegex.matcher(buffer);
        floatMatcher.region(pos,buffer.length());
        if (floatMatcher.lookingAt()) {
            String str = floatMatcher.group();
            pos = floatMatcher.end();
            return str;
        }
        else 
            return null;
    }
    
    private static String readIntegerString() {  // leer caracteres de entrada siguiendo la sintaxis de enteros
        skipWhitespace();
        if (lookChar() == EOF)
            return null;
        if (integerMatcher == null)
            integerMatcher = integerRegex.matcher(buffer);
        integerMatcher.region(pos,buffer.length());
        if (integerMatcher.lookingAt()) {
            String str = integerMatcher.group();
            pos = integerMatcher.end();
            return str;
        }
        else 
            return null;
    }
    
    private static long readInteger(long min, long max) {  // leer entero largo, limitado al rango especificado
        long x=0;
        while (true) {
            String s = readIntegerString();
            if (s == null){
                errorMessage("Integer value not found in input.",
                        "Integer in the range " + min + " to " + max);
            }
            else {
                String str = s.toString();
                try { 
                    x = Long.parseLong(str);
                }
                catch (NumberFormatException e) {
                    errorMessage("Illegal integer input, " + str + ".",
                            "Integer in the range " + min + " to " + max);
                    continue;
                }
                if (x < min || x > max) {
                    errorMessage("Integer input outside of legal range, " + str + ".",
                            "Integer in the range " + min + " to " + max);
                    continue;
                }
                break;
            }
        }
        inputErrorCount = 0;
        return x;
    }
    
    
    private static void errorMessage(String message, String expecting) {  // Informe error en la entrada.
        if (readingStandardInput && writingStandardOutput) {
                // inform user of error and force user to re-enter.
            out.println();
            out.print("  *** Error in input: " + message + "\n");
            out.print("  *** Expecting: " + expecting + "\n");
            out.print("  *** Discarding Input: ");
            if (lookChar() == '\n')
                out.print("(end-of-line)\n\n");
            else {
                while (lookChar() != '\n')    // Deseche y repita los caracteres restantes en la línea de entrada actual.
                    out.print(readChar());
                out.print("\n\n");
            }
            out.print("Please re-enter: ");
            out.flush();
            readChar();  // descartar el personaje de fin de línea
            inputErrorCount++;
            if (inputErrorCount >= 10)
                throw new IllegalArgumentException("Too many input consecutive input errors on standard input.");
        }
        else if (inputFileName != null)
            throw new IllegalArgumentException("Error while reading from file \"" + inputFileName + "\":\n" 
                    + message + "\nExpecting " + expecting);
        else
            throw new IllegalArgumentException("Error while reading from inptu stream:\n" 
                    + message + "\nExpecting " + expecting);
    }
    
    private static char lookChar() {  // devolver el siguiente carácter de la entrada
        if (buffer == null || pos > buffer.length())
            fillBuffer();
        if (buffer == null)
            return EOF;
        else if (pos == buffer.length())
            return '\n';
        else 
            return buffer.charAt(pos);
    }
    
    private static char readChar() {  //devolver y descartar el siguiente carácter de la entrada
        char ch = lookChar();
        if (buffer == null) {
            if (readingStandardInput)
                throw new IllegalArgumentException("Attempt to read past end-of-file in standard input???");
            else
                throw new IllegalArgumentException("Attempt to read past end-of-file in file \"" + inputFileName + "\".");
        }
        pos++;
        return ch;
    }
        
    private static void fillBuffer() {    // Espere a que el usuario escriba una línea y presione regresar,
        try {
            buffer = in.readLine();
        }
        catch (Exception e) {
            if (readingStandardInput)
                throw new IllegalArgumentException("Error while reading standard input???");
            else if (inputFileName != null)
                throw new IllegalArgumentException("Error while attempting to read from file \"" + inputFileName + "\".");
            else
                throw new IllegalArgumentException("Errow while attempting to read form an input stream.");
        }
        pos = 0;
        floatMatcher = null;
        integerMatcher = null;
    }
    
    private static void emptyBuffer() {   // discard the rest of the current line of input
        buffer = null;
    }
    
    private static void outputError(String message) {  // Report an error on output.
        if (writingStandardOutput) {
            System.err.println("Error occurred in TextIO while writing to standard output!!");
            outputErrorCount++;
            if (outputErrorCount >= 10) {
                outputErrorCount = 0;
                throw new IllegalArgumentException("Too many errors while writing to standard output.");
            }
        }
        else if (outputFileName != null){
            throw new IllegalArgumentException("Error occurred while writing to file \"" 
                    + outputFileName+ "\":\n   " + message);
        }
        else {
            throw new IllegalArgumentException("Error occurred while writing to output stream:\n   " + message);
        }
    }
    
}
