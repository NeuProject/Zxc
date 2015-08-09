import java.io.*;
import java.net.*;
import java.util.*;

/**
* 简单的HTTP 1.0 服务器程序
* @author  WangQi_Experiment4
* @version 1.0.0
*/
public class Server {
	
	public static void main(String args[]) throws  IOException {
		
		/*
		 * 典型的HTTP 请求报文：
			GET /somedir/page.html HTTP/1.0
			Host:www.someschool.edu
			Connection: close
			User-agent:Mozilla/4.0
			Accept-language:fr
		 */
		 int PORT=8000;
		 String Path = "";	//基础路径	
		 try{
				if(args.length!=1){
					
					System.out.println("HTTP服务器的路径为/，端口号为：" + PORT);
				}else if(args.length ==1){
					Path = args[0];
					System.out.println("HTTP服务器的路径为" + args[0] + "，" + "端口号为：" + PORT);
					
				}
			}catch (Exception ex) {
				System.err.println("无效的路径");
	        } 
		
				ServerSocket SS = new ServerSocket(PORT);
					
		while(true){			
				Socket client = SS.accept();//客户端Socket,客户机已经连接到当前服务器
				if(client !=null){
					System.out.println("目前与服务器连接的用户为:"+client);
				}
							  //打开输入流
				BufferedReader br = new BufferedReader(new InputStreamReader(
						client.getInputStream()));

			  //读取请求报文的第一行
				
                                try{
                                        String firstline = br.readLine();
                                        System.out.println("客户端发送的请求报文:\n" + firstline);
                                        StringTokenizer tokenizer = new StringTokenizer(firstline," ");
                                        tokenizer.nextToken();

                                        String URL = tokenizer.nextToken();
                                        StringTokenizer st = new StringTokenizer(URL,"/");

                                        String URLRight = st.nextToken();//读取文件类型
                                        System.out.println(URLRight);

                                        if(URLRight.equals("")){
                                            URLRight = "index.html";//缺省状态
                                        }
                                          //找出文件的存储路径
                                        String path ="";
                                        String dir = Path ; 
                                        if(Path.equals("")){
                                            path = "." + dir + "/"+ URLRight ;  
                                        }else{
                                            path = dir + "/" +URLRight;
                                        }


                                       //判断客户端请求的内容，并提取文件
                                        File file = new File(path);

                                        if (file.exists() && file.isFile()){
                                                 if(URLRight.contains(".jpeg") || URLRight.contains(".jpg"))
                                                 imageSend(file,client);
                                                 else
                                                 textSend(file,client);      
                                        }else{
                                            System.out.println("服务器返回报文:\n");
                                            String outprint = "HTTP/1.0 404 no found\r\n"+
		   					 "Connection: close\r\n"+
		   					 "Date:" + new Date().toGMTString() + "\r\n"+
		   					 "Server:WangQi server"+
		   					 "\r\n"+
		   					 "Content-Length:" + file.length() + "\r\n"+
		   					 "Content-Type:text/html\r\n"+
		   					 "\r\n";
                                            System.out.println(outprint);
                                            PrintWriter out = new PrintWriter(client.getOutputStream(),true);
                                            
                                            String nofind="<html><head><title>Not Found</title></head>" +
                                                                "<body><h1>Not Found !!!</h1></body>" +
                                                                       "</html>"+
                                                    "HTTP/1.0 404 no found\r\n"+
		   					 "Connection: close\r\n"+
		   					 "Date:" + new Date().toGMTString() + "\r\n"+
		   					 "Server:WangQi server"+
		   					 "\r\n"+
		   					 "Content-Length:" + file.length() + "\r\n"+
		   					 "Content-Type:text/html\r\n"+
		   					 "\r\n";
                                            
                                            out.println(nofind);
                                            

                                        }
                                }catch (Exception ex) {
                                    System.err.println("null");
                                }
				
			   
			 
	            
				client.close();
			
	}
		
		
}


public static void textSend(File file, Socket socket){
	   
	try{
            System.out.println("服务器返回报文:\n");
		  String outprint = "HTTP/1.0 200 OK\r\n"+
                                    "Connection: close"+
                                    "Date:" + new Date().toGMTString() + "\r\n"+
                                    "Server:WangQi server"+
                                    "\r\n"+
                                    "Content-Length:" + file.length() + "\r\n"+
                                    "Content-Type:text/html\r\n"+
                                    "\r\n";
		  
		   System.out.println(outprint);
		  //字符流
		   PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
		   BufferedReader input = new BufferedReader( new FileReader(file.getPath()));
		   String line = input.readLine();

	        while (line != null) {
	            out.println(line);
	            line = input.readLine();
	        }
	        input.close();
	        out.close();
	}catch(IOException e){
		System.err.println(e);
	}
	   
	}

public static void imageSend(File file, Socket socket){
	try{
            System.out.println("服务器返回报文:\n");
		   String outprint = "HTTP/1.0 200 OK\r\n"+
                                    "Connection: close\r\n"+
                                    "Date:" + new Date().toGMTString() + "\r\n"+
                                    "Server:WangQi server"+
                                    "\r\n"+
                                    "Content-Length:" + file.length() + "\r\n"+
                                    "Content-Type:text/html\r\n"+
                                    "\r\n";
		 
		   System.out.println(outprint);
		   //字节流
		   PrintStream out1 = new PrintStream(socket.getOutputStream(), true);
		   FileInputStream fis = new FileInputStream(file);
	       byte data[] = new byte[fis.available()];
	       fis.read(data);
	       out1.write(data);
	       out1.close();
	       fis.close();
	}catch(IOException e){
		System.err.println(e);
	}   
	
}

}


