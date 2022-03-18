import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadTest {
    public static void main(String[] args) throws IOException, MyException {
        ClientGlobal.init("d:/tracker_client.conf");
        // 3、创建一个TrackerClient对象。
        TrackerClient trackerClient = new TrackerClient();
        // 4、创建一个TrackerServer对象。
        TrackerServer trackerServer = trackerClient.getConnection();
        // 5、声明一个StorageServer对象，null。
        StorageServer storageServer = null;
        // 6、获得StorageClient对象。
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);

        // 7、直接调用StorageClient对象方法上传文件即可。
        URL url = new URL("d:/pic/bf.jpg");//65536
//            URL url = new URL("http://localhost:8080/dd/dddd.jpg");//65536
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        int fileLength = conn.getContentLength();//重要：网络文件的大小
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        NameValuePair[] metaList = new NameValuePair[3];
        metaList[0] = new NameValuePair("fileName", "dddddd.jpg");
        metaList[1] = new NameValuePair("fileExtName", "jpg");
        metaList[2] = new NameValuePair("fileLength", String.valueOf(fileLength));

        UploadFileSender us = new UploadFileSender(inputStream);

        String[] strings = storageClient.upload_file(null,fileLength,us, "jpg", metaList);

        for (String string : strings) {
            System.out.println(string);
        }
    }
}
