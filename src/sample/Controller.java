package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public class Controller {
    String configFile="camconfig.inp.mypc";
    //Path congFilePath= Paths.get(configArea+configFile);
    Path congFilePath= Paths.get(configFile);

    @FXML
    private TextArea c2cAlarm1;

    @FXML
    private Button refresh01;

    @FXML
    private Button ack01;

    @FXML
    private Hyperlink scene2link;

    @FXML
    private TextField satid;

    @FXML
    private ListView<String> listTable01;

    @FXML
    private TableView<Alarms> table01;

    @FXML
    private TableColumn<Alarms, String> tCol01;

    @FXML
    private TableColumn<Alarms, String> tCol02;


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
        String line = null, satids="xxx", dir="", exe4cam="";
        String LONGCLOG="", PLOT4DAY="";
        String sp1[]; int lineNo=0;
        try {
            BufferedReader br = Files.newBufferedReader(file);
            while ((line = br.readLine()) != null) {
                sp1 = line.split(" ");
                lineNo=lineNo+1;
                if (line.contains("Execute")) {
                    exe4cam=sp1[1];
                }
                if(line.length() >= 5 && sp1[0].length()==3 && line.contains(satid.getText())) {
                    sat = sp1[0];
                    dir = sp1[1];
                }
            }
            //System.out.println(lineNo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sat +" "+dir+" "+exe4cam+" "+lineNo;
    }

    ArrayList<String> arl01 = new ArrayList<>();

    @FXML
    void ShowAlarms(ActionEvent event) {
        ack01.setVisible(true);
        String defaultSat="";

        if (satid.getText().length()!=3) {
            defaultSat="xxx";
        } else {
            defaultSat=satid.getText();
        }

        String str=loadConfigData(congFilePath, defaultSat);
        if (defaultSat.length()==3) {
            String line, sp2[]; int rtp=1;
            System.out.println(str);
            sp2 = str.split(" ");
            System.out.println(sp2[0] +" "+sp2[1]);
            String sat=sp2[0], path=sp2[1];
            String file="CamLog."+sat.toUpperCase()+".mx2"+sat+"rtp"+rtp+".txt";
            System.out.println(file);
            c2cAlarm1.clear();
            c2cAlarm1.appendText(sat.toUpperCase()+"\n");
            String shellcmd=sp2[2];
            Path exepath= Paths.get(shellcmd);

            try {
                Process p = Runtime.getRuntime().exec(shellcmd);
                System.out.println("Running "+shellcmd+" execute");
                //BufferedReader br = Files.newBufferedReader(Paths.get(exepath.getParent()+"/"+"logtest"));
                if(!Files.exists(Paths.get("logtest."+sat))) {
                    (Paths.get("logtest."+sat)).toFile().createNewFile();
                }

                arl01.clear();
                BufferedReader br = Files.newBufferedReader(Paths.get("logtest."+sat));
                while ((line = br.readLine()) != null) {
                            //---------------------
                            String textAlarm="";
                            String split_01_Inside[];
                            split_01_Inside = line.split(" ");
                            //for (String element: split_01_Inside) {
                            //System.out.print(element);
                            for (int a1=2; a1<split_01_Inside.length; a1++) {
                            System.out.print(split_01_Inside[a1]+" ");
                            textAlarm= textAlarm + split_01_Inside[a1]+" ";
                            }
                            System.out.println("");
                            //---------------------
                    //if (arl01.get(arl01.size()-1)!=textAlarm)
                    arl01.add(textAlarm+"\n");
                    System.out.println(arl01.get(arl01.size()-1));
                    System.out.println(textAlarm +""+textAlarm.length());
                    c2cAlarm1.appendText(line+"\n");
                }

                System.out.println(satid.getText()+" "+satid.getText().length());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ObservableList observableList = FXCollections.observableArrayList();
            observableList.addAll(arl01);
            listTable01.setItems(observableList);

            tCol01 = new TableColumn("AlarmS");
            tCol01.setMinWidth(481);
            tCol01.setCellValueFactory(new PropertyValueFactory<>("alrms"));

            tCol02 = new TableColumn("Reps");
            tCol02.setMinWidth(158);
            tCol02.setCellValueFactory(new PropertyValueFactory<>("repetition"));

            //table01 = new TableView<>();
            table01.setItems(getAlarms());
            //table01.getColumns().setAll(tCol01);
            table01.getColumns().setAll(tCol01,tCol02);

            table01.getSelectionModel().selectedIndexProperty().addListener(observable -> System.out.printf("Indice sélectionné: %d", table01.getSelectionModel().getSelectedIndex()).println());
            System.out.println("Hello");

        }
    }
    @FXML
    void chaneSceneNow(ActionEvent event) throws IOException {
        Parent parentScene2 = FXMLLoader.load(getClass().getResource("AllCams.fxml"));
        Scene scene2 = new Scene(parentScene2);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }
    public ObservableList<Alarms> getAlarms() {
        ObservableList<Alarms> alarms1 = FXCollections.observableArrayList();
        String defaultSat="";
        if (satid.getText().length()!=3) {
            defaultSat="xxx";
        } else {
            defaultSat=satid.getText();
        }

        String str=loadConfigData(congFilePath, defaultSat);
        String line,line2, sp2[];
        sp2 = str.split(" ");
        String sat=sp2[0];
        String textAlarm, textAlarm2="random";
        int rptCnt, cnt=-1, newValCntr;
        try {
            BufferedReader br = Files.newBufferedReader(Paths.get("logtest_sorted."+sat));
            while ((line = br.readLine()) != null) {
                        //---------------------
                        textAlarm="";
                        newValCntr=0;
                        if(line.length()>2 && textAlarm2.length()>2) {
                            System.out.println("------1");
                            String split_01_Inside[];
                            split_01_Inside = line.split(" ");
                            if (!line.contains(textAlarm2.substring(1, textAlarm2.length() - 1))) {
                                for (int a1 = 0; a1 < split_01_Inside.length; a1++) {
                                    System.out.print(split_01_Inside[a1] + " ");
                                    textAlarm = textAlarm + split_01_Inside[a1] + " ";
                                }
                            textAlarm2=textAlarm;
                                newValCntr=1;
                            }
                            System.out.println();
                            rptCnt=0;
                            if (newValCntr==1) {
                                BufferedReader br2 = Files.newBufferedReader(Paths.get("logtest."+sat));
                                while ((line2 = br2.readLine()) != null && textAlarm2.length()>2) {
                                    if (line2.contains(textAlarm2.substring(1, textAlarm2.length() - 1))) {
                                        //System.out.println(line2);
                                        rptCnt = rptCnt + 1;
                                    }
                                }
                                    cnt=rptCnt;
                                        if (rptCnt==cnt) {
                                            alarms1.add(new Alarms(textAlarm,cnt));
                                            System.out.println("Repeated:"+rptCnt+" " + textAlarm + " " + textAlarm.length());
                                        }
                                System.out.println(textAlarm+": "+cnt+" times");
                            }
                            //---------------------
                        }
                                                     //Add all alarms over here, i.e, from textAlarm OR from Arl01
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //alarms1.add(new Alarms(""));                                                 //Add all alarms over here, i.e, from textAlarm OR from Arl01

        return alarms1;
    }


}
