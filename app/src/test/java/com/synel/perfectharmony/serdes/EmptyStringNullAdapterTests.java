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
public class EmptyStringNullAdapterTests {

    @Mock
    private JsonWriter writerOutMock;

    @Mock
    private JsonReader readerInMock;

    private EmptyStringNullAdapter testedAdapter;

    @Before
    public void setUp() {

        testedAdapter = new EmptyStringNullAdapter();
    }

    @Test
    public void writeNullTest() throws IOException {

        testedAdapter.write(writerOutMock, null);
        Mockito.verify(writerOutMock).value("");
    }

    @Test
    public void writeEmptyTest() throws IOException {

        testedAdapter.write(writerOutMock, "");
        Mockito.verify(writerOutMock).value("");
    }

    @Test
    public void writeBlankTest() throws IOException {

        testedAdapter.write(writerOutMock, " ");
        Mockito.verify(writerOutMock).value(" ");
    }

    @Test
    public void writeStringTest() throws IOException {

        testedAdapter.write(writerOutMock, "test string");
        Mockito.verify(writerOutMock).value("test string");
    }

    @Test
    public void readNullTest() throws IOException {

        Mockito.when(readerInMock.nextString()).thenReturn(null);
        Assert.assertNull("Wrong value of null input!", testedAdapter.read(readerInMock));
    }

    @Test
    public void readEmptyTest() throws IOException {

        Mockito.when(readerInMock.nextString()).thenReturn("");
        Assert.assertNull("Wrong value of empty input!", testedAdapter.read(readerInMock));
    }

    @Test
    public void readBlankTest() throws IOException {

        Mockito.when(readerInMock.nextString()).thenReturn(" ");
        Assert.assertEquals("Wrong value of blank input!", " ", testedAdapter.read(readerInMock));
    }

    @Test
    public void readStringTest() throws IOException {

        Mockito.when(readerInMock.nextString()).thenReturn("test string");
        Assert.assertEquals("Wrong value of string input!", "test string", testedAdapter.read(readerInMock));
    }
}
