package repositoryfinder.com.testchambr.repositoryfinder.models;

import com.google.gson.annotations.SerializedName;

public class Repository {

    @SerializedName("id")
    private int Id;

    @SerializedName("name")
    private String Name;

    @SerializedName("full_name")
    private String FullName;

    @SerializedName("owner")
    private Owner ItemOwner;

    @SerializedName("description")
    private String Description;

    @SerializedName("size")
    private int Size;

    @SerializedName("has_wiki")
    private boolean HasWiki;

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getFullName() {
        return FullName;
    }

    public Owner getItemOwner() {
        return ItemOwner;
    }

    public int getSize() {
        return Size;
    }

    public boolean isHasWiki() {
        return HasWiki;
    }

    public String getDescription() {
        return Description;
    }
}