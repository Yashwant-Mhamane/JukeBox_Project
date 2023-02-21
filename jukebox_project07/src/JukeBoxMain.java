
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
import java.sql.*;
import java.util.*;
public class JukeBoxMain {
    Connection connection = null;
    Statement statement;
    ResultSet rs;
    User us = new User();
    Songs sg = new Songs();
    int choose = 0;
    static int uid1;
    int i = 0;
    int x = 0;
    String str = "";

    public String getPodcastPath(int pid) throws SQLException {
        System.out.println(pid);
           connection= us.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("select File_Path from Podcast where Podcast_Id=" + pid);
            while (rs.next()) {
                str = rs.getString(1);
            }
            return str;
    }

    public void musicPlay(ArrayList<Songs> list, int songid) {
        Scanner sc = new Scanner(System.in);
        Iterator<Songs> it = list.iterator();
        String songpath = "";
        while (it.hasNext()) {
            Songs s = it.next();
            if (s.getSong_Id() == songid) {
                songpath = s.getFile_Path();
            }
        }

        int i = -1;
        File musicfile = new File(songpath);
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(musicfile);
            Clip clip = AudioSystem.getClip();
            long time = 0l;
            clip.open(audio);
            do {
                System.out.println("Enter 1.Play▶️ 2.Pause⏸️ 3.Resume⏯️ 4.Replay♻ 5.Stop🚫 6.Prevous⏮️ 7.Next⏭️ the song🎶 & 0 to exit ");
                i = sc.nextInt();
                if (i > 7 || i < 0) {
                    System.out.println("Wrong input.");
                }
                switch (i) {
                    case 1: {
                        clip.start();
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        break;
                    }
                    case 5: {
                        clip.close();
                        break;
                    }
                    case 4: {
                        clip.setMicrosecondPosition(0);
                        clip.start();
                        break;
                    }
                    case 2: {
                        clip.stop();
                        time = clip.getMicrosecondPosition();
                        long tot= (clip.getMicrosecondLength()/1000000)/60;
                        System.out.println("Total duration: "+tot+" seconds");
                        long micro=(clip.getMicrosecondPosition()/1000000)/60;
                        System.out.format("Song played for:"+micro+" seconds");
                        System.out.println("Time remaining ⌛:"+(tot-micro)+" seconds");
                        break;
                    }
                    case 3: {
                        clip.setMicrosecondPosition(time);
                        clip.start();
                        break;
                    }
                    case 6:{
                       clip.close();
                        System.out.println("⏮️⏮️ Previous Song");
                        if(songid-1==0)
                        {
                            System.out.println("This is the 1st song.\n");
                        }
                        else
                        {
                            musicPlay(list,songid-1);
                            break;
                        }
                    }
                    case 7:{

                        System.out.println("Next Song ⏭️⏭️");
                        clip.close();
                        clip.stop();
                        if(songid==list.size())
                        {
                            System.out.println("This is last song.\n");

                        }
                        else
                        {
                            musicPlay(list,songid+1);
                            i=0;
                        }
                        break;
                    }
                }
            }
            while (i != 0);

        } catch (Exception e) {
            System.out.println("Enter Above mentioned Song Id only.");

            System.out.println("Enter Song id :");
            songid = sc.nextInt();
            musicPlay(list, songid);

        }
    }

    public void display() {
        int choose=0;
        Playlist pl = new Playlist();
        Podcast pc = new Podcast();
       Scanner sc = new Scanner(System.in);
        try {
            System.out.println("\nChoose 1 for LogIn 2 for Signup / Create New Account :");
            choose = sc.nextInt();
            if (choose == 1) {
                 us.checkUser();
                displayLogIn();
            }

            else if (choose == 2) {
                us.newUser();
                us.checkUser();
                displaySignUp();
            }
           else if (choose >= 3) {
                System.out.println("Invalid selection.🫤");
                display();
            }
            }catch(Exception ex){
            System.out.println("\nEnter Integers Only.");
            display();
        }
    }
    public void displayLogIn() throws SQLException {
            int choose;
        Scanner sc = new Scanner(System.in);
            Playlist pl = new Playlist();

            Podcast pc = new Podcast();
            try {
                do {
                    System.out.println("Choose 1 for Songs🎵 2 for Podcast🔊 3 for Playlist📃 & 0 to exit ↪️");
                    choose = sc.nextInt();
                    if (choose == 1) {
                        sg.displaySongs();
                    }
                   else if (choose == 2) {
                        pc.displayPodcast();
                    }
                   else if (choose == 3) {
                        pl.displayPlaylist();
                        System.out.println("Enter C for create playlist or any key to return in main menu");
                        String opt = sc.next();
                        if (opt.equals("C")) {
                            pl.createNewPlaylist();
                        }
                    }
                    else if (choose > 3|| choose<0) {
                        System.out.println("Invalid selection.🫤\n");
                        displaySignUp();
                    }

                } while (choose != 0);
           }
            catch(Exception e)
            {
                System.out.println("Wrong Input.");
                 displayLogIn();
            }

    }
    public void displaySignUp() throws SQLException {
            int choose;
        Scanner sc = new Scanner(System.in);
            Playlist pl = new Playlist();
            Podcast pc = new Podcast();

           try {
                do {
                    System.out.println("Choose 1 for Songs🎵 2 for Podcast🔊 3 for Playlist📃 & 0 to exit ↪️");
                    choose = sc.nextInt();

                    if (choose == 1) {

                        sg.displaySongs();
                    }
                   else if (choose == 2) {
                        pc.displayPodcast();
                    }
                   else if (choose == 3) {
                        pl.displayPlaylist();
                        System.out.println("Enter C for create playlist or any key to return in main menu");
                        String opt = sc.next();
                        if (opt.equals("C")) {
                            pl.createNewPlaylist();
                        }
                    }
                    else if (choose > 3|| choose<0) {
                        System.out.println("Invalid selection.🫤\n");
                        displaySignUp();
                    }

                } while (choose != 0);
            } catch (Exception e) {
                System.out.println("Wrong Input.\n");
                displaySignUp();
            }
        }


    public static void main(String[] args) throws SQLException {
        JukeBoxMain jb = new JukeBoxMain();
        Playlist pl = new Playlist();
        int choose=0;
        String str1 = "";
        Scanner sc = new Scanner(System.in);
        JOptionPane.showMessageDialog(null, "WelCome to Audio Stream.");
        Juke_Var ref=()-> {
            System.out.println("----------------------------------------------------------------------------------------------------------");
            System.out.println("------🎶💙💚💛🧡💜️️❤️🎶🎶💙💚💛🧡💜️️❤️🎶️-WELCOME TO SAREGAMA-🎶💙💚💛🧡💜️️❤️🎶🎶💙💚💛🧡💜️️❤️🎶------");
            System.out.println("----------------------------------------------------------------------------------------------------------");
            System.out.println("------------------✨✨✨Music 🎶 Podcast 📖 and many more- By Mirchi Production.✨✨✨------------------");
        };
        ref.welcome();
            try {
                jb.display();
            }catch (Exception e){
                System.out.println("Enter valid input.");
            }


    }
}



