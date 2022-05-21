package com.synel.perfectharmony.serdes;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IntSecondNumOfHoursAdapterTests {

    @Mock
    private JsonWriter writerOutMock;

    @Mock
    private JsonReader readerInMock;

    private IntSecondNumOfHoursAdapter testedAdapter;

    @Before
    public void setUp() {

        testedAdapter = new IntSecondNumOfHoursAdapter();
    }

    @Test
    public void writeNullTest() throws IOException {

        testedAdapter.write(writerOutMock, null);
        Mockito.verify(writerOutMock).value("");
    }

    @Test
    public void writeZeroTest() throws IOException {

        testedAdapter.write(writerOutMock, 0);
        Mockito.verify(writerOutMock).value("00:00");
    }

    @Test
    public void writePositiveTest() throws IOException {

        testedAdapter.write(writerOutMock, 12720);
        Mockito.verify(writerOutMock).value("03:32");
        testedAdapter.write(writerOutMock, 85620);
        Mockito.verify(writerOutMock).value("23:47");
    }

    @Test
    public void writeNegativeTest() throws IOException {

        testedAdapter.write(writerOutMock, -12720);
        Mockito.verify(writerOutMock).value("-03:32");
        testedAdapter.write(writerOutMock, -85620);
        Mockito.verify(writerOutMock).value("-23:47");
    }

    @Test
    public void readNullTest() throws IOException {

        Mockito.when(readerInMock.nextString()).thenReturn(null);
        Assert.assertNull("Wrong value of null input!", testedAdapter.read(readerInMock));
        Mockito.when(readerInMock.nextString()).thenReturn("");
        Assert.assertNull("Wrong value of empty string input!", testedAdapter.read(readerInMock));
        Mockito.when(readerInMock.nextString()).thenReturn(" ");
        Assert.assertNull("Wrong value of blank string input!", testedAdapter.read(readerInMock));
    }

    @Test
    public void readZeroTest() throws IOException {

        Mockito.when(readerInMock.nextString()).thenReturn("00:00");
        Assert.assertEquals("Wrong value of zero input!", Integer.valueOf(0), testedAdapter.read(readerInMock));
    }

    @Test
    public void readPositiveTest() throws IOException {

        Mockito.when(readerInMock.nextString()).thenReturn("03:32");
        Assert.assertEquals("Wrong value of positive input!", Integer.valueOf(12720), testedAdapter.read(readerInMock));
        Mockito.when(readerInMock.nextString()).thenReturn("23:47");
        Assert.assertEquals("Wrong value of positive input!", Integer.valueOf(85620), testedAdapter.read(readerInMock));
    }

    @Test
    public void readNegativeTest() throws IOException {

        Mockito.when(readerInMock.nextString()).thenReturn("-03:32");
        Assert.assertEquals("Wrong value of negative input!", Integer.valueOf(-12720), testedAdapter.read(readerInMock));
        Mockito.when(readerInMock.nextString()).thenReturn("-23:47");
        Assert.assertEquals("Wrong value of negative input!", Integer.valueOf(-85620), testedAdapter.read(readerInMock));
    }
}
