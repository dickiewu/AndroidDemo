package name.dickie.android.demo;

import android.support.v4.widget.TextViewCompat;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.IntStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Sink;
import okio.Source;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testOkiO() throws Exception {
        BufferedSource bufferedSource = Okio.buffer(Okio.source(new File("iotest.txt")));
        String s = bufferedSource.readUtf8(3);
        System.out.println("read String:"+s);

    }


    @Test
    public void test() throws IOException {
        Buffer buffer = new Buffer();
        buffer.writeString("hello world", Charset.forName("utf-8"));
        String s = buffer.readUtf8();
        System.out.println("result:"+s);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("").build();
        Response response = okHttpClient.newCall(request).execute();
    }
}