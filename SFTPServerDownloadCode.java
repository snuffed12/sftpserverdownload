package downloadfile;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.*;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.Date;
import java.util.Scanner;


public class DownloadFile {
 
    public static void main(String[] args) throws Exception {

 JSch jsch = new JSch();
        Session session = null;
        try {
            //find current date
            Calendar today = Calendar.getInstance();
            int day=today.get(DAY_OF_MONTH);
            int month=today.get(MONTH)+1; //added one cause i'm getting back last month, not the current one
            int year=today.get(YEAR);
            String date=year+"-"+"0"+month+"-"+day;
            System.out.println(date);
            
            //connect and download the log
            session = jsch.getSession(username, ipAddress, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();
            
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.get(pathToFileOnTheServer);  
            sftpChannel.exit();
            session.disconnect();
            
           //find number of occurences of some word
           
            String word="word";
            
            int count=0;
              
            BufferedReader in = new BufferedReader(new FileReader("testDownload.txt"));
              
            String line = in.readLine();
               do {
                count += (line.length() - line.replace(word, "").length()) / word.length();
                line= in.readLine();
            } while (line != null);
               
               //print out the occurences
            
            System.out.print("There are " + count + " occurrences of " + word + " in today's log\n ");
            
            
        } catch (JSchException e) {
            e.printStackTrace();  
        } catch (SftpException e) {
            e.printStackTrace();
        }

   }

}