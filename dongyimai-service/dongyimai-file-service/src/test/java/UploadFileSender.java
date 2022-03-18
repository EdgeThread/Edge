import org.csource.fastdfs.UploadCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class UploadFileSender implements UploadCallback {

    private InputStream inStream;

    public UploadFileSender(InputStream inStream) {
        this.inStream = inStream;
    }

    public int send(OutputStream out) throws IOException {
        byte[] bs = new byte[1024];
        int i=0;
        try{
            while ((i = inStream.read(bs)) != -1) {

                out.write(bs, 0, i);

            }
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }

}
