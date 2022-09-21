import com.sun.nio.zipfs.ZipFileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class myThread extends Thread{
    private InputStream inp;
    public myThread(InputStream inpu) {
        inp = inpu;
    }
    int znak;

    public void run(){
        try{
            do{
                znak = inp.read();
                System.out.print((char) znak);
            }while(znak >= 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection closed, thread finished");
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            String host = "";
            String port = "";
            if(args.length >= 2) {
                host = args[0];
                port = args[1];
            }else{
                System.out.println("Invalid arguments");
                System.exit(-1);
            }
            Socket s = new Socket(host,Integer.parseInt(port));
            InputStream inp = s.getInputStream();
            OutputStream outp = s.getOutputStream();

            myThread mT = new myThread(inp);
            mT.start();

            int znak;
            while (true) {
                znak = System.in.read();
                if(mT.isAlive()){
                    outp.write(System.in.read());
                    outp.flush();
                }else{
                    System.out.println("Connection finished, terminating");
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}