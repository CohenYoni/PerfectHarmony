package com.synel.perfectharmony.serdes;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocalTimeStringAdapterTests {

    @Mock
    private JsonWriter writerOutMock;

    @Mock
    private JsonReader readerInMock;

    private LocalTimeStringAdapter testedAdapter;

    @Before
    public void setUp() {

        testedAdapter = new LocalTimeStringAdapter();
    }

    @Test
    public void writeNullTest() throws IOException {

        testedAdapter.write(writerOutMock, null);
        Mockito.verify(writerOutMock).value("");
    }

    @Test
    public void writeLocalDateTest() throws IOException {

        testedAdapter.write(writerOutMock, LocalTime.of(12, 39));
        Mockito.verify(writerOutMock).value("12:39");
    }

    @Test
    public void readNullTest() throws IOException {

        Mockito.when(readerInMock.nextString()).thenReturn(null);
        Assert.assertNull("Wrong value of null input", testedAdapter.read(readerInMock));
        Mockito.when(readerInMock.nextString()).thenReturn("");
        Assert.assertNull("Wrong value of empty string input", testedAdapter.read(readerInMock));
        Mockito.when(readerInMock.nextString()).thenReturn(" ");
        Assert.assertNull("Wrong value of blank string input", testedAdapter.read(readerInMock));
    }

    @Test
    public void readLocalDateTest() throws IOException {

        Mockito.when(readerInMock.nextString()).thenReturn("12:39");
        Assert.assertEquals("Wrong value of local time input", LocalTime.of(12, 39), testedAdapter.read(readerInMock));
    }
}
