/*
 *  Linkficator - fixes specific URLs
 *  Copyright (C) 2014  Jan Zajaczkowski
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package linkficator;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author XZAJACZK
 */
public class Linkficator extends Application {
    Button paste,fix,options,about,reset,copy1,copy2;
    TextField input, output1, output2;
    
    final boolean DEFAULTmode = true;
    boolean mode = DEFAULTmode;
    @Override
    public void start(Stage primaryStage) { 
        //define toolbar buttons
        paste = new Button("Paste");
        fix = new Button("Fix");
        options = new Button("Options");
        about = new Button("About");
        //assign buttons to toolbar
        ToolBar commandBar = new ToolBar(paste, fix, options, about);
        
        //assign actions to toolbar buttons
        paste.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                paste();
            }
            
        });
        
        fix.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                if(mode==true){
                    fix();
                }else{
                    otherfix();
                }
            }
            
        });
        
        options.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                options();
            }
            
        });
        
        about.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                about();
            }
            
        });
        
        commandBar.setId("commandBar");
        
        
        HBox row1 = new HBox();
        Label l1 = new Label("URL to fix:  ");
        input = new TextField();
        Label s1 = new Label("  ");
        reset = new Button("Reset");
        row1.getChildren().addAll(l1,input,s1,reset);
        
        HBox row2 = new HBox();
        Label l2 = new Label("Fixed 'yes': ");
        output1 = new TextField();
        Label s2 = new Label("  ");
        copy1 = new Button("Copy");
        row2.getChildren().addAll(l2,output1,s2,copy1);
        
        HBox row3 = new HBox();
        Label l3 = new Label("Fixed 'no':  ");
        output2 = new TextField();
        Label s3 = new Label("  ");
        copy2 = new Button("Copy");
        row3.getChildren().addAll(l3,output2,s3,copy2);
        
        VBox ioBox = new VBox();
        ioBox.getChildren().addAll(row1,row2,row3);
        
        reset.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                reset();
            }
            
        });
        
        copy1.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                copy1();
            }
            
        });
        
        copy2.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                copy2();
            }
            
        });
        
        final Separator separator = new Separator();
        
        VBox top = new VBox();
        top.getChildren().addAll(commandBar,separator);
        
        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(ioBox);
        
        root.setPadding(new Insets(0,0,0,10));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                Linkficator.class.getResource(
                        "linkficator.css").toExternalForm());
        
        primaryStage.setTitle("Linkficator");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public void paste(){
        input.selectAll();
        input.paste();
        input.selectAll();
        input.requestFocus();
    }
    
    public void fix(){
        try {
            String text = input.getText();
            if("".equals(text)){
                output1.setText("URL cannot be blank");
            }
            else {
                String search1 = "?accountName=";
                String search2 = "&auditAccountName=";
                String search3 = "&activityCriteria";
                String search4 = "%20Yes%20";
                String search5 = "&refineSearchParms";
                String search6 = "&submitted=true";

                int s1 = text.lastIndexOf(search1);
                int s2 = text.indexOf(search2);
                int s3 = text.lastIndexOf(search2);
                int s4 = text.indexOf(search3);
                int s5 = text.indexOf(search4);
                int s6 = text.lastIndexOf(search4);
                int s7 = text.indexOf(search5);
                int s8 = text.lastIndexOf(search6);

                String part1 = text.substring(0, s1 + search1.length());
                String part2 = text.substring(s2, s3 + search2.length());
                String part3 = text.substring(s4, s5);
                String part4 = "%20Yes%20";
                String part4b = "%20No%20";
                String part5 = text.substring(s6, s7);
                String part6 = text.substring(s8, text.length());
                String part6b = text.substring(s8, text.length() - 4);
                String part7 = "false";
                
                String FixedText = part1 + part2 + part3 + part4 + part5 
                                 + part6;
                
                output1.setText(FixedText);
                
                String FixedText2 = part1 + part2 + part3 + part4b + part5
                                  + part6b + part7;
                
                output2.setText(FixedText2);
            }
            
            
        } catch (StringIndexOutOfBoundsException e){
            output1.setText("Error parsing link: "+e.getMessage().toString());

        }
    }
    
  public void otherfix(){
        try {
            String text = input.getText().toLowerCase();
            if("".equals(text)){
                output1.setText("URL cannot be blank");
            }
            else {
                String search1 = "?accountname=";
                String search2 = "&auditaccountname=";
                String search3 = "&contactfilter";

                int s1 = text.lastIndexOf(search1);
                int s2 = text.indexOf(search2);
                int s3 = text.lastIndexOf(search2);
                int s4 = text.indexOf(search3);

                String part1 = text.substring(0, s1 + search1.length());
                String part2 = text.substring(s2, s3 + search2.length());
                String part3 = text.substring(s4, text.length());
                
                String FixedText = part1 + part2 + part3;
                
                output1.setText(FixedText);
            }
            
            
        } catch (StringIndexOutOfBoundsException e){
            output1.setText("Error parsing link: "+e.getMessage().toString());

        }
    }
    
    public void options(){
        Label on = new Label("Select the type of link:\n");
        
        VBox Options = new VBox();
        
        final ToggleGroup radioOptions = new ToggleGroup();
        
        final RadioButton RSVP = new RadioButton();
        RSVP.setText("RSVP 'Yes' Link");
        RSVP.setToggleGroup(radioOptions);
           
        final RadioButton Other = new RadioButton();
        Other.setText("Folder Link");
        Other.setToggleGroup(radioOptions);
        if(mode==true){
            RSVP.setSelected(true);
        }else{
            Other.setSelected(true);
        }
        radioOptions.selectedToggleProperty().addListener(
                new ChangeListener<Toggle>(){

            @Override
            public void changed(
                    ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                mode = RSVP.isSelected();
            }
            
        });
        
        Options.getChildren().addAll(on,RSVP,Other);
        
        Button ok = new Button("OK");
        final Stage optionsStage = new Stage();
        
        BorderPane opane = new BorderPane();
        BorderPane.setAlignment(ok, Pos.CENTER);
        
        opane.setCenter(Options);
        opane.setBottom(ok);
        
        opane.setPadding(new Insets(20,20,20,20));
        
        Scene oscene = new Scene(opane);
        oscene.getStylesheets().add(
                Linkficator.class.getResource(
                        "linkficator.css").toExternalForm());
        
        optionsStage.setTitle("Options");
        optionsStage.setScene(oscene);
        optionsStage.sizeToScene();
        optionsStage.setResizable(false);
        optionsStage.show();
        
        ok.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                optionsStage.hide();
            }
            
        });
    }
    
    public void about(){    
        Label a = new Label("Linkficator - fixes specific URLs\n" +
                    "Copyright © 2014 Jan Zajaczkowski\n" +
                    "\n" +
                    "This program is free software: you can "
                        + "redistribute it and/or modify\n" +
                    "it under the terms of the GNU General Public "
                        + "License as published by\n" +
                    "the Free Software Foundation, either version 3 "
                        + "of the License, or\n" +
                    "(at your option) any later version.\n" +
                    "\n" +
                    "This program is distributed in the hope that it "
                        + "will be useful,\n" +
                    "but WITHOUT ANY WARRANTY; without even the "
                        + "implied warranty of\n" +
                    "MERCHANTABILITY or FITNESS FOR A PARTICULAR "
                        + "PURPOSE.  See the\n" +
                    "GNU General Public License for more details.\n" +
                    "\n" +
                    "You should have received a copy of the GNU "
                        + "General Public License\n" +
                    "along with this program.  If not, see "
                        + "<http://www.gnu.org/licenses/>."); 
        
        Button ok = new Button("I Agree");
        Button cancel = new Button("I Disagree");
        final Stage aboutStage = new Stage();
        
        HBox acontrols = new HBox();
        
        acontrols.getChildren().addAll(ok,cancel);
        acontrols.setAlignment(Pos.CENTER);
        
        BorderPane apane = new BorderPane();
        
        apane.setCenter(a);
        apane.setBottom(acontrols);
        
        apane.setPadding(new Insets(10,10,10,10));
        
        Scene ascene = new Scene(apane);
        ascene.getStylesheets().add(
                Linkficator.class.getResource(
                        "linkficator.css").toExternalForm());
        
        aboutStage.setTitle("About");
        aboutStage.setScene(ascene);
        aboutStage.sizeToScene();
        aboutStage.setResizable(false);
        aboutStage.show();
        
        ok.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                aboutStage.close();
            }
            
        });
        
        cancel.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                //
                
            }
            
        });
    }
    
    public void reset(){
        input.setText("");
        output1.setText("");
        output2.setText("");
        input.requestFocus();
    }
    
    public void copy1(){
        output1.selectAll();
        output1.copy();
    }
    
    public void copy2(){
        output2.selectAll();
        output2.copy();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
