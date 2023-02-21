import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Songs
{
        private int Song_Id;
        private String Song_Name;
        private String Album_Name;
        private String Artist_Name;
        private String Genre;
        private String Duration;
        private String File_Path;

        public int getSong_Id() {
                return Song_Id;
        }
        public void setSong_Id(int song_Id) {
                Song_Id = song_Id;
        }
        public String getSong_Name() {
                return Song_Name;
        }

        public void setSong_Name(String song_Name) {
                Song_Name = song_Name;
        }

        public String getAlbum_Name() {
                return Album_Name;
        }

        public void setAlbum_Name(String album_Name) {
                Album_Name = album_Name;
        }

        public String getArtist_Name() {
                return Artist_Name;
        }

        public void setArtist_Name(String artist_Name) {
                Artist_Name = artist_Name;
        }

        public String getGenre() {
                return Genre;
        }

        public void setGenre(String genre) {
                Genre = genre;
        }

        public String getDuration() {
                return Duration;
        }

        public void setDuration(String duration) {
                Duration = duration;
        }

        public String getFile_Path() {
                return File_Path;
        }

        public void setFile_Path(String file_Path) {
                File_Path = file_Path;
        }

        Connection connection=null;
        Statement statement;
        ResultSet rs;
        Scanner sc;
       JukeBoxMain jb;
        User us=new User();
        int i=0;
        String str="";

        public Songs(int song_Id, String file_Path) {
                Song_Id = song_Id;
                File_Path = file_Path;
        }

        public Songs(int song_Id, String song_Name, String album_Name, String artist_Name, String genre, String duration, String file_Path) {
                Song_Id = song_Id;
                Song_Name = song_Name;
                Album_Name = album_Name;
                Artist_Name = artist_Name;
                Genre = genre;
                Duration = duration;
                File_Path = file_Path;
        }
        public Songs() {

        }
        public void displaySongs() throws SQLException
        {  String str1;
                jb = new JukeBoxMain();
                connection=us.getConnection();
                statement = connection.createStatement();
                do{     sc= new Scanner(System.in);
                        System.out.println("\nChoose 1 To view All Songs 2 To view Songs by Genre 3 to view songs by artist & 0 to return in main menu.");
                        i = sc.nextInt();
                        if(i==1){
                                ArrayList<Songs> listsong=new ArrayList<>();
                                rs = statement.executeQuery("select * from songs");
                                System.out.println("-----------------------------------------------------------------------------------------------------------------");
                                System.out.format("%-10s %-22s %-22s %-20s %-20s %-18s","Song_ID","Song_Name","Album_Name", "Artist_Name", "Genre", "Duration");
                                System.out.println();
                                System.out.println("-----------------------------------------------------------------------------------------------------------------");
                                while (rs.next())
                                {   listsong.add(new Songs(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5),rs.getString(6),rs.getString(7)));
                                        System.out.format("%-10s %-22s %-22s %-20s %-20s %-18s \n",rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                                }
                                System.out.println("-----------------------------------------------------------------------------------------------------------------");
                                System.out.println("Enter Song id :");
                                i = sc.nextInt();
                                jb.musicPlay(listsong,i);
                        }
                        else if(i==2)
                        {
                                System.out.println("Enter Genre u want to ➡️ Pop🎧,Classical🎷,Devotional🛕,Patriot🚩.");
                                str = sc.next();
                                str1=str.toLowerCase();
                                if(str1.equals("pop")|| str1.equals("devotional")||str1.equals("classical")||str1.equals("patriot")) {
                                        ArrayList<Songs> listGenre=new ArrayList<>();
                                        rs = statement.executeQuery("select Genre, Song_Id, Song_Name, Duration,File_Path from songs where Genre='" + str1 + "'");
                                        System.out.println("---------------------------------------------------------");
                                        System.out.format("%-11s %-30s %-25s", "Song_ID", "Song_Name", "Duration");
                                        System.out.println();
                                        System.out.println("---------------------------------------------------------");
                                        while (rs.next()) {

                                                listGenre.add(new Songs(rs.getInt(2),rs.getString(5)));
                                                System.out.format("%-11s %-30s %-25s\n", rs.getInt(2), rs.getString(3), rs.getString(4));
                                        }
                                        System.out.println("---------------------------------------------------------");
                                        System.out.println("Enter Song id :");
                                        i = sc.nextInt();
                                                jb.musicPlay(listGenre,i);

                                }
                                else {
                                        System.out.println("Wrong Genre Input,It should be mentioned only.");
                                }
                        }

                       else if(i==3)
                        { try{
                                System.out.println("Enter Artist Name u want to ➡️ Brodha_V, 🎧Ayushman,🎵B_Praak,🎶Santh_Kumari");
                                str=sc.next();
                                str1=str.toLowerCase();
                                if(str1.equals("brodha_v")|| str1.equals("ayushman")||str1.equals("b_praak")||str1.equals("santha_kumari")) {
                                        ArrayList<Songs> listGenre = new ArrayList<>();
                                        rs = statement.executeQuery("select Artist_Name, Song_Id, Song_Name, Duration, File_Path  from songs where Artist_Name='" + str + "';");
                                        System.out.println("----------------------------------------------------");
                                        System.out.format("%-11s %-30s %-25s", "Song_ID", "Song_Name", "Duration");
                                        System.out.println();
                                        System.out.println("-----------------------------------------------------");
                                        while (rs.next()) {
                                                listGenre.add(new Songs(rs.getInt(2), rs.getString(5)));
                                                System.out.format("%-11s %-30s %-25s\n", rs.getInt(2), rs.getString(3), rs.getString(4));
                                        }
                                        System.out.println("-----------------------------------------------------");
                                        System.out.println("Enter Song id :");
                                        i = sc.nextInt();
                                        jb.musicPlay(listGenre, i);
                                }
                                 else {
                                                System.out.println("Wrong Genre Input,It should be mentioned only.");
                                        }
                        }catch (Exception e)
                        {
                                System.out.println(e);
                        }
                        }
                        else if (i > 3|| i<0) {
                                System.out.println("Invalid selection.🫤");
                                displaySongs();
                        }

                }while (i!=0);
        }
}
