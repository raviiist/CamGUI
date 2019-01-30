package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

public class AllCams {
    String configFile="camconfig.inp";
    //Path congFilePath= Paths.get(configArea+configFile);
    Path congFilePath= Paths.get(configFile);

    @FXML
    private Hyperlink scene1link;

    @FXML
    private Button refresh01;

    @FXML
    private TextArea c2cAlarm1;

    @FXML
    private TextArea c2cAlarm11;

    @FXML
    private TextArea c2cAlarm12;

    @FXML
    private TextArea c2cAlarm13;

    @FXML
    private TextArea c2cAlarm14;

    @FXML
    private TextArea c2cAlarm15;

    @FXML
    private Button ack01;

    @FXML
    private Label sc_label11;

    @FXML
    private Label sc_label12;

    @FXML
    private Label sc_label13;

    @FXML
    private Label sc_label14;

    @FXML
    private Label sc_label15;

    @FXML
    private Label sc_label16;


    @FXML
    void AckAlarms(ActionEvent event) {
            Charset charset = Charset.forName("US-ASCII");
            Path fpath = Paths.get("camProg.out");
            //String exeline4terminal = "gnome-terminal --geometry=100x25 -- sh -c \""+hm+"/"+exefile+"; exec sh\"";
            System.out.println(fpath.toString()+" "+fpath);
        Calendar t = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy:DDD:HH:mm:ss.SSS");
        System.out.println(sdf1.format(t.getTime()));
        String timeOfAck = "Time of Ack: "+sdf1.format(t.getTime());
        try {
            if(!Files.exists(fpath)) {
                fpath.toFile().createNewFile();
            }
                //System.out.println("Writing and setting permissions..");
                Set<PosixFilePermission> perms = Files.readAttributes(fpath, PosixFileAttributes.class).permissions();
                //System.out.println("Permission before:" + PosixFilePermissions.toString(perms));
                perms.add(PosixFilePermission.OWNER_WRITE);
                perms.add(PosixFilePermission.GROUP_READ);
                perms.add(PosixFilePermission.OTHERS_READ);
                Files.setPosixFilePermissions(fpath, perms);
                //System.out.println("Permission now:" + PosixFilePermissions.toString(perms));

            BufferedWriter w = new BufferedWriter(new FileWriter(fpath.toString(),true));
            w.write(timeOfAck+"\n");
            w.close();
            w = new BufferedWriter(new FileWriter(fpath.toString(),true));
            w.write("---------------------------------------------\n");
            w.close();
            Set<PosixFilePermission> perms1 = Files.readAttributes(fpath, PosixFileAttributes.class).permissions();
            perms1.add(PosixFilePermission.OWNER_READ);
            perms1.add(PosixFilePermission.GROUP_READ);
            perms1.add(PosixFilePermission.OTHERS_READ);
            Files.setPosixFilePermissions(fpath, perms);
            //System.out.println("Permission now:" + PosixFilePermissions.toString(perms));
            ack01.setVisible(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadConfigData(Path file, String sat) {
        String line = null, dir="", exe4cam="";
        String LONGCLOG="", PLOT4DAY="";
        String sp1[]; int lineNo=0;
        String satids[] = new String[20];
        try {
            BufferedReader br = Files.newBufferedReader(file);
            while ((line = br.readLine()) != null) {
                sp1 = line.split(" ");

                if (line.contains("Execute")) {
                    exe4cam=sp1[1];
                }
                if(line.length() >= 5 && sp1[0].length()==3 ) {
                    sat = sp1[0];
                    dir = sp1[1];
                }
                satids[lineNo]=sat;
                lineNo=lineNo+1;
            }
            System.out.println(satids[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i=0; i<lineNo; i++){

        }
        return dir+" "+exe4cam+" "+lineNo+" "+satids[1]+" "+satids[2]+" "+satids[3]+" "+satids[4]+" "+satids[5]+" "+satids[6]+" "+satids[7];
    }

    @FXML
    void ShowAlarms(ActionEvent event) {
        ack01.setVisible(true);
        String defaultSat="xxx";

        /*if (satid.getText().length()!=3) {
            defaultSat="xxx";
        } else {
            defaultSat=satid.getText();
        }*/

        String str=loadConfigData(congFilePath, defaultSat);
        if (defaultSat.length()==3) {
            /*String line = null;
            String sp1[]; int lineNo=0;
            BufferedReader br = Files.newBufferedReader(file);
            while ((line = br.readLine()) != null) {
                sp1 = line.split(" ");
                lineNo=lineNo+1;
                if (line.contains("Execute")) {
                    exe4cam=sp1[1];
                }
                if(line.length() >= 5 && sp1[0].length()==3) {
                    //satids = line.substring(0, 3);
                    satids = sp1[0];
                    dir = sp1[1];
                }
            }*/
        //} else {
            String line, sp2[]; int rtp=1;
            System.out.println(str);
            sp2 = str.split(" ");
            System.out.println(sp2[0] +" "+sp2[1]);
            //String sat=sp2[0], path=sp2[1];
            //String file="CamLog."+sat.toUpperCase()+".mx2"+sat+"rtp"+rtp+".txt";
            //System.out.println(file);
            c2cAlarm1.clear();
            c2cAlarm1.appendText(sp2[3].toUpperCase()+"\n");
            c2cAlarm11.clear();
            c2cAlarm11.appendText(sp2[4].toUpperCase()+"\n");
            c2cAlarm12.clear();
            c2cAlarm12.appendText(sp2[5].toUpperCase()+"\n");
            c2cAlarm13.clear();
            c2cAlarm13.appendText(sp2[6].toUpperCase()+"\n");
            c2cAlarm14.clear();
            c2cAlarm14.appendText(sp2[7].toUpperCase()+"\n");
            c2cAlarm15.clear();
            c2cAlarm15.appendText(sp2[8].toUpperCase()+"\n");
            sc_label11.autosize();
            sc_label12.autosize();
            sc_label13.autosize();
            sc_label14.autosize();
            sc_label15.autosize();
            sc_label16.autosize();
            sc_label11.setWrapText(true);
            sc_label12.setWrapText(true);
            sc_label13.setWrapText(true);
            sc_label14.setWrapText(true);
            sc_label15.setWrapText(true);
            sc_label16.setWrapText(true);
            sc_label11.setText(sp2[3].toUpperCase());
            sc_label12.setText(sp2[4].toUpperCase());
            sc_label13.setText(sp2[5].toUpperCase());
            sc_label14.setText(sp2[6].toUpperCase());
            sc_label15.setText(sp2[7].toUpperCase());
            sc_label16.setText(sp2[8].toUpperCase());

            String shellcmd=sp2[1];
            //Path exepath= Paths.get(shellcmd);
            try {
                Process p = Runtime.getRuntime().exec(shellcmd);
                System.out.println("Running "+shellcmd+" execute");
                //BufferedReader br = Files.newBufferedReader(Paths.get(exepath.getParent()+"/"+"logtest"));
                if(!Files.exists(Paths.get("logtest."+sp2[3]))) {
                    (Paths.get("logtest."+sp2[3])).toFile().createNewFile();
                }
                BufferedReader br3 = Files.newBufferedReader(Paths.get("logtest."+sp2[3]));          //For First Line satellite
                while ((line = br3.readLine()) != null) {

                    c2cAlarm1.appendText(line+"\n");
                }
                if(!Files.exists(Paths.get("logtest."+sp2[4]))) {
                    (Paths.get("logtest."+sp2[4])).toFile().createNewFile();
                }
                BufferedReader br4 = Files.newBufferedReader(Paths.get("logtest."+sp2[4]));          //For First Line satellite
                while ((line = br4.readLine()) != null) {

                    c2cAlarm11.appendText(line+"\n");
                }
                if(!Files.exists(Paths.get("logtest."+sp2[5]))) {
                    (Paths.get("logtest."+sp2[5])).toFile().createNewFile();
                }
                BufferedReader br5 = Files.newBufferedReader(Paths.get("logtest."+sp2[5]));          //For First Line satellite
                while ((line = br5.readLine()) != null) {

                    c2cAlarm12.appendText(line+"\n");
                }
                if(!Files.exists(Paths.get("logtest."+sp2[6]))) {
                    (Paths.get("logtest."+sp2[6])).toFile().createNewFile();
                }
                BufferedReader br6 = Files.newBufferedReader(Paths.get("logtest."+sp2[6]));          //For First Line satellite
                while ((line = br6.readLine()) != null) {

                    c2cAlarm13.appendText(line+"\n");
                }
                if(!Files.exists(Paths.get("logtest."+sp2[7]))) {
                    (Paths.get("logtest."+sp2[7])).toFile().createNewFile();
                }
                BufferedReader br7 = Files.newBufferedReader(Paths.get("logtest."+sp2[7]));          //For First Line satellite
                while ((line = br7.readLine()) != null) {

                    c2cAlarm14.appendText(line+"\n");
                }
                if(!Files.exists(Paths.get("logtest."+sp2[8]))) {
                    (Paths.get("logtest."+sp2[8])).toFile().createNewFile();
                }
                BufferedReader br8 = Files.newBufferedReader(Paths.get("logtest."+sp2[8]));          //For First Line satellite
                while ((line = br8.readLine()) != null) {

                    c2cAlarm15.appendText(line+"\n");
                }
                //System.out.println(satid.getText()+" "+satid.getText().length());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    void switch2Scene1(ActionEvent event) throws IOException {
        Parent scene1Parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene1 = new Scene(scene1Parent);
        System.out.println("Switching scenes");
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene1);
        window.show();
    }

}
