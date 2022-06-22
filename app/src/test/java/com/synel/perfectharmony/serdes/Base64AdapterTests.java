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
public class Base64AdapterTests {

    @Mock
    private JsonWriter writerOutMock;

    @Mock
    private JsonReader readerInMock;

    private Base64Adapter testedAdapter;

    @Before
    public void setUp() {

        testedAdapter = new Base64Adapter();
    }

    @Test
    public void writeNullTest() throws IOException {

        testedAdapter.write(writerOutMock, null);
        Mockito.verify(writerOutMock).value((String) null);
    }

    @Test
    public void writeEmptyTest() throws IOException {

        testedAdapter.write(writerOutMock, "");
        Mockito.verify(writerOutMock).value("");
    }

    @Test
    public void writeBlankTest() throws IOException {

        testedAdapter.write(writerOutMock, " ");
        Mockito.verify(writerOutMock).value("IA==");
    }

    @Test
    public void writeStringTest() throws IOException {

        testedAdapter.write(writerOutMock, "test string");
        Mockito.verify(writerOutMock).value("dGVzdCBzdHJpbmc=");
    }

    @Test
    public void readNullTest() throws IOException {

        Mockito.when(readerInMock.nextString()).thenReturn(null);
        Assert.assertNull("Wrong value of null input!", testedAdapter.read(readerInMock));
    }

    @Test
    public void readEmptyTest() throws IOException {

        Mockito.when(readerInMock.nextString()).thenReturn("");
        Assert.assertEquals("Wrong value of empty input!", "", testedAdapter.read(readerInMock));
    }

    @Test
    public void readBlankTest() throws IOException {

        Mockito.when(readerInMock.nextString()).thenReturn("IA==");
        Assert.assertEquals("Wrong value of blank input!", " ", testedAdapter.read(readerInMock));
    }

    @Test
    public void readStringTest() throws IOException {

        Mockito.when(readerInMock.nextString()).thenReturn("dGVzdCBzdHJpbmc=");
        Assert.assertEquals("Wrong value of string input!", "test string", testedAdapter.read(readerInMock));
    }
}
