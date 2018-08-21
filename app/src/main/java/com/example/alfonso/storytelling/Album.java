package com.example.alfonso.storytelling;

import android.os.Parcel;
import android.os.Parcelable;

public class Album implements Parcelable {


    private String nome;
    private int id;
    private String path;
    private int tipo;




    public Album() {
    }

    public Album(int id,String nome,  String path, int tipo) {
        this.nome = nome;
        this.id = id;
        this.path = path;
        this.tipo = tipo;


    }

    protected Album(Parcel in) {
        nome = in.readString();
        id = in.readInt();
        path = in.readString();
        tipo = in.readInt();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Album{" +
                "nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", id=" + id +
                ", path='" + path + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        parcel.writeInt(id);
        parcel.writeString(path);
        parcel.writeInt(tipo);
    }
}
