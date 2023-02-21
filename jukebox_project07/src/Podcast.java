import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Podcast {
    Statement statement;
    ResultSet rs;
    Scanner sc= new Scanner(System.in);
    User us=new User();
    JukeBoxMain jb;
    int i=0;
    String str="";

    public void displayPodcast() throws SQLException {
        String str1;
        Connection connection=us.getConnection();
        statement = connection.createStatement();
        jb = new JukeBoxMain();
               do{
            System.out.println("\nChoose 1 To view All Podcast 2 to view Podcast by Type  3 to view Podcast by Speaker & 0 to return in main menu.");
            i = sc.nextInt();
            if(i==1){
                ArrayList<Integer> listpodcast=new ArrayList<>();
                rs = statement.executeQuery("select * from podcast");
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.format("%-13s %-30s %-55s %-20s %-20s %-18s", "Podcast_ID", "Podcast_Name", "Podcast_Ep", "Speaker_Name", "Type_of_podcast", "Duration");
                System.out.println();
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
                while (rs.next()) {
                    listpodcast.add(rs.getInt(1));
                    System.out.format("%-13s %-30s %-55s %-20s %-20s %-18s \n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                }
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Enter Podcast id :");
                int pid = sc.nextInt();
                if(listpodcast.contains(pid))
                {
                    str1=jb.getPodcastPath(pid);
                    System.out.println(str1);
                } else
                {
                    System.out.println("Enter above mentioned Podcast Id only");
                }
            }
           else if(i==2) {
                System.out.println("Enter Type of Podcast u want  ➡️ Motivation🎧,History⌛,Science🌍.");
                str = sc.next();
                ArrayList<Integer> listtype=new ArrayList<>();
                rs = statement.executeQuery("select Type_of_podcast, Podcast_Id, Podcast_Name,Podcast_Ep from podcast where " +
                        "Type_of_podcast like'" + str + "%';");
                System.out.println("---------------------------------------------------------------------------------------------------");
                System.out.format("%-13s %-30s %-55s", "Podcast_ID", "Podcast_Name", "Podcast_Ep");
                System.out.println();
                System.out.println("---------------------------------------------------------------------------------------------------");
                while (rs.next()) {
                    listtype.add(rs.getInt(2));
                    System.out.format("%-13s %-30s %-55s\n",rs.getInt(2),rs.getString(3),rs.getString(4));
                }
                System.out.println("---------------------------------------------------------------------------------------------------");
                System.out.println("Enter Podcast id :");
                int pid = sc.nextInt();
                if(listtype.contains(pid))
                {
                    str1=jb.getPodcastPath(pid);
                    System.out.println(str1);
                } else
                {
                    System.out.println("Enter above mentioned Podcast Id only");
                }
            }
           else if(i==3){
                System.out.println("Enter Speaker Name u want to listen ➡️ Sadguru,🎧,Sudipta_Bhawmik,🎶Suno_India");
                str = sc.next();
                ArrayList<Integer> listtype=new ArrayList<>();
                rs = statement.executeQuery("select Speaker_Name, Podcast_Id, Podcast_Name,Podcast_Ep  from podcast where " +
                        "Speaker_Name like'" + str + "%';");
                System.out.println("--------------------------------------------------------------------------------------------------");
                System.out.format("%-13s %-30s %-52s", "Podcast_ID", "Podcast_Name", "Podcast_Ep");
                System.out.println();
                System.out.println("--------------------------------------------------------------------------------------------------");
                while (rs.next()) {
                    listtype.add(rs.getInt(2));
                    System.out.format("%-13s %-30s %-52s\n",rs.getInt(2), rs.getString(3),rs.getString(4));
                }
                System.out.println("--------------------------------------------------------------------------------------------------");

                System.out.println("Enter Podcast id :");
                int pid = sc.nextInt();
                if(listtype.contains(pid)){
                    str1=jb.getPodcastPath(pid);
                    System.out.println(str1);
                }
                else {
                    System.out.println("Enter above mentioned Podcast Id only");
                }
            }
        }while (i!=0);
    }
}
