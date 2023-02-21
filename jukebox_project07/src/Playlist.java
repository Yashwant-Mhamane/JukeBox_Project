import java.sql.*;
import java.util.*;
public class Playlist {
    JukeBoxMain jb;
    User us = new User();
    Connection con = us.getConnection();
    Statement statement = null;
    ResultSet rs;
    int x = 0;
    public void createNewPlaylist() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1 To add Song 2 to Add Podcast 3 to add Both in playlist.");
        int choose = sc.nextInt();
        if (choose == 1) {
            createNewPlaylistSong();
        }
        if (choose == 2) {
            createNewPlaylistPodcast();
        }
        if (choose == 3) {
            createNewPlaylistBoth();
        }
    }
    public void createNewPlaylistBoth() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Playlist Id :");
        int plid = sc.nextInt();
        System.out.println("Enter Playlist Name :");
        String pname = sc.next();
        int uid = us.uid1;
        System.out.println("Enter Song Id :");
        int sid = sc.nextInt();
        rs = statement.executeQuery("select Song_Id from playlist where userid = " + uid + ";");
        ArrayList<Integer> str = new ArrayList<Integer>();
        while (rs.next()) {
            str.add(rs.getInt(1));
        }
        if (str.contains(sid)) {
            System.out.println("Song already exist in Playlist add another one.");
            createNewPlaylistBoth();
        } else {
            System.out.println("Enter podcast Id :");
            int pid = sc.nextInt();
            rs = statement.executeQuery("select Podcast_Id from playlist where userid = " + uid + ";");
            ArrayList<Integer> str1 = new ArrayList<Integer>();
            while (rs.next()) {
                str1.add(rs.getInt(1));
            }
            if (str1.contains(pid)) {
                System.out.println("Podcast already exist in Playlist add another one.");
                createNewPlaylistBoth();
            } else {
                x = statement.executeUpdate("insert into playlist (Playlist_Id,Playlist_Name, userid, Song_Id, Podcast_Id) values(" + plid + " ,'" + pname + "' , '" + uid + "','" + sid + "','" + pid + "');");
                if (x > 0) {
                    System.out.println("Song & Podcast added to playlist successfully.😊\n");
                } else {
                    System.out.println("Sorry🫤,Error while adding song & Podcast to playlist");
                }
            }
        }
    }

    public void createNewPlaylistSong() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Playlist Id :");
        int plid = sc.nextInt();
        System.out.println("Enter Playlist Name :");
        String pname = sc.next();
        int uid = us.uid1;
        System.out.println("Enter Song Id :");
        int sid = sc.nextInt();
        rs = statement.executeQuery("select Song_Id from playlist where userid = " + uid + ";");
        ArrayList<Integer> str = new ArrayList<Integer>();
        while (rs.next()) {
            str.add(rs.getInt(1));
        }
        if (str.contains(sid)) {
            System.out.println("Song already exist in Playlist add another one.");
            createNewPlaylistSong();
        } else {
            statement = con.createStatement();
            x = statement.executeUpdate("insert into playlist (Playlist_Id,Playlist_Name, userid, Song_Id) values(" + plid + " ,'" + pname + "' , '" + uid + "','" + sid + "');");
            if (x > 0) {
                System.out.println("Song added to playlist successfully.😊\n");
            } else {
                System.out.println("Sorry🫤,Error while adding song to playlist");
            }
        }
    }

    public void createNewPlaylistPodcast() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Playlist Id :");
        int plid = sc.nextInt();
        System.out.println("Enter Playlist Name :");
        String pname = sc.next();
        int uid = us.uid1;
        System.out.println("Enter podcast Id :");
        int pid = sc.nextInt();
        rs = statement.executeQuery("select Podcast_Id from playlist where userid = " + uid + ";");
        ArrayList<Integer> str = new ArrayList<Integer>();
        while (rs.next()) {
            str.add(rs.getInt(1));
        }
        if (str.contains(pid)) {
            System.out.println("Podcast already exist in Playlist add another one.");
            createNewPlaylistPodcast();
        } else {
            x = statement.executeUpdate("insert into playlist (Playlist_Id,Playlist_Name, userid, Podcast_Id) values('" + plid + " ,'" + pname + "' , '" + uid + "','" + pid + "');");
            if (x > 0) {
                System.out.println("Podcast added to playlist successfully.😊\n");
            } else {
                System.out.println("Sorry🫤,Error while adding Podcast to playlist");
            }
        }
    }

    public void displayPlaylist() throws SQLException {
        Scanner sc = new Scanner(System.in);
        jb = new JukeBoxMain();
        statement = con.createStatement();
        ArrayList<Songs> listPlayList = new ArrayList<>();
        rs = statement.executeQuery("select * from playlist where userid=" + us.uid1 + ";");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.format("%-10s %-15s %-20s %-15s %-20s", "Sr.No", "Playlist_Id", "Playlist_Name", "Song_Id", "Podcast_ID");
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------");
        while (rs.next()) {

            System.out.format("%-10s %-15s %-20s %-15s %-20s\n", rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(5), rs.getInt(6));
        }
        rs = statement.executeQuery("select songs.Song_Id, File_Path from songs join playlist on songs.Song_Id=playlist.Song_Id");
        while (rs.next()) {
            listPlayList.add(new Songs(rs.getInt(1), rs.getString(2)));
        }
        System.out.println("--------------------------------------------------------------------------------------");
        try {
            int i = 0;
            do {
                System.out.println("Enter 1 to listen Song & 2 to listen Podcast or 0 to Create Playlist or return in main menu");
                i = sc.nextInt();
                String str1 = "";
                if (i == 1) {
                    System.out.println("Enter Song id :");
                    int songid = sc.nextInt();
                    jb.musicPlay(listPlayList, songid);
                }
                else if (i == 2) {
                    ArrayList<String> listPlayListpc = new ArrayList<>();
                    ArrayList<Integer> listPlayListpc1 = new ArrayList<>();
                    rs = statement.executeQuery("select podcast.Podcast_Id, File_Path from Podcast join playlist on podcast.Podcast_Id=playlist.Podcast_Id and playlist.userid="+us.uid1);
                    while (rs.next()) {
                        listPlayListpc.add(rs.getString(2));
                        listPlayListpc1.add(rs.getInt(1));
                    }
                    System.out.println("Enter Podcast id :");
                    int pid = sc.nextInt();
                    if (listPlayListpc1.contains(pid)) {
                        str1 = jb.getPodcastPath(pid);
                        System.out.println(str1);
                    }
                    else {
                        System.out.println("Enter above mentioned Podcast Id only");
                    }
                }
            } while (i != 0);
        } catch (Exception e) {
            System.out.println("\nEnter valid input.");
            displayPlaylist();
        }
    }
}