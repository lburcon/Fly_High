package com.example.yggdralisk.flyhighconference.BackEnd.ORMliteClasses;

import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Organiser;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yggdralisk on 19.03.16.
 */
@DatabaseTable(tableName = "organiser")
public class ORMOrganiser {
    @DatabaseField(id = true, generatedId = false)
    int id;

    @DatabaseField String name;
    @DatabaseField String title;
    @DatabaseField String email;
    @DatabaseField  String description;
    @DatabaseField  String image; //Url to image

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getTitle() {
        return title;
    }
    public String getEmail() {
        return email;
    }
    public String getDescription() {
        return description;
    }
    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ORMOrganiser() {
    }

    public ORMOrganiser(Organiser o)
    {
        this.id = o.getId();
        this.name = o.getName();
        this.email = o.getEmail();
        this.description = o.getDescription();
        this.image = o.getImage();
    }
}
