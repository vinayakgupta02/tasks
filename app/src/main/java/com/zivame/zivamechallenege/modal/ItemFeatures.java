package com.zivame.zivamechallenege.modal;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vinayak on 4/15/2018.
 */

public class ItemFeatures implements Serializable {

    private ArrayList<ItemDetails> values;

    public ArrayList<ItemDetails> getValues() {
        return values;
    }

    public void setValues(ArrayList<ItemDetails> values) {
        this.values = values;
    }

    public static class ItemDetails implements Serializable {

        private String name;
        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
