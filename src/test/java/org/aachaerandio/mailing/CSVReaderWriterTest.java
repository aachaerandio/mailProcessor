package org.aachaerandio.mailing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class CSVReaderWriterTest {

    private CSVReaderWriter csvReaderWriter;

    @Before
    public void setup() {
        csvReaderWriter = new CSVReaderWriter();
    }

    @Test (expected = Exception.class)
    public void shouldThrowException_WhenFileDoesNotExist() throws Exception {
        csvReaderWriter.open("unicorn.csv", CSVReaderWriter.Mode.Read);
    }

    @Test (expected = Exception.class)
    public void shouldThrowException_WhenModeIsNull() throws Exception {
        csvReaderWriter.open("src/test/resources/test1.csv", null);
    }

    @Test
    public void shouldOpenFile_WhenFileAndModeExists() throws Exception {
        csvReaderWriter.open("src/test/resources/test1.csv", CSVReaderWriter.Mode.Read);
        csvReaderWriter.close();
    }

    @Test
    public void shouldReadData() throws Exception {
        csvReaderWriter.open("src/test/resources/test2.csv", CSVReaderWriter.Mode.Read);
        String[] columns = new String[2];
        csvReaderWriter.read(columns);
        csvReaderWriter.close();

        Assert.assertEquals("hello", columns[0]);
        Assert.assertEquals("world", columns[1]);

        //Identified shortcoming on the number of columns read from the method limited to only 2.
        // What happens if there's more?
    }

    @Test
    public void shouldWriteData() throws Exception {
        csvReaderWriter.open("build/resources/test/test3.csv", CSVReaderWriter.Mode.Write);
        csvReaderWriter.write("hello", "world");
        csvReaderWriter.close();

        csvReaderWriter.open("build/resources/test/test3.csv", CSVReaderWriter.Mode.Read);
        String[] columns = new String[2];
        csvReaderWriter.read(columns);
        csvReaderWriter.close();

        Assert.assertEquals("hello", columns[0]);
        Assert.assertEquals("world", columns[1]);
    }

    @Test
    public void shouldReadLine() throws Exception {
        csvReaderWriter.open("src/test/resources/test2.csv", CSVReaderWriter.Mode.Read);
        Optional<String[]> line = csvReaderWriter.read();
        csvReaderWriter.close();

        Assert.assertEquals("hello", line.get()[0]);
        Assert.assertEquals("world", line.get()[1]);
        Assert.assertEquals("bye", line.get()[2]);
    }

    @Test
    public void shouldReadAndReturnRequiredColumns() throws Exception {
        int numberRequiredColumns = 4;
        csvReaderWriter.open("src/test/resources/test2.csv", CSVReaderWriter.Mode.Read);
        Optional<String[]> line = csvReaderWriter.read(numberRequiredColumns);
        csvReaderWriter.close();

        Assert.assertEquals("hello", line.get()[0]);
        Assert.assertEquals("world", line.get()[1]);
        Assert.assertEquals(numberRequiredColumns, line.get().length);
    }
}