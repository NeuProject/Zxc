import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.util.Date;

public class Client {
    public static void main(String args[]) {
        Socket cSocket = null;
        PrintWriter os = null;
        BufferedReader is = null;
        String s = null;
        String path =null;
        try {

            // 创建通讯并且和主机Rock连接
            String add = "";
            System.out.println("请输入您要访问的客户端：\n");
             //client = new Client("127.0.0.1", SERVICE_POR)
            //cSocket = new Socket("www.baidu.com", 8000);
            //cSocket = new Socket(add, 8000);
            // 打开这个Socket的输入/输出流                                        
            os = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()));
            is = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
            System.out.println("请输入您要访问的文件：\n");
            String add1 = "";
            add1 = new Scanner(System.in).next();
            os.print("GET /"+add1+" HTTP/1.0 \r\n" + "Host:127.0.0.1 \r\n\r\n");
            os.flush();
            try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
            if(add1.contains(".jpeg") || add1.contains(".jpg")){
                path="data.jpg";
                FileInputStream fis = null;
                FileOutputStream fos = null;
                try{
                    fis = new FileInputStream(add1);
                    fos= new FileOutputStream(path);
                    byte [] buffer = new byte[1024];
                    while(true){
                       int temp = fis.read(buffer, 0, buffer.length);
                       if(temp == -1){
                           break;
                       }
                       fos.write(buffer, 0, temp); 
                    }
                    System.out.println("读取成功！");
                    System.out.println("文件保存成功！");

                }catch(Exception e){
                    System.out.println(e);

                }

            }

            else{
                path="data.html";
                FileInputStream fis = null;
                FileOutputStream fos = null;
                try{
                     fis = new FileInputStream(add1);
                     fos= new FileOutputStream(path);
                     byte [] buffer = new byte[1024];
                     while(true){
                        int temp = fis.read(buffer, 0, buffer.length);
                        if(temp == -1){
                            break;
                        }
                        fos.write(buffer, 0, temp); 
                     }
                     System.out.println("文件保存成功！");

                }catch(Exception e){
                    System.out.println("");
                }
                 path="data.xml";
                 FileWriter fw=new FileWriter(path,true);   
                 PrintWriter pw=new PrintWriter(fw);
                System.out.println();
                System.out.println("服务器端的返回报文为：");
                for(int i =0;i<=11;i++){
                    s=is.readLine();  
                    if(s!=null){
                         pw.println(s);
                         System.out.println(s);
                    }
                    else{
                        pw.println("Date:" + new Date().toGMTString() + "\r\n");
                        break;
                    }

                }
                pw.close();   
                fw.close(); 
            }      










    } catch (IOException e) {   
        // TODO Auto-generated catch block   
        e.printStackTrace();   
    }   
        try {
                os.close();
                is.close();
                cSocket.close();
        } catch (Exception e) {
                e.printStackTrace();
        }

    }

}

//127.0.0.1
//GET /index.html HTTP/1.0



