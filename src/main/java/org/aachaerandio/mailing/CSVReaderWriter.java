package org.aachaerandio.mailing;

/*
A junior developer was tasked with writing a reusable implementation for a mass mailing application to read and write text files that hold tab separated data. He proceeded and as a result produced the CSVReaderWriter class.

His implementation, although it works and meets the needs of the application, is of very low quality.

Your task:
     - Identify and annotate the shortcomings in the current implementation as if you were doing a code review, using comments in the CSVReaderWriter.java file.
     - Refactor the CSVReaderWriter implementation into clean, elegant, rock-solid & well performing code.
     - Provide evidence that the code is working as expected.
     - Where you make trade offs, comment & explain.
     - Assume this code is in production and backwards compatibility must be maintained. Therefore if you decide to change the public interface,
       please deprecate the existing methods. Feel free to evolve the code in other ways though.
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class CSVReaderWriter {
    private final String SEPARATOR = "\t";
    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;

    public enum Mode {
        Read, Write
    }

    public void open(String fileName, Mode mode) throws Exception {
        if (mode == Mode.Read)
        {
            bufferedReader = new BufferedReader(new FileReader(fileName));
        }
        else if (mode == Mode.Write)
        {
            FileWriter fileWriter = new FileWriter(fileName);
            bufferedWriter = new BufferedWriter(fileWriter);
        }
        else
        {
            throw new Exception("Unknown file mode for " + fileName);
        }
    }

    public void write(String... columns) throws IOException {
        String outPut = String.join(SEPARATOR, columns);
        writeLine(outPut);
    }

    public Optional<String[]> read() throws IOException {
        return readLine().map(l -> l.split(SEPARATOR));
    }

    /**
     * This method returns an array of the specified length in <tt>numColumns</tt>.
     * If the source array's length is greater than <tt>numColumns</tt>, the rest of positions in the array are filled with null.
     * In other case a sub array of the source array is returned.
     * @param numColumns the number of columns expected from the line in the file.
     * @return
     * @throws IOException
     */
    public Optional<String[]> read(int numColumns) throws IOException {
        return read().map(l -> Arrays.copyOfRange(l, 0, numColumns));
    }

    /**
     * @deprecated use {@link #read(int)} instead.
     *
     * Review: No need of output parameters. Instead I would return the data read from the file.
     */
    @Deprecated
    public boolean read(String[] columns) throws IOException {
        //This method is used to return a fixed sized array filling with null the empty values.
        Optional<String[]> line = read(columns.length);
        if (line.isPresent())
        {
            System.arraycopy(line.get(),0, columns, 0, columns.length);
            return true;
        }
        else
        {
            Arrays.fill(columns, null);
            return false;
        }
    }

    /**
     * @deprecated use {@link #read(int)} instead.
     *
     * This method it meant to return the value of columns 1 and 2 of a line.
     * Modifying the variables column1 and 2 inside the method will not affect the original variables from where the method was called
     * This could be done by using a mutable class, like StringBuilder, instead of String
     *
     * Even after the refactor it will still need to use something like StringBuilder.
     *
     */
    @Deprecated
    public boolean read(String column1, String column2) throws IOException {
        final int FIRST_COLUMN = 0;
        final int SECOND_COLUMN = 1;

        //This method guarantees an array of length 2 will be return filling with null the empty elements.
        Optional<String[]> line = read(2);
        if (line.isPresent())
        {
            column1 = line.get()[FIRST_COLUMN];
            column2 = line.get()[SECOND_COLUMN];
            return true;
        }
        else
        {
            column1 = null;
            column2 = null;
            return false;
        }

    }

    private void writeLine(String line) throws IOException {
        bufferedWriter.write(line);
    }

    private Optional<String> readLine() throws IOException {
        return Optional.ofNullable(bufferedReader.readLine());
    }

    public void close() throws IOException {
        if (bufferedWriter != null)
        {
            bufferedWriter.close();
        }

        if (bufferedReader != null)
        {
            bufferedReader.close();
        }
    }
}
