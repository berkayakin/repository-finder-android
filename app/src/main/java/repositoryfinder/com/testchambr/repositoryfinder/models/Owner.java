package repositoryfinder.com.testchambr.repositoryfinder.models;

import com.google.gson.annotations.SerializedName;

/*
 * Coded by Berkay AKIN
 */

public class Owner {

    @SerializedName("login")
    private String Login;

    @SerializedName("id")
    private int Id;

    @SerializedName("avatar_url")
    private String AvatarURL;

    public String getLogin() {
        return Login;
    }

    public int getId() {
        return Id;
    }

    public String getAvatarURL() {
        return AvatarURL;
    }
}
