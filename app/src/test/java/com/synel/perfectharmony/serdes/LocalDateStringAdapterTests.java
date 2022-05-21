package com.synel.perfectharmony.serdes;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocalDateStringAdapterTests {

    @Mock
    private JsonWriter writerOutMock;

    @Mock
    private JsonReader readerInMock;

    private LocalDateStringAdapter testedAdapter;

    @Before
    public void setUp() {

        testedAdapter = new LocalDateStringAdapter();
    }

    @Test
    public void writeNullTest() throws IOException {

        testedAdapter.write(writerOutMock, null);
        Mockito.verify(writerOutMock).value("");
    }

    @Test
    public void writeLocalDateTest() throws IOException {

        testedAdapter.write(writerOutMock, LocalDate.of(2022, 5, 21));
        Mockito.verify(writerOutMock).value("2022-05-21T00:00:00");
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

        Mockito.when(readerInMock.nextString()).thenReturn("2022-05-21T00:00:00");
        Assert.assertEquals("Wrong value of local date input", LocalDate.of(2022, 5, 21), testedAdapter.read(readerInMock));
    }
}
