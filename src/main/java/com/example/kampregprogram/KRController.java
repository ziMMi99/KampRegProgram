package com.example.kampregprogram;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Arrays;

public class KRController {

    @FXML
    private Label kampRapportLabel;

    public void changeLabel(Game[] id){
        System.out.println(id[0].getId());

    }

}
