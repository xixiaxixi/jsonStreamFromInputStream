import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;

public class tcpclient {


    public static void main(String[] args) throws IOException {

//创建client的socket服务，指定目的主机和port
        Socket s = new Socket("127.0.0.1",10003);
        //为了发送数据。获取socket流中的输出流
        java.io.OutputStream out = s.getOutputStream();
        out.write("{\"special_char\":\"中文\\\"\\\\,;/.\"}\0\0\0\0\0\0".getBytes("gbk"));

        s.close();
    }
}
